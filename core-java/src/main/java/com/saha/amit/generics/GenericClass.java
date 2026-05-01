package com.saha.amit.generics;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class GenericClass {
    static class Box<T> {
        T t;

        public T getT() {
            return t;
        }

        public void setT(T t) {
            this.t = t;
        }

        public boolean isEmpty() {
            return null == t;
        }
    }

    static class Pair<K, V> {
        private K k;
        private V v;

        @Override
        public String toString() {
            return "Pair{" + "k=" + k + ", v=" + v + '}';
        }
    }

    static class Employee {
        String id;
        String dept;
        int salary;

        public Employee(String id, String dept, int salary) {
            this.id = id;
            this.dept = dept;
            this.salary = salary;
        }
    }

    static class Triple<A, B, C> {
        private final A a;
        private final B b;
        private final C c;

        public Triple(A a, B b, C c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }

        @Override
        public String toString() {
            return "Triple{a=" + a + ", b=" + b + ", c=" + c + '}';
        }
    }

    static class GenericMethods {
        public static <T> void print(T t) {
            if (null != t)
                System.out.println(t.toString());
        }

        public static <T> T findFirst(List<T> list) {
            return list.isEmpty() ? null : list.getFirst();
        }

        public static <T> List<T> swap(List<T> t, int i, int j) {
            if (null != t && t.size() > Integer.max(i, j)) {
                T temp = t.get(j);
                t.set(j, t.get(i));
                t.set(i, temp);
            }
            return t;
        }

        public static <T extends Number> Double sum(List<T> list) {
            double d = 0d;
            for (T t : list) {
                d = d + t.doubleValue();
            }
            return d;
        }


    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n========= Stream Practice Menu =========");
        System.out.println("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Results ==>");
        switch (choice) {
            case 1 -> basicGenericTypes();
            case 2 -> genericKeyPair();
            case 3 -> immutableTripleGeneric();
            case 4 -> {
                String s = "amit";
                Integer i = 1;
                Employee employee = new Employee("1", "HR", 123);
                List<String> list = List.of("car", "bike", "dick");
                String s2 = null;
                GenericMethods.print(s);
                GenericMethods.print(i);
                GenericMethods.print(employee);
                GenericMethods.print(list);
                GenericMethods.print(s2);
            }
            case 5 -> {
                List<Integer> integers = new ArrayList<>();
                List<String> strings = List.of("SAM", "BAM", "TAM");
                List<Employee> employees = List.of(new Employee("1", "IT", 45000), new Employee("2", "HR", 50_000));
                System.out.println(GenericMethods.findFirst(integers));
                System.out.println(GenericMethods.findFirst(strings));
                System.out.println(GenericMethods.findFirst(employees));
            }
            case 6 -> {
                List<Integer> integers = Arrays.asList(1, 2, 3, 4);
                List<String> strings = new ArrayList<>();
                List<String> strings1 = null;
                System.out.println(GenericMethods.swap(integers, 1, 3));
                System.out.println(GenericMethods.swap(strings, 1, 3));
                System.out.println(GenericMethods.swap(strings, 0, 0));
            }
            default -> System.out.println("Invalid Choice");
        }
    }

    /**
     * 1
     */
    public static void basicGenericTypes() {
        Box<String> stringBox = new Box<>();
        stringBox.setT("Hello");
        System.out.println(stringBox.getT());
        System.out.println(stringBox.isEmpty());

        Box<Integer> integerBox = new Box<>();
        System.out.println(integerBox.isEmpty());
        integerBox.setT(222);
        System.out.println(integerBox.getT());

        Box<Employee> employeeBox = new Box<>();
        employeeBox.setT(new Employee("1", "HR", 5656));
        System.out.println(employeeBox.getT());
        System.out.println(employeeBox.isEmpty());
    }

    /**
     * 2
     */
    public static void genericKeyPair() {
        Pair<Integer, String> integerStringPair = new Pair<>();
        Pair<String, List<String>> stringListPair = new Pair<>();
        integerStringPair.k = 99;
        integerStringPair.v = "HEllO";
        stringListPair.k = "DELTA";
        var x = List.of("RAM", "JAM", "PAM");

        System.out.println(integerStringPair.toString());
        System.out.println(stringListPair.toString());
    }

    /**
     * 3
     */
    public static void immutableTripleGeneric() {
        Triple<String, Integer, Double> triple = new Triple<>("a", 1, 1.1d);
        System.out.println(triple.toString());
    }

}
