package com.saha.amit.stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.*;

/**
 * Advanced Stream Practice Workbook
 * Author: Amit Saha
 * Topics:
 *  - Parallel Streams
 *  - Collectors (groupingBy, partitioningBy, mapping)
 *  - Custom Collectors
 *  - Performance comparison
 *  - Advanced transformations
 */

public class C_AdvancedStreamPractice {
    
    private static final Logger log = LoggerFactory.getLogger(C_AdvancedStreamPractice.class);

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            /* Uncomment to see options
            log.info("\n========= Java Stream Practice Menu =========");
            log.info("1. Parallel Stream Basics");
            log.info("2. Compare Sequential vs Parallel Performance");
            log.info("3. Group Employees by Department");
            log.info("4. Partition Numbers into Even/Odd");
            log.info("5. Nested List Flattening");
            log.info("6. Custom Collector: Join Strings");
            log.info("7. Mapping Collector Example");
            log.info("8. Summing and Averaging with Collectors");
            log.info("9. Custom Thread Count in Parallel Stream");
            log.info("10. Stream Peek and Debug Example");
            log.info("0. Exit");*/
            log.info("Enter your choice: ");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1 -> parallelStreamBasics();
                case 2 -> compareSequentialVsParallel();
                case 3 -> groupByDepartment();
                case 4 -> partitionEvenOdd();
                case 5 -> flattenNestedList();
                case 6 -> customCollectorJoinStrings();
                case 7 -> mappingCollectorExample();
                case 8 -> summingAndAveragingCollectors();
                case 9 -> customThreadCountParallel();
                case 10 -> peekDebugExample();
                case 0 -> {
                    log.info("Goodbye 👋");
                    return;
                }
                default -> log.info("Invalid choice! Try again.");
            }
        }
    }

    // Data record for examples
    record Employee(String name, String dept, double salary) {}

    static List<Employee> employees = List.of(
            new Employee("Amit", "IT", 90000),
            new Employee("Sneha", "IT", 95000),
            new Employee("Rahul", "HR", 60000),
            new Employee("Priya", "Finance", 75000),
            new Employee("Ravi", "HR", 65000),
            new Employee("Neha", "Finance", 70000)
    );

    // 1️⃣ Parallel Stream Basics
    public static void parallelStreamBasics() {
        log.info("\n-- Parallel Stream Example --");
        List<Integer> numbers = IntStream.rangeClosed(1, 10).boxed().toList();

        numbers.parallelStream()
                .forEach(n ->
                        log.info("{} processed {}", Thread.currentThread().getName(), n)
                );
    }

    // 2️⃣ Compare Sequential vs Parallel
    public static void compareSequentialVsParallel() {
        log.info("\n-- Performance Comparison --");

        List<Integer> numbers = IntStream.rangeClosed(1, 100_000_00).boxed().toList();

        long startSeq = System.currentTimeMillis();
        long sum1 = numbers.stream().mapToLong(Integer::longValue).sum();
        long timeSeq = System.currentTimeMillis() - startSeq;

        long startPar = System.currentTimeMillis();
        long sum2 = numbers.parallelStream().mapToLong(Integer::longValue).sum();
        long timePar = System.currentTimeMillis() - startPar;

        log.info("Sequential sum time: {} ms", timeSeq);
        log.info("Parallel sum time:   {} ms", timePar);
    }

    // 3️⃣ Group by Department
    public static void groupByDepartment() {
        log.info("\n-- Group Employees by Department --");
        Map<String, List<Employee>> grouped = employees.stream()
                .collect(Collectors.groupingBy(Employee::dept));
        grouped.forEach((dept, list) ->
                log.info("{} -> {}", dept, list.stream().map(Employee::name).toList()));
    }

    // 4️⃣ Partitioning
    public static void partitionEvenOdd() {
        log.info("\n-- Partition Numbers into Even/Odd --");
        List<Integer> numbers = IntStream.rangeClosed(1, 10).boxed().toList();
        Map<Boolean, List<Integer>> result = numbers.stream()
                .collect(Collectors.partitioningBy(n -> n % 2 == 0));
        log.info(result.toString());
    }

    // 5️⃣ Flatten Nested List (flatMap)
    public static void flattenNestedList() {
        log.info("\n-- Flatten Nested Lists --");
        List<List<String>> nested = List.of(
                List.of("Java", "Spring"),
                List.of("Docker", "Kubernetes"),
                List.of("Grafana", "Prometheus")
        );
        List<String> flat = nested.stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        log.info("Flattened: {}", flat);
    }

    // 6️⃣ Custom Collector - Join Strings manually
    public static void customCollectorJoinStrings() {
        log.info("\n-- Custom Collector Example (Join Strings) --");
        List<String> words = List.of("Java", "Stream", "API", "Rocks");

        String result = words.stream().collect(
                StringBuilder::new,
                (sb, str) -> sb.append(str).append(" "),
                StringBuilder::append
        ).toString().trim();

        log.info("Joined String: {}", result);
    }

    // 7️⃣ Mapping Collector
    public static void mappingCollectorExample() {
        log.info("\n-- Mapping Collector Example --");
        Map<String, List<Double>> salariesByDept = employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::dept,
                        Collectors.mapping(Employee::salary, Collectors.toList())
                ));
        log.info("Salaries by Dept: {}", salariesByDept);
    }

    // 8️⃣ Summing and Averaging
    public static void summingAndAveragingCollectors() {
        log.info("\n-- Summing and Averaging Collectors --");
        double total = employees.stream().mapToDouble(Employee::salary).sum();
        double avg = employees.stream()
                .collect(Collectors.averagingDouble(Employee::salary));

        log.info("Total Salary = {}", total);
        log.info("Average Salary = {}", avg);
    }

    // 9️⃣ Custom Thread Count with Parallel Stream
    public static void customThreadCountParallel() {
        log.info("\n-- Custom Thread Count Parallel Stream --");

        ForkJoinPool customPool = new ForkJoinPool(3);
        try {
            customPool.submit(() -> {
                IntStream.rangeClosed(1, 10)
                        .parallel()
                        .forEach(i ->
                                log.info("{} -> {}", Thread.currentThread().getName(), i)
                        );
            }).get();
        } catch (Exception e) {
            log.error("Error: {}", e.getMessage());
        } finally {
            customPool.shutdown();
        }
    }

    // 🔟 Peek for Debugging
    public static void peekDebugExample() {
        log.info("\n-- Stream Peek for Debugging --");
        List<String> words = List.of("java", "stream", "debug", "peek");

        List<String> result = words.stream()
                .peek(w -> log.info("Before map: {}", w))
                .map(String::toUpperCase)
                .peek(w -> log.info("After map: {}", w))
                .filter(w -> w.length() > 4)
                .toList();

        log.info("Final result: {}", result);
    }
}

