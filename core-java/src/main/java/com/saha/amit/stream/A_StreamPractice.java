package com.saha.amit.stream;


import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


/**
 * Stream Practice Workbook
 * Author: Amit Saha
 * <p>
 * 25 Problems to master Java Stream API
 * -------------------------------------
 * Each method demonstrates one concept:
 * - filter, map, collect
 * - reduce, grouping, partitioning
 * - flatMap, sorting, distinct, etc.
 */

public class A_StreamPractice {
    static List<Employee> employees = new ArrayList<>();

    record Employee(String name, String dept, double salary) {
    }


    static {
        Random random = new Random();
        for (int i = 0; i < 15; i++) {
            var employee = new Employee(
                    "Hello" + i,
                    random.nextInt(1, 5) + "", // dept 1 to 4
                    random.nextDouble(1000, 10000) // salary between 1000 and 10000
            );
            System.out.println(employee);
            employees.add(employee);
        }

    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n========= Stream Practice Menu =========");
        while (true) {
            /* Uncomment to see options
            System.out.println("1. Filter and Map Example");
            System.out.println("2. Square Numbers");
            System.out.println("3. Names Starting with A");
            System.out.println("4. Convert to Uppercase");
            System.out.println("5. Count Numbers Greater Than 10");
            System.out.println("6. Remove Duplicates and Sort");
            System.out.println("7. Top 3 Highest Numbers");
            System.out.println("8. Skip and Sum");
            System.out.println("9. Find Max with Reduce");
            System.out.println("10. Product of Elements");
            System.out.println("11. Get Employee Names");
            System.out.println("12. Find Employees in Department");
            System.out.println("13. Find Employee with Highest Salary");
            System.out.println("14. Average Salary in Department");
            System.out.println("15. Group Employees by Department");
            System.out.println("16. Total Salary per Department");
            System.out.println("17. Word Frequency Counter");
            System.out.println("18. Flatten Nested List");
            System.out.println("19. Unique Characters from Words");
            System.out.println("20. Partition Even and Odd Numbers");
            System.out.println("21. Find Second Highest Salary");
            System.out.println("22. Common Elements Between Lists");
            System.out.println("23. Fibonacci Using Stream");
            System.out.println("24. Palindrome Words");
            System.out.println("25. Sort Map by Value Descending");
            System.out.println("0. Exit");*/
            System.out.println("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> findEven_FilterCollect();
                case 2 -> squareOfNumbers();
                case 3 -> namesStartingWithA();
                case 4 -> convertToUppercase();
                case 5 -> countOfNumbersGreaterThan10();        //*
                case 6 -> removeDuplicatesAndSort();            //*
                case 7 -> topThreeHighest();                    //*
                case 8 -> skipFiveAndSum();                     //**
                case 9 -> findMax();                            //*
                case 10 -> productOfElements();                 //*
                case 11 -> getEmployeeNames();
                case 12 -> findEmployeesInDept();
                case 13 -> findEmployeeWithHighestSalary();     //**
                case 14 -> averageSalaryInDept();
                case 15 -> groupByDepartment();
                case 16 -> totalSalaryPerDept();
                case 17 -> wordFrequency();
                case 18 -> flattenList();
                case 19 -> uniqueCharactersFromWords();
                case 20 -> partitionEvenOdd();
                case 21 -> secondHighestSalary();
                case 22 -> commonElementsBetweenLists();
                case 23 -> fibonacciStream();
                case 24 -> palindromeWords();
                case 25 -> sortMapByValueDescending();
                case 0 -> {
                    System.out.println("Goodbye 👋");
                    return;
                }
                default -> System.out.println("Invalid choice! Try again.");
            }
        }
    }

