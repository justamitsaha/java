package com.example.jsondiff.component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class FeatureCompare {

    private final ObjectMapper objectMapper = new ObjectMapper();

    // Default filenames for resource mode
    private static final String DEFAULT_A_NAME = "Feature_SG.json";
    private static final String DEFAULT_B_NAME = "Feature_IPB.json";

    /**
     * Constructor for Dependency Injection.
     *
     * @param objectMapper The Jackson ObjectMapper provided by Spring.
     */
//    public FeatureCompare(ObjectMapper objectMapper) {
//        this.objectMapper = objectMapper;
//    }

    /**
     * Entry point for the FeatureCompare component.
     * This method decides whether to load files from command-line arguments (FILE mode)
     * or from resource files (RESOURCES mode) if no arguments are provided.
     *
     * @param args The raw command line arguments (expected: <fileA.json> <fileB.json> [output.txt]).
     * @throws Exception if file loading or processing fails.
     */
    public void execute(String... args) throws Exception {

        if (args == null || args.length == 0) {
            // RESOURCES mode (Fallback to classpath)
            System.out.printf("FeatureCompare running in RESOURCES mode. A=%s, B=%s%n", DEFAULT_A_NAME, DEFAULT_B_NAME);

            JsonNode left = readJsonFromResources(DEFAULT_A_NAME);
            JsonNode right = readJsonFromResources(DEFAULT_B_NAME);

            // Placeholder for core comparison logic that relied on JsonDiffService/Reporter
            if (left != null && right != null) {
                System.out.println("Processing JSON nodes from resources...");
            } else {
                System.out.println("Error: One or both resource files failed to load.");
            }
            System.out.println("Comparison complete. (Detailed reporting logic omitted due to missing dependencies.)");

        } else {
            // FILE mode (Using command line arguments)
            if (args.length < 2 || args.length > 3) {
                printUsage();
                return;
            }

            String srcA = args[0];
            String srcB = args[1];
            String outputPath = (args.length == 3) ? args[2] : null;

            String aLabel = Path.of(srcA).getFileName().toString();
            String bLabel = Path.of(srcB).getFileName().toString();

            System.out.printf("FeatureCompare running in FILE mode. A=%s (%s), B=%s (%s)%n", aLabel, srcA, bLabel, srcB);

            // Load from file system paths
            JsonNode left = objectMapper.readTree(Path.of(srcA).toFile());
            JsonNode right = objectMapper.readTree(Path.of(srcB).toFile());

            // Placeholder for core comparison logic that relied on JsonDiffService/Reporter
            if (left != null && right != null) {
                System.out.println("Processing JSON nodes from file system...");
            }

            if (outputPath != null) {
                // Simplified output writing (original used Reporter)
                String mockReport = "Mock Report Content for " + aLabel + " vs " + bLabel;
                Files.writeString(Path.of(outputPath), mockReport);
                System.out.printf("Report written to %s%n", outputPath);

                String csvPath = deriveCsvPath(outputPath);
                // Reporter.writeCsv logic replaced
                System.out.printf("CSV report would be written to %s%n", csvPath);
            } else {
                System.out.println("Comparison complete. (Detailed console output/CSV writing omitted due to missing dependencies.)");
            }
        }
    }

    private void printUsage() {
        System.out.println(
                "Usage:\n\n" +
                        "  FeatureCompare.execute() called with arguments:\n" +
                        "  [fileA.json] [fileB.json] [output.txt]\n" +
                        "  (If no arguments are provided, it falls back to resource files: " + DEFAULT_A_NAME + " and " + DEFAULT_B_NAME + ".)"
        );
    }

    private JsonNode readJsonFromResources(String resourceName) throws Exception {
        ClassPathResource res = new ClassPathResource(resourceName);
        if (!res.exists()) {
            System.err.println("Resource not found in classpath: " + resourceName);
            return null;
        }
        try (InputStream is = res.getInputStream()) {
            return objectMapper.readTree(is);
        } catch (IOException e) {
            System.err.println("Error reading resource '" + resourceName + "': " + e.getMessage());
            throw e;
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
