package com.saha.amit.collection;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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


    public static void main(String[] args) {
        System.out.println(reverseList(numbers));

    }

    public static <T extends Objects> List<T> reverseList(List<T> list) {
        if (null == list || list.isEmpty())
            throw new IllegalArgumentException();
        List<T> reversed = new ArrayList<>();
        int count = 0;
        for (int i = list.size() - 1; i >= 0; i--) {
            reversed.set(count, list.get(i));
            count++;
        }
        return reversed;
    }
}
