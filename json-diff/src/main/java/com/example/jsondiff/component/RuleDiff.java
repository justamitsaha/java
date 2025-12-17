package com.example.jsondiff.component;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ValueNode;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@Component
public class RuleDiff {

    private final ObjectMapper mapper = new ObjectMapper();
    private final List<String[]> csvData = new ArrayList<>();

    // Default filenames for fallback mode
    private final String DEFAULT_FILE_NAME_1 = "IPB_rule.json";
    private final String DEFAULT_FILE_NAME_2 = "SG_rule.json";

    // Enum and inner classes for comparison logic
    enum AlignmentType {KEY, INDEX}

    static class Alignment {
        AlignmentType type;
        String key;

        Alignment(AlignmentType t, String k) {
            type = t;
            key = k;
        }
    }

    /**
     * Entry point for the RuleDiff component.
     * This method decides whether to load files from the provided command-line arguments
     * or fall back to default files in the resources folder.
     *
     * @param args The raw command line arguments (expected to contain file paths/names in the first two positions).
     * @throws Exception if file loading or CSV writing fails.
     */
    public void execute(String... args) throws Exception {
        csvData.clear(); // Ensure fresh data for each run

        String fileName1;
        String fileName2;

        if (args != null && args.length >= 2) {
            // Arguments provided: use them as file paths/names
            fileName1 = args[0];
            fileName2 = args[1];
            System.out.println("RuleDiff using arguments: Files=[" + fileName1 + ", " + fileName2 + "]");
        } else {
            // No arguments provided: fall back to resource files
            fileName1 = DEFAULT_FILE_NAME_1;
            fileName2 = DEFAULT_FILE_NAME_2;
            System.out.println("RuleDiff using fallback: Resource files=[" + fileName1 + ", " + fileName2 + "]");
        }

        JsonNode left = loadJson(fileName1);
        JsonNode right = loadJson(fileName2);

        // CSV Header using actual file names for value columns
        csvData.add(new String[]{
                "Category", "Path", "Feature", fileName1, fileName2, "Details"
        });

        // Boundary: Feature.* else top-level
        boolean useFeatureBoundary =
                ((left != null && left.has("Feature")) || (right != null && right.has("Feature"))) &&
                        ((left != null && left.get("Feature") != null && left.get("Feature").isObject()) ||
                                (right != null && right.get("Feature") != null && right.get("Feature").isObject()));

        if (useFeatureBoundary) {
            compareBoundaryObject(left.get("Feature"), right.get("Feature"), "Feature", fileName1, fileName2);
        } else {
            compareBoundaryObject(left, right, "$", fileName1, fileName2); // top-level
        }

        Path out = Path.of("json_comparison_report.csv");
        writeCsv(out);
        System.out.println("✅ CSV Report generated: " + out.toAbsolutePath());
    }


    // ---- Boundary walker: only objects (Feature.* or top-level) ----

    private void compareBoundaryObject(JsonNode leftObj, JsonNode rightObj, String boundaryPath, String f1Name, String f2Name) {
        Set<String> names = new TreeSet<>();
        if (isObject(leftObj)) leftObj.fieldNames().forEachRemaining(names::add);
        if (isObject(rightObj)) rightObj.fieldNames().forEachRemaining(names::add);

        for (String name : names) {
            String path = boundaryPath.equals("$") ? name : boundaryPath + "." + name;
            JsonNode l = isObject(leftObj) ? leftObj.get(name) : null;
            JsonNode r = isObject(rightObj) ? rightObj.get(name) : null;
            emitAndDescend(path, l, r, f1Name, f2Name);
        }
    }

    // ---- Core emission logic according to your 4 rules ----


