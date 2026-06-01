### 🔹 1. We can't modify `List<Integer> list = List.of(1,2);` . How can we quickly create List in another way?

**Answer:**
```java
                List<Integer> list = Arrays.asList(1,2);
                list.set(1, null);
```
But still we can't change the size `list.add(3);` can only change existing items

### 🔹 2. `Set<Integer> a = Set.of(1, 2, 3, 4, 5);` creates immutable set how to create mutable set

**Answer:** The most common way to create a mutable set from existing values is to wrap the Set.of() call within a new HashSet<>()
```java
Set<Integer> a = new HashSet<>(Set.of(1, 2, 3, 4, 5));
a.add(6); 
```
You can collect a stream of elements directly into a mutable set.
```java
Set<Integer> a = Stream.of(1, 2, 3, 4, 5)
                       .collect(Collectors.toSet());
```
or 
```java
Set<Integer> a = new HashSet<>();
a.addAll(Set.of(1, 2, 3, 4, 5));
```

### 🔹 3. Will the below remove at index 1 or value 1?
```java
static List<Integer> smallList = new ArrayList<>(List.of(1, 2, 3, 4, 5, 1));
smallList.remove(1);
```

**Answer:** remove has overloaded one takes `int` which removes at index. other one `Object` class 
```java
static List<Integer> smallList = new ArrayList<>(List.of(1, 2, 3, 4, 5, 1));
smallList.remove(1);
```
This will remove from index 1

```java
                smallList = new ArrayList<>(List.of(1, 2, 3, 4, 5, 1));
                smallList.remove(Integer.valueOf(1));
```
This will remove value 1 but only the 1st one

### 🔹 4. What will be the output of below code? Will it print 3 employees or 2 employees?
```java
record Employee(
        int id,
        String name,
        String dept,
        int age,
        double salary) {
}
public static void main(String[] args) {
    Set<Employee> employeeSet = new HashSet<>();
    employeeSet.add(new Employee(101, "Amit", "IT", 55, 50000));
    employeeSet.add(new Employee(101, "Amit", "IT", 55, 50000));
    employeeSet.add(new Employee(102, "Rahul", "HR", 30, 64000));
    employeeSet.add(new Employee(103, "Neha", "IT", 27, 55000));
    System.out.println(employeeSet);
}
```

**Answer:** It will print 2 employees because record class has implemented equals and hashcode method based on all the fields. So when we try to add the same employee again it will not be added to the set as it is already present.