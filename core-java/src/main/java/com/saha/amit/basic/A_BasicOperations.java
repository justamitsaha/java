package com.saha.amit.basic;

import com.github.javafaker.Faker;
import com.saha.amit.dto.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class A_BasicOperations {

    private static final Logger logger = LoggerFactory.getLogger(A_BasicOperations.class);
    public static final Faker faker = new Faker();

    public static void main(String[] args) {
        // Only i initialized not j also can use un-initialized local variables Class level variables get default values
        int i, j = 8;
        int k = 8, l = 8; //both initialized
        logger.info("k *= 2 equals: {} ", k *= 2);
        logger.info("k += 4 equals: {} ", k += 4);
        logger.info("k -= 4 equals: {} ", k -= 4);
        logger.info("k /= 2 equals: {} ", k /= 2);

        callByValue();
        incrementDecement();

    }


    public static void incrementDecement() {
        int i = 5;
        i = i++ + ++i;
        logger.info("Value of i++ + ++i is:{}", i);
        int a = 1;
        a = a++ + a + ++a;
        logger.info("Value of a++ +a+ ++a is:{}", a);

        int x = 10;
        int y = (x = 5) + 2;
        logger.info("value of x and y is {} , {}", x, y);

        i = 0;
        logger.info("value of i after i = 3 * 5 is {}", i = 3 * 5);
        logger.info("i = {}", i);

        /*
        The first condition (x > 0) is false,
        so the second (++x > 0) never executes because of short-circuit AND (&&).
         */
        x = 0;
        boolean b = (x > 0) && (++x > 0);
        logger.info("x = {}", x);
        y = 0;
        boolean b2 = (y > 0) & (++y > 0);
        logger.info("y = {}", y);

        // Automatic casting happening and changes the return type
        logger.info("Ternary operation {}", faker.bool().bool() ? 1 : "hello");
        logger.info("Ternary operation {}", faker.bool().bool() ? 1 : 1.5);

        /*a = 31536000000; won't compile as it is out of range
        but for expression with value greater than range int overflow happens silently —result wraps around.*/
        a = Integer.MAX_VALUE + Integer.MAX_VALUE;
        logger.info("a, {}", a);

        byte b3 = 10;
        //cast needed as Because all arithmetic promotes operands to int.
        b3 = (byte) (b3 * 2);

        //✅ true — int promoted to double
        logger.info("10 == 10.0 does auto casting {}", 10 == 10.0);

        //Cashing
        Integer o = 128, p = 128;
        logger.info("o==p {}", (o == p));
        o = 127;
        p = 127;
        logger.info("o==p {}", (o == p));
        o = p = 128;
        logger.info("o==p {}", (o == p));

    }

    public static void callByValue() {
        int k = 8;
        // Call be Value k is not changed
        logger.info("Value of K: {}", k);  // value 8
        doubleValue(k);
        logger.info("Value of K: {}", k);  //value still 8

        //Still call be value but for objects value is the memory location
        Employee employee = new Employee("Amit", 20);
        changeEmployee(employee);
        logger.info("Employee details: {}", employee.toString()); //value changed
    }

    public static void doubleValue(int i) {
        i = i * 2;
    }

    public static void changeEmployee(Employee employee) {
        employee.setName("Adam");
        employee.setSalary(34);
    }


}