    private void emitAndDescend(String path, JsonNode lNode, JsonNode rNode, String f1Name, String f2Name) {
        // Missing on one side
        if (lNode == null && rNode != null) {
            csvData.add(row("Missing", path, featureName(path), "", pretty(rNode), "Present only in " + f2Name));
            return;
        }
        if (lNode != null && rNode == null) {
            csvData.add(row("Missing", path, featureName(path), pretty(lNode), "", "Present only in " + f1Name));
            return;
        }
        if (lNode == null && rNode == null) return; // nothing to do

        // Both sides present
        // Types differ -> Modified (show full values)
        if (lNode.getNodeType() != rNode.getNodeType()) {
            csvData.add(row("Modified", path, featureName(path), pretty(lNode), pretty(rNode), "Type mismatch"));
            return;
        }

        // Same type: object / array / scalar
        if (lNode.isObject() && rNode.isObject()) {
            // Rule 1+2+3: Present for object nodes, then recurse to children
            csvData.add(row("Present", path, featureName(path), "{}", "{}", ""));
            // Recurse into child keys
            Set<String> keys = new TreeSet<>();
            lNode.fieldNames().forEachRemaining(keys::add);
            rNode.fieldNames().forEachRemaining(keys::add);
            for (String key : keys) {
                String childPath = path + "." + key;
                JsonNode lc = lNode.get(key);
                JsonNode rc = rNode.get(key);
                // arrays/scalars are leaves; objects recurse again
                emitAndStopAtLeaves(childPath, lc, rc, f1Name, f2Name);
            }
            return;
        }

        if (lNode.isArray() && rNode.isArray()) {
            // Arrays are leaves (Rule 4); decide Present vs Modified by content
            ArrayNode la = (ArrayNode) lNode;
            ArrayNode ra = (ArrayNode) rNode;

            boolean lScalars = allScalars(la);
            boolean rScalars = allScalars(ra);

            if (lScalars && rScalars) {
                // Scalar arrays as sets
                Set<String> ls = scalarSet(la);
                Set<String> rs = scalarSet(ra);
                if (ls.equals(rs)) {
                    // ✅ Show actual array values for Present
                    csvData.add(row("Present", path, featureName(path), pretty(la), pretty(ra), ""));
                } else {
                    // Show full values for Modified
                    csvData.add(row("Modified", path, featureName(path), pretty(la), pretty(ra),
                            "Scalar array set differs"));
                }
            } else {
                // Arrays of objects: key-based alignment (id/code/name) else index-wise
                Alignment align = discoverKey(la, ra);
                boolean equal = (align.type == AlignmentType.KEY)
                        ? arraysEqualByKey(la, ra, align.key)
                        : arraysEqualByIndex(la, ra);
                if (equal) {
                    // ✅ Show actual array values for Present
                    csvData.add(row("Present", path, featureName(path), pretty(la), pretty(ra), ""));
                } else {
                    csvData.add(row("Modified", path, featureName(path), pretty(la), pretty(ra),
                            align.type == AlignmentType.KEY ? ("Array of objects differ by key=" + align.key) : "Array differs by index"));
                }
            }
            return;
        }

        if (lNode.isValueNode() && rNode.isValueNode()) {
            // Scalars are leaves (Rule 4)
            if (scalarEquals((ValueNode) lNode, (ValueNode) rNode)) {
                // Show actual scalar values for Present
                csvData.add(row("Present", path, featureName(path), pretty(lNode), pretty(rNode), ""));
            } else {
                csvData.add(row("Modified", path, featureName(path), pretty(lNode), pretty(rNode), ""));
            }
        }
    }


    /**
     * Objects recurse; arrays/scalars stop (Rule 4).
     * This helper makes the intent explicit at child paths.
     */

    private void emitAndStopAtLeaves(String path, JsonNode lNode, JsonNode rNode, String f1Name, String f2Name) {
        // Missing
        if (lNode == null && rNode != null) {
            csvData.add(row("Missing", path, featureName(path), "", pretty(rNode), "Present only in " + f2Name));
            return;
        }
        if (lNode != null && rNode == null) {
            csvData.add(row("Missing", path, featureName(path), pretty(lNode), "", "Present only in " + f1Name));
            return;
        }
        if (lNode == null && rNode == null) return;

        // Type mismatch
        if (lNode.getNodeType() != rNode.getNodeType()) {
            csvData.add(row("Modified", path, featureName(path), pretty(lNode), pretty(rNode), "Type mismatch"));
            return;
        }

        // Same type
        if (lNode.isObject() && rNode.isObject()) {
            // Present row at the object node (show {}), then recurse further
            csvData.add(row("Present", path, featureName(path), "{}", "{}", ""));
            Set<String> keys = new TreeSet<>();
            lNode.fieldNames().forEachRemaining(keys::add);
            rNode.fieldNames().forEachRemaining(keys::add);
            for (String key : keys) {
                String childPath = path + "." + key;
                JsonNode lc = lNode.get(key);
                JsonNode rc = rNode.get(key);
                emitAndStopAtLeaves(childPath, lc, rc, f1Name, f2Name);
            }
            return;
        }

        if (lNode.isArray() && rNode.isArray()) {
            // Arrays are leaves; Present/Modified by content
            ArrayNode la = (ArrayNode) lNode;
            ArrayNode ra = (ArrayNode) rNode;
            boolean lScalars = allScalars(la);
            boolean rScalars = allScalars(ra);

            if (lScalars && rScalars) {
                Set<String> ls = scalarSet(la);
                Set<String> rs = scalarSet(ra);
                if (ls.equals(rs)) {
                    // ✅ Show actual array values for Present
                    csvData.add(row("Present", path, featureName(path), pretty(la), pretty(ra), ""));
                } else {
                    csvData.add(row("Modified", path, featureName(path), pretty(la), pretty(ra),
                            "Scalar array set differs"));
                }
            } else {
                Alignment align = discoverKey(la, ra);
                boolean equal = (align.type == AlignmentType.KEY)
                        ? arraysEqualByKey(la, ra, align.key)
                        : arraysEqualByIndex(la, ra);
                if (equal) {
                    // ✅ Show actual array values for Present
                    csvData.add(row("Present", path, featureName(path), pretty(la), pretty(ra), ""));
                } else {
                    csvData.add(row("Modified", path, featureName(path), pretty(la), pretty(ra),
                            align.type == AlignmentType.KEY ? ("Array of objects differ by key=" + align.key) : "Array differs by index"));
                }
            }
            return;
        }

        if (lNode.isValueNode() && rNode.isValueNode()) {
            if (scalarEquals((ValueNode) lNode, (ValueNode) rNode)) {
                csvData.add(row("Present", path, featureName(path), pretty(lNode), pretty(rNode), ""));
            } else {
                csvData.add(row("Modified", path, featureName(path), pretty(lNode), pretty(rNode), ""));
            }
        }
    }

