package com.saha.amit.generics;

import java.util.List;

public class UpperBounds {

    public static void main(String[] args) {
        noReturn(1.1f);
        noReturn(5L);
        System.out.println("========================");
        noReturn1(List.of(1.1f, 1.2f));
        System.out.println("========================");
        noReturn2(List.of(1.1f, 1.2f));
        noReturn2(List.of(2, 4));
        System.out.println("========================");
        noReturn3(List.of(1.1f, 1.2f));
        noReturn3(List.of(1, 2));
        System.out.println("========================");


        //noReturn3(List.of(2,4));
    }

    //1. ⭐⭐No return type Object input

    /* =========================================================
       1. SINGLE BOUND (The Basics)
       =========================================================
    */

    // Bound on a single object: Useful for internal logic/instanceof.
    public static <T extends Number> void singleObject(T item) {
        System.out.println(item.doubleValue());
    }


    // Compiler won't know what is T so we have to define the type of T in method signature so that it can be used inside the method body
    // Can't user noReturn(<? extends Number> t) as its not part of parameter like noReturn(List<? extends Number> list)
    private static <T extends Number> void noReturn(T t) {
        if (t instanceof Float) {
            System.out.println("Float: " + t);
        } else if (t instanceof Integer) {
            System.out.println("Integer: " + t);
        } else {
            System.out.println("Other Number: " + t);
        }
    }


    //2. ⭐⭐  No return type Parameterized  input  like List<>

    // ❌ Here T is unnecessary and not required as it's not linking to anything or doing anything
    public static <T> void noReturn1(List<? extends Number> list) {
        System.out.println(list);
    }

    // Here we haven't used T so we can't use type inside
    // Use when you just need to read data and don't need to refer to the specific type by name.
    public static void noReturn2(List<? extends Number> list) {
        for (Number element : list) {
            // We can treat 'element' as a Number, but we don't know its specific type (e.g., Integer, Float).
            System.out.println(element.doubleValue());
        }
    }

    // ✅T is now bound: it must be a Number, and the List MUST contain T objects.
    // Use when the type T needs to be "remembered" for returns, logic, or multiple arguments.
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

    //3. ⭐⭐ Return along with input

    //✅We use T so the return type matches the list's specific type
    public static <T extends Number> T returnValue(List<T> list) {
        return list.getLast();
    }




}
