
package com.example.jsondiff.util;

import com.example.jsondiff.model.DiffEntry;
import com.example.jsondiff.model.DiffType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class Reporter {

    public String format(List<DiffEntry> diffs, String aLabel, String bLabel) {
        StringBuilder sb = new StringBuilder();

        // Categorize
        List<DiffEntry> missing = diffs.stream()
                .filter(d -> d.getType() == DiffType.ADDED || d.getType() == DiffType.REMOVED)
                .collect(Collectors.toList());

        List<DiffEntry> modified = diffs.stream()
                .filter(d -> d.getType() == DiffType.CHANGED)
                .collect(Collectors.toList());

        int total = missing.size() + modified.size();

        sb.append("=== JSON DIFF REPORT ===\n");
        sb.append(String.format("Total differences: %d%n%n", total));

        // Print Missing
        for (DiffEntry d : missing) {
            String ruleName = lastPathSegment(d.getPath());
            // Decide which file is missing
            boolean missingInB = (d.getType() == DiffType.REMOVED); // existed only in A
            boolean missingInA = (d.getType() == DiffType.ADDED);   // existed only in B

            sb.append(String.format("[Missing] %s | Missing in %s | %s value%n",
                    d.getPath(),
                    missingInB ? bLabel : aLabel,
                    missingInB ? aLabel : bLabel));

            // Print the value present in the other file (pretty string already in DiffEntry)
            String presentValue = missingInB ? d.getLeftValue() : d.getRightValue();
            if (presentValue != null && !presentValue.isBlank()) {
                sb.append(indentBlock(presentValue)).append("\n");
            }
            sb.append("\n");
        }

        // Print Modified
        for (DiffEntry d : modified) {
            sb.append(String.format("[Modified] %s%n", d.getPath()));

            // Show A side
            sb.append("    ").append(aLabel).append("\n");
            if (d.getLeftValue() != null && !d.getLeftValue().isBlank()) {
                sb.append(indentBlock(d.getLeftValue())).append(",\n");
            } else if (d.getDetails() != null && !d.getDetails().isBlank()) {
                sb.append(indentBlock("Details: " + d.getDetails())).append(",\n");
            } else {
                sb.append("    (no A value)\n");
            }

            // Show B side
            sb.append("    ").append(bLabel).append("\n");
            if (d.getRightValue() != null && !d.getRightValue().isBlank()) {
                sb.append(indentBlock(d.getRightValue())).append(",\n");
            } else if (d.getDetails() != null && !d.getDetails().isBlank()) {
                sb.append(indentBlock("Details: " + d.getDetails())).append(",\n");
            } else {
                sb.append("    (no B value)\n");
            }

            sb.append("\n");
        }

        // Summary
        sb.append("--- Summary ---\n");
        sb.append(String.format("Missing: %d | Modified: %d%n", missing.size(), modified.size()));

        // Missing listings by file
        List<String> missingInA = missing.stream()
                .filter(d -> d.getType() == DiffType.ADDED) // present only in B -> missing in A
                .map(d -> lastPathSegment(d.getPath()))
                .distinct()
                .sorted()
                .collect(Collectors.toList());

        List<String> missingInB = missing.stream()
                .filter(d -> d.getType() == DiffType.REMOVED) // present only in A -> missing in B
                .map(d -> lastPathSegment(d.getPath()))
                .distinct()
                .sorted()
                .collect(Collectors.toList());

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

    public void writeToFile(String content, String outputPath) {
        try {
            Files.writeString(Path.of(outputPath), content);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write report: " + e.getMessage(), e);
        }
    }

    private String lastPathSegment(String path) {
        if (path == null || path.isEmpty()) return path;
        int idx = path.lastIndexOf('.');
        if (idx < 0) return path;
        return path.substring(idx + 1);
    }

    private String indentBlock(String block) {
        // Indent each line by 4 spaces
        String[] lines = block.split("\\r?\\n");
        StringBuilder out = new StringBuilder();
        for (String line : lines) {
            out.append("    ").append(line).append("\n");
        }
        return out.toString();
    }
}

/*
Every thing is working now I just want the report to be different now , It should provide 2 categories Missing and Modified in below format
1. Missing -> Should tell rule which is not present and in which file
2. Modified -> Should tell when file is present but change in attribute

e.g.
```
Running in RESOURCES mode (IntelliJ). A=SG.json, B=IPB.json
=== JSON DIFF REPORT ===
Total differences: 14

[Missing] Feature.feature4 | Missing in IPB.json |SG value
      "feature4": {
      "status": "ON"
    },

[Modified] Feature.feature11
    SG.json
    "feature11": {
      "status": "OVERRIDE",
      "override": {
        "segments": [
          "SEG1",
          "SEG5"
        ]
      }
    },
    IPB.json
     "feature11": {
      "status": "OVERRIDE",
      "override": {
        "segments": [
          "SEG1",
          "SEG4"
        ]
      }
    },

--- Summary ---
Missing: 12 | Modified:9
Missing in SG.json
feature4
feature5
Missing in IPB.json
feature1
feature2

```

 */
