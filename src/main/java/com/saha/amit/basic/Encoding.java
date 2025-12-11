package com.saha.amit.basic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.saha.amit.dto.Employee;

import java.nio.charset.StandardCharsets;

public class Encoding {

    /*
    Java does not have a single universal default encoding.
    It uses the platform’s default charset, which depends on the operating system and environment.
    Typical defaults
    Windows (India/US/most regions): Cp1252 or a locale-specific Windows code page (e.g., windows-1252, windows-1251, MS932, etc.).
    Linux/macOS: Usually UTF-8.
    Android: Always UTF-8.
     */

    public static void main(String[] args) throws JsonProcessingException {
        String defaultEncoding = System.getProperty("file.encoding");
        System.out.println("Default Encoding: " + defaultEncoding);

        Employee emp = new Employee("München", 100000);

        ObjectMapper mapper = new ObjectMapper();
        byte[] jsonBytes = mapper.writeValueAsString(emp).getBytes(StandardCharsets.UTF_8);

        String correct = new String(jsonBytes, StandardCharsets.UTF_8);
        System.out.println("Correct JSON: " + correct);
        String corrupted = new String(jsonBytes, StandardCharsets.ISO_8859_1);
        System.out.println("Corrupted JSON: " + corrupted);
        Employee p1 = mapper.readValue(corrupted, Employee.class);
        Employee p2 = mapper.readValue(corrupted, Employee.class);
    }
}
