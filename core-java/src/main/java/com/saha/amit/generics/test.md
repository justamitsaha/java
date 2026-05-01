
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
Box<String>Box<Integer>Box<Employee>
```

Task:

-   prove same class handles all three types

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

----------

## 9) Statistics Utility

Create:

```
average(List<? extends Number>)min(List<? extends Number>)max(List<? extends Number>)
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

----------

## 17)

Method should add Integers.

Choose signature.

----------

## 18)

Method should both read and safely add.

Possible?

Why / why not?

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

----------

## 21) Comparable vs Comparator Generic Design

Create:

```
sort(List<T>)sort(List<T>, Comparator<? super T>)
```

Question:  
Why second uses `? super T`?

----------

# Set 9 — Interview Level

## 22) Fix Compilation

Explain:

```
List<Integer> ints = new ArrayList<>();List<Number> nums = ints;
```

Why illegal?

----------

## 23)

Explain:

```
List<? extends Number>
```

Why add not allowed?

----------

## 24)

Explain:

```
List<? super Integer>
```

Why read returns Object?

----------

## 25) Design Question

Design:

```
filter(...)map(...)flatMap(...)
```

using generics.

Choose method signatures.