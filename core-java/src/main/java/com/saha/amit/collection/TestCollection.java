package com.saha.amit.collection;

import java.util.*;

public class TestCollection {

    record Employee(
            int id,
            String name,
            String dept,
            int age,
            double salary
    ) {
    }

    record Product(
            int id,
            String name,
            String category,
            double price
    ) {
    }

    record Order(
            int id,
            int customerId,
            List<Product> products,
            double total
    ) {
    }

    List<Employee> employees = List.of(
            new Employee(101, "Amit", "IT", 25, 50000),
            new Employee(102, "Rahul", "HR", 30, 65000),
            new Employee(103, "Neha", "IT", 27, 55000),
            new Employee(104, "Pooja", "FIN", 32, 70000),
            new Employee(105, "Karan", "IT", 29, 52000),
            new Employee(106, "Simran", "HR", 30, 65000),
            new Employee(107, "Vikram", "OPS", 40, 80000),
            new Employee(108, "Amit", "IT", 25, 50000), // duplicate data, diff id
            new Employee(109, "Riya", "FIN", 28, 70000),
            new Employee(110, "Zara", "OPS", 35, 78000)
    );

    List<Product> products = List.of(
            new Product(1, "Laptop", "Electronics", 70000),
            new Product(2, "Phone", "Electronics", 30000),
            new Product(3, "Mouse", "Electronics", 800),
            new Product(4, "Shirt", "Clothing", 2000),
            new Product(5, "Jeans", "Clothing", 3000),
            new Product(6, "Book", "Books", 500),
            new Product(7, "Notebook", "Books", 200),
            new Product(8, "Laptop", "Electronics", 70000) // duplicate
    );

    static List<Integer> numbers =
            List.of(4, 8, 26, 8, 5, 98, 56, 8, 32, 11, 9, 4, 77, 7, 3, 2, 5, 5, 3, 2, 1);
    static List<Integer> samllList = Arrays.asList(1, 2, 3, 4, 5, 6);

    public static void main(String[] args) {
        System.out.println(reverseListWithLoop(numbers));
        System.out.println(reverseListWithCollection(numbers));
        System.out.println(reverseUsingStack(numbers));
        System.out.println(rotate(samllList, 2, direction.RIGHT));

    }

    public static <T> List<T> reverseListWithLoop(List<T> list) {
        if (null == list || list.isEmpty())
            throw new IllegalArgumentException();
        List<T> reversed = new ArrayList<>();
        int count = 0;
        for (int i = list.size() - 1; i >= 0; i--) {
            System.out.println(i + " " + count);
            reversed.add(list.get(i));
            count++;
        }
        return reversed;
    }

    public static <T> List<T> reverseListWithCollection(List<T> list) {
        return list.reversed();
    }

    public static <T> List<T> reverseUsingStack(List<T> list) {
        if (null == list || list.isEmpty())
            throw new IllegalArgumentException();
        Stack<T> tStack = new Stack<>();
        for (T t : list)
            tStack.push(t);
        list.clear();
        for (T t : tStack) {
            var item = tStack.pop();
            list.add(item);
        }
        return list;
    }

    enum direction {RIGHT, LEFT}

    public static <T> List<T> rotate(List<T> list, int shift, direction direction) {
        if (null == list || list.isEmpty())
            throw new IllegalArgumentException();
        List<T> result = new ArrayList<>();
        if (direction == TestCollection.direction.RIGHT) {
            for (int i = list.size() - shift; i < list.size(); i++)
                result.add(list.get(i));
            for (int i = 0; i < list.size() - shift - 1; i++)
                result.add(list.get(i));
        } else {
            for (int i = shift; i < list.size(); i++)
                result.add(list.get(i));
            for (int i = 0; i < shift; i++)
                result.add(list.get(i));
        }
        return result;
    }

}
