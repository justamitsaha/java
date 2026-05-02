package com.saha.amit.generics;

import java.util.List;

public class Bounds {

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

    //💚💚💚💚💚💚💚  No return type Object input 💚💚💚💚💚💚💚

    // Here T is necessary because we want to refer to the specific type of Number that was passed in, and we want to perform type-specific logic based on that type.
    // We can't use ? here because ?: Used for parameter types (e.g., List<? extends Number>), not for declaring the parameter variable itself.
    private static <T extends Number> void noReturn(T t) {
        if (t instanceof Float) {
            System.out.println("Float: " + t);
        } else if (t instanceof Integer) {
            System.out.println("Integer: " + t);
        } else {
            System.out.println("Other Number: " + t);
        }
    }

    //💚💚💚💚💚💚💚  No return type Parameterized  input  like List<> 💚💚💚💚💚💚💚

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

    //💚💚💚💚💚💚💚  Return along with input 💚💚💚💚💚💚💚

    //✅We use T so the return type matches the list's specific type
    public static <T extends Number> T returnValue(List<T> list) {
        return list.getLast();
    }

    // ❌ The "Broken" Relationship, Inout and Out put are not linked so if we try to return value from input
    //Compiler treats it as ?  doesn't consider it of type T
    public static <T extends Number> T returnValue2(List<? extends Number> list) {
        return null;
    }


}