    // 1️⃣ filter even number from List
    public static void findEven_FilterCollect() {
        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9);
        List<Integer> evens = numbers.stream()
                .filter(n -> n % 2 == 0)
                .collect(Collectors.toList());
        System.out.printf("Even numbers: %s%n", evens);
    }

    // 2️⃣ Square of each number of list
    public static void squareOfNumbers() {
        List<Integer> numbers = List.of(2, 3, 4, 5);
        List<Integer> squares = numbers.stream()
                .map(n -> n * n)
                .toList();
        System.out.printf("Squares: %s%n", squares);
    }

    // 3️⃣ Names staring with A
    public static void namesStartingWithA() {
        List<String> names = List.of("Amit", "Rahul", "Ankit", "Ravi");
        List<String> result = names.stream()
                .filter(n -> n.startsWith("A"))
                .toList();
        System.out.printf("Names starting with A: %s%n", result);
    }

    // 4️⃣ Convert List to Uppercase
    public static void convertToUppercase() {
        List<String> names = List.of("amit", "rahul", "ankit");
        List<String> upper = names.stream()
                .map(String::toUpperCase)
                .toList();
        System.out.printf("Uppercase: %s%n", upper);
    }

    // 5️⃣ *Find the count of number greater than 10
    public static void countOfNumbersGreaterThan10() {
        List<Integer> numbers = List.of(5, 10, 15, 20, 25);
        long count = numbers.stream()
                .filter(n -> n > 10)
                .count();
        System.out.printf("Numbers > 10: %s%n", count);
    }

    // 6️⃣ Find Distinct and sort reversed
    public static void removeDuplicatesAndSort() {
        List<Integer> numbers = List.of(3, 5, 3, 9, 1, 9);
        List<Integer> sorted = numbers.stream()
                .distinct()
                .sorted()
                .toList()
                .reversed();
        System.out.printf("Distinct + Sorted: %s%n", sorted);
    }

    // 7️⃣ Find top 3 highest
    public static void topThreeHighest() {
        List<Integer> numbers = List.of(10, 40, 20, 70, 30);
        List<Integer> top3 = numbers.stream()
                .sorted(Comparator.reverseOrder())
                .limit(3)
                .toList();
        System.out.printf("Top 3 numbers: %s%n", top3);
    }

    // 8️⃣ Skip first 5 and add
    public static void skipFiveAndSum() {
        int sum = IntStream.rangeClosed(1, 10)
                .skip(5)
                .sum();
        System.out.printf("Sum after skipping first 5: %s%n", sum);
        List<Integer> numbers = List.of(3, 5, 3, 9, 1, 9, 8, 6, 4);
        int sum2 = numbers
                .stream()
                .skip(5)
                .mapToInt(value -> value)
                .sum();
        System.out.printf("Sum after skipping first 5: %s%n", sum2);
    }

    // 9️⃣ Find max
    public static void findMax() {
        List<Integer> numbers = List.of(3, 10, 5, 7, 15, 2);
        int max = numbers.stream()
                .reduce(Integer::max)
                .orElse(-1);
        System.out.printf("Max value: %s%n", max);

        var x = numbers.stream()
                .mapToInt(value -> value)
                .max()
                .orElse(-1);
        System.out.println(x);

        x = numbers.stream()
                .reduce(-1, (integer, integer2) -> ((integer - integer2) > 0) ? integer : integer2);
        System.out.println(x);
    }

    // 🔟 Reduce (product)
    public static void productOfElements() {
        List<Integer> numbers = List.of(2, 3, 4);
        int product = numbers.stream()
                .reduce(1, (a, b) -> a * b);
        System.out.printf("Product: %s%n", product);

        List<Integer> integers = List.of(4, 8, 26, 8, 5, 98, 56, 8, 32, 11, 9, 4, 77, 7, 3, 2, 5, 5, 3, 2, 1);

        var x = integers.stream()
                //Since it may go out of range
                .mapToDouble(value -> value)
                .reduce((integer, integer2) -> integer * integer2);
        System.out.println(x);
    }

    // Object use case using Employee object

    // 11️⃣ Change to Employee names
    public static void getEmployeeNames() {
        List<String> names = employees.stream()
                .map(Employee::name)
                .toList();
        System.out.printf("Employee names: %s%n", names);
    }

    // 12️⃣ Filter by field
    public static void findEmployeesInDept() {
        List<Employee> itEmployees = employees.stream()
                .filter(e -> e.dept().equals("3"))
                .toList();
        System.out.printf("Employee in department 3 %s%n", itEmployees);
    }

    // 13️⃣ Find employee with the highest salary
    public static void findEmployeeWithHighestSalary() {
        Employee max = employees.stream()
                .max(Comparator.comparing(Employee::salary))
                .orElse(null);
        System.out.printf("Highest Salary: %s%n", max);

        var x = employees.stream()
                .max((o1, o2) -> Double.compare(o1.salary, o2.salary))
                .orElse(null);
        System.out.println(x);

        x = employees.stream()
                .max(Comparator.comparingDouble(Employee::salary))
                .orElse(null);
        System.out.println(x);

        x = employees.stream()
                .reduce((employee, employee2) -> (employee.salary() > employee2.salary()) ? employee : employee2)
                .orElse(null);
        System.out.println(x);
    }

    // 14️⃣ Average Department 3 salary
    public static void averageSalaryInDept() {
        double avg = employees.stream()
                .filter(e -> e.dept().equals("3"))
                .mapToDouble(Employee::salary)
                .average()
                .orElse(0);
        System.out.printf("Average Department 3 salary: %s%n", avg);
    }

    // 15️⃣ Grouping by department
    public static void groupByDepartment() {
        Map<String, List<Employee>> grouped = employees.stream()
                .collect(Collectors.groupingBy(Employee::dept));
        System.out.printf("Grouped by dept: %s%n", grouped);
    }

    // 16️⃣ Summing by group
    public static void totalSalaryPerDept() {
        Map<String, Double> totals = employees.stream()
                .collect(Collectors.groupingBy(Employee::dept,
                        Collectors.summingDouble(Employee::salary)));
        System.out.printf("Total salary per dept: %s%n", totals);
    }

    // 17️⃣ Word frequency
    public static void wordFrequency() {
        String text = "Java Stream API makes Java powerful and fun";
        Map<String, Long> freq = Arrays.stream(text.toLowerCase().split("\\s+"))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        System.out.printf("Word frequency: %s%n", freq);
    }

    // 18️⃣ FlatMap
    public static void flattenList() {
        List<List<Integer>> listOfLists = List.of(
                List.of(1, 2), List.of(3, 4), List.of(5, 6)
        );
        List<Integer> flat = listOfLists.stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        System.out.printf("Flattened list: %s%n", flat);
    }

    // 19️⃣ Unique characters
    public static void uniqueCharactersFromWords() {
        List<String> words = List.of("java", "stream", "rocks");
        List<Character> chars = words.stream()
                .flatMap(w -> w.chars().mapToObj(c -> (char) c))
                .distinct()
                .toList();
        System.out.printf("Unique characters: %s%n", chars);
    }

    // 20️⃣ Partitioning
    public static void partitionEvenOdd() {
        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6);
        Map<Boolean, List<Integer>> partitioned = numbers.stream()
                .collect(Collectors.partitioningBy(n -> n % 2 == 0));
        System.out.printf("Partitioned: %s%n", partitioned);
    }

    // 21️⃣ Second-highest salary
    public static void secondHighestSalary() {
        Optional<Double> second = employees.stream()
                .map(Employee::salary)
                .sorted(Comparator.reverseOrder())
                .skip(1)
                .findFirst();
        System.out.printf("Second highest salary: %s%n", second.orElse(0.0));
    }

    // 22️⃣ Common elements
    public static void commonElementsBetweenLists() {
        List<Integer> list1 = List.of(1, 2, 3, 4, 5);
        List<Integer> list2 = List.of(4, 5, 6, 7);
        List<Integer> common = list1.stream()
                .filter(list2::contains)
                .toList();
        System.out.printf("Common elements: %s%n", common);
    }

    // 23️⃣ Fibonacci with streams
    public static void fibonacciStream() {
        List<Long> fib = Stream.iterate(new long[]{0, 1}, f -> new long[]{f[1], f[0] + f[1]})
                .limit(10)
                .map(f -> f[0])
                .toList();
        System.out.printf("Fibonacci series: %s%n", fib);
    }

    // 24️⃣ Palindrome words
    public static void palindromeWords() {
        String text = "madam level radar hello world";
        List<String> palindromes = Arrays.stream(text.split(" "))
                .filter(s -> new StringBuilder(s).reverse().toString().equalsIgnoreCase(s))
                .toList();
        System.out.printf("Palindromes: %s%n", palindromes);
    }

    // 25️⃣ Sort map by value descending
    public static void sortMapByValueDescending() {
        Map<String, Integer> map = Map.of("A", 3, "B", 1, "C", 5);
        LinkedHashMap<String, Integer> sorted = map
                .entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .collect(
                        Collectors.toMap(
                                Map.Entry::getKey,
                                Map.Entry::getValue,
                                (e1, e2) -> e1,
                                LinkedHashMap::new
                        ));
        System.out.printf("Sorted by value: %s%n", sorted);
        sorted = map
                .entrySet()
                .stream()
                .sorted((o1, o2) -> o1.getValue() - o2.getValue()) //Ascending
                //.sorted((o1, o2) -> o2.getValue()-o1.getValue()) //Descending
                .collect(Collectors.toMap(
                        o -> o.getKey(),
                        o -> o.getValue(),
                        (integer, integer2) -> integer,
                        () -> new LinkedHashMap<>()
                ));

    }
}
