package com.example.jsondiff;

import com.example.jsondiff.component.FeatureCompare;
import com.example.jsondiff.component.RuleDiff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class JsonDiffApplication implements CommandLineRunner {

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


    @Override
    public void run(String... args) throws Exception {
        // Treat null as empty for safety
        int len = (args == null) ? 0 : args.length;

        switch (len) {
            case 0 -> this.ruleDiff.findDifferance(args);

            case 1 -> {
                if ("FEATURE_MODE".equals(args[0])) {
                    featureCompare.compareFeature(args);
                } else {
                    ruleDiff.findDifferance(args);
                }
            }
            case 3 -> {
                if ("FEATURE_MODE".equals(args[0])) {
                    featureCompare.compareFeature(args);
                } else {
                    ruleDiff.findDifferance(args);
                }
            }
            default -> ruleDiff.findDifferance(args);
        }
    }
}
