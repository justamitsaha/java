package com.saha.amit.stream;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private static final Logger log = LoggerFactory.getLogger(D_ExpertStreamPractice.class);

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);

        while (true) {
            /*Uncomment to see options
            log.info("\n========= Expert Stream Practice Menu =========");
            log.info("1. Stream.generate() - Infinite Random Numbers");
            log.info("2. Stream.iterate() - Arithmetic Progression");
            log.info("3. takeWhile() & dropWhile()");
            log.info("4. Collectors.teeing() Example");
            log.info("5. Stream.concat() Example");
            log.info("6. Read File using Files.lines()");
            log.info("7. Split String using Pattern.splitAsStream()");
            log.info("8. Stream.Builder Example");
            log.info("9. Custom Spliterator Example");
            log.info("10. Stream of Optionals Example");
            log.info("11. Reusable Stream using Supplier");
            log.info("0. Exit");*/
            log.info("Enter your choice: ");

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
                    log.info("Goodbye 👋");
                    return;
                }
                default -> log.info("Invalid choice! Try again.");
            }
        }
    }

    // 1️⃣ Stream.generate()
    public static void infiniteRandomNumbers() {
        log.info("\n-- Stream.generate() --");
        Stream.generate(Math::random)
                .limit(5)
                .forEach(System.out::println);
    }

    // 2️⃣ Stream.iterate()
    public static void arithmeticProgression() {
        log.info("\n-- Stream.iterate() --");
        Stream.iterate(2, n -> n + 3)
                .limit(10)
                .forEach(System.out::println);
    }

    // 3️⃣ takeWhile() / dropWhile()
    public static void takeWhileDropWhile() {
        log.info("\n-- takeWhile() & dropWhile() --");
        List<Integer> numbers = List.of(1, 2, 3, 0, -1, 4, 5);

        log.info("takeWhile > 0:");
        numbers.stream().takeWhile(n -> n > 0).forEach(System.out::println);

        log.info("dropWhile > 0:");
        numbers.stream().dropWhile(n -> n > 0).forEach(System.out::println);
    }

    // 4️⃣ Collectors.teeing()
    public static void teeingCollectorExample() {
        log.info("\n-- Collectors.teeing() --");
        Map<String, Object> result = IntStream.rangeClosed(1, 10)
                .boxed()
                .collect(Collectors.teeing(
                        Collectors.summingInt(Integer::intValue),
                        Collectors.averagingInt(Integer::intValue),
                        (sum, avg) -> Map.of("sum", sum, "average", avg)
                ));
        log.info(result.toString());
    }

    // 5️⃣ Stream.concat()
    public static void streamConcatExample() {
        log.info("\n-- Stream.concat() --");
        Stream<String> s1 = Stream.of("Java", "Spring");
        Stream<String> s2 = Stream.of("Kubernetes", "Docker");
        Stream.concat(s1, s2).forEach(System.out::println);
    }

    // 6️⃣ Files.lines()
    public static void filesLinesExample() throws IOException {
        log.info("\n-- Files.lines() --");
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
        log.info("\n-- Pattern.splitAsStream() --");
        String text = "Java,Python,Go,Rust";
        Pattern.compile(",")
                .splitAsStream(text)
                .forEach(System.out::println);
    }

    // 8️⃣ Stream.Builder
    public static void streamBuilderExample() {
        log.info("\n-- Stream.Builder() --");
        Stream<String> stream = Stream.<String>builder()
                .add("Hello")
                .add("from")
                .add("Stream.Builder!")
                .build();

        stream.forEach(System.out::println);
    }

    // 9️⃣ Custom Spliterator
    public static void customSpliteratorExample() {
        log.info("\n-- Custom Spliterator Example --");
        Spliterator<Integer> spliterator = IntStream.rangeClosed(1, 5).spliterator();
        Stream<Integer> stream = StreamSupport.stream(spliterator, false);
        stream.forEach(System.out::println);
    }

    // 🔟 Stream of Optionals
    public static void streamOfOptionals() {
        log.info("\n-- Stream of Optionals --");
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
        log.info("\n-- Reusable Stream with Supplier --");
        Supplier<Stream<Integer>> streamSupplier = () -> Stream.of(1, 2, 3, 4, 5);

        log.info("First use:");
        streamSupplier.get().forEach(System.out::print);

        log.info("\nSecond use:");
        streamSupplier.get().map(n -> n * n).forEach(System.out::print);
    }
}