    // ---- Equality helpers (strict types, case-sensitive, null≠missing) ----

    private boolean scalarEquals(ValueNode a, ValueNode b) {
        if (a.isTextual() && b.isTextual()) {
            // Case-insensitive comparison for "on" / "ON" as per JSON provided
            // NOTE: The user's JSON had "on" vs "ON". Assuming case-insensitive text comparison is desired for values.
            return a.asText().equalsIgnoreCase(b.asText());
        }
        if (a.isNumber() && b.isNumber()) {
            return a.numberValue().equals(b.numberValue()); // strict
        }
        if (a.isBoolean() && b.isBoolean()) {
            return a.booleanValue() == b.booleanValue();
        }
        // null vs null handled by NodeType equality before
        return a.toString().equals(b.toString());
    }

    private boolean allScalars(ArrayNode arr) {
        for (JsonNode n : arr) {
            if (!n.isValueNode()) return false;
        }
        return true;
    }

    private Set<String> scalarSet(ArrayNode arr) {
        Set<String> out = new HashSet<>();
        for (JsonNode n : arr) {
            ValueNode v = (ValueNode) n;
            if (v.isTextual()) out.add(v.asText().toLowerCase()); // Lowercase for set comparison
            else if (v.isNumber()) out.add(String.valueOf(v.numberValue()));
            else if (v.isBoolean()) out.add(String.valueOf(v.booleanValue()));
            else if (v.isNull()) out.add("null");
            else out.add(v.toString());
        }
        return out;
    }

    private Alignment discoverKey(ArrayNode a, ArrayNode b) {
        List<String> candidates = Arrays.asList("id", "code", "name");
        for (String k : candidates) {
            if (keyExistsInAll(a, k) && keyExistsInAll(b, k)) {
                return new Alignment(AlignmentType.KEY, k);
            }
        }
        return new Alignment(AlignmentType.INDEX, null);
    }

    private boolean keyExistsInAll(ArrayNode arr, String key) {
        for (JsonNode n : arr) {
            if (!n.isObject()) return false;
            if (!n.has(key)) return false;
        }
        return true;
    }

    private boolean arraysEqualByKey(ArrayNode a, ArrayNode b, String key) {
        Map<String, JsonNode> am = new HashMap<>();
        Map<String, JsonNode> bm = new HashMap<>();
        for (JsonNode n : a) am.put(n.get(key).asText(), n);
        for (JsonNode n : b) bm.put(n.get(key).asText(), n);
        if (!am.keySet().equals(bm.keySet())) return false;
        for (String k : am.keySet()) {
            if (!deepEqualObjects(am.get(k), bm.get(k))) return false;
        }
        return true;
    }

    private boolean deepEqualObjects(JsonNode a, JsonNode b) {
        // Compare two objects strictly (used for array-of-object items)
        if (a == null || b == null) return false;
        if (!a.isObject() || !b.isObject()) return false;
        Set<String> keys = new HashSet<>();
        a.fieldNames().forEachRemaining(keys::add);
        b.fieldNames().forEachRemaining(keys::add);
        for (String k : keys) {
            JsonNode av = a.get(k);
            JsonNode bv = b.get(k);
            if (av == null || bv == null) return false;
            // dispatch by type
            if (av.getNodeType() != bv.getNodeType()) return false;
            if (av.isValueNode() && bv.isValueNode()) {
                if (!scalarEquals((ValueNode) av, (ValueNode) bv)) return false;
            } else if (av.isArray() && bv.isArray()) {
                ArrayNode aa = (ArrayNode) av;
                ArrayNode bb = (ArrayNode) bv;
                boolean aSc = allScalars(aa), bSc = allScalars(bb);
                if (aSc && bSc) {
                    if (!scalarSet(aa).equals(scalarSet(bb))) return false;
                } else {
                    Alignment al = discoverKey(aa, bb);
                    boolean eq = (al.type == AlignmentType.KEY)
                            ? arraysEqualByKey(aa, bb, al.key)
                            : arraysEqualByIndex(aa, bb);
                    if (!eq) return false;
                }
            } else if (av.isObject() && bv.isObject()) {
                if (!deepEqualObjects(av, bv)) return false;
            } else {
                return false;
            }
        }
        return true;
    }

