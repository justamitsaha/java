package com.saha.amit.generics;

import java.io.Serializable;
import java.util.List;

/**
 * A comprehensive guide to every common Upper Bound combination in Java Generics.
 */
public class UpperBounds {

    public static void main(String[] args) {
        // 1. Single Object Input
        noReturn(42.5f);

        // 2. Wildcard Input (Anonymous/Read-only)
        noReturnListInput(List.of(1, 2, 3));

        // 3. Recursive Bound (Comparable)
        List<String> names = List.of("Zebra", "Apple", "Mango");
        System.out.println("Max Name: " + findMax(names));

        // 4. Named T with Type-Specific Logic
        // Note: Using List<Number> here so T becomes Number
        noReturn3(List.of(1, 2, 3, 4.5f, 5.5));

        // 5. Multiple Bounds (Intersection)
        handleMultiBound(10); // Integer is a Number & Comparable

        // 6. Multiple Type Parameters
        combine(10, 20.5);

        // 7. Nested Bounds
        List<List<Integer>> matrix = List.of(List.of(1, 2), List.of(3, 4));
        processMatrix(matrix);
    }

    /* =========================================================
       1. ⭐⭐ SINGLE BOUND (The Basics)
       =========================================================
    */

    /**
     * Compiler needs the <T> declaration to use it in the method body.
     * We cannot use (<? extends Number> t) for a single object parameter.
     */
    public static <T extends Number> void noReturn(T item) {
        // We can use Number methods on T because we know T is a subtype of Number.
        if (item instanceof Integer) {
            System.out.println("Integer detected: " + item.intValue());
        } else if (item instanceof Double) {
            System.out.println("Double detected: " + item.doubleValue());
        } else {
            System.out.println("Other Number type: " + item);
        }
    }

    /**
     * Bound on a Parameterized Type (Wildcard): Best for "Read-Only" access.
     * Use this when you don't need to return T or link it to other parameters.
     */
    public static void noReturnListInput(List<? extends Number> list) {
        // list.getFirst() returns 'capture of ? extends Number'
        Number n = list.getFirst();
        System.out.println("Read from wildcard list: " + n + " (Is Number: " + (n instanceof Number) + ")");
    }


    /* =========================================================
       2. ⭐⭐ RECURSIVE BOUNDS (Self-Referential)
       =========================================================
    */

    /**
     * This ensures T is comparable to itself.
     * Use when the type T needs to be "remembered" for returns or linking.
     */
    public static <T extends Comparable<T>> T findMax(List<T> list) {
        T max = list.getFirst();
        for (T item : list) {
            if (item.compareTo(max) > 0) max = item;
        }
        return max;
    }

    /**
     * Using T to perform type-specific logic inside a loop.
     */
    public static <T extends Number> void noReturn3(List<T> list) {
        for (T element : list) {
            if (element instanceof Float) {
                System.out.println("Float value: " + element);
            } else if (element instanceof Integer) {
                System.out.println("Integer value: " + element);
            } else {
                System.out.println("Value: " + element + " (" + element.getClass().getSimpleName() + ")");
            }
        }
    }

    /**
     * ❌ REDUNDANT: T is unnecessary here because it's not used in logic or return.
     * Use noReturnListInput(List<? extends Number>) instead.
     */
    public static <T> void noReturn1(List<? extends Number> list) {
        System.out.println(list);
    }


    /**
     * ❌ BROKEN RELATIONSHIP: T is unlinked from the list.
     * The compiler cannot guarantee that list.get(0) is a T.
     */
    public static <T extends Number> T returnValue2(List<? extends Number> list) {
        // return list.getFirst(); // This would fail to compile!
        return null;
    }

    /* =========================================================
       3. ⭐⭐ MULTIPLE BOUNDS (Intersection Types)
       =========================================================
    */

    /**
     * RULE: Only ONE class is allowed, and it MUST be first.
     */
    public static <T extends Number & Comparable<T> & Serializable> void handleMultiBound(T item) {
        double d = item.doubleValue(); // From Number
        int cmp = item.compareTo(item); // From Comparable
        System.out.println("Handled intersection type: " + item);
    }

    /* =========================================================
       4. ⭐⭐ MULTIPLE TYPE PARAMETERS
       =========================================================
    */

    /**
     * Two independent bounds (T and U).
     */
    public static <T extends Number, U extends Number> void combine(T first, U second) {
        double sum = first.doubleValue() + second.doubleValue();
        System.out.println("Sum of " + first.getClass().getSimpleName() + " and " +
                second.getClass().getSimpleName() + " is: " + sum);
    }

    /* =========================================================
       5. ⭐⭐ NESTED BOUNDS
       =========================================================
    */

    public static void processMatrix(List<? extends List<? extends Number>> matrix) {
        for (List<? extends Number> row : matrix) {
            for (Number n : row) {
                System.out.print(n + " ");
            }
            System.out.println();
        }
    }

    /* =========================================================
       6. ⭐⭐ CONSTRUCTOR BOUNDS (Generic Classes)
       =========================================================
    */

    public static class Box<T extends Number & Cloneable> {
        private T content;
        public Box(T content) { this.content = content; }
        public T getContent() { return content; }
    }
}