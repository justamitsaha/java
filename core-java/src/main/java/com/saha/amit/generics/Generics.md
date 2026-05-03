
# Set 1 — Basic Generic Types

## 1) Generic Box

Create:

```
class Box<T>
```

Requirements:

-   store one item
-   `set(T item)`
-   `get()`
-   `isEmpty()`

Test data:

```
Box<String>
Box<Integer>
Box<Employee>
```

Task:

-   prove same class handles all three types

Solutions:

```java
    static class Box<T> {
        T t;

        private T getT() {
            return t;
        }

        private void setT(T t) {
            this.t = t;
        }

        private boolean isEmpty() {
            return null == t;
        }
    }
```
Test:
```java
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
```
----------

## 2) Pair<K,V>

Create:

```
class Pair<K,V>
```

Store:

-   key
-   value

Test data:

```
Pair<Integer,String>Pair<String,Double>Pair<String,List<String>>
```

Task:

-   getter methods
-   pretty `toString()`

Solution:
```java
    static class Pair<K, V> {
        private K k;
        private V v;

        @Override
        public String toString() {
            return "Pair{" + "k=" + k + ", v=" + v + '}';
        }
    }
```
Test:
```java
        Pair<Integer, String> integerStringPair = new Pair<>();
        Pair<String, List<String>> stringListPair = new Pair<>();
        integerStringPair.k = 99;
        integerStringPair.v = "Hello";
        stringListPair.k = "DELTA";

        System.out.println(integerStringPair.toString());
        System.out.println(stringListPair.toString());
```
----------

## 3) Triple Generic

Create:

```
class Triple<A,B,C>
```

Test:

```
Triple<String,Integer,Double>
```

Task:

-   immutable record-style design

Solution:
```java
    static class Triple<A, B, C> {
        private final A a;
        private final B b;
        private final C c;

        private Triple(A a, B b, C c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }

        @Override
        public String toString() {
            return "Triple{a=" + a + ", b=" + b + ", c=" + c + '}';
        }
    }
```
Test:
```java
        Triple<String, Integer, Double> triple = new Triple<>("a", 1, 1.1d);
        System.out.println(triple.toString());
```        


----------

# Set 2 — Generic Methods

## 4) Generic Printer

Create method:

```
print(T value)
```

Works for:

```
StringIntegerEmployeeList<String>
```

Task:

-   single method handles all

Solution:
```java
        private static <T> void print(T t) {
            if (null != t)
                System.out.println(t.toString());
        }
```        

Test:
```java
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
```                

----------

## 5) Generic Array/List Finder

Create:

```
findFirst(List<T>)
```

Return:

-   first element

Edge case:

-   empty list

Test:

```
List<Integer>List<String>List<Employee>
```

Solution:
```java
        private static <T> T findFirst(List<T> list) {
            return list.isEmpty() ? null : list.getFirst();
        }
```        

test:
```java
                List<Integer> integers = new ArrayList<>();
                List<String> strings = List.of("SAM", "BAM", "TAM");
                List<Employee> employees = List.of(new Employee("1", "IT", 45000), new Employee("2", "HR", 50_000));
                System.out.println(GenericMethods.findFirst(integers));
                System.out.println(GenericMethods.findFirst(strings));
                System.out.println(GenericMethods.findFirst(employees));
```                
----------

## 6) Swap Method

Create generic method:

```
swap(List<T>, int i, int j)
```

Test:

```
List<Integer>List<String>
```
Solution:
```java
        private static <T> List<T> swap(List<T> t, int i, int j) {
            if (null != t && t.size() > Integer.max(i, j)) {
                T temp = t.get(j);
                t.set(j, t.get(i));
                t.set(i, temp);
            }
            return t;
        }
```

test:
```java
                List<Integer> integers = Arrays.asList(1, 2, 3, 4);
                List<String> strings = new ArrayList<>();
                List<String> strings1 = null;
                System.out.println(GenericMethods.swap(integers, 1, 3));
                System.out.println(GenericMethods.swap(strings, 1, 3));
                System.out.println(GenericMethods.swap(strings1, 0, 0));
```                
----------

