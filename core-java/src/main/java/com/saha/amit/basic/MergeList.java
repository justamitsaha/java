package com.saha.amit.basic;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class MergeList {
    public static void main(String[] args) {
        String[] arr1 = str1.split("\\s+");
        Set<String> set1 = new HashSet<>();
        for (String str : arr1) {
            set1.add(str.trim());
        }
        System.out.println(set1);

        String[] arr2 = str2.split("\\s+");
        Set<String> set2 = new HashSet<>();
        for (String str : arr2) {
            set2.add(str.trim());
        }
        System.out.println(set2);
        /*
         * // This will also remove duplicates but either set1 or set2 gets modified
         * set1.retainAll(set2);
         * System.out.println(set1);
         * // or
         * set2.retainAll(set1);
         * System.out.println(set2);
         */

        //Without modifying original sets we can create new set and retain common elements
        Set<String> intersection = new HashSet<>(set1);
        intersection.retainAll(set2);
        System.out.println(intersection);

        // With stream approach original sets are not modified also steams are lazy
        // hence
        // with large set it doesn't take up a lot of memory
        Set<String> mergedset = set1.stream()
                .filter(s -> set2.contains(s))
                .collect(Collectors.toSet());

        System.out.println(mergedset);
    }

    public static String str1 = """
            Sachin
            Rahul
            Rahul
            Sunil
            Sunil
            Amit
                Amit
            """;

    public static String str2 = """
            Sachin
            Rahul
            Ricky
            Ricky
            Lara
                      Ricky
            Lara
            """;
}
