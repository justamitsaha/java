package com.saha.amit.stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.*;

    /**
     * Primitive Stream Practice Workbook
     * Author: Amit Saha
     * Covers:
     *  - IntStream, LongStream, DoubleStream
     *  - Boxing & Unboxing
     *  - Conversion between primitive and object streams
     *  - Range, sum, reduce, average, summaryStatistics
     */
    public class B_PrimitiveStreamPractice {

        private static final Logger log = LoggerFactory.getLogger(B_PrimitiveStreamPractice.class);

        public static void main(String[] args) {
            Scanner sc = new Scanner(System.in);

            while (true) {
                /* Uncomment to see options
                log.info("\n========= Primitive Stream Practice Menu =========");
                log.info("1. IntStream Basics");
                log.info("2. IntStream Sum and Average");
                log.info("3. IntStream Filter and Map");
                log.info("4. Range vs RangeClosed");
                log.info("5. Boxing Example");
                log.info("6. Unboxing Example (List to IntStream)");
                log.info("7. Factorial Using LongStream");
                log.info("8. LongStream to DoubleStream");
                log.info("9. DoubleStream Statistics");
                log.info("10. Generate Random Doubles");
                log.info("11. Find Max and Min");
                log.info("12. Reduce - Product Example");
                log.info("13. Type Conversion Examples");
                log.info("14. Combined Employee Example");
                log.info("15. IntStream Summary Statistics");
                log.info("0. Exit");*/
                log.info("Enter your choice: ");

                int choice = sc.nextInt();

                switch (choice) {
                    case 1 -> intStreamBasics();
                    case 2 -> intStreamSumAndAverage();
                    case 3 -> intStreamFilterAndMap();
                    case 4 -> intStreamRangeVsRangeClosed();
                    case 5 -> intStreamBoxingExample();
                    case 6 -> listToIntStreamConversion();
                    case 7 -> factorialUsingLongStream();
                    case 8 -> longStreamConversionToDoubleStream();
                    case 9 -> doubleStreamStatsExample();
                    case 10 -> generateRandomDoubles();
                    case 11 -> findMaxMinWithIntStream();
                    case 12 -> reduceProductExample();
                    case 13 -> typeConversionsExample();
                    case 14 -> combinedEmployeeExample();
                    case 15 -> intStreamSummary();
                    case 0 -> {
                        log.info("Goodbye 👋");
                        return;
                    }
                    default -> log.info("Invalid choice! Try again.");
                }
            }
        }

    // 1️⃣ IntStream basics: create and print
    public static void intStreamBasics() {
        IntStream.range(1, 6).forEach(System.out::println);
        // Output: 1 2 3 4 5
    }

    // 2️⃣ Sum and Average
    public static void intStreamSumAndAverage() {
        int sum = IntStream.rangeClosed(1, 10).sum();
        double avg = IntStream.rangeClosed(1, 10).average().orElse(0);
        log.info("Sum = {}, Avg = {}", sum, avg);
        // Output: Sum = 55, Avg = 5.5
    }

    // 3️⃣ Filter + Map
    public static void intStreamFilterAndMap() {
        IntStream.rangeClosed(1, 10)
                .filter(n -> n % 2 == 0)
                .map(n -> n * n)
                .forEach(n -> System.out.print(n + " "));
        // Output: 4 16 36 64 100
    }

    // 4️⃣ Range vs RangeClosed
    public static void intStreamRangeVsRangeClosed() {
        log.info("Range(1,5):");
        IntStream.range(1, 5).forEach(System.out::print);  // 1234
        log.info("\nRangeClosed(1,5):");
        IntStream.rangeClosed(1, 5).forEach(System.out::print);  // 12345
    }

    // 5️⃣ Boxing: convert IntStream → Stream<Integer>
    public static void intStreamBoxingExample() {
        List<Integer> boxed = IntStream.rangeClosed(1, 5)
                .boxed()
                .toList();
        log.info("Boxed list: {}", boxed);
        // Output: [1, 2, 3, 4, 5]
    }

    // 6️⃣ Unboxing: convert List<Integer> → IntStream
    public static void listToIntStreamConversion() {
        List<Integer> list = List.of(10, 20, 30, 40);
        int sum = list.stream()
                .mapToInt(Integer::intValue)
                .sum();
        log.info("Sum from list = {}", sum);
        // Output: 100
    }

    // 7️⃣ LongStream: factorial using reduce
    public static void factorialUsingLongStream() {
        int n = 5;
        long fact = LongStream.rangeClosed(1, n)
                .reduce(1, (a, b) -> a * b);
        log.info("Factorial({}) = {}", n, fact);
        // Output: 120
    }

    // 8️⃣ Convert LongStream → DoubleStream
    public static void longStreamConversionToDoubleStream() {
        LongStream.rangeClosed(1, 3)
                .asDoubleStream()
                .forEach(System.out::println);
        // Output: 1.0 2.0 3.0
    }

    // 9️⃣ DoubleStream: statistics (min, max, avg, sum)
    public static void doubleStreamStatsExample() {
        DoubleSummaryStatistics stats = DoubleStream.of(2.5, 3.7, 4.1, 1.9)
                .summaryStatistics();
        log.info(String.valueOf(stats));
        // Output: count=4, sum=12.2, min=1.9, average=3.05, max=4.1
    }

    // 🔟 Generate random doubles between 0 and 1
    public static void generateRandomDoubles() {
        new Random().doubles(5, 0, 1)
                .forEach(System.out::println);
        // Output: 5 random values like 0.134, 0.543, ...
    }

    // 11️⃣ Find max and min with IntStream
    public static void findMaxMinWithIntStream() {
        IntStream stream = IntStream.of(5, 10, 2, 15, 8);
        int max = stream.max().orElse(-1);
        int min = IntStream.of(5, 10, 2, 15, 8).min().orElse(-1);
        log.info("Max = {}, Min = {}", max, min);
    }

    // 12️⃣ Reduce: product of elements
    public static void reduceProductExample() {
        int product = IntStream.rangeClosed(1, 5)
                .reduce(1, (a, b) -> a * b);
        log.info("Product of 1..5 = {}", product);
        // Output: 120
    }

    // 13️⃣ Type conversion examples
    public static void typeConversionsExample() {
        // IntStream → LongStream
        LongStream longStream = IntStream.range(1, 4).asLongStream();
        longStream.forEach(System.out::println);

        // IntStream → DoubleStream
        DoubleStream doubleStream = IntStream.rangeClosed(1, 3).asDoubleStream();
        doubleStream.forEach(System.out::println);

        // DoubleStream → boxed Stream<Double>
        List<Double> boxed = DoubleStream.of(1.1, 2.2, 3.3)
                .boxed()
                .toList();
        log.info("Boxed Double List: {}", boxed);
    }

    // 14️⃣ Combined real-world: sum of salaries using mapToDouble
    public static void combinedEmployeeExample() {
        record Employee(String name, double salary) {}
        List<Employee> employees = List.of(
                new Employee("Amit", 80000),
                new Employee("Sneha", 90000),
                new Employee("Ravi", 75000)
        );

        double total = employees.stream()
                .mapToDouble(Employee::salary)
                .sum();
        log.info("Total Salary = {}", total);
        // Output: 245000.0
    }

    // 15️⃣ IntStream summary statistics
    public static void intStreamSummary() {
        IntSummaryStatistics stats = IntStream.rangeClosed(1, 10)
                .summaryStatistics();
        log.info(String.valueOf(stats));
        // Output: count=10, sum=55, min=1, average=5.5, max=10
    }
}

