package com.saha.amit.collection;

import java.security.InvalidParameterException;
import java.util.*;
import java.util.stream.Collectors;

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


    private static <T> List<T> reverseListWithLoop(List<T> list) {
        if (null == list || list.isEmpty())
            throw new IllegalArgumentException();
        List<T> reversed = new ArrayList<>();
        int count = 0;
        for (int i = list.size() - 1; i >= 0; i--) {
            reversed.add(list.get(i));
            count++;
        }
        return reversed;
    }


    private static <T> List<T> reverseListWithCollection(List<T> list) {
        return list.reversed();
    }

    private static <T> List<T> reverseUsingStack(List<T> list) {
        if (null == list || list.isEmpty())
            throw new IllegalArgumentException();
        Stack<T> stack = new Stack<>();
        for (T t : list)
            stack.push(t);
        /* When list created using
            1. Arrays.asList-> can't  change size so we can't call clear()
            2. List.of() -> No change allowed
        list.clear();
        for (T t : tStack) {
            var item = tStack.pop();
            list.add(item);
        }
        Hence have to switch to loop and set() method*/
        for (int i = 0; i < list.size(); i++)
            list.set(i, stack.pop());
        return list;
    }

    enum direction {RIGHT, LEFT}

    private static <T> List<T> rotate(List<T> list, int shift, direction direction) {
        System.out.println(list);
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

    private static <T> List<T> swap(List<T> list, int i, int j) {
        if (null == list || list.isEmpty() || list.size() < i || list.size() < j)
            throw new IllegalArgumentException();
        T temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
        return list;
    }

    private static Integer secondHighest(List<Integer> list) {
        if (null == list || list.isEmpty() || list.size() < 2)
            throw new IllegalArgumentException();
        list.sort(Comparator.reverseOrder());
        System.out.println(list.get(1));
        int max = 0;
        int secondMax = 0;
        for (Integer i : list) {
            if (i >= max)
                max = i;
            else if (i >= secondMax)
                secondMax = i;
        }
        return secondMax;

    }

    private static List<Integer> topThreeLargest(List<Integer> list) {
        if (null == list || list.isEmpty() || list.size() < 3)
            throw new IllegalArgumentException();
        List<Integer> top = list.stream()
                .sorted(Comparator.reverseOrder())
                .limit(3)
                .collect(Collectors.toList());
        System.out.println(top);
        top.clear();
        list.sort(Comparator.reverseOrder());
        for (int i = 0; i < 3; i++) {
            top.add(list.get(i));
        }
        return top;
    }

    private static List<Integer> removeDuplicate(List<Integer> list) {
        if (null == list || list.isEmpty())
            throw new IllegalArgumentException();
        List<Integer> unique = new ArrayList<>();
        Set<Integer> set = new HashSet<>();
        for (Integer i : list)
            if (set.add(i))
                unique.add(i);
        return unique;
    }

    private static void oddEven(List<Integer> list) {
        List<Integer> odd = new ArrayList<>();
        List<Integer> even = new ArrayList<>();
        for (Integer i : list)
            if (i % 2 == 0)
                even.add(i);
            else
                odd.add(i);
        System.out.println("Even " + even + " Odd " + odd);
    }

    private static Set<String> uniqueDepartment(List<Employee> employees) {
        Set<String> department = new HashSet<>();
        for (Employee employee : employees)
            department.add(employee.dept);
        return department;

    }

    private static void setOperations() {
        Set<Integer> a = Set.of(1, 2, 3, 4, 5);
        Set<Integer> b = Set.of(4, 5, 6, 7, 8);
        System.out.println("Union: " + a.addAll(b));
        System.out.println("Intersection:" + a.contains(b));
        System.out.println("Difference: " + a.removeAll(b));
    }

    private static List<Integer> detectDuplicates(List<Integer> list) {
        if (null == list || list.isEmpty())
            throw new IllegalArgumentException();
        List<Integer> duplicates = new ArrayList<>();
        Set<Integer> set = new HashSet<>();
        for (Integer i : list)
            if (!set.add(i))
                duplicates.add(i);
        return duplicates;
    }

    private static Set<Integer> nonRepeatingItems(List<Integer> list) {
        if (null == list || list.isEmpty())
            throw new IllegalArgumentException();
        Set<Integer> set = new HashSet<>();
        Set<Integer> duplicates = new HashSet<>();
        for (Integer i : list)
            if (!set.add(i))
                duplicates.add(i);
        set.removeAll(duplicates);
        return set;
    }

    public static void main(String[] args) {
        System.out.println("Reversed List: " + reverseListWithLoop(getData("smallList")));
        System.out.println("Reversed List: " + reverseListWithLoop(getData("products")));
        System.out.println("Reversed List: " + reverseListWithCollection(getData("smallList")));
        System.out.println("Reversed List: " + reverseUsingStack(getData("smallList")));
        System.out.println("Rotated List: " + rotate(getData("smallList"), 2, direction.RIGHT));
        System.out.println("Rotated List: " + rotate(getData("smallList"), 2, direction.LEFT));
        System.out.println("Swapped List: " + swap(getData("smallList"), 2, 4));
        System.out.println("Second Highest: " + secondHighest((List<Integer>) getData("numbers")));
        System.out.println("Top three: " + topThreeLargest((List<Integer>) getData("numbers")));
        System.out.println("Remove duplicates preserving insertion order: " + removeDuplicate((List<Integer>) getData("numbers")));
        oddEven((List<Integer>) getData("numbers"));
        System.out.println("Unique Dept: " + uniqueDepartment((List<Employee>) getData("employees")));
        //setOperations();
        System.out.println("Detect Duplicates: " + detectDuplicates((List<Integer>) getData("numbers")));
        System.out.println("Non repeating items : " + nonRepeatingItems((List<Integer>) getData("numbers")));


    }


    /**
     * Since static data was getting modified hence we are generating fresh unmodified data fo every operation
     *
     * @param type Which type of data we need
     * @param <T>  any
     * @return List<?>
     */
    public static <T> List<?> getData(String type) {
        switch (type) {
            case ("employees") -> {
                return Arrays.asList(
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
            }
            case ("products") -> {
                return Arrays.asList(
                        new Product(1, "Laptop", "Electronics", 70000),
                        new Product(2, "Phone", "Electronics", 30000),
                        new Product(3, "Mouse", "Electronics", 800),
                        new Product(4, "Shirt", "Clothing", 2000),
                        new Product(5, "Jeans", "Clothing", 3000),
                        new Product(6, "Book", "Books", 500),
                        new Product(7, "Notebook", "Books", 200),
                        new Product(8, "Laptop", "Electronics", 70000) // duplicate
                );
            }
            case ("numbers") -> {
                return Arrays.asList(4, 8, 26, 8, 5, 98, 56, 8, 32, 11, 9, 4, 77, 7, 3, 2, 5, 5, 3, 2, 1);
            }
            case ("smallList") -> {
                return Arrays.asList(1, 2, 3, 4, 5, 6);
            }
            default -> {
                throw new InvalidParameterException();
            }
        }
    }


}
