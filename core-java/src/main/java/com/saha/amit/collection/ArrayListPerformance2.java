package com.saha.amit.collection;

import java.util.*;

public class ArrayListPerformance2 {

    static List<Employee> arrayList = new ArrayList<>();
    static List<Employee> linkedList = new LinkedList<>();
    static int counter = 900000;
    static int filter = 50_000;


    static {
        Random random = new Random();
        long start1 = System.currentTimeMillis();
        for (int i = 0; i < counter; i++) {
            Employee employee = new Employee(i, "Hello" + i, random.nextInt(100, 100_000));
            arrayList.add(employee);
        }
        long end1 = System.currentTimeMillis();
        System.out.println("Time taken to generate Array List With counter " + counter + " Time taken " + (end1 - start1));

        start1 = System.currentTimeMillis();
        for (int i = 0; i < counter; i++) {
            Employee employee = new Employee(i, "Hello" + i, random.nextInt(100, 100_000));
            linkedList.add(employee);
        }
        end1 = System.currentTimeMillis();
        System.out.println("Time taken to generate Linked List With counter " + counter + " Time taken " + (end1 - start1));
    }

    public static void main(String[] args) {
        long start1 = System.currentTimeMillis();
        filterEmployee(filter, arrayList);
        long end1 = System.currentTimeMillis();
        System.out.println("Time taken to Filter Array List With counter " + counter + " Time taken " + (end1 - start1));

        start1 = System.currentTimeMillis();
        filterEmployee(filter, linkedList);
        end1 = System.currentTimeMillis();
        System.out.println("Time taken to Filter Linked List With counter " + counter + " Time taken " + (end1 - start1));

        start1 = System.currentTimeMillis();
        Collections.sort(arrayList, new SalaryComparator());
        end1 = System.currentTimeMillis();
        System.out.println("Time taken to Sort Array List With counter " + counter + " Time taken " + (end1 - start1));

        start1 = System.currentTimeMillis();
        Collections.sort(linkedList, new SalaryComparator());
        end1 = System.currentTimeMillis();
        System.out.println("Time taken to Sort Linked List With counter " + counter + " Time taken " + (end1 - start1));
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

    static class SalaryComparator implements Comparator<Employee> {

        @Override
        public int compare(Employee o1, Employee o2) {
            return o1.salary - o2.salary;
        }
    }

}




