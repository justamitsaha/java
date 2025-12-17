package com.example.jsondiff;

import com.example.jsondiff.component.FeatureCompare;
import com.example.jsondiff.component.RuleDiff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JsonDiffApplication implements CommandLineRunner {

    // The constant for the feature comparison mode
    private static final String FEATURE_MODE = "FEATURE_MODE";

    // Injected comparison components
    private final FeatureCompare featureCompare;
    private final RuleDiff ruleDiff;

    @Autowired
    public JsonDiffApplication(FeatureCompare featureCompare, RuleDiff ruleDiff) {
        this.featureCompare = featureCompare;
        this.ruleDiff = ruleDiff;
    }


    public static void main(String[] args) {
        SpringApplication.run(JsonDiffApplication.class, args);
    }


    /**
     * Executes the application logic by inspecting the command line arguments
     * and delegating to the correct component.
     *
     * Handles three scenarios:
     * 1. 3+ args (F1, F2, MODE): Mode is determined by args[2].
     * 2. 1 arg (MODE): Mode is determined by args[0].
     * 3. 0 or 2 args (F1, F2): Default to RuleDiff (no explicit mode provided).
     *
     * @param args The command line arguments passed to the JAR.
     * @throws Exception if component execution fails.
     */
    @Override
    public void run(String... args) throws Exception {

        String mode = null;

        if (args == null || args.length == 0) {
            // Scenario 2: No parameters provided. Mode is null (default).
            System.out.println("No arguments provided. Falling back to default RuleDiff mode.");

        } else if (args.length == 1) {
            // Scenario 3: Only the mode parameter is provided.
            mode = args[0];
            System.out.println("One argument provided. Treating as Mode: " + mode);

        } else if (args.length >= 3) {
            // Scenario 1: Three or more parameters (F1, F2, MODE, ...). Mode is the third argument.
            mode = args[2];
            System.out.println("Three or more arguments provided. Mode is: " + mode);

        } else { // args.length == 2
            // Two file names provided, but no mode. Default to RuleDiff.
            System.out.println("Two file arguments provided. Defaulting to RuleDiff mode.");
        }

        if (FEATURE_MODE.equals(mode)) {
            System.out.println("Starting FeatureCompare component.");
            // Pass all arguments. FeatureCompare will interpret them (or use defaults).
            featureCompare.execute(args);
        } else {
            System.out.println("Starting RuleDiff component.");
            // Pass all arguments. RuleDiff will interpret them (or use defaults).
            ruleDiff.execute(args);
        }
    }
}