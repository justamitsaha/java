
package com.example.jsondiff.service;

import com.example.jsondiff.model.DiffEntry;
import com.example.jsondiff.model.DiffType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ValueNode;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class JsonDiffService {

    /**
     * Feature-level diff:
     * - Missing: feature present only in one side -> ADDED/REMOVED
     * - Modified: feature present in both but objects differ -> CHANGED
     * Emits ONE DiffEntry per feature (path = "Feature.<name>") with whole feature objects
     * as leftValue/rightValue.
     */
    public List<DiffEntry> diffFeatures(JsonNode leftRoot, JsonNode rightRoot) {
        List<DiffEntry> diffs = new ArrayList<>();
        if (leftRoot == null && rightRoot == null) return diffs;

        JsonNode leftFeatureObj = (leftRoot != null) ? leftRoot.get("Feature") : null;
        JsonNode rightFeatureObj = (rightRoot != null) ? rightRoot.get("Feature") : null;

        // If both sides don't have "Feature" object, nothing to compare
        if ((leftFeatureObj == null || !leftFeatureObj.isObject())
                && (rightFeatureObj == null || !rightFeatureObj.isObject())) {
            return diffs;
        }

        Set<String> featureNames = new TreeSet<>();
        if (leftFeatureObj != null && leftFeatureObj.isObject()) {
            leftFeatureObj.fieldNames().forEachRemaining(featureNames::add);
        }
        if (rightFeatureObj != null && rightFeatureObj.isObject()) {
            rightFeatureObj.fieldNames().forEachRemaining(featureNames::add);
        }

        for (String name : featureNames) {
            JsonNode l = (leftFeatureObj != null) ? leftFeatureObj.get(name) : null;
            JsonNode r = (rightFeatureObj != null) ? rightFeatureObj.get(name) : null;
            String path = "Feature." + name;

            if (l == null && r != null) {
                // Present only in right -> Missing in A
                diffs.add(new DiffEntry(DiffType.ADDED, path, null, toPrintable(r), null));
            } else if (l != null && r == null) {
                // Present only in left -> Missing in B
                diffs.add(new DiffEntry(DiffType.REMOVED, path, toPrintable(l), null, null));
            } else if (l != null && r != null && !deepEquals(l, r)) {
                // Present in both, but structure/value differs -> Modified
                diffs.add(new DiffEntry(DiffType.CHANGED, path, toPrintable(l), toPrintable(r), null));
            }
            // If equal, no entry
        }

        // Sort by path then type for stable report
        diffs.sort(Comparator.comparing(DiffEntry::getPath)
                .thenComparing(e -> e.getType().name()));
        return diffs;
    }

    // --- Helpers (kept from previous service) ---

    /** Deep equality using JsonNode.equals for structural and value equality. */
    private boolean deepEquals(JsonNode a, JsonNode b) {
        return Objects.equals(a, b);
    }

    private Object normalizeValueNode(JsonNode node) {
        ValueNode vn = (ValueNode) node;
        if (vn.isTextual()) return vn.asText();
        if (vn.isNumber()) return vn.numberValue();
        if (vn.isBoolean()) return vn.booleanValue();
        if (vn.isNull()) return null;
        return vn.toString();
    }

    /** Pretty prints objects; value nodes are stringified as scalars. */
    private String toPrintable(JsonNode node) {
        if (node == null || node.isNull()) return "null";
        if (node.isValueNode()) return String.valueOf(normalizeValueNode(node));
        return node.toPrettyString();
    }

    // If you keep the old fine-grained diff, that's fine; the app will call diffFeatures().
}
