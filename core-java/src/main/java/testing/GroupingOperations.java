package testing;

import java.util.*;
import java.util.stream.Collectors;

/*
groupingBy creates a Map in this general form:

stream.collect(
    Collectors.groupingBy(
        classifierFunction,
        downstreamCollector
    )
);

classifierFunction:
- decides the Map key
- groups elements by that key

downstreamCollector:
- decides what to do with elements in each group
- its result becomes the Map value

Examples:

groupingBy(Employee::dept)
-> Map<String, List<Employee>>
(default downstream is toList())

groupingBy(Employee::dept, counting())
-> Map<String, Long>

groupingBy(Employee::dept, summingDouble(Employee::salary))
-> Map<String, Double>

groupingBy(Employee::dept, mapping(Employee::name, toList()))
-> Map<String, List<String>>
*/
public class GroupingOperations {

    record Order(int id, String category, double amount) {
    }

    static List<Order> orders = List.of(
            new Order(1, "Electronics", 1200),
            new Order(2, "Books", 300),
            new Order(3, "Electronics", 800),
            new Order(4, "Clothing", 150),
            new Order(5, "Books", 500),
            new Order(6, "Clothing", 700)
    );

    record Student(String name, String dept, int marks) {
    }

    static List<Student> students = List.of(
            new Student("Amit", "CS", 80),
            new Student("Rahul", "CS", 90),
            new Student("Neha", "IT", 85),
            new Student("Pooja", "IT", 70),
            new Student("Karan", "ECE", 60)
    );

    static List<String> words = List.of("apple", "ant", "banana", "ball", "ball", "cat", "car", "cat", "dog", "doll");

    record Product(String name, String category, double price) {
    }

    static List<Product> products = List.of(
            new Product("Laptop", "Electronics", 70000),
            new Product("Phone", "Electronics", 30000),
            new Product("Shirt", "Clothing", 2000),
            new Product("Jeans", "Clothing", 3000),
            new Product("Book1", "Books", 500),
            new Product("Book2", "Books", 800)
    );

    record Employee(String name, String dept, int age, int salary) {
    }

