package com.saha.amit.collection;

import java.util.*;

public class ArrayListPerformance2 {

    public static void main(String[] args) {
        int counter = 900000;
        int filter = 50_000;
        List<Employee> arrayList = new ArrayList<>();
        List<Employee> linkedList = new LinkedList<>();

        long start1 = System.currentTimeMillis();
        generateEmployee(counter, arrayList);
        long end1 = System.currentTimeMillis();
        System.out.println("Time taken to generate Array List With counter " + counter + " Time taken " + (end1 - start1));

        start1 = System.currentTimeMillis();
        generateEmployee(counter, linkedList);
        end1 = System.currentTimeMillis();
        System.out.println("Time taken to generate Linked List With counter " + counter + " Time taken " + (end1 - start1));

        start1 = System.currentTimeMillis();
        filterEmployee(filter, arrayList);
        end1 = System.currentTimeMillis();
        System.out.println("Time taken to Filter Array List With counter " + counter + " Time taken " + (end1 - start1));

        start1 = System.currentTimeMillis();
        filterEmployee(filter, linkedList);
        end1 = System.currentTimeMillis();
        System.out.println("Time taken to Filter Linked List With counter " + counter + " Time taken " + (end1 - start1));
    }

    public static List<Employee> generateEmployee(int count, List<Employee> list) {
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            Employee employee = new Employee(i, "Hello" + i, random.nextInt(100, 100_000));
            list.add(employee);
        }

        return list;
    }

    public static List<Employee> filterEmployee(int filter, List<Employee> list) {
        list.removeIf(employee -> employee.salary > filter);
        return list;
    }

    static class Employee {
        int id;
        String name;
        int salary;

        public Employee(int id, String name, int salary) {
            this.id = id;
            this.name = name;
            this.salary = salary;
        }
    }

}