# Set 3 — Bounds (`extends`)

## 7) Sum Numbers

Create:

```
sum(List<? extends Number>)
```

Must work:

```
List<Integer>List<Double>List<Long>
```

Task:

-   compute total

Question:  
Why not `List<Object>`?

Solution:
```java
        private static <T extends Number> Double sum(List<T> list) {
            double d = 0d;
            for (T t : list) {
                d = d + t.doubleValue();
            }
            return d;
        }
```        

test:
```java
                List<Integer> integers = List.of(1, 4, 8, 6);
                List<Float> floats = List.of(1.4f, 7.9f);
                List<String> strings = List.of("Ab", "Cd");
                System.out.println(GenericMethods.sum(integers));
                System.out.println(GenericMethods.sum(floats));
```                

----------

## 8) Max Finder

Create:

```
max(List<T>)
```

Constraint:

T must be comparable

Hint:

```
<T extends Comparable<T>>
```

Test:

```
List<Integer>List<String>List<Employee by salary>
```
Solution:
```java
        private static <T extends Comparable<T>> T max(List<T> list) {
            if (null == list || list.isEmpty())
                throw new UnsupportedOperationException();
            /* Instead of sorting, you should iterate through the list to find the maximum. This is also much faster ($O(n)$ instead of $O(n \log n)$).
            list.sort(Comparable::compareTo);
            return list.getLast();
            */

            T max = list.getFirst();
            for (T element : list) {
                if (element.compareTo(max) > 0) {
                    max = element;
                }
            }
            return max;
        }
```

test:
```java
List<String> strings = Arrays.asList("SAM", "BAM", "TAM");
                System.out.println(GenericMethods.max(strings));
                List<Integer> integers = Arrays.asList(1, 4, 8, 6);
                System.out.println(GenericMethods.max(integers));
                List<Float> floats = Arrays.asList(1.4f, 7.9f);
                System.out.println(GenericMethods.max(floats));
                List<Employee> employees = Arrays.asList(new Employee("1", "IT", 45000), new Employee("2", "HR", 50_000));
                //System.out.println(GenericMethods.max(employees));  //Won't compile
```                
----------

## 9) Statistics Utility

Create:

```
average(List<? extends Number>)min(List<? extends Number>)max(List<? extends Number>)
```
Solution:
```java
        private static <T extends Number> T max2(List<T> list) {
            if (null == list || list.isEmpty())
                throw new UnsupportedOperationException();

            return list.stream().max(Comparator.comparingDouble(Number::doubleValue)).orElse(null);

        }

        private static <T extends Number> T min(List<T> list) {
            if (null == list || list.isEmpty())
                throw new UnsupportedOperationException();
            return list.stream().min(Comparator.comparingDouble((Number o) -> o.doubleValue())).orElse(null);
        }

        private static Double avergae(List<? extends Number> list) {
            if (null == list || list.isEmpty())
                throw new UnsupportedOperationException();
            return list.stream().collect(Collectors.averagingDouble(Number::doubleValue));
        }
```
test
```java
                List<Integer> integers = Arrays.asList(1, 4, 8, 6);
                System.out.println(GenericMethods.max2(integers));
                System.out.println(GenericMethods.min(integers));
                System.out.println(GenericMethods.avergae(integers));
```                    
----------

# Set 4 — Lower Bounds (`super`)

## 10) Add Integers

Create:

```
addNumbers(List<? super Integer>)
```

Must accept:

```
List<Integer>List<Number>List<Object>
```

Task:  
add:

```
10,20,30
```

Question:  
Why not `List<Double>`?