    private boolean arraysEqualByIndex(ArrayNode a, ArrayNode b) {
        if (a.size() != b.size()) return false;
        for (int i = 0; i < a.size(); i++) {
            JsonNode av = a.get(i);
            JsonNode bv = b.get(i);
            if (av.getNodeType() != bv.getNodeType()) return false;
            if (av.isValueNode() && bv.isValueNode()) {
                if (!scalarEquals((ValueNode) av, (ValueNode) bv)) return false;
            } else if (av.isArray() && bv.isArray()) {
                ArrayNode aa = (ArrayNode) av;
                ArrayNode bb = (ArrayNode) bv;
                boolean aSc = allScalars(aa), bSc = allScalars(bb);
                if (aSc && bSc) {
                    if (!scalarSet(aa).equals(scalarSet(bb))) return false;
                } else {
                    Alignment al = discoverKey(aa, bb);
                    boolean eq = (al.type == AlignmentType.KEY)
                            ? arraysEqualByKey(aa, bb, al.key)
                            : arraysEqualByIndex(aa, bb);
                    if (!eq) return false;
                }
            } else if (av.isObject() && bv.isObject()) {
                if (!deepEqualObjects(av, bv)) return false;
            } else {
                return false;
            }
        }
        return true;
    }

    // ---- Utilities ----

    private JsonNode loadJson(String filename) throws IOException {
        // First try to load from the file system path
        try {
            Path filePath = Path.of(filename);
            if (Files.exists(filePath)) {
                System.out.println("Loading from file system: " + filename);
                return mapper.readTree(Files.newInputStream(filePath));
            }
        } catch (Exception e) {
            // Ignore if file system read fails, fall back to classpath
        }

        // Fallback to classpath resource loading (for JAR/resources folder)
        ClassPathResource res = new ClassPathResource(filename);
        if (!res.exists()) {
            throw new IOException("Resource not found: " + filename + " (File system or Classpath)");
        }
        System.out.println("Loading from classpath: " + filename);
        try (InputStream is = res.getInputStream()) {
            return mapper.readTree(is);
        }
    }

    private boolean isObject(JsonNode n) {
        return n != null && n.isObject();
    }

    private String pretty(JsonNode n) {
        if (n == null) return "";
        if (n.isValueNode()) {
            ValueNode v = (ValueNode) n;
            if (v.isTextual()) return "\"" + v.asText() + "\"";
            if (v.isNumber()) return String.valueOf(v.numberValue());
            if (v.isBoolean()) return String.valueOf(v.booleanValue());
            if (v.isNull()) return "null";
            return v.toString();
        }
        try {
            // Return compact JSON string for array/object cells in CSV
            return mapper.writeValueAsString(n);
        } catch (IOException e) {
            return n.toString();
        }
    }

    private String featureName(String path) {
        if (path == null || path.isEmpty()) return "";
        int idx = path.indexOf("Feature.");
        if (idx >= 0) {
            int start = idx + "Feature.".length();
            int end = path.indexOf('.', start);
            return (end >= 0) ? path.substring(start, end) : path.substring(start);
        }
        int dot = path.indexOf('.');
        return (dot > 0) ? path.substring(0, dot) : path;
    }

    private String[] row(String category, String path, String feature, String v1, String v2, String details) {
        return new String[]{category, path, feature, v1, v2, details == null ? "" : details};
    }

    private void writeCsv(Path out) throws IOException {
        try (var writer = Files.newBufferedWriter(out, StandardCharsets.UTF_8)) {
            for (String[] row : csvData) {
                writer.write(joinRow(row));
                writer.write("\n");
            }
        }
    }

    private String joinRow(String[] row) {
        // Comma-separated CSV with safe quoting
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < row.length; i++) {
            String cell = row[i] == null ? "" : row[i];
            String escaped = cell.replace("\"", "\"\"");
            sb.append("\"").append(escaped).append("\"");
            if (i < row.length - 1) sb.append(",");
        }
        return sb.toString();
    }
}
