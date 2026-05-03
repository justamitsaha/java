package com.saha.amit.generics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A comprehensive guide to every common Upper Bound combination in Java Generics.
 */
public class UltimateUpperBounds {

    public static void main(String[] args) {
        noReturnListInput(List.of(1, 2, 3));
        System.out.println("--- 1. Multiple Bounds ---");
        handleMultiBound(10); // Integer is a Number & Comparable

        System.out.println("\n--- 2. Recursive Bounds ---");
        List<String> names = List.of("Zebra", "Apple", "Mango");
        System.out.println("Max: " + findMax(names));

        System.out.println("\n--- 3. Multiple Types ---");
        combine(10, 20.5);

        System.out.println("\n--- 4. Nested Bounds ---");
        List<List<Integer>> matrix = List.of(List.of(1, 2), List.of(3, 4));
        processMatrix(matrix);
    }

    /* =========================================================
       1. ⭐⭐ SINGLE BOUND (The Basics)
       =========================================================
    */

    // Compiler won't know what is T so we have to define the type of T in method signature so that it can be used inside the method body
    // Can't user singleObject(<? extends Number> t) This can be used only for parameterized obj like singleObject(List<? extends Number> list)
    public static <T extends Number> void noReturn(T item) {
        System.out.println(item.doubleValue());
    }

    // Bound on a Parameterized Type (Wildcard): Best for "Read-Only" access.
    // Idiomatic: Use this when you don't need to read data(e.g. var x = list) or return T or link it to other params.
    public static void noReturnListInput(List<? extends Number> list) {
        // Compiler treats this as ? extends Number, so we can only assign it to a Number reference, not T.
        var x = list.getFirst();
        System.out.println("Type -->"+(x instanceof Number));
        Number n = list.getFirst();
    }


    /* =========================================================
       2. ⭐⭐RECURSIVE BOUNDS (Self-Referential)
       =========================================================
    */

    /**
     * This ensures T is comparable to itself.
     * Use when the type T needs to be "remembered" for returns, logic, or linking to inputs parameters.
     */
    public static <T extends Comparable<T>> T findMax(List<T> list) {
        T max = list.getFirst();
        for (T item : list) {
            if (item.compareTo(max) > 0) max = item;
        }
        return max;
    }

    /*Same as above but with no return type and just printing the elements with type-specific logic.
    We can refer to <T> here and use it for type-specific logic.*/
    public static <T extends Number> void noReturn3(List<T> list) {
        for (T element : list) {
            //Since we have T, we can refer to the specific type of Number that was passed in, and we can perform type-specific logic based on that type.
            if (element instanceof Float) {
                System.out.println("Float: " + element);
            } else if (element instanceof Integer) {
                System.out.println("Integer: " + element);
            } else {
                System.out.println("Other Number: " + element);
            }
        }
    }

    // ❌ Here T is unnecessary and not required as it's not linking to anything or doing anything
    public static <T> void noReturn1(List<? extends Number> list) {
        System.out.println(list);
    }


    // ❌ The "Broken" Relationship, Inout and Out put are not linked so if we try to return value from input
    //Compiler treats it as ?  doesn't consider it of type T
    public static <T extends Number> T returnValue2(List<? extends Number> list) {
        return null;
    }
    /* =========================================================
       3. MULTIPLE BOUNDS (Intersection Types)
       =========================================================
    */

    /**
     * RULE: Only ONE class is allowed, and it MUST be first.
     * You can have multiple interfaces after.
     */
    public static <T extends Number & Comparable<T> & Serializable> void handleMultiBound(T item) {
        // We can use Number methods
        double d = item.doubleValue();
        // AND we can use Comparable methods
        int cmp = item.compareTo(item);
        System.out.println("Handled item with 3 bounds: " + item);
    }



    /* =========================================================
       4. MULTIPLE TYPE PARAMETERS
       =========================================================
    */

    /**
     * You can declare multiple independent bounds.
     * Useful for methods that process different types of inputs together.
     */
    public static <T extends Number, U extends Number> void combine(T first, U second) {
        double sum = first.doubleValue() + second.doubleValue();
        System.out.println("Combined " + first.getClass().getSimpleName() +
                " and " + second.getClass().getSimpleName() + ": " + sum);
    }

    /* =========================================================
       5. NESTED BOUNDS
       =========================================================
    */

    /**
     * Used for complex data structures like a list of lists.
     * Here, the outer list contains elements that are themselves lists of numbers.
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
       6. CONSTRUCTOR BOUNDS (Generic Classes)
       =========================================================
    */

    // You can also apply these bounds to the entire class
    public static class Box<T extends Number & Cloneable> {
        private T content;

        public Box(T content) {
            this.content = content;
        }

        public T getContent() {
            return content;
        }
    }
}