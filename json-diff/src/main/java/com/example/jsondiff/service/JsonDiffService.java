
package com.example.jsondiff.service;

import com.example.jsondiff.model.DiffEntry;
import com.example.jsondiff.model.DiffType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ValueNode;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class JsonDiffService {

    public List<DiffEntry> diff(JsonNode left, JsonNode right) {
        List<DiffEntry> diffs = new ArrayList<>();
        compareNodes(left, right, "", diffs);
        diffs.sort(Comparator.comparing(DiffEntry::getPath).thenComparing(e -> e.getType().name()));
        return diffs;
    }

    private void compareNodes(JsonNode left, JsonNode right, String path, List<DiffEntry> diffs) {
        if (left == null && right == null) return;

        // Handle missing sides
        if (left == null) {
            diffs.add(new DiffEntry(DiffType.ADDED, pathOrRoot(path), null, toPrintable(right), null));
            return;
        }
        if (right == null) {
            diffs.add(new DiffEntry(DiffType.REMOVED, pathOrRoot(path), toPrintable(left), null, null));
            return;
        }

        // Value nodes
        if (left.isValueNode() && right.isValueNode()) {
            if (!Objects.equals(normalizeValueNode(left), normalizeValueNode(right))) {
                diffs.add(new DiffEntry(
                        DiffType.CHANGED, pathOrRoot(path),
                        toPrintable(left), toPrintable(right), null
                ));
            }
            return;
        }

        // Object nodes
        if (left.isObject() && right.isObject()) {
            Set<String> fieldNames = new TreeSet<>();
            left.fieldNames().forEachRemaining(fieldNames::add);
            right.fieldNames().forEachRemaining(fieldNames::add);
            for (String field : fieldNames) {
                String childPath = appendPath(path, field);
                JsonNode lChild = left.get(field);
                JsonNode rChild = right.get(field);
                if (lChild == null && rChild != null) {
                    diffs.add(new DiffEntry(DiffType.ADDED, childPath, null, toPrintable(rChild), null));
                } else if (lChild != null && rChild == null) {
                    diffs.add(new DiffEntry(DiffType.REMOVED, childPath, toPrintable(lChild), null, null));
                } else {
                    compareNodes(lChild, rChild, childPath, diffs);
                }
            }
            return;
        }

        // Array nodes
        if (left.isArray() && right.isArray()) {
            compareArrays((ArrayNode) left, (ArrayNode) right, path, diffs);
            return;
        }

        // Different node types -> changed
        diffs.add(new DiffEntry(DiffType.CHANGED, pathOrRoot(path), toPrintable(left), toPrintable(right),
                "Different JSON node types"));
    }

    private void compareArrays(ArrayNode left, ArrayNode right, String path, List<DiffEntry> diffs) {
        boolean allScalars = isAllScalars(left) && isAllScalars(right);

        if (allScalars) {
            // SET comparison: show ADDED/REMOVED items ignoring order and duplicates
            Set<String> leftSet = toScalarSet(left);
            Set<String> rightSet = toScalarSet(right);

            Set<String> added = new TreeSet<>(rightSet);
            added.removeAll(leftSet);

            Set<String> removed = new TreeSet<>(leftSet);
            removed.removeAll(rightSet);

            if (!added.isEmpty()) {
                diffs.add(new DiffEntry(DiffType.ADDED, pathOrRoot(path),
                        null, null, "Array items added: " + added));
            }
            if (!removed.isEmpty()) {
                diffs.add(new DiffEntry(DiffType.REMOVED, pathOrRoot(path),
                        null, null, "Array items removed: " + removed));
            }

            // If lengths differ but sets same, no change reported (order-insensitive).
        } else {
            // INDEX comparison: show per-index changes
            int max = Math.max(left.size(), right.size());
            for (int i = 0; i < max; i++) {
                String idxPath = path + "[" + i + "]";
                JsonNode l = i < left.size() ? left.get(i) : null;
                JsonNode r = i < right.size() ? right.get(i) : null;
                compareNodes(l, r, idxPath, diffs);
            }
        }
    }

    private boolean isAllScalars(ArrayNode array) {
        return StreamSupport.stream(array.spliterator(), false)
                .allMatch(JsonNode::isValueNode);
    }

    private Set<String> toScalarSet(ArrayNode array) {
        return StreamSupport.stream(array.spliterator(), false)
                .map(this::normalizeValueNode)
                .map(Objects::toString)
                .collect(Collectors.toCollection(TreeSet::new));
    }

    private Object normalizeValueNode(JsonNode node) {
        ValueNode vn = (ValueNode) node;
        if (vn.isTextual()) return vn.asText();
        if (vn.isNumber()) return vn.numberValue();
        if (vn.isBoolean()) return vn.booleanValue();
        if (vn.isNull()) return null;
        return vn.toString();
    }

    private String toPrintable(JsonNode node) {
        if (node == null || node.isNull()) return "null";
        if (node.isValueNode()) return String.valueOf(normalizeValueNode(node));
        return node.toPrettyString();
    }

    private String appendPath(String base, String field) {
        if (base == null || base.isEmpty()) return field;
        return base + "." + field;
    }

    private String pathOrRoot(String path) {
        return (path == null || path.isEmpty()) ? "$" : path;
    }
}
