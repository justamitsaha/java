package com.saha.amit.stream;


import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.*;

/**
 * Advanced Stream Practice Workbook
 * Author: Amit Saha
 * Topics:
 * - Parallel Streams
 * - Collectors (groupingBy, partitioningBy, mapping)
 * - Custom Collectors
 * - Performance comparison
 * - Advanced transformations
 */

public class C_AdvancedStreamPractice {


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            /* Uncomment to see options
            System.out.println("\n========= Java Stream Practice Menu =========");
            System.out.println("1. Parallel Stream Basics");
            System.out.println("2. Compare Sequential vs Parallel Performance");
            System.out.println("3. Group Employees by Department");
            System.out.println("4. Partition Numbers into Even/Odd");
            System.out.println("5. Nested List Flattening");
            System.out.println("6. Custom Collector: Join Strings");
            System.out.println("7. Mapping Collector Example");
            System.out.println("8. Summing and Averaging with Collectors");
            System.out.println("9. Custom Thread Count in Parallel Stream");
            System.out.println("10. Stream Peek and Debug Example");
            System.out.println("0. Exit");*/
            System.out.println("Enter your choice: ");

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
                    System.out.println("Goodbye 👋");
                    return;
                }
                default -> System.out.println("Invalid choice! Try again.");
            }
        }
    }

    // Data record for examples
    record Employee(String name, String dept, double salary) {
    }

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
        System.out.println("\n-- Parallel Stream Example --");
        List<Integer> numbers = IntStream.rangeClosed(1, 10).boxed().toList();

        numbers.parallelStream()
                .forEach(n ->
                        System.out.println(Thread.currentThread().getName() + " processed " + n)
                );
    }

    // 2️⃣ Compare Sequential vs Parallel
    public static void compareSequentialVsParallel() {
        System.out.println("\n-- Performance Comparison --");

        List<Integer> numbers = IntStream.rangeClosed(1, 100_000_00).boxed().toList();

        long startSeq = System.currentTimeMillis();
        long sum1 = numbers.stream().mapToLong(Integer::longValue).sum();
        long timeSeq = System.currentTimeMillis() - startSeq;

        long startPar = System.currentTimeMillis();
        long sum2 = numbers.parallelStream().mapToLong(Integer::longValue).sum();
        long timePar = System.currentTimeMillis() - startPar;

        System.out.printf("Sequential sum time: %s ms%n", timeSeq);
        System.out.printf("Parallel sum time:   %s ms%n", timePar);
    }

    // 3️⃣ Group by Department
    public static void groupByDepartment() {
        System.out.println("\n-- Group Employees by Department --");
        Map<String, List<Employee>> grouped = employees.stream()
                .collect(Collectors.groupingBy(Employee::dept));
        grouped.forEach((dept, list) ->
                System.out.println(dept + " -> " + list.stream().map(Employee::name).toList())
        );
    }

    // 4️⃣ Partitioning
    public static void partitionEvenOdd() {
        System.out.println("\n-- Partition Numbers into Even/Odd --");
        List<Integer> numbers = IntStream.rangeClosed(1, 10).boxed().toList();
        Map<Boolean, List<Integer>> result = numbers.stream()
                .collect(Collectors.partitioningBy(n -> n % 2 == 0));
        System.out.println(result.toString());
    }

    // 5️⃣ Flatten Nested List (flatMap)
    public static void flattenNestedList() {
        System.out.println("\n-- Flatten Nested Lists --");
        List<List<String>> nested = List.of(
                List.of("Java", "Spring"),
                List.of("Docker", "Kubernetes"),
                List.of("Grafana", "Prometheus")
        );
        List<String> flat = nested.stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        System.out.printf("Flattened: %s%n", flat);
    }

    // 6️⃣ Custom Collector - Join Strings manually
    public static void customCollectorJoinStrings() {
        System.out.println("\n-- Custom Collector Example (Join Strings) --");
        List<String> words = List.of("Java", "Stream", "API", "Rocks");

        String result = words.stream().collect(
                StringBuilder::new,
                (sb, str) -> sb.append(str).append(" "),
                StringBuilder::append
        ).toString().trim();

        System.out.printf("Joined String: %s%n", result);
    }

    // 7️⃣ Mapping Collector
    public static void mappingCollectorExample() {
        System.out.println("\n-- Mapping Collector Example --");
        Map<String, List<Double>> salariesByDept = employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::dept,
                        Collectors.mapping(Employee::salary, Collectors.toList())
                ));
        System.out.printf("Salaries by Dept: %s%n", salariesByDept);
    }

    // 8️⃣ Summing and Averaging
    public static void summingAndAveragingCollectors() {
        System.out.println("\n-- Summing and Averaging Collectors --");
        double total = employees.stream().mapToDouble(Employee::salary).sum();
        double avg = employees.stream()
                .collect(Collectors.averagingDouble(Employee::salary));

        System.out.printf("Total Salary = %s%n", total);
        System.out.printf("Average Salary = %s%n", avg);
    }

    // 9️⃣ Custom Thread Count with Parallel Stream
    public static void customThreadCountParallel() {
        System.out.println("\n-- Custom Thread Count Parallel Stream --");

        ForkJoinPool customPool = new ForkJoinPool(3);
        try {
            customPool.submit(() -> {
                IntStream.rangeClosed(1, 10)
                        .parallel()
                        .forEach(i ->
                                System.out.println(Thread.currentThread().getName() + " processed " + i)
                        );
            }).get();
        } catch (Exception e) {
            System.out.printf("Error: %s%n", e.getMessage());
        } finally {
            customPool.shutdown();
        }
    }

    // 🔟 Peek for Debugging
    public static void peekDebugExample() {
        System.out.println("\n-- Stream Peek for Debugging --");
        List<String> words = List.of("java", "stream", "debug", "peek");

        List<String> result = words.stream()
                .peek(w -> System.out.println("Before map: " + w))
                .map(String::toUpperCase)
                .peek(w -> System.out.println("After map: " + w))
                .filter(w -> w.length() > 4)
                .toList();

        System.out.printf("Final result: %s%n", result);
    }
}

