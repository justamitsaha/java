package com.saha.amit.generics;

import java.util.ArrayList;
import java.util.List;

/**
 * A comprehensive guide to Lower Bound combinations in Java Generics.
 * Core Principle: PECS (Producer Extends, Consumer Super).
 * Use 'super' when you want to WRITE to a collection.
 */
public class LowerBounds {

    public static void main(String[] args) {
        // 1. Basic usage: Writing Integers to a list of its supertypes
        List<Number> numberList = new ArrayList<>();
        addNumbers(numberList);
        System.out.println("Number List: " + numberList);

        List<Object> objectList = new ArrayList<>();
        addNumbers(objectList); // Works because Object is a supertype of Integer
        System.out.println("Object List: " + objectList);

        // 2. Using T with super for linking
        List<Integer> intList = new ArrayList<>(List.of(1, 2));
        copy(List.of(10, 20), intList);
        System.out.println("Copied List: " + intList);
    }

    /* =========================================================
       1. ⭐⭐ THE BASIC LOWER BOUND (Wildcard)
       =========================================================
    */

    /**
     * Accepts a list of Integer or any of its supertypes (Number, Object).
     * This is a "Consumer": We are putting Integers INTO the list.
     */
    public static void addNumbers(List<? super Integer> list) {
        // We can safely add Integers because the list is guaranteed
        // to be at least a List<Integer>, List<Number>, or List<Object>.
        list.add(1);
        list.add(2);
        list.add(3);

        // ❌ CANNOT READ as Integer safely.
        // The compiler only knows the elements are at least 'Object'.
        // Integer i = list.get(0); // Error!
        Object obj = list.getFirst(); // This is allowed.
        if(list.getFirst() instanceof Integer) {
            Integer i = (Integer) obj; // We can cast if we know the actual type.
            System.out.println("Read Integer: " + i);
        }
    }

    /* =========================================================
       2. ⭐⭐ NAMED T WITH SUPER (Linking)
       =========================================================
    */

    /**
     * Common in 'copy' methods.
     * We read from 'src' (Producer - Extends)
     * We write to 'dest' (Consumer - Super)
     */
    public static <T> void copy(List<? extends T> src, List<? super T> dest) {
        for (T item : src) {
            dest.add(item); // Writing T into a list that accepts T or its supertypes
        }
    }

    /* =========================================================
       3. ⭐⭐ COMPARABLE WITH SUPER (Advanced)
       =========================================================
    */

    /**
     * Often seen in Collections.sort().
     * This allows us to sort a List<Integer> using a Comparator<Number>.
     * It is more flexible than <T extends Comparable<T>>.
     */
    public static <T> void sortWithSuper(List<T> list, java.util.Comparator<? super T> c) {
        list.sort(c);
    }

    /* =========================================================
       4. ❌ WHAT IS NOT ALLOWED WITH SUPER
       =========================================================
    */

    /** ⭐⭐⭐
     * ❌ RULE: You CANNOT use 'super' in a formal type parameter declaration.
     * Only wildcards (?) support 'super'.
     */
    // public static <T super Integer> void illegal(T item) { } // COMPILER ERROR

    /**
     * ❌ RULE: You generally shouldn't return a lower-bounded wildcard.
     * It forces the caller to treat the return value as 'Object'⭐.
     */
    public static List<? super Integer> badReturn() {
        return new ArrayList<Number>();
    }

    /* =========================================================
       5. ⭐⭐ PECS SUMMARY
       =========================================================
    */
    // Producer Extends: List<? extends T> - Use when you only GET values.
    // Consumer Super:   List<? super T>   - Use when you only PUT values.

}