    static List<Employee> employees = List.of(
            new Employee("B", "IT", 30, 70000),
            new Employee("A", "IT", 25, 50000),
            new Employee("C", "HR", 25, 40000),
            new Employee("D", "HR", 30, 60000),
            new Employee("E", "IT", 25, 55000)

    );


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n========= Stream Practice Menu =========");
        System.out.println("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice) {
            case 1 -> groupOrdersById();
            case 2 -> countOfOrdersPerCategory();
            case 3 -> totalRevenuePerCategory();
            case 4 -> averageOrderAmountPerCategory();
            case 5 -> listOfStudentNamesPerDepartment();
            case 6 -> concatenatedStudentNamesPerDepartment();
            case 7 -> studentNamesPipeSeparatedPerDepartment();
            case 8 -> studentWithHighestMarksPerDept();
            case 9 -> removeDuplicateWords();
            case 10 -> studentsNameCommaSeparatedGroupedByDepartment();
            case 11 -> productSummaryStatisticsGroupedByCategory();
            case 12 -> groupEmployeeByDepartmentAge();
            case 13 -> avgSalaryGroupEmployeeByDepartmentAge();
            case 14 -> groupEmployeesInRangedBasedGroup();
            case 15 -> sortEmployeeNamesGroupByDepartment();
            case 16 -> groupEmployeeBySeniority();
            case 17 -> topTwoSalariedEmployeePerDepartment();
            default -> System.out.println("Invalid Choice");
        }
    }

    /**
     * 1 Group Orders by category
     */
    public static void groupOrdersById() {
        Map<String, List<Order>> map = orders.stream()
                .collect(Collectors.groupingBy(Order::category));
        System.out.println(map);
    }

    /**
     * 2 Group by category and Count number of orders per category
     **/
    public static void countOfOrdersPerCategory() {
        Map<String, Long> map = orders.stream()
                .collect(Collectors.groupingBy(
                        Order::category,
                        Collectors.counting()
                ));
        System.out.println(map);
    }

    /**
     * 3 Group by category Calculate total revenue per category
     */
    public static void totalRevenuePerCategory() {
        Map<String, Double> map = orders.stream()
                .collect(Collectors.groupingBy(
                        Order::category,
                        Collectors.summingDouble(Order::amount)
                ));
        System.out.println(map);
    }

    /**
     * 4 Group by category Find average order amount per category
     */
    public static void averageOrderAmountPerCategory() {
        Map<String, Double> map = orders.stream()
                .collect(Collectors.groupingBy(
                        Order::category,
                        Collectors.averagingDouble(Order::amount)
                ));
        System.out.println(map);
    }

    /**
     * 5 List of Students names per category
     */
    public static void listOfStudentNamesPerDepartment() {
        Map<String, List<String>> map = students.stream()
                .collect(Collectors.groupingBy(
                        Student::dept,
                        Collectors.mapping(
                                Student::name,
                                Collectors.toList()
                        )
                ));
        System.out.println(map);
    }

    /**
     * 6 Students names concatenated per category eg. IT=NehaPooja
     */
    public static void concatenatedStudentNamesPerDepartment() {
        Map<String, String> map = students.stream()
                .collect(Collectors.groupingBy(
                        Student::dept,
                        Collectors.mapping(
                                Student::name,
                                Collectors.joining()
                        )
                ));
        System.out.println(map);
    }

    /**
     * 7 Students names concatenated with | per category eg. IT=Neha | Pooja
     */
    public static void studentNamesPipeSeparatedPerDepartment() {
        Map<String, String> map = students.stream()
                .collect(Collectors.groupingBy(
                        Student::dept,
                        Collectors.mapping(
                                Student::name,
                                Collectors.reducing("", (o, o2) -> o + " | " + o2)
                        )
                ));

        System.out.println(map);

        Map<String, String> map1 = students.stream()
                .collect(Collectors.groupingBy(
                        Student::dept,
                        Collectors.mapping(
                                Student::name,
                                Collectors.joining(" | ")
                        )
                ));
        System.out.println(map1);
    }

    /**
     * 8 Students with highest per department
     */
    public static void studentWithHighestMarksPerDept() {
        Map<String, Optional<Student>> map = students.stream()
                .collect(Collectors.groupingBy(
                        Student::dept,
                        Collectors.maxBy((o1, o2) -> o1.marks() - o2.marks())
                ));
        System.out.println(map);

        Map<String, Student> map1 = students.stream()
                .collect(Collectors.groupingBy(
                        Student::dept,
                        Collectors.reducing(null, (student, student2) -> student.marks > student2.marks ? student : student2)
                ));
        System.out.println(map1);
    }

    /**
     * 9 Group words with fist letter and remove duplicates
     */
    public static void removeDuplicateWords() {
        Map<Character, Set<String>> map = words.stream()
                .collect(Collectors.groupingBy(
                        o -> o.charAt(0),
                        Collectors.toSet()
                ));
        System.out.println(map);
    }

    /**
     * 10 Group students by departments and display names comma separtated
     */
    public static void studentsNameCommaSeparatedGroupedByDepartment() {
        Map<String, String> map = students.stream()
                .collect(Collectors.groupingBy(
                        Student::dept,
                        Collectors.mapping(
                                Student::name,
                                Collectors.joining(",")
                        )
                ));

        System.out.println(map);
    }

    /**
     * 11 Product statistics grouped by Category
     */
    public static void productSummaryStatisticsGroupedByCategory() {
        Map<String, DoubleSummaryStatistics> map = products.stream()
                .collect(Collectors.groupingBy(
                        Product::category,
                        Collectors.mapping(
                                Product::price,
                                Collectors.summarizingDouble(value -> value)
                        )
                ));

        System.out.println(map);
    }

    /**
     * 12 Group Employee by Department and Age
     */
    public static void groupEmployeeByDepartmentAge() {
        Map<String, Map<Integer, List<Employee>>> map = employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::dept,
                        Collectors.groupingBy(
                                Employee::age
                        )
                ));

        System.out.println(map);
    }

    /**
     * 13 Find max Salary in per age department, per age
     */
    public static void avgSalaryGroupEmployeeByDepartmentAge() {
        Map<String, Map<Integer, Integer>> map = employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::dept,
                        Collectors.groupingBy(
                                Employee::age,
                                Collectors.mapping(
                                        Employee::salary,
                                        Collectors.collectingAndThen(
                                                Collectors.maxBy((o1, o2) -> o1 - o1),
                                                integer -> integer.get()
                                        )
                                )
                        )
                ));
        System.out.println(map);
    }

    /**
     * 14 Find Employees in rage higher than 60k or lower than 60k
     */
    public static void groupEmployeesInRangedBasedGroup() {
        int range = 60_000;
        Map<Boolean, List<Employee>> map = employees.stream()
                .collect(Collectors.groupingBy(
                        employee -> employee.salary > range,
                        Collectors.toList()
                ));
        System.out.println(map);
    }

    /**
     * 15
     */
    public static void sortEmployeeNamesGroupByDepartment() {
        Map<String, String> map = employees.stream()
                .sorted((o1, o2) -> o1.name.compareTo(o2.name))
                .collect(Collectors.groupingBy(
                        Employee::dept,
                        Collectors.mapping(
                                Employee::name,
                                Collectors.joining(",")
                        )
                ));

        System.out.println(map);
    }

    /**
     * 16  Group employees into: "Young" (<30), "Mid" (30–40), "Senior" (>40)
     */
    public static void groupEmployeeBySeniority() {
        Map<String, List<Employee>> map = employees.stream()
                .collect(Collectors.groupingBy(
                        employee -> employee.age < 30 ? "Young" : employee.age < 40 ? "Mid" : "Senior"
                ));
        System.out.println(map);
    }

    /**
     * 17
     */
    public static void topTwoSalariedEmployeePerDepartment() {
        Map<String, List<Employee>> map = employees.stream()
                .sorted(Comparator.comparingInt(Employee::salary))
                .collect(Collectors.groupingBy(
                        Employee::dept,
                        Collectors.toList()
                ));

        System.out.println(map);
    }


}
