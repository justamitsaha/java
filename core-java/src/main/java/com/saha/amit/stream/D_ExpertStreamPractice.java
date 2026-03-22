package com.saha.amit.stream;



import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.*;

/**
 * Expert Stream Practice Workbook
 * Author: Amit Saha
 * Topics covered:
 *  - Stream. Generate / iterate (infinite streams)
 *  - takeWhile / dropWhile (Java 9+)
 *  - Collectors.teeing() (Java 12+)
 *  - Stream.concat()
 *  - Files.lines() and Pattern.splitAsStream()
 *  - Stream.Builder
 *  - Spliterator (custom data source)
 *  - Stream of Optional values
 *  - Reusable Stream suppliers
 */

public class D_ExpertStreamPractice {
    

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);

        while (true) {
            /*Uncomment to see options
            System.out.println("\n========= Expert Stream Practice Menu =========");
            System.out.println("1. Stream.generate() - Infinite Random Numbers");
            System.out.println("2. Stream.iterate() - Arithmetic Progression");
            System.out.println("3. takeWhile() & dropWhile()");
            System.out.println("4. Collectors.teeing() Example");
            System.out.println("5. Stream.concat() Example");
            System.out.println("6. Read File using Files.lines()");
            System.out.println("7. Split String using Pattern.splitAsStream()");
            System.out.println("8. Stream.Builder Example");
            System.out.println("9. Custom Spliterator Example");
            System.out.println("10. Stream of Optionals Example");
            System.out.println("11. Reusable Stream using Supplier");
            System.out.println("0. Exit");*/
            System.out.println("Enter your choice: ");

            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1 -> infiniteRandomNumbers();
                case 2 -> arithmeticProgression();
                case 3 -> takeWhileDropWhile();
                case 4 -> teeingCollectorExample();
                case 5 -> streamConcatExample();
                case 6 -> filesLinesExample();
                case 7 -> patternSplitAsStream();
                case 8 -> streamBuilderExample();
                case 9 -> customSpliteratorExample();
                case 10 -> streamOfOptionals();
                case 11 -> reusableStreamSupplier();
                case 0 -> {
                    System.out.println("Goodbye 👋");
                    return;
                }
                default -> System.out.println("Invalid choice! Try again.");
            }
        }
    }

    // 1️⃣ Stream.generate()
    public static void infiniteRandomNumbers() {
        System.out.println("\n-- Stream.generate() --");
        Stream.generate(Math::random)
                .limit(5)
                .forEach(System.out::println);
    }

    // 2️⃣ Stream.iterate()
    public static void arithmeticProgression() {
        System.out.println("\n-- Stream.iterate() --");
        Stream.iterate(2, n -> n + 3)
                .limit(10)
                .forEach(System.out::println);
    }

    // 3️⃣ takeWhile() / dropWhile()
    public static void takeWhileDropWhile() {
        System.out.println("\n-- takeWhile() & dropWhile() --");
        List<Integer> numbers = List.of(1, 2, 3, 0, -1, 4, 5);

        System.out.println("takeWhile > 0:");
        numbers.stream().takeWhile(n -> n > 0).forEach(System.out::println);

        System.out.println("dropWhile > 0:");
        numbers.stream().dropWhile(n -> n > 0).forEach(System.out::println);
    }

    // 4️⃣ Collectors.teeing()
    public static void teeingCollectorExample() {
        System.out.println("\n-- Collectors.teeing() --");
        Map<String, Object> result = IntStream.rangeClosed(1, 10)
                .boxed()
                .collect(Collectors.teeing(
                        Collectors.summingInt(Integer::intValue),
                        Collectors.averagingInt(Integer::intValue),
                        (sum, avg) -> Map.of("sum", sum, "average", avg)
                ));
        System.out.println(result.toString());
    }

    // 5️⃣ Stream.concat()
    public static void streamConcatExample() {
        System.out.println("\n-- Stream.concat() --");
        Stream<String> s1 = Stream.of("Java", "Spring");
        Stream<String> s2 = Stream.of("Kubernetes", "Docker");
        Stream.concat(s1, s2).forEach(System.out::println);
    }

    // 6️⃣ Files.lines()
    public static void filesLinesExample() throws IOException {
        System.out.println("\n-- Files.lines() --");
        Path path = Paths.get("sample.txt");
        if (!Files.exists(path)) {
            Files.write(path, List.of("Java Stream API", "makes coding fun", "and readable"));
        }

        try (Stream<String> lines = Files.lines(path)) {
            lines.forEach(System.out::println);
        }
    }

    // 7️⃣ Pattern.splitAsStream()
    public static void patternSplitAsStream() {
        System.out.println("\n-- Pattern.splitAsStream() --");
        String text = "Java,Python,Go,Rust";
        Pattern.compile(",")
                .splitAsStream(text)
                .forEach(System.out::println);
    }

    // 8️⃣ Stream.Builder
    public static void streamBuilderExample() {
        System.out.println("\n-- Stream.Builder() --");
        Stream<String> stream = Stream.<String>builder()
                .add("Hello")
                .add("from")
                .add("Stream.Builder!")
                .build();

        stream.forEach(System.out::println);
    }

    // 9️⃣ Custom Spliterator
    public static void customSpliteratorExample() {
        System.out.println("\n-- Custom Spliterator Example --");
        Spliterator<Integer> spliterator = IntStream.rangeClosed(1, 5).spliterator();
        Stream<Integer> stream = StreamSupport.stream(spliterator, false);
        stream.forEach(System.out::println);
    }

    // 🔟 Stream of Optionals
    public static void streamOfOptionals() {
        System.out.println("\n-- Stream of Optionals --");
        List<Optional<String>> list = List.of(
                Optional.of("Java"),
                Optional.empty(),
                Optional.of("Stream"),
                Optional.empty(),
                Optional.of("API")
        );

        list.stream()
                .flatMap(Optional::stream)
                .forEach(System.out::println);
    }

    // 11️⃣ Reusable Stream using Supplier
    public static void reusableStreamSupplier() {
        System.out.println("\n-- Reusable Stream with Supplier --");
        Supplier<Stream<Integer>> streamSupplier = () -> Stream.of(1, 2, 3, 4, 5);

        System.out.println("First use:");
        streamSupplier.get().forEach(System.out::print);

        System.out.println("\nSecond use:");
        streamSupplier.get().map(n -> n * n).forEach(System.out::print);
    }
}
