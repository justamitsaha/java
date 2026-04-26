package testing;


import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Test {
    static List<Employee> employees = new ArrayList<>();
    static List<Integer> integers1 = List.of(3);
    static List<Integer> integers2 = List.of();
    static List<Integer> integers = List.of(4, 8, 26, 8, 5, 98, 56, 8, 32, 11, 9, 4, 77, 7, 3, 2, 5, 5, 3, 2, 1);
    static String text = "Java Stream API makes Java powerful and fun";

    record Employee(String name, String dept, double salary) {
    }

    static {
        Random random = new Random();
        for (int i = 0; i < 15; i++) {
            var employee = new Employee(
                    "Hello" + i,
                    String.valueOf(random.nextInt(1, 5)), // dept 1 to 4
                    random.nextDouble(1000, 10000) // salary between 1000 and 10000
            );
            //System.out.println(employee);
            employees.add(employee);
        }

    }

    /*
    So we know the grouping by Creates Map ad the format is
    stream().collect(Collectors.groupingBy(function(), Collectors))
    function determine the key
    The 2nd Collectors determine how to create the Values of Map, if its original item then we don;t need it, other wise se we have to create the values with another collector
     */
    public static void main(String[] args) {
        Map<String, List<Employee>> map = employees.stream()
                .collect(Collectors.groupingBy(Employee::dept));
        System.out.println(map);

        var x = employees.stream()
                .collect(Collectors.groupingBy(Employee::dept, Collectors.counting()));
        System.out.println(x);

        var y = employees.stream()
                .collect(Collectors.groupingBy(Employee::dept, Collectors.summingDouble(Employee::salary)));
        System.out.println(y);

        var z = employees.stream()
                .collect(Collectors.groupingBy(Employee::dept,Collectors.averagingDouble(Employee::salary)));
        System.out.println(z);


    }
}




















































