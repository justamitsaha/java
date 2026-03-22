package com.saha.amit.basic;


import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class B_CommonProblemSolving {
    
    private static final Random random = new Random();
    static int[] integers = new int[4];
    static int input = 0;
    static int smallInput = 0;

    static {
        System.out.println("Initializing");
        input = random.nextInt(999,9999);
        smallInput = random.nextInt(0, 25); // Generate a random integer between 0 and 19
        for (int i = 0; i < integers.length; i++) {
            integers[i] = random.nextInt(999, 9999); // Generate random integers between 0 and 99
        }
    }

    public static void main(String[] args) {
        System.out.printf("Sum of all the digits of the Input: %s is : %s%n", input, sumOfDigit(input));
        System.out.printf("Largest element of this integer array: %s using stream, is %s%n", integers, findLargestUsingStrem(integers));
        System.out.printf("Largest element of this integer array: %s using loops, is %s%n", integers, findLargestUsingLoop(integers));
        System.out.printf("2nd Largest element of this integer array: %s using loops, is %s%n", integers, findSecondLargest(integers));
        swapWithoutTempVariable();
        fibonacciPrint(9);
        System.out.printf("Factorial of: %s is: %s %n", smallInput, factorial(smallInput));
    }


    public static int sumOfDigit(Integer number) {
        //Approach 1 using loop
        int sum = 0, temp = number;
        while (temp > 0) {
            sum += temp % 10;
            temp /= 10; //This is equivalent to a = a / b;
        }
        System.out.printf("Sum of the digits of number: %s using basic loop is : %s%n", number, sum);

        return number.toString()
                .chars()
                .map(Character::getNumericValue)
                //.sum();
                .reduce(0, Integer::sum);
    }

    public static int findLargestUsingStrem(int[] integer) {
        var result = Arrays.stream(integer)
                .reduce((integer1, integer2) -> {
                    int largest = 0;
                    if (integer1 > integer2) {
                        largest = integer1;
                    } else {
                        largest = integer2;
                    }
                    return largest;
                });
        //or
        result = Arrays.stream(integer).reduce(Math::max);
        //When no identity value is provided, the result is wrapped in an Optional to handle the case of an empty stream.
        return result.orElse(0);

    }

    /*Pro -->Fastest (O(n)) Works for primitives and doesn’t box/unbox No extra memory Easy to understand
    Con Verbose Must handle empty array manually*/
    public static int findLargestUsingLoop(int[] numbers) {
        int max = numbers[0];
        for (int i = 1; i < numbers.length; i++) {
            if (numbers[i] > max) {
                max = numbers[i];
            }
        }
        return max;
    }

    public static int findSecondLargest(int[] numbers) {
        /*
        Pro -->Fastest (O(n)) Works for primitives and doesn’t box/unbox No extra memory Easy to understand
        Con Verbose Must handle empty array manually
         */
        if (numbers == null || numbers.length < 2) {
            return -1; // Not enough elements
        }
        int largest = Integer.MIN_VALUE;
        int secondLargest = Integer.MIN_VALUE;
        for (int number : numbers) {
            if (number > largest) {
                secondLargest = largest;
                largest = number;
            } else if (number > secondLargest && number != largest) {
                secondLargest = number;
            }
        }
        int temp = Arrays.stream(numbers)
                .distinct()
                .boxed()
                .sorted(Comparator.reverseOrder())
                .skip(1)
                .findFirst()
                .orElse(-1);
        System.out.println(String.valueOf(temp));
        //System.out.printf("Second Largest using basic loop: %s%n", secondLargest);
        return secondLargest;
    }

    public static void swapWithoutTempVariable() {
        int a = 5;
        int b = 10;
        System.out.printf("Before Swap: a = %s, b = %s%n", a, b);
        a = a + b; // a now becomes 15
        b = a - b; // b becomes 5
        a = a - b; // a becomes 10
        System.out.printf("After Swap: a = %s, b = %s%n", a, b);
    }

    public static long factorial(int input) {
        int result = 1;
        for (int i = 1; i <= input; i++) {
            result *= i;
        }
        //return result;
        return Stream.iterate(1, i -> i + 1)
                .limit(input)
                .reduce(1, (i, j) -> i * j);
    }

    public static boolean primeCheck(int input) {
        if (input <= 1) return false;  // 0, 1 are not prime
        for (int i = 2; i <= Math.sqrt(input); i++) {
            if (input % i == 0) {
                return false;
            }
        }
        //return true;

        if (input <= 1) return false;
        return IntStream.rangeClosed(2, (int) Math.sqrt(input))
                .noneMatch(i -> input % i == 0);
    }


    public static void fibonacciPrint(int limit) {
        if (limit <= 0) {
            System.out.println("Limit must be greater than 0");
            return;
        }

        // ----- LOOP VERSION -----
        long first = 0, second = 1;
        List<Long> list = new ArrayList<>();
        list.add(first);
        if (limit > 1) list.add(second);

        for (int i = 2; i < limit; i++) {
            long next = first + second;
            first = second;
            second = next;
            list.add(next);
        }

        System.out.printf("Fibonacci (loop) %s%n", list);

        // ----- STREAM VERSION -----
        List<Long> streamList = Stream.iterate(new long[]{0, 1}, f -> new long[]{f[1], f[0] + f[1]})
                .limit(limit)
                .map(f -> f[0])
                .collect(Collectors.toList());

        System.out.printf("Fibonacci (stream) %s%n", streamList);
    }


    public static Map<String, Integer> countWordFrequency(String str) {
        System.out.printf("Input: %s%n", str);

        // Normalize input — lowercase and remove punctuation
        str = str.toLowerCase().replaceAll("[^a-z\\s]", ""); // keep only letters and spaces

        String[] words = str.split("\\s+"); // split by one or more spaces

        Map<String, Integer> wordCount = new HashMap<>();

        for (String word : words) {
            if (!word.isEmpty()) {
                wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
            }
        }

        System.out.printf("Word Frequency: %s%n", wordCount);
        return wordCount;
    }

    public static Map<String, Long> countWordFrequencyStream(String str) {
        return Arrays.stream(str.toLowerCase().replaceAll("[^a-z\\s]", "").split("\\s+"))
                .filter(word -> !word.isEmpty())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }


}
