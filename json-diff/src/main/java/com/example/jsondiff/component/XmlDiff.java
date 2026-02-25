package com.example.jsondiff.component;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

@Component
public class XmlDiff {

    private final List<String[]> csvData = new ArrayList<>();
    private final String DEFAULT_FILE_NAME_1 = "rule1.xml";
    private final String DEFAULT_FILE_NAME_2 = "rule2.xml";

    public void execute(String... args) throws Exception {
        csvData.clear();
        String f1 = (args != null && args.length >= 2) ? args[0] : DEFAULT_FILE_NAME_1;
        String f2 = (args != null && args.length >= 2) ? args[1] : DEFAULT_FILE_NAME_2;

        Document doc1 = loadXml(f1);
        Document doc2 = loadXml(f2);

        csvData.add(new String[]{"Category", "Path", f1, f2, "Details"});

        compareNodes(doc1.getDocumentElement(), doc2.getDocumentElement(), "$", f1, f2);

        Path out = Path.of("xml_comparison_report.csv");
        writeCsv(out);
        System.out.println("✅ XML CSV Report generated: " + out.toAbsolutePath());
    }

    private void compareNodes(Node left, Node right, String path, String f1, String f2) {
        if (left == null && right == null) return;

        String currentPath = path + "/" + (left != null ? left.getNodeName() : right.getNodeName());

        // 1. Check for missing nodes
        if (left == null || right == null) {
            String cat = "Missing";
            String v1 = left != null ? left.getTextContent() : "";
            String v2 = right != null ? right.getTextContent() : "";
            String detail = left == null ? "Present only in " + f2 : "Present only in " + f1;
            csvData.add(new String[]{cat, currentPath, v1, v2, detail});
            return;
        }

        // 2. Compare Attributes (Treated like JSON keys)
        compareAttributes(left, right, currentPath, f1, f2);

        // 3. Compare Values (Text content if it's a leaf node)
        if (!hasChildElements(left) && !hasChildElements(right)) {
            String v1 = left.getTextContent().trim();
            String v2 = right.getTextContent().trim();
            if (!v1.equals(v2)) {
                csvData.add(new String[]{"Modified", currentPath, v1, v2, "Value mismatch"});
            } else {
                csvData.add(new String[]{"Present", currentPath, v1, v2, ""});
            }
        }

        // 4. Recurse into children
        compareChildren(left, right, currentPath, f1, f2);
    }

    private void compareAttributes(Node left, Node right, String path, String f1, String f2) {
        NamedNodeMap leftAttrs = left.getAttributes();
        NamedNodeMap rightAttrs = right.getAttributes();
        Set<String> allAttrNames = new TreeSet<>();

        for (int i = 0; i < leftAttrs.getLength(); i++) allAttrNames.add(leftAttrs.item(i).getNodeName());
        for (int i = 0; i < rightAttrs.getLength(); i++) allAttrNames.add(rightAttrs.item(i).getNodeName());

        for (String attr : allAttrNames) {
            Node lAttr = leftAttrs.getNamedItem(attr);
            Node rAttr = rightAttrs.getNamedItem(attr);
            String attrPath = path + "[@" + attr + "]";

            if (lAttr == null) {
                csvData.add(new String[]{"Missing", attrPath, "", rAttr.getNodeValue(), "Attr missing in " + f1});
            } else if (rAttr == null) {
                csvData.add(new String[]{"Missing", attrPath, lAttr.getNodeValue(), "", "Attr missing in " + f2});
            } else if (!lAttr.getNodeValue().equals(rAttr.getNodeValue())) {
                csvData.add(new String[]{"Modified", attrPath, lAttr.getNodeValue(), rAttr.getNodeValue(), "Attr value mismatch"});
            }
        }
    }

    private void compareChildren(Node left, Node right, String path, String f1, String f2) {
        Map<String, List<Node>> leftChildren = groupChildren(left);
        Map<String, List<Node>> rightChildren = groupChildren(right);
        Set<String> allTags = new TreeSet<>(leftChildren.keySet());
        allTags.addAll(rightChildren.keySet());

        for (String tag : allTags) {
            List<Node> lList = leftChildren.getOrDefault(tag, Collections.emptyList());
            List<Node> rList = rightChildren.getOrDefault(tag, Collections.emptyList());

            int max = Math.max(lList.size(), rList.size());
            for (int i = 0; i < max; i++) {
                Node l = i < lList.size() ? lList.get(i) : null;
                Node r = i < rList.size() ? rList.get(i) : null;
                // If there are multiple children of same tag, we use index [i] similar to JSON arrays
                String childPath = path + "/" + tag + (max > 1 ? "[" + i + "]" : "");
                compareNodes(l, r, path, f1, f2); // Note: path updated inside compareNodes
            }
        }
    }

    private Map<String, List<Node>> groupChildren(Node node) {
        Map<String, List<Node>> map = new LinkedHashMap<>();
        NodeList children = node.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            if (child.getNodeType() == Node.ELEMENT_NODE) {
                map.computeIfAbsent(child.getNodeName(), k -> new ArrayList<>()).add(child);
            }
        }
        return map;
    }

    private boolean hasChildElements(Node node) {
        NodeList nl = node.getChildNodes();
        for (int i = 0; i < nl.getLength(); i++) {
            if (nl.item(i).getNodeType() == Node.ELEMENT_NODE) return true;
        }
        return false;
    }

    private Document loadXml(String filename) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        InputStream is;
        Path filePath = Path.of(filename);
        if (Files.exists(filePath)) {
            is = Files.newInputStream(filePath);
        } else {
            is = new ClassPathResource(filename).getInputStream();
        }
        return factory.newDocumentBuilder().parse(is);
    }

    private void writeCsv(Path out) throws IOException {
        try (var writer = Files.newBufferedWriter(out, StandardCharsets.UTF_8)) {
            for (String[] row : csvData) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < row.length; i++) {
                    sb.append("\"").append(row[i].replace("\"", "\"\"")).append("\"");
                    if (i < row.length - 1) sb.append(",");
                }
                writer.write(sb.toString() + "\n");
            }
        }
    }
}