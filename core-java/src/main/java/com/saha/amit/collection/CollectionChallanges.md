Below is a **Collections practice set** structured like the Streams one: **test data + task only**, no solutions. It covers `List`, `Set`, `Map`, `Queue`, `Deque`, sorting, comparators, equals/hashCode behavior, and common interview scenarios.

----------

# Model

```java
record Employee(
    int id,
    String name,
    String dept,
    int age,
    double salary
) {}

record Product(
    int id,
    String name,
    String category,
    double price
) {}

record Order(
    int id,
    int customerId,
    List<Product> products,
    double total
) {}
```

----------

# Test Data

```java
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

        static List<Integer> smallList = Arrays.asList(1, 2, 3, 4, 5, 6, 1);

```

----------

# Section A — List

## 1) Reverse manually

Implement reverse of `numbers`:

-   using loop
-   using `Collections.reverse()`
-   using stack

**Answer:**
Using loop
```java
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
```

Using collection utility
```java

    private static <T> List<T> reverseListWithCollection(List<T> list) {
        return list.reversed();
    }
```

Using stack
```java

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
```

----------

## 2) Rotate list

Rotate by:

-   2 right
-   3 left

Use:

-   manual logic
-   collection utility

**Answer:**
```java
    enum direction {RIGHT, LEFT}

    private static <T> List<T> rotate(List<T> list, int shift, direction direction) {
        System.out.println(list);
        if (null == list || list.isEmpty())
            throw new IllegalArgumentException();
        List<T> result = new ArrayList<>();
        if (direction == SampleProblems.direction.RIGHT) {
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
```    

----------

## 3) Swap positions

Swap:

-   first and last
-   index 2 and index 7


**Answer:**
```java
    private static <T> List<T> swap(List<T> list, int i, int j) {
        if (null == list || list.isEmpty() || list.size() < i || list.size() < j)
            throw new IllegalArgumentException();
        T temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
        return list;
    }
```    
----------

## 4) Find second highest unique number

Input:

```
numbers
```

Return:

```
98
```

No sorting first.

**Answer:**
```java
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
```    

----------

## 5) Top 3 largest

Return:

```
[98,77,56]
```
**Answer:**
```java
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
```    
----------

## 6) Remove duplicates preserving insertion order

Input:

```
[4,8,26,8,5,4]
```

Output:

```
[4,8,26,5]
```

**Answer:**
```java
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
```    

----------

## 7) Partition list

Split numbers into:

-   even
-   odd

Store separately.
**Answer:**
```java
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
```    

----------

# Section B — Set

## 8) Unique departments

Extract unique department names.

Expected:

```
[IT, HR, FIN, OPS]
```

Preserve insertion order.

**Answer:**
```java
    private static Set<String> uniqueDepartment(List<Employee> employees) {
        Set<String> department = new HashSet<>();
        for (Employee employee : employees)
            department.add(employee.dept);
        return department;

    }
```    

----------

## 9) Union / Intersection / Difference

Create:

```
Set<Integer> a = Set.of(1,2,3,4,5);Set<Integer> b = Set.of(4,5,6,7,8);
```

Implement:

-   union
-   intersection
-   A-B
-   B-A

**Answer:**
```java
                Set<Integer> a = new HashSet<>(Set.of(1, 2, 3, 4, 5));
                Set<Integer> b = new HashSet<>(Set.of(4, 5, 6, 7, 8));
                System.out.println("Union: " + a.addAll(b));
                System.out.println("Intersection:" + a.contains(b));
                System.out.println("Difference: " + a.removeAll(b));

```    

----------

## 10) Detect duplicates

Find duplicate numbers in list.

Expected:

```
[8,5,4,3,2]
```
**Answer:**
```java
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
```    

----------

## 11) First non-repeating element

Input:

```
[1,2,2,3,3,4,4,5]
```

Output:

```
1
```
***Answer:**
```java
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
```    
----------

# Section C — Map

## 12) Frequency map

Create:

```
Map<Integer,Integer>
```

for `numbers`.

Example:

```
8 -> 35 -> 3
```
key is the number and value is the frequency.

**Answer**
```java
    private static Map<Integer, Integer> frequencyMap(List<Integer> list) {
        if (null == list || list.isEmpty())
            throw new IllegalArgumentException();
        Map<Integer, Integer> map = new HashMap<>();
        for (Integer i : list)
            if (null != map.putIfAbsent(i, 1)) {
                var count = map.get(i) + 1;
                map.put(i, count);
            }
        return map;
    }
```
----------

## 13) Invert map

Input:

```
A -> 1B -> 2C -> 1D -> 2
```

Convert:

```
1 -> [A,C]2 -> [B,D]
```

----------

## 14) Highest salary per department

Create:

```
Map<String, Employee>
```

----------

## 15) Group employees by salary

Range:

-   Low (<55000)
-   Mid
-   High (>70000)

Create:

```
Map<String,List<Employee>>
```

----------

## 16) Sort map by value descending

Input:  
frequency map

Sort by frequency descending.

----------

## 17) LRU Cache

Implement simple LRU cache with:

```
LinkedHashMap
```

Capacity = 3

Operations:

-   put
-   get
-   eviction

----------

# Section D — Queue / Deque

## 18) Priority queue

Store employees by salary highest first.

Poll all.

Expected descending salary order.

----------

## 19) Sliding window max

Input:

```
[1,3,-1,-3,5,3,6,7]
```

Window size:

```
3
```

Output:

```
[3,3,5,5,6,7]
```

Use deque.

----------

## 20) Palindrome check

Use deque:

```
racecarmadamjava
```

----------

# Section E — Sorting / Comparator

## 21) Sort employees:

By:

-   salary asc
-   salary desc
-   dept asc + salary desc
-   age asc + name asc

----------

## 22) Custom null handling

List:

```
["Amit", null, "Rahul", null, "Neha"]
```

Sort:

-   null first
-   null last

----------

## 23) Multi-field ranking

Rank employees:

1.  highest salary
2.  if tie → younger first
3.  if tie → name asc

----------

# Section F — equals/hashCode

## 24) HashSet experiment

Create class:  
(no record)

```
class Employee
```

Insert duplicates.

Observe behavior:

-   without equals/hashCode
-   equals only
-   hashCode only
-   both

Document result.

----------

## 25) HashMap key mutation bug

Use object as key.

Insert into map.

Mutate field used in hashCode.

Try fetching.

Explain result.

----------

# Section G — Hard

## 26) Merge intervals

Input:

```
[1,3][2,6][8,10][15,18]
```

Output:

```
[1,6][8,10][15,18]
```

----------

## 27) Top K frequent elements

Input:  
numbers

Return top 3 frequent.

Use heap.

----------

## 28) Median finder

Design class:

```
add(int)findMedian()
```

Efficient.

----------

## 29) Employee hierarchy traversal

Create manager → employee tree.

Implement:

-   DFS
-   BFS
-   level order traversal

----------

## 30) Mini in-memory DB

Implement:

```
insert(Employee)findById(id)findByDept(dept)findHighestSalary(dept)delete(id)
```

Optimize with collections.