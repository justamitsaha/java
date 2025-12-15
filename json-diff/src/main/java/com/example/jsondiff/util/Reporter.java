
package com.example.jsondiff.util;

import com.example.jsondiff.model.DiffEntry;
import com.example.jsondiff.model.DiffType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Reporter {

    public String format(List<DiffEntry> diffs) {
        StringBuilder sb = new StringBuilder();
        sb.append("=== JSON DIFF REPORT ===\n");
        sb.append(String.format("Total differences: %d%n%n", diffs.size()));

        for (DiffEntry d : diffs) {
            sb.append(summarize(d)).append("\n");
        }

        // Optional summary by type
        long changed = diffs.stream().filter(e -> e.getType() == DiffType.CHANGED).count();
        long added = diffs.stream().filter(e -> e.getType() == DiffType.ADDED).count();
        long removed = diffs.stream().filter(e -> e.getType() == DiffType.REMOVED).count();

        sb.append("\n--- Summary ---\n");
        sb.append(String.format("CHANGED: %d | ADDED: %d | REMOVED: %d%n", changed, added, removed));

        return sb.toString();
    }

    private String summarize(DiffEntry d) {
        String base = String.format("[%s] %s", d.getType().name(), d.getPath());
        if (d.getDetails() != null && !d.getDetails().isBlank()) {
            return base + " -> " + d.getDetails();
        }
        String lv = d.getLeftValue();
        String rv = d.getRightValue();
        switch (d.getType()) {
            case CHANGED -> {
                return base + String.format(" | A: %s | B: %s", lv, rv);
            }
            case ADDED -> {
                return base + String.format(" | Added in B: %s", rv);
            }
            case REMOVED -> {
                return base + String.format(" | Removed from A: %s", lv);
            }
            default -> {
                return base;
            }
        }
    }

    public void writeToFile(String content, String outputPath) {
        try {
            Files.writeString(Path.of(outputPath), content);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write report: " + e.getMessage(), e);
        }
    }
}
