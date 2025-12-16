
package com.example.jsondiff.util;

import com.example.jsondiff.model.DiffEntry;
import com.example.jsondiff.model.DiffType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class Reporter {

    /**
     * Console/Text report in two categories:
     * - Missing: which rule is not present and in which file; shows the present side's snippet.
     * - Modified: exists in both; show A and B snippets side by side.
     */
    public String format(List<DiffEntry> diffs, String aLabel, String bLabel) {
        StringBuilder sb = new StringBuilder();

        // Categorize diffs
        List<DiffEntry> missing = diffs.stream()
                .filter(d -> d.getType() == DiffType.ADDED || d.getType() == DiffType.REMOVED)
                .collect(Collectors.toList());

        List<DiffEntry> modified = diffs.stream()
                .filter(d -> d.getType() == DiffType.CHANGED)
                .collect(Collectors.toList());

        int total = missing.size() + modified.size();

        sb.append("=== JSON DIFF REPORT ===\n");
        sb.append(String.format("Total differences: %d%n%n", total));

        // Missing section
        for (DiffEntry d : missing) {
            boolean missingInB = (d.getType() == DiffType.REMOVED); // present only in A
            String presentValue = missingInB ? d.getLeftValue() : d.getRightValue();
            String missingFile = missingInB ? bLabel : aLabel;
            String presentFile = missingInB ? aLabel : bLabel;

            sb.append(String.format("[Missing] %s | Missing in %s | %s value%n",
                    d.getPath(), missingFile, presentFile));

            if (presentValue != null && !presentValue.isEmpty()) {
                sb.append(indentBlock(presentValue)).append("\n");
            }
            sb.append("\n");
        }

        // Modified section
        for (DiffEntry d : modified) {
            sb.append(String.format("[Modified] %s%n", d.getPath()));

            // A side
            sb.append("    ").append(aLabel).append("\n");
            if (notBlank(d.getLeftValue())) {
                sb.append(indentBlock(d.getLeftValue())).append(",\n");
            } else if (notBlank(d.getDetails())) {
                sb.append(indentBlock("Details: " + d.getDetails())).append(",\n");
            } else {
                sb.append("    (no A value)\n");
            }

            // B side
            sb.append("    ").append(bLabel).append("\n");
            if (notBlank(d.getRightValue())) {
                sb.append(indentBlock(d.getRightValue())).append(",\n");
            } else if (notBlank(d.getDetails())) {
                sb.append(indentBlock("Details: " + d.getDetails())).append(",\n");
            } else {
                sb.append("    (no B value)\n");
            }

            sb.append("\n");
        }

        // Summary
        sb.append("--- Summary ---\n");
        sb.append(String.format("Missing: %d | Modified: %d%n", missing.size(), modified.size()));

        // Missing lists by file (feature-level names)
        List<String> missingInA = missing.stream()
                .filter(d -> d.getType() == DiffType.ADDED) // present only in B -> missing in A
                .map(d -> featureName(d.getPath()))
                .distinct().sorted().collect(Collectors.toList());

        List<String> missingInB = missing.stream()
                .filter(d -> d.getType() == DiffType.REMOVED) // present only in A -> missing in B
                .map(d -> featureName(d.getPath()))
                .distinct().sorted().collect(Collectors.toList());

        if (!missingInA.isEmpty()) {
            sb.append("Missing in ").append(aLabel).append("\n");
            missingInA.forEach(name -> sb.append(name).append("\n"));
        }
        if (!missingInB.isEmpty()) {
            sb.append("Missing in ").append(bLabel).append("\n");
            missingInB.forEach(name -> sb.append(name).append("\n"));
        }

        return sb.toString();
    }

    /**
     * CSV output: Category, Path, Feature, A_File, B_File, A_Value, B_Value, Details
     * Values are safely quoted and inner quotes are escaped. Multiline JSON is preserved inside quotes.
     */
    public String formatCsv(List<DiffEntry> diffs, String aLabel, String bLabel) {
        StringBuilder sb = new StringBuilder();
        // Header
        sb.append("Category,Path,Feature,A_File,B_File,A_Value,B_Value,Details\n");

        for (DiffEntry d : diffs) {
            String category = (d.getType() == DiffType.CHANGED) ? "Modified" : "Missing";
            String path = d.getPath();
            String feature = featureName(path);

            String aValue = "";
            String bValue = "";
            String details = emptyIfNull(d.getDetails());

            if (d.getType() == DiffType.CHANGED) {
                aValue = emptyIfNull(d.getLeftValue());
                bValue = emptyIfNull(d.getRightValue());
            } else if (d.getType() == DiffType.REMOVED) {
                // present only in A, missing in B
                aValue = emptyIfNull(d.getLeftValue());
                bValue = "";
            } else if (d.getType() == DiffType.ADDED) {
                // present only in B, missing in A
                aValue = "";
                bValue = emptyIfNull(d.getRightValue());
            }

            // Row
            sb.append(csv(category)).append(',')
                    .append(csv(path)).append(',')
                    .append(csv(feature)).append(',')
                    .append(csv(aLabel)).append(',')
                    .append(csv(bLabel)).append(',')
                    .append(csv(aValue)).append(',')
                    .append(csv(bValue)).append(',')
                    .append(csv(details))
                    .append('\n');
        }

        return sb.toString();
    }

    public void writeToFile(String content, String outputPath) {
        try {
            Files.writeString(Path.of(outputPath), content);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write report: " + e.getMessage(), e);
        }
    }

    public void writeCsv(String content, String outputPath) {
        try {
            Files.writeString(Path.of(outputPath), content);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write CSV: " + e.getMessage(), e);
        }
    }

    // Helpers

    /** Extracts the top-level feature under "Feature." from a path like "Feature.feature4.override.segments". */
    private String featureName(String path) {
        if (path == null || path.isEmpty()) return "";
        int idx = path.indexOf("Feature.");
        if (idx >= 0) {
            int start = idx + "Feature.".length();
            int end = path.indexOf('.', start);
            if (end >= 0) return path.substring(start, end);
            return path.substring(start);
        }
        // fallback: last segment
        return lastPathSegment(path);
    }

    private String lastPathSegment(String path) {
        if (path == null || path.isEmpty()) return path;
        int idx = path.lastIndexOf('.');
        if (idx < 0) return path;
        return path.substring(idx + 1);
    }

    /** Indents each line with 4 spaces for nicer multi-line JSON blocks in console output. */
    private String indentBlock(String block) {
        String[] lines = block.split("\\r?\\n");
        StringBuilder out = new StringBuilder();
        for (String line : lines) {
            out.append("    ").append(line).append("\n");
        }
        return out.toString();
    }

    private boolean notBlank(String s) {
        return s != null && !s.trim().isEmpty();
    }

    private String emptyIfNull(String s) {
        return (s == null) ? "" : s;
    }

    /** CSV-safe quoting: wrap in quotes and escape inner quotes by doubling them. */
    private String csv(String s) {
        if (s == null) return "\"\"";
        String escaped = s.replace("\"", "\"\"");
        return "\"" + escaped + "\"";
    }
}

/*
    "feature12": {
      "status": "OVERRIDE",
      "override": {
        "staff": true
      }
    },

        "feature12": {
      "status": "ON"
    }

    Missing	Feature.feature14	feature14	SG.json	IPB.json	"{
  ""status"" : ""ON""
}"

 */