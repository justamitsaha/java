package com.saha.amit.collection;

import java.util.*;

/*
    This class is to compare the performance of sort and random access operation in ArrayList and LinkedList
    We will be sorting a list of Employee objects based on their salary, and then we will be doing random access to get the salary of random employees and sum it up
    While we do random access, ArrayList should outperform LinkedList as it has O(1) time complexity for get operation, while LinkedList has O(n) time complexity for get operation.
For sorting, the performance difference may not be significant as both ArrayList and LinkedList use the same sorting algorithm (TimSort) which has O(n log n) time complexity. Linked list is converted to array before sorting hence the performance difference may not be significant.
 */
public class ListSortSearchPerformance {

    static final int SIZE = 90_000;
    static final int RANDOM_ACCESS_OPS = 20_000;
    static List<Employee> baseData = new ArrayList<>();


    public static void main(String[] args) {
        generateData();
        // Warm-up (important for JIT)
        for (int i = 0; i < 3; i++) {
            runAllTests(false);
        }
        System.out.println("==== FINAL RUN ====");
        runAllTests(true);
    }

    static void runAllTests(boolean print) {
        testSort(print);
        testRandomAccess(print);
        System.out.println();
    }


    // ---------------- SORT ----------------
    static void testSort(boolean print) {
        List<Employee> arrayList = new ArrayList<>(baseData);
        List<Employee> linkedList = new LinkedList<>(baseData);

        Comparator<Employee> comparator = Comparator.comparingInt(e -> e.salary);

        long start = System.nanoTime();
        arrayList.sort(comparator);
        long end = System.nanoTime();
        if (print) System.out.println("ArrayList sort: " + (end - start) / 1_000_000 + " ms");

        start = System.nanoTime();
        linkedList.sort(comparator);
        end = System.nanoTime();
        if (print) System.out.println("LinkedList sort: " + (end - start) / 1_000_000 + " ms");
    }

    // ---------------- RANDOM ACCESS ----------------
    static void testRandomAccess(boolean print) {
        List<Employee> arrayList = new ArrayList<>(baseData);
        List<Employee> linkedList = new LinkedList<>(baseData);
        Random random = new Random(42);

        long sum = 0;
        long start = System.nanoTime();
        for (int i = 0; i < RANDOM_ACCESS_OPS; i++) {
            sum += arrayList.get(random.nextInt(SIZE)).salary;
        }
        long end = System.nanoTime();
        if (print) System.out.println(sum + " ArrayList random access: " + (end - start) / 1_000_000 + " ms");

        sum = 0;
        start = System.nanoTime();
        for (int i = 0; i < RANDOM_ACCESS_OPS; i++) {
            sum += linkedList.get(random.nextInt(SIZE)).salary;
        }
        end = System.nanoTime();
        if (print) System.out.println(sum + " LinkedList random access: " + (end - start) / 1_000_000 + " ms");
    }

    // ---------------- DATA ----------------
    static void generateData() {
        Random random = new Random(42);

        for (int i = 0; i < SIZE; i++) {
            baseData.add(new Employee(i, "Emp" + i, random.nextInt(100, 100_000)));
        }
    }

    // ---------------- MODEL ----------------
    static class Employee {
        int id;
        String name;
        int salary;

        Employee(int id, String name, int salary) {
            this.id = id;
            this.name = name;
            this.salary = salary;
        }
    }
}

/*
==== FINAL RUN ====
ArrayList sort: 23 ms
LinkedList sort: 26 ms
1002776178 ArrayList random access: 2 ms
1002876935 LinkedList random access: 747 ms
 */