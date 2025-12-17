package com.example.jsondiff;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

@SpringBootApplication
public class JsonToCsvComparator implements CommandLineRunner {

    // Using Spring's default ObjectMapper is often a better practice than creating a new one
    // We use 'new' here as we don't need Spring to manage it for this CLI context.
    private final ObjectMapper mapper = new ObjectMapper();
    private final List<String[]> csvData = new ArrayList<>();

    // Define file names for clarity
    private final String FILE_NAME_1 = "IPB_rule.json";
    private final String FILE_NAME_2 = "SG_rule.json";

    public static void main(String[] args) {
        SpringApplication.run(JsonToCsvComparator.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            // FIX: Load files using ClassPathResource for reliable access in resource folder or packaged JAR
            JsonNode tree1 = loadJsonFromResources(FILE_NAME_1);
            JsonNode tree2 = loadJsonFromResources(FILE_NAME_2);

            // Add CSV Header, using the defined file names
            csvData.add(new String[]{
                    FILE_NAME_1 + " node name",
                    FILE_NAME_2 + " node name",
                    FILE_NAME_1 + " value",
                    FILE_NAME_2 + " value"
            });

            // Start comparison at the root level
            compareNodes(tree1, tree2, "");

            writeToCsv("comparison_report.csv");

        } catch (IOException e) {
            System.err.println("Error reading JSON files. Ensure " + FILE_NAME_1 + " and " + FILE_NAME_2 + " are in src/main/resources.");
            e.printStackTrace();
        }
    }

    /**
     * Helper method to reliably load JSON file from the classpath.
     */
    private JsonNode loadJsonFromResources(String filename) throws IOException {
        // ClassPathResource is the standard way to access files in src/main/resources
        ClassPathResource resource = new ClassPathResource(filename);
        if (!resource.exists()) {
            throw new IOException("Resource not found: " + filename);
        }
        return mapper.readTree(resource.getInputStream());
    }

    /**
     * Recursively compares nodes and records differences to csvData.
     * Note: We are comparing existence and raw values (as strings) regardless of type.
     */
    private void compareNodes(JsonNode n1, JsonNode n2, String parentPath) {
        // Get all unique keys from both nodes (if they are objects)
        Set<String> allKeys = new TreeSet<>();
        if (n1 != null && n1.isObject()) n1.fieldNames().forEachRemaining(allKeys::add);
        if (n2 != null && n2.isObject()) n2.fieldNames().forEachRemaining(allKeys::add);

        for (String key : allKeys) {
            JsonNode child1 = (n1 != null) ? n1.get(key) : null;
            JsonNode child2 = (n2 != null) ? n2.get(key) : null;

            // Determine if the node exists and get its string representation
            String node1Name = (child1 != null) ? key : "";
            String node2Name = (child2 != null) ? key : "";

            // Capture the full string value, or "Missing"
            String val1 = (child1 != null) ? child1.toString() : "Missing";
            String val2 = (child2 != null) ? child2.toString() : "Missing";

            csvData.add(new String[]{node1Name, node2Name, val1, val2});

            // Only recurse if BOTH children exist and are Objects.
            // If one is null or not an Object, we stop here for that branch.
            if ((child1 != null && child1.isObject()) && (child2 != null && child2.isObject())) {
                String currentPath = parentPath.isEmpty() ? key : parentPath + "." + key;
                compareNodes(child1, child2, currentPath);
            }
            // If one is an array and the other is an object, the recursion stops,
            // and the full values are compared in the current row.
        }
    }

    /**
     * Writes the collected data to a CSV file.
     */
    private void writeToCsv(String filename) throws IOException {
        try (FileWriter writer = new FileWriter(filename)) {
            for (String[] row : csvData) {
                // Use the pipe '|' as the separator as requested
                writer.append(String.join("|", row)).append("\n");
            }
        }
        System.out.println("\n✅ CSV Report generated successfully: " + filename);
    }
}