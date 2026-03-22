package com.saha.amit.stream;


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

        

        public static void main(String[] args) {
            Scanner sc = new Scanner(System.in);

            while (true) {
                /* Uncomment to see options
                System.out.println("\n========= Primitive Stream Practice Menu =========");
                System.out.println("1. IntStream Basics");
                System.out.println("2. IntStream Sum and Average");
                System.out.println("3. IntStream Filter and Map");
                System.out.println("4. Range vs RangeClosed");
                System.out.println("5. Boxing Example");
                System.out.println("6. Unboxing Example (List to IntStream)");
                System.out.println("7. Factorial Using LongStream");
                System.out.println("8. LongStream to DoubleStream");
                System.out.println("9. DoubleStream Statistics");
                System.out.println("10. Generate Random Doubles");
                System.out.println("11. Find Max and Min");
                System.out.println("12. Reduce - Product Example");
                System.out.println("13. Type Conversion Examples");
                System.out.println("14. Combined Employee Example");
                System.out.println("15. IntStream Summary Statistics");
                System.out.println("0. Exit");*/
                System.out.println("Enter your choice: ");

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
                        System.out.println("Goodbye 👋");
                        return;
                    }
                    default -> System.out.println("Invalid choice! Try again.");
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
        System.out.printf("Sum = %s, Avg = %s%n", sum, avg);
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
        System.out.println("Range(1,5):");
        IntStream.range(1, 5).forEach(System.out::print);  // 1234
        System.out.println("\nRangeClosed(1,5):");
        IntStream.rangeClosed(1, 5).forEach(System.out::print);  // 12345
    }

    // 5️⃣ Boxing: convert IntStream → Stream<Integer>
    public static void intStreamBoxingExample() {
        List<Integer> boxed = IntStream.rangeClosed(1, 5)
                .boxed()
                .toList();
        System.out.printf("Boxed list: %s%n", boxed);
        // Output: [1, 2, 3, 4, 5]
    }

    // 6️⃣ Unboxing: convert List<Integer> → IntStream
    public static void listToIntStreamConversion() {
        List<Integer> list = List.of(10, 20, 30, 40);
        int sum = list.stream()
                .mapToInt(Integer::intValue)
                .sum();
        System.out.printf("Sum from list = %s%n", sum);
        // Output: 100
    }

    // 7️⃣ LongStream: factorial using reduce
    public static void factorialUsingLongStream() {
        int n = 5;
        long fact = LongStream.rangeClosed(1, n)
                .reduce(1, (a, b) -> a * b);
        System.out.printf("Factorial(%s) = %s%n", n, fact);
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
        System.out.println(String.valueOf(stats));
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
        System.out.printf("Max = %s, Min = %s%n", max, min);
    }

    // 12️⃣ Reduce: product of elements
    public static void reduceProductExample() {
        int product = IntStream.rangeClosed(1, 5)
                .reduce(1, (a, b) -> a * b);
        System.out.printf("Product of 1..5 = %s%n", product);
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
        System.out.printf("Boxed Double List: %s%n", boxed);
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
        System.out.printf("Total Salary = %s%n", total);
        // Output: 245000.0
    }

    // 15️⃣ IntStream summary statistics
    public static void intStreamSummary() {
        IntSummaryStatistics stats = IntStream.rangeClosed(1, 10)
                .summaryStatistics();
        System.out.println(String.valueOf(stats));
        // Output: count=10, sum=55, min=1, average=5.5, max=10
    }
}

