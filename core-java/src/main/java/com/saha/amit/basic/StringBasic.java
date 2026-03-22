package com.saha.amit.basic;


import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class StringBasic {
    

    static boolean printLongLogs = true;

    public static void main(String[] args) {
        String st = "hello world";
        //Un comment below for very long input to calculate performance, it disables logs which print inout/output
        st = """
                Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.
                """;
        printLongLogs = false;
        reverseUsingCollection(st);
        reverseSimple(st);
        reverseUsingStream(st);
        String palindrome = "A man, a plan, a canal: Panama";
        isPalindrome(st);
        countVowelsConsonants(st);

    }

    /* Too many conversions (inefficient), verbose */
    public static void reverseUsingCollection(String st) {
        Long start = System.currentTimeMillis();
        List<Character> characters = st.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toList());
        Collections.reverse(characters);
        StringBuilder result = new StringBuilder(characters.size());
        characters.forEach(result::append);
        Long end = System.currentTimeMillis();
        System.out.printf("Time taken for collection: %s %n", (end - start));
        if (printLongLogs)
            System.out.printf("Reverse using collections Input String: \"%s\" and Reversed String: \"%s\" %n", st, result);
    }

    /* Very simple loops  low level code fast*/
    public static void reverseSimple(String st) {
        Long start = System.currentTimeMillis();
        char[] ch = st.toCharArray();
        StringBuilder result = new StringBuilder();
        for (int i = st.length() - 1; i >= 0; i--) {
            result.append(ch[i]);
        }
        Long end = System.currentTimeMillis();
        System.out.printf("Time taken for simple loop: %s %n", (end - start));
        if (printLongLogs)
            System.out.printf("Reverse using simple loop Input String: \"%s\" and Reversed String: \"%s\" %n", st, result);
    }

    /*
    Con: Using stream Collection, Slightly slower due to autoboxing, stream overhead
    Pro: Elegant, modern Java style
     */
    public static void reverseUsingStream(String st) {
        Long start = System.currentTimeMillis();
        String result = st.chars()
                //.parallel()
                .mapToObj(c -> (char) c)
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(),
                        lst -> {
                            Collections.reverse(lst);
                            return lst.stream()
                                    .map(String::valueOf)
                                    .collect(Collectors.joining());
                        }));
        Long end = System.currentTimeMillis();
        System.out.printf("Time taken for stream: %s %n", (end - start));
        if (printLongLogs)
            System.out.printf("Reverse using stream Input String: \"%s\" and Reversed String: \"%s\"%n", st, result);
    }

    /*
    Using String builder to reverse the string better than above methods
    uses native java methods faster Internally optimized (native code).
     */
    public static void isPalindrome(String st) {
        Long start = System.currentTimeMillis();
        String cleaned = st.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
        boolean bool = cleaned.contentEquals(new StringBuilder(cleaned).reverse());
        Long end = System.currentTimeMillis();
        System.out.printf("Time taken for palindrome: %s %n", (end - start));
        if (printLongLogs)
            System.out.printf("Is this input Palindrome:  \"%s\", result: %s%n", st, bool);
    }


    public static void countVowelsConsonants(String st) {
        Long start = System.currentTimeMillis();
        int vowels = 0, consonants = 0;
        st = st.toLowerCase();
        for (char ch : st.toCharArray()) {
            if (Character.isLetter(ch)) {
                if ("aeiou".indexOf(ch) >= 0)
                    vowels++;
                else
                    consonants++;
            }
        }
        Long end = System.currentTimeMillis();
        System.out.printf("Time taken for counting vowels and constants: %s %n", (end - start));
        if (printLongLogs)
            System.out.printf("Vowels: %s, Consonants: %s%n", vowels, consonants);
    }
}
