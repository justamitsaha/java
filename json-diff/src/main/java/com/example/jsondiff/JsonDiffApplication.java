
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
            // IntelliJ / IDE mode — load from classpath resources
            String aName = "SG.json"; // you can rename to featuresA.json if you prefer
            String bName = "IPB.json";

            System.out.printf("Running in RESOURCES mode (IntelliJ). A=%s, B=%s%n", aName, bName);
            JsonNode left = readJsonFromResources(aName);
            JsonNode right = readJsonFromResources(bName);

            List<DiffEntry> diffs = diffService.diff(left, right);
            String report = reporter.format(diffs);
            System.out.println(report);

        } else {
            // JAR CLI mode — use file arguments: <A.json> <B.json> [output.txt]
            if (args.length < 2 || args.length > 3) {
                printUsage();
                return;
            }

            String srcA = args[0];
            String srcB = args[1];
            String outputPath = (args.length == 3) ? args[2] : null;

            System.out.printf("Running in FILE mode (JAR). A=%s, B=%s%n", srcA, srcB);

            JsonNode left = objectMapper.readTree(Path.of(srcA).toFile());
            JsonNode right = objectMapper.readTree(Path.of(srcB).toFile());

            List<DiffEntry> diffs = diffService.diff(left, right);
            String report = reporter.format(diffs);

            if (outputPath == null) {
                System.out.println(report);
            } else {
                reporter.writeToFile(report, outputPath);
                System.out.printf("Report written to %s%n", outputPath);
            }
        }
    }

    private void printUsage() {
        System.out.println("""
                Usage:
                
                  # JAR (file mode) — pass paths as arguments
                  java -jar json-diff.jar <fileA.json> <fileB.json> [output.txt]
                
                  # IntelliJ/IDE (resources mode) — run with NO arguments
                  # The app will load SG.json and IPB.json from src/main/resources automatically.
                """);
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
}