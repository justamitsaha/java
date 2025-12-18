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
//    private final String DEFAULT_FILE_NAME_1 = "SGprelogin.rule.json";
//    private final String DEFAULT_FILE_NAME_2 = "IPBprelogin.rule.json";

    private final String DEFAULT_FILE_NAME_1 = "SG_rule.json";
    private final String DEFAULT_FILE_NAME_2 = "IPB.rule.json";

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
     */
    public void execute(String... args) throws Exception {
        csvData.clear();

        String fileName1;
        String fileName2;

        if (args != null && args.length >= 2) {
            fileName1 = args[0];
            fileName2 = args[1];
            System.out.println("RuleDiff using arguments: Files=[" + fileName1 + ", " + fileName2 + "]");
        } else {
            fileName1 = DEFAULT_FILE_NAME_1;
            fileName2 = DEFAULT_FILE_NAME_2;
            System.out.println("RuleDiff using fallback: Resource files=[" + fileName1 + ", " + fileName2 + "]");
        }

        JsonNode left = loadJson(fileName1);
        JsonNode right = loadJson(fileName2);

        csvData.add(new String[]{
                "Category", "Path", "Feature", fileName1, fileName2, "Details"
        });

        boolean useFeatureBoundary =
                ((left != null && left.has("Feature")) || (right != null && right.has("Feature"))) &&
                        ((left != null && left.get("Feature") != null && left.get("Feature").isObject()) ||
                                (right != null && right.get("Feature") != null && right.get("Feature").isObject()));

        if (useFeatureBoundary) {
            compareBoundaryObject(left.get("Feature"), right.get("Feature"), "Feature", fileName1, fileName2);
        } else {
            compareBoundaryObject(left, right, "$", fileName1, fileName2);
        }

        Path out = Path.of("json_comparison_report.csv");
        writeCsv(out);
        System.out.println("✅ CSV Report generated: " + out.toAbsolutePath());
    }

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

    private void emitAndDescend(String path, JsonNode lNode, JsonNode rNode, String f1Name, String f2Name) {
        if (lNode == null && rNode != null) {
            csvData.add(row("Missing", path, featureName(path), "", pretty(rNode), "Present only in " + f2Name));
            return;
        }
        if (lNode != null && rNode == null) {
            csvData.add(row("Missing", path, featureName(path), pretty(lNode), "", "Present only in " + f1Name));
            return;
        }
        if (lNode == null && rNode == null) return;

        if (lNode.getNodeType() != rNode.getNodeType()) {
            csvData.add(row("Modified", path, featureName(path), pretty(lNode), pretty(rNode), "Type mismatch"));
            return;
        }

        if (lNode.isObject() && rNode.isObject()) {
            csvData.add(row("Present", path, featureName(path), "{}", "{}", ""));
            Set<String> keys = new TreeSet<>();
            lNode.fieldNames().forEachRemaining(keys::add);
            rNode.fieldNames().forEachRemaining(keys::add);
            for (String key : keys) {
                emitAndStopAtLeaves(path + "." + key, lNode.get(key), rNode.get(key), f1Name, f2Name);
            }
            return;
        }

        if (lNode.isArray() && rNode.isArray()) {
            handleArrayComparison(path, (ArrayNode) lNode, (ArrayNode) rNode, f1Name, f2Name);
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

    private void emitAndStopAtLeaves(String path, JsonNode lNode, JsonNode rNode, String f1Name, String f2Name) {
        if (lNode == null && rNode != null) {
            csvData.add(row("Missing", path, featureName(path), "", pretty(rNode), "Present only in " + f2Name));
            return;
        }
        if (lNode != null && rNode == null) {
            csvData.add(row("Missing", path, featureName(path), pretty(lNode), "", "Present only in " + f1Name));
            return;
        }
        if (lNode == null && rNode == null) return;

        if (lNode.getNodeType() != rNode.getNodeType()) {
            csvData.add(row("Modified", path, featureName(path), pretty(lNode), pretty(rNode), "Type mismatch"));
            return;
        }

        if (lNode.isObject() && rNode.isObject()) {
            csvData.add(row("Present", path, featureName(path), "{}", "{}", ""));
            Set<String> keys = new TreeSet<>();
            lNode.fieldNames().forEachRemaining(keys::add);
            rNode.fieldNames().forEachRemaining(keys::add);
            for (String key : keys) {
                emitAndStopAtLeaves(path + "." + key, lNode.get(key), rNode.get(key), f1Name, f2Name);
            }
            return;
        }

        if (lNode.isArray() && rNode.isArray()) {
            handleArrayComparison(path, (ArrayNode) lNode, (ArrayNode) rNode, f1Name, f2Name);
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

    /**
     * Specialized handler for arrays.
     * If array contains objects (like demographicRule.rules), we drill down.
     * If array contains scalars (like simple lists), we treat it as a leaf.
     */
    private void handleArrayComparison(String path, ArrayNode la, ArrayNode ra, String f1Name, String f2Name) {
        boolean lScalars = allScalars(la);
        boolean rScalars = allScalars(ra);

        if (lScalars && rScalars) {
            // Leaf logic for scalar arrays
            Set<String> ls = scalarSet(la);
            Set<String> rs = scalarSet(ra);
            if (ls.equals(rs)) {
                csvData.add(row("Present", path, featureName(path), pretty(la), pretty(ra), ""));
            } else {
                csvData.add(row("Modified", path, featureName(path), pretty(la), pretty(ra), "Scalar array set differs"));
            }
        } else {
            // Complex logic for arrays of objects
            csvData.add(row("Present", path, featureName(path), "[]", "[]", ""));

            Alignment align = discoverKey(la, ra);
            if (align.type == AlignmentType.KEY) {
                // Key-based alignment for objects in arrays
                Map<String, JsonNode> am = new HashMap<>();
                Map<String, JsonNode> bm = new HashMap<>();
                la.forEach(n -> am.put(n.get(align.key).asText(), n));
                ra.forEach(n -> bm.put(n.get(align.key).asText(), n));

                Set<String> allKeys = new TreeSet<>(am.keySet());
                allKeys.addAll(bm.keySet());

                for (String k : allKeys) {
                    String itemPath = path + "[" + align.key + "=" + k + "]";
                    emitAndStopAtLeaves(itemPath, am.get(k), bm.get(k), f1Name, f2Name);
                }
            } else {
                // Index-based alignment if no identifying key found
                int max = Math.max(la.size(), ra.size());
                for (int i = 0; i < max; i++) {
                    String itemPath = path + "[" + i + "]";
                    emitAndStopAtLeaves(itemPath, la.get(i), ra.get(i), f1Name, f2Name);
                }
            }
        }
    }

    private boolean scalarEquals(ValueNode a, ValueNode b) {
        if (a.isTextual() && b.isTextual()) {
            return a.asText().equalsIgnoreCase(b.asText());
        }
        if (a.isNumber() && b.isNumber()) {
            return a.numberValue().equals(b.numberValue());
        }
        if (a.isBoolean() && b.isBoolean()) {
            return a.booleanValue() == b.booleanValue();
        }
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
            if (v.isTextual()) out.add(v.asText().toLowerCase());
            else if (v.isNumber()) out.add(String.valueOf(v.numberValue()));
            else if (v.isBoolean()) out.add(String.valueOf(v.booleanValue()));
            else if (v.isNull()) out.add("null");
            else out.add(v.toString());
        }
        return out;
    }

    private Alignment discoverKey(ArrayNode a, ArrayNode b) {
        List<String> candidates = Arrays.asList("id", "code", "name", "demographic_PhoneNumberCountryCode");
        for (String k : candidates) {
            if (keyExistsInSome(a, k) || keyExistsInSome(b, k)) {
                return new Alignment(AlignmentType.KEY, k);
            }
        }
        return new Alignment(AlignmentType.INDEX, null);
    }

    private boolean keyExistsInSome(ArrayNode arr, String key) {
        for (JsonNode n : arr) {
            if (n.isObject() && n.has(key)) return true;
        }
        return false;
    }

    private JsonNode loadJson(String filename) throws IOException {
        try {
            Path filePath = Path.of(filename);
            if (Files.exists(filePath)) {
                return mapper.readTree(Files.newInputStream(filePath));
            }
        } catch (Exception ignored) {}

        ClassPathResource res = new ClassPathResource(filename);
        if (!res.exists()) throw new IOException("Resource not found: " + filename);
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
            return v.toString();
        }
        try {
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
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < row.length; i++) {
            String cell = row[i] == null ? "" : row[i];
            sb.append("\"").append(cell.replace("\"", "\"\"")).append("\"");
            if (i < row.length - 1) sb.append(",");
        }
        return sb.toString();
    }
}