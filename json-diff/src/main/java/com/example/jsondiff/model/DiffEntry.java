
package com.example.jsondiff.model;

public class DiffEntry {
    private final DiffType type;
    private final String path;       // e.g., Feature.feature3.status
    private final String leftValue;  // value in file A (null if ADDED)
    private final String rightValue; // value in file B (null if REMOVED)
    private final String details;    // optional for arrays/objects

    public DiffEntry(DiffType type, String path, String leftValue, String rightValue, String details) {
        this.type = type;
        this.path = path;
        this.leftValue = leftValue;
        this.rightValue = rightValue;
        this.details = details;
    }

    public DiffType getType() { return type; }
    public String getPath() { return path; }
    public String getLeftValue() { return leftValue; }
    public String getRightValue() { return rightValue; }
    public String getDetails() { return details; }
}