Solution:
```java
        private static void sumLowerInteger(List<? super Integer> list){
            if(null == list || list.isEmpty())
                throw new UnsupportedOperationException();
            if (list.getFirst() instanceof Integer) {
                int sum = 0;
                for (Object o : list) {
                    sum += (Integer) o;
                }
                System.out.println("Sum: " + sum);
            } else {
                System.out.println("List does not contain Integers.");
            }
        }
```        

test:
```java
                List<Integer> integers = Arrays.asList(1, 4, 8, 6);
                System.out.println("Sum of Integers:");
                GenericMethods.sumLowerInteger(integers);

                // Super can be tricky below will throw exception
                List<Object> objects = Arrays.asList(1, "Hello", 8.5, new Employee("1", "IT", 45000));
                System.out.println("Sum of Objects:");
                GenericMethods.sumLowerInteger(objects);

                List<Number> numbers = Arrays.asList(1, 4.5, 8, 6.7);
                System.out.println("Sum of Numbers:");
                GenericMethods.sumLowerInteger(numbers);
```                
----------

## 11) Employee Loader

Model:

```
class Employeeclass Manager extends Employeeclass Director extends Manager
```

Create:

```
loadManagers(List<? super Manager>)
```

Insert:

```
ManagerDirector
```

Test with:

```
List<Employee>List<Manager>List<Object>
```

Solutions:
```java
static class Manager extends Employee {
    Manager(String id, String dept, int salary) {
        super(id, dept, salary);
    }
}

static class Director extends Manager {
    Director(String id, String dept, int salary) {
        super(id, dept, salary);
    }
}

static void loadManagers(List<? super Manager> list) {
    list.add(new Manager("M1", "IT", 90000));
    list.add(new Director("D1", "IT", 120000));
}
```
----------

# Set 5 — PECS (important)

Producer Extends, Consumer Super

## 12) Copy Utility

Create:

```
copy(List<? extends T> source,     List<? super T> target)
```

Test:

```
List<Integer> -> List<Number>List<Manager> -> List<Employee>
```

Question:  
Why does this work?

Solution:
```java
static <T> void copy(
        List<? extends T> source,
        List<? super T> target) {

    for (T t : source) {
        target.add(t);
    }
}
```

Test:
```java
List<Integer> src = List.of(1,2,3);
List<Number> dest = new ArrayList<>();

copy(src, dest);
```

----------

## 13) Merge Lists

Create:

```
merge(...)
```

Combine:

```
List<Integer>List<Integer>
```

into:

```
List<Number>
```

Design signature yourself.

Solution:
```java
static <T> List<T> merge(
        List<? extends T> l1,
        List<? extends T> l2) {

    List<T> result = new ArrayList<>();
    result.addAll(l1);
    result.addAll(l2);
    return result;
}
```
test:
```java
List<Number> list = merge(List.of(1,2), List.of(3.5,4.6));
```    
----------

# Set 6 — Generic Interfaces

## 14) Repository<T>

Create:

```
interface Repository<T>
```

Methods:

```
save(T)findById(int)findAll()delete(T)
```

Implement:

```
EmployeeRepositoryProductRepositoryStudentRepository
```

Task:  
single interface → many types

Solution:
```java
interface Repository<T> {
    void save(T t);
    T findById(int id);
    List<T> findAll();
    void delete(T t);
}
```

test:
```java
class EmployeeRepository implements Repository<Employee> {

    private final List<Employee> db = new ArrayList<>();

    public void save(Employee e) {
        db.add(e);
    }

    public Employee findById(int id) {
        return db.get(id);
    }

    public List<Employee> findAll() {
        return db;
    }

    public void delete(Employee e) {
        db.remove(e);
    }
}
```

----------

## 15) Mapper<S,T>

Create:

```
interface Mapper<S,T>
```

Method:

```
T map(S source)
```

Implement:

```
Employee -> EmployeeDTOProduct -> ProductDTO
```

