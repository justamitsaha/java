
package com.example.jsondiff;

import com.example.jsondiff.model.DiffEntry;
import com.example.jsondiff.service.JsonDiffService;
import com.example.jsondiff.util.Reporter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

@SpringBootApplication
public class JsonDiffApplication implements CommandLineRunner {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final JsonDiffService diffService = new JsonDiffService();

    public static void main(String[] args) {
        SpringApplication.run(JsonDiffApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        if (args.length < 2 || args.length > 3) {
            System.out.println("""
                    Usage:
                      java -jar json-diff.jar <fileA.json> <fileB.json> [output.txt]
                    
                    Notes:
                      - If output.txt is omitted, report is printed to console.
                      - Arrays default to SET comparison for scalar arrays.
                    """);
            return;
        }

        File fileA = Path.of(args[0]).toFile();
        File fileB = Path.of(args[1]).toFile();
        String outputPath = (args.length == 3) ? args[2] : null;

        if (!fileA.exists() || !fileB.exists()) {
            System.err.printf("Input file(s) not found: %s %s%n", fileA.getAbsolutePath(), fileB.getAbsolutePath());
            return;
        }

        JsonNode left = objectMapper.readTree(fileA);
        JsonNode right = objectMapper.readTree(fileB);

        List<DiffEntry> diffs = diffService.diff(left, right);

        Reporter reporter = new Reporter();
        String report = reporter.format(diffs);

        if (outputPath == null) {
            System.out.println(report);
        } else {
            reporter.writeToFile(report, outputPath);
            System.out.printf("Report written to %s%n", outputPath);
        }
    }
}