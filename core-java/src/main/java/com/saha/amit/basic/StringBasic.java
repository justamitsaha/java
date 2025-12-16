package com.saha.amit.basic;

import com.github.javafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class StringBasic {
    private static final Logger log = LoggerFactory.getLogger(StringBasic.class);

    static Faker faker = new Faker();
    static boolean printLongLogs = true;

    public static void main(String[] args) {
        String st = faker.howIMetYourMother().catchPhrase();
        //Un comment below for very long input to calculate performance, it disables logs which print inout/output
        st = faker.lorem().sentence(9000000);         printLongLogs = false;
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
        log.info("Time taken for collection: {} ", (end - start));
        if (printLongLogs)
            log.info("Reverse using collections Input String: \"{}\" and Reversed String: \"{}\" ", st, result);
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
        log.info("Time taken for simple loop: {} ", (end - start));
        if (printLongLogs)
            log.info("Reverse using simple loop Input String: \"{}\" and Reversed String: \"{}\" ", st, result);
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
        log.info("Time taken for stream: {} ", (end - start));
        if (printLongLogs)
            log.info("Reverse using stream Input String: \"{}\" and Reversed String: \"{}\"", st, result);
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
        log.info("Time taken for palindrome: {} ", (end - start));
        if (printLongLogs)
            log.info("Is this input Palindrome:  \"{}\", result: {}", st, bool);
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
        log.info("Time taken for counting vowels and constants: {} ", (end - start));
        if (printLongLogs)
            log.info("Vowels: {}, Consonants: {}", vowels, consonants);
    }
}
