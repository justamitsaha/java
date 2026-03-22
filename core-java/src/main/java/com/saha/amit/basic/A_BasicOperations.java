package com.saha.amit.basic;


import com.saha.amit.dto.Employee;

import java.util.Random;


public class A_BasicOperations {

    
    public static final Random random = new Random();

    public static void main(String[] args) {
        // Only i initialized not j also can use un-initialized local variables Class level variables get default values
        int i, j = 8;
        int k = 8, l = 8; //both initialized
        System.out.printf("k *= 2 equals: %s %n", k *= 2);
        System.out.printf("k += 4 equals: %s %n", k += 4);
        System.out.printf("k -= 4 equals: %s %n", k -= 4);
        System.out.printf("k /= 2 equals: %s %n", k /= 2);

        callByValue();
        incrementDecement();

    }


    public static void incrementDecement() {
        int i = 5;
        i = i++ + ++i;
        System.out.printf("Value of i++ + ++i is:%s%n", i);
        int a = 1;
        a = a++ + a + ++a;
        System.out.printf("Value of a++ +a+ ++a is:%s%n", a);

        int x = 10;
        int y = (x = 5) + 2;
        System.out.printf("value of x and y is %s , %s%n", x, y);

        i = 0;
        System.out.printf("value of i after i = 3 * 5 is %s%n", i = 3 * 5);
        System.out.printf("i = %s%n", i);

        /*
        The first condition (x > 0) is false,
        so the second (++x > 0) never executes because of short-circuit AND (&&).
         */
        x = 0;
        boolean b = (x > 0) && (++x > 0);
        System.out.printf("x = %s%n", x);
        y = 0;
        boolean b2 = (y > 0) & (++y > 0);
        System.out.printf("y = %s%n", y);

        // Automatic casting happening and changes the return type
        System.out.printf("Ternary operation %s%n", random.nextBoolean() ? 1 : "hello");
        System.out.printf("Ternary operation %s%n", random.nextBoolean() ? 1 : 1.5);

        /*a = 31536000000; won't compile as it is out of range
        but for expression with value greater than range int overflow happens silently —result wraps around.*/
        a = Integer.MAX_VALUE + Integer.MAX_VALUE;
        System.out.printf("a, %s%n", a);

        byte b3 = 10;
        //cast needed as Because all arithmetic promotes operands to int.
        b3 = (byte) (b3 * 2);

        //✅ true — int promoted to double
        System.out.printf("10 == 10.0 does auto casting %s%n", 10 == 10.0);

        //Cashing
        Integer o = 128, p = 128;
        System.out.printf("o==p %s%n", (o == p));
        o = 127;
        p = 127;
        System.out.printf("o==p %s%n", (o == p));
        o = p = 128;
        System.out.printf("o==p %s%n", (o == p));

    }

    public static void callByValue() {
        int k = 8;
        // Call be Value k is not changed
        System.out.printf("Value of K: %s%n", k);  // value 8
        doubleValue(k);
        System.out.printf("Value of K: %s%n", k);  //value still 8

        //Still call be value but for objects value is the memory location
        Employee employee = new Employee("Amit", 20);
        changeEmployee(employee);
        System.out.printf("Employee details: %s%n", employee.toString()); //value changed
    }

    public static void doubleValue(int i) {
        i = i * 2;
    }

    public static void changeEmployee(Employee employee) {
        employee.setName("Adam");
        employee.setSalary(34);
    }


}