Solution:
```java
interface Mapper<S,T> {
    T map(S source);
}
```
test:
```java
record EmployeeDto(String id, int salary){}

class EmployeeMapper
        implements Mapper<Employee, EmployeeDto> {

    public EmployeeDto map(Employee e) {
        return new EmployeeDto(e.id, e.salary);
    }
}
```

----------

# Set 7 — Wildcard Reading

Given:

```
List<Integer>List<Double>List<Number>List<Object>
```

Write methods to answer:

----------

## 16)

Method should accept:

```
Integer, Double, Long
```

but only read values.

Choose signature.

Solution:
```java
static void print(List<? extends Number> list) {
    for (Number n : list) {
        System.out.println(n);
    }
}
```

----------

## 17)

Method should add Integers.

Choose signature.

Solution:
```java
static void addIntegers(List<? super Integer> list) {
    list.add(10);
    list.add(20);
}
```

----------

## 18)

Method should both read and safely add.

Possible?

Why / why not?


No.  You cannot have both flexibly. Because:

```
? extends
```

safe read, unsafe write

and

```
? super
```

safe write, unsafe typed read

Pick one.

This is exactly why **PECS** exists.

----------

# Set 8 — Advanced

## 19) Generic Cache

Create:

```
Cache<K,V>
```

Methods:

```
putgetcontainsKeyremovesize
```

Internally use:

```
Map<K,V>
```

Test:

```
Cache<Integer,String>Cache<String,Employee>
```

Solution:
```java
static class Cache<K,V> {

    private final Map<K,V> map = new HashMap<>();

    void put(K k, V v) {
        map.put(k,v);
    }

    V get(K k) {
        return map.get(k);
    }

    boolean containsKey(K k) {
        return map.containsKey(k);
    }

    V remove(K k) {
        return map.remove(k);
    }

    int size() {
        return map.size();
    }
}
```

----------

## 20) Generic Builder

Create:

```
Builder<T>
```

Can:

```
addField(...)build()reset()
```

Design flexible generic API.

Solution:
```java
static class Builder<T> {

    private T value;

    Builder<T> addField(T value) {
        this.value = value;
        return this;
    }

    T build() {
        return value;
    }

    void reset() {
        value = null;
    }
}
```

----------

## 21) Comparable vs Comparator Generic Design

Create:

```
sort(List<T>)sort(List<T>, Comparator<? super T>)
```

Question:  
Why second uses `? super T`?

```
sort(List<T>, Comparator<? super T>)
```

Because comparator may compare:

-   T
-   parent of T

Example:

```
Comparator<Number>
```

can compare:

```
IntegerDoubleLong
```

Hence `super`.

----------

# Set 9 — Interview Level

## 22) Fix Compilation

Explain:

```
List<Integer> ints = new ArrayList<>();List<Number> nums = ints;
```

Why illegal?


Illegal:

```
List<Integer> ints = new ArrayList<>();List<Number> nums = ints;
```

If allowed:

```
nums.add(3.14);
```

Now Integer list contains Double.

Type safety broken.

Generics invariant.

----------

## 23)

Explain:

```
List<? extends Number>
```

Why add not allowed?


```
List<? extends Number>
```

Could be:

-   List<Integer>
-   List<Double>

Compiler doesnot know which. Adding:

```java 
list.add(10);
```

unsafe.

Blocked.

----------

## 24)

Explain:

```
List<? super Integer>
```

Why read returns Object?

Answer:
```
List<? super Integer>
```

Could be:

-   List<Integer>
-   List<Number>
-   List<Object>

Common safe read type:

```
Object
```

Only guaranteed type.

----------

## 25) Design Question

Design:

```
filter(...)map(...)flatMap(...)
```

using generics.

Choose method signatures.

Answer:

Filter:

```
static <T> List<T> filter(        List<T> list,        Predicate<T> predicate)
```

Map:

```
static <T,R> List<R> map(        List<T> list,        Function<T,R> mapper)
```

FlatMap:

```
static <T,R> List<R> flatMap(        List<T> list,        Function<T,List<R>> mapper)
```