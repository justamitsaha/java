package testing;

import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Test {

    record Product(
            int id,
            String name,
            String category,
            double price) {
    }

    record Order(
            int id,
            int customerId,
            List<Product> products,
            double total) {
    }

    static List<Employee> employees = Arrays.asList(
            new Employee(101, "Amit", "IT", 25, 50000),
            new Employee(102, "Rahul", "HR", 30, 65000),
            new Employee(103, "Neha", "IT", 27, 55000),
            new Employee(104, "Pooja", "FIN", 32, 70000),
            new Employee(105, "Karan", "IT", 29, 52000),
            new Employee(106, "Simran", "HR", 30, 65000),
            new Employee(107, "Vikram", "OPS", 40, 80000),
            new Employee(108, "Amit", "IT", 25, 50000), // duplicate data, diff id
            new Employee(109, "Riya", "FIN", 28, 70000),
            new Employee(110, "Zara", "OPS", 35, 78000));

    static List<Product> products = Arrays.asList(
            new Product(1, "Laptop", "Electronics", 70000),
            new Product(2, "Phone", "Electronics", 30000),
            new Product(3, "Mouse", "Electronics", 800),
            new Product(4, "Shirt", "Clothing", 2000),
            new Product(5, "Jeans", "Clothing", 3000),
            new Product(6, "Book", "Books", 500),
            new Product(7, "Notebook", "Books", 200),
            new Product(8, "Laptop", "Electronics", 70000) // duplicate
    );

    static List<Integer> numbers = Arrays.asList(4, 8, 26, 8, 5, 98, 56, 8, 32, 11, 9, 4, 77, 7, 3, 2, 5, 5, 3, 2,
            1);

    static List<Integer> smallList = new ArrayList<>(List.of(1, 2, 3, 4, 5, 1));

    static class Student{
        int id;
        int marks;

        Student(int id, int marks){
            this.id = id;
            this.marks = marks;
        }

        @Override
        public String toString() {
            return "Student{id=" + id + ", marks=" + marks + '}';
        }
    }
    record Employee(
            int id,
            String name,
            String dept,
            int age,
            double salary) {
    }
    public static void main(String[] args) {
        List<Integer> lst = new ArrayList<Integer>();
        lst.add(1);
        System.out.println("Hi"+lst);
    }

    public static void test(List<Integer> lst){
        lst.add(2);
    }

}
