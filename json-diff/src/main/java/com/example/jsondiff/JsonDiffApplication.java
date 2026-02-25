package com.example.jsondiff;

import com.example.jsondiff.component.RuleDiff;
import com.example.jsondiff.component.XmlDiff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JsonDiffApplication implements CommandLineRunner {

    // The constant for the feature comparison mode

    // Injected comparison components
    private final RuleDiff ruleDiff;
    private final XmlDiff xmlDiff;

    @Autowired
    public JsonDiffApplication(RuleDiff ruleDiff, XmlDiff xmlDiff) {
        this.ruleDiff = ruleDiff;
        this.xmlDiff = xmlDiff;
    }


    public static void main(String[] args) {
        SpringApplication.run(JsonDiffApplication.class, args);
    }


   @Override
    public void run(String... args) throws Exception {
       //ruleDiff.execute(args);
       xmlDiff.execute(args);
    }
}