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