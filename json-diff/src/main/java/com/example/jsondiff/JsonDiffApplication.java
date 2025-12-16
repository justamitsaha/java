
package com.example.jsondiff;

import com.example.jsondiff.model.DiffEntry;
import com.example.jsondiff.service.JsonDiffService;
import com.example.jsondiff.util.Reporter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;
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
        Reporter reporter = new Reporter();

        if (args == null || args.length == 0) {
            // RESOURCES mode (IntelliJ)
            String aName = "SG.json";
            String bName = "IPB.json";

            System.out.printf("Running in RESOURCES mode (IntelliJ). A=%s, B=%s%n", aName, bName);

            JsonNode left = readJsonFromResources(aName);
            JsonNode right = readJsonFromResources(bName);

            // 🔸 Feature-level diff
            List<DiffEntry> diffs = diffService.diffFeatures(left, right);

            String report = reporter.format(diffs, aName, bName);
            System.out.println(report);

            String csv = reporter.formatCsv(diffs, aName, bName);
            String csvPath = "json-diff-report.csv";
            reporter.writeCsv(csv, csvPath);
            System.out.printf("CSV written to %s%n", csvPath);

        } else {
            // FILE mode (JAR)
            if (args.length < 2 || args.length > 3) {
                printUsage();
                return;
            }

            String srcA = args[0];
            String srcB = args[1];
            String outputPath = (args.length == 3) ? args[2] : null;

            String aLabel = Path.of(srcA).getFileName().toString();
            String bLabel = Path.of(srcB).getFileName().toString();

            System.out.printf("Running in FILE mode (JAR). A=%s, B=%s%n", srcA, srcB);

            JsonNode left = objectMapper.readTree(Path.of(srcA).toFile());
            JsonNode right = objectMapper.readTree(Path.of(srcB).toFile());

            // 🔸 Feature-level diff
            List<DiffEntry> diffs = diffService.diffFeatures(left, right);

            String report = reporter.format(diffs, aLabel, bLabel);

            if (outputPath == null) {
                System.out.println(report);
                String csv = reporter.formatCsv(diffs, aLabel, bLabel);
                String csvPath = "json-diff-report.csv";
                reporter.writeCsv(csv, csvPath);
                System.out.printf("CSV written to %s%n", csvPath);
            } else {
                reporter.writeToFile(report, outputPath);
                System.out.printf("Report written to %s%n", outputPath);

                String csv = reporter.formatCsv(diffs, aLabel, bLabel);
                String csvPath = deriveCsvPath(outputPath);
                reporter.writeCsv(csv, csvPath);
                System.out.printf("CSV written to %s%n", csvPath);
            }
        }
    }

    private void printUsage() {
        System.out.println(
                "Usage:\n\n" +
                        "  # JAR (file mode) — pass paths as arguments\n" +
                        "  java -jar json-diff.jar <fileA.json> <fileB.json> [output.txt]\n\n" +
                        "  # IntelliJ/IDE (resources mode) — run with NO arguments\n" +
                        "  # The app will load SG.json and IPB.json from src/main/resources automatically.\n"
        );
    }

    private JsonNode readJsonFromResources(String resourceName) throws Exception {
        ClassPathResource res = new ClassPathResource(resourceName);
        if (!res.exists()) {
            throw new IllegalArgumentException("Resource not found in classpath: " + resourceName);
        }
        try (InputStream is = res.getInputStream()) {
            return objectMapper.readTree(is);
        }
    }

    private String deriveCsvPath(String outputPath) {
        int dot = outputPath.lastIndexOf('.');
        if (dot > 0) {
            return outputPath.substring(0, dot) + ".csv";
        }
        return outputPath + ".csv";
    }
}
