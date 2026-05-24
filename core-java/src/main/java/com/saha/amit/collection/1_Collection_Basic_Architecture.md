

# Collections


The **`Collection` interface** is the root of the Java Collections Framework hierarchy, located in the `java.util` package.

Contains blueprint of many methods
-   **Modification:**  `add(E e)`,  `remove(Object o)`,  `clear()`.
-   **Querying:**  `size()`,  `isEmpty()`,  `contains(Object o)`.
-   **Bulk Operations:**  `addAll(Collection c)`,  `removeAll(Collection c)`,  `retainAll(Collection c)`.
-   **Conversion:**  `toArray()`,  `iterator()`, and Java 8+ features like  `stream()` and  `parallelStream()`

## 1. List
It allows you to maintain the insertion order of elements and supports the inclusion of duplicate values.    
**Key Characteristics**

-   **Ordered:** Elements are stored based on their insertion order.
-   **Index-based Access:** You can access, insert, or remove elements using their integer index (starting at 0).
-   **Duplicates Allowed:** Unlike a  `Set`, a  `List` can contain multiple identical elements.
-   **Null Elements:** Most implementations allow for  `null` elements


**Essential Methods**
- `add(E element)` : Appends the specified element to the end of the list.
- `add(int index, E element)` : Inserts an element at a specific index.
- `get(int index)`: Returns the element at the specified position.
- `set(int index, E element)` : Replaces the element at the given index.
- `remove(int index)` : Removes the element at the specified index.
- `indexOf(Object o)`: Returns the index of the first occurrence of an element.
- `size()`: Returns the number of elements in the list.
- `listIterator()` : Returns a  ListIterator  to traverse the list in both directions.

### 1.  ArrayList
An **ArrayList** in Java is a resizable, dynamic array found in the `java.util` package. Unlike standard arrays that have a fixed size, an `ArrayList` automatically grows or shrinks as you add or remove element

Key Characteristics

-   **Dynamic Resizing**: It adjusts its capacity automatically, typically defaulting to an initial capacity of 10.
-   **Ordered**: It maintains the insertion order of elements.
-   **Index-Based Access**: Elements can be retrieved instantly using an index (starting from 0).
-   **Not Thread-Safe**: It is not synchronized by default. For multi-threaded environments, you must manually synchronize it using the  Collections.synchronizedList()  method.
-   **Reference Types Only**: It can only store objects. To store primitive types like  `int` or  `char`, you must use  Wrapper Classes  like  `Integer` or  `Character`

Basic Operations for Linked List
- **Access** `get(index)`  (using index lookup)  O(1)
- **Search** `indexOf(element)`  (finding by value) O(n)
- **Insertion (End)**  `add(element)`  (no resizing needed) O(1)
- **Insertion (Middle)** `add(index, element)`  (requires shifting) O(n)
- **Deletion (End)**  `remove(size - 1)`  (no shifting needed)  O(1)
- **Deletion (Middle)** `remove(index)`  (requires element shifting) O(n)
- **Modify** `set(index, element)` O(n)
- **Size** `size()`  O(1)



### 2. Linked List
**LinkedList** is a linear data structure that stores elements in non-contiguous memory locations. Instead of an index-based array, it consists of "nodes" where each node contains its data and a reference (pointer) to the next node in the sequence

Key Features
-   **Doubly Linked Implementation**: Java's standard  `java.util.LinkedList`  is a  **doubly linked list**, meaning each node stores pointers to both its  **previous**  and  **next**  neighbors.
-   **Dynamic Size**: Unlike arrays, its size can grow or shrink at runtime without needing manual resizing.
-   **Performance Trade-off**: It is much faster for  **inserting and deleting**  elements (especially at the ends) but slower for  **random access**  (getting an element at a specific index) compared to an  `ArrayList`

| Operation | Method | Time Complexity | Why?                                                              |
| --- | --- |-----------------|-------------------------------------------------------------------|
| **Access** | `get(index)` | **O(n)**        | Must traverse nodes sequentially to reach the index.              |
| **Search** | `indexOf(element)` | **O(n)**        | Must look through nodes from the beginning.                       |
| **Insertion (End)** | `add(element)` | **O(1)**        | Java keeps a direct pointer to the last node (`tail`).            |
| **Insertion (Middle)** | `add(index, element)` | **O(n)**        | **O(n)** to find the index + **O(1)** to flip pointers.           |
| **Deletion (End)** | `remove(size - 1)` | **O(1)***       | Java's list is doubly-linked; it instantly drops the `tail` node. |
| **Deletion (Middle)** | `remove(index)` | **O(n)**        | **O(n)** to find the index + **O(1)** to disconnect pointers.     |
| **Modify** | `set(index, element)` | **O(n)**        | Must traverse nodes to find the index before changing the value.  |
| **Size** | `size()` | **O(1)**        | Uses a cached variable; does not count nodes on the fly.          |


### 3. Vectors

In Java, a **Vector** is a growable array of objects that belongs to the `java.util` package. It is similar to an `ArrayList` but is **synchronized**, meaning it is thread-safe for use in multi-threaded environments.

Key Characteristics

-   **Dynamic Sizing:** Unlike standard arrays, vectors can grow or shrink in size as needed.
-   **Thread Safety:** Every individual operation in a Vector is synchronized, making it safe for multiple threads to access it concurrently.
-   **Legacy Class:** Introduced in JDK 1.0, it is considered a legacy class but was later updated to implement the `List` interface.
-   **Performance:** Because of synchronization overhead, it is generally slower than

Note: For modern applications where thread safety is not required, ArrayList is preferred. If you need thread safety, consider CopyOnWriteArrayList for better performance

### 4. Stack
In Java, a **Stack** is a linear data structure that follows the **Last-In, First-Out (LIFO)** principle. This means the last element added to the stack is the first one to be removed, similar to a physical stack of plates.

**Core Operations** The `java.util.Stack` class provides five primary methods for managing elements: 

-   **`push(E item)`**: Adds an item to the very top of the stack.
-   **`pop()`**: Removes and returns the object at the top of the stack.
-   **`peek()`**: Looks at the top object without removing it from the stack.
-   **`empty()`**: Checks if the stack is currently empty.
-   **`search(Object o)`**: Returns the 1-based position of an object from the top of the stack

```java
Stack<String> stack = new Stack<>();
stack.push("First");
stack.push("Second");

System.out.println(stack.peek()); // Output: Second
System.out.println(stack.pop());  // Output: Second (removes it)
System.out.println(stack.empty()); // Output: false

```

you should **not** use the traditional `java.util.Stack` class in modern Java development. 

Why You Should Avoid?

-   **Flawed Design**: It extends `Vector`, allowing you to use non-stack methods like `add(index, element)`. This breaks the core LIFO principle.
-   **Poor Performance**: It uses synchronized methods for thread safety, creating unnecessary performance overhead in single-threaded applications.

you should **not** use the traditional `java.util.Stack` class in modern Java development. 

Why You Should Avoid It

-   **Flawed Design**: It extends `Vector`, allowing you to use non-stack methods like `add(index, element)`. This breaks the core LIFO principle.
-   **Poor Performance**: It uses synchronized methods for thread safety, creating unnecessary performance overhead in single-threaded applications.

## 2. Queue
A **Queue** is a collection designed for holding elements prior to processing. Besides basic `Collection` operations, queues provide additional insertion, extraction, and inspection operations. Most follow a **FIFO (First-In-First-Out)** order.

**Key Methods**
- `add(E e)` / `offer(E e)`: Inserts an element. `offer` is preferred for capacity-restricted queues.
- `remove()` / `poll()`: Removes and returns the head. `poll` returns `null` if empty.
- `element()` / `peek()`: Returns the head without removing. `peek` returns `null` if empty.

### 1. PriorityQueue
An unbounded queue based on a **priority heap**. Elements are ordered according to their natural ordering or by a `Comparator`.
- **Ordering**: Not FIFO; elements are processed based on priority.
- **Nulls**: Does not permit `null` elements.
- **Performance**: $O(\log n)$ for `offer` and `poll`; $O(1)$ for `peek`.

### 2. Deque (Double Ended Queue)
A linear collection that supports element insertion and removal at both ends.
- **ArrayDeque**: Faster than `Stack` when used as a stack, and faster than `LinkedList` when used as a queue.
- **LinkedList**: Also implements `Deque`.

## 3. Set
A **Set** is a `Collection` that cannot contain duplicate elements. It models the mathematical set abstraction.

### 1. HashSet
The standard implementation of a set, backed by a `HashMap`.
- **Uniqueness**: Ensures no duplicates.
- **Ordering**: No guarantee of iteration order; it can change over time.
- **Performance**: Offers constant time $O(1)$ performance for basic operations (`add`, `remove`, `contains`).

### 2. LinkedHashSet
Hash table and linked list implementation of the `Set` interface.
- **Ordering**: Maintains a **doubly-linked list** across all entries, preserving **insertion order**.
- **Performance**: Slightly slower than `HashSet` due to the overhead of maintaining the linked list, but still $O(1)$.

### 3. TreeSet
A `NavigableSet` implementation backed by a `TreeMap`.
- **Ordering**: Elements are sorted according to their **natural ordering** or a custom `Comparator`.
- **Performance**: $O(\log n)$ for basic operations.

## 4. Map (Related Hierarchy)
While not extending the `Collection` interface, **Map** is a core part of the framework. It maps unique keys to values.

### 1. HashMap
- **Storage**: Key-value pairs.
- **Nulls**: Allows one `null` key and multiple `null` values.
- **Ordering**: No guarantee of order.

### 2. LinkedHashMap
- **Ordering**: Maintains **insertion order**.

### 3. TreeMap
- **Ordering**: Maintains **sorted order** of keys.
- **Hierarchy**: Implements `NavigableMap`, which extends `SortedMap`.

### 4. SortedMap (Interface)
A `Map` that further provides a total ordering on its keys.
- **Key Methods**: `firstKey()`, `lastKey()`, `headMap(toKey)`, `tailMap(fromKey)`.

### 5. NavigableMap (Interface)
A `SortedMap` extended with navigation methods reporting closest matches for given search targets.
- **Key Methods**: `lowerEntry(key)`, `floorEntry(key)`, `ceilingEntry(key)`, `higherEntry(key)`, `descendingMap()`.

### 6. ConcurrentHashMap
A highly concurrent, thread-safe implementation of the `Map` interface.
- **Mechanism**: Uses fine-grained locking (buckets/stripes) and CAS operations instead of locking the entire map.
- **Performance**: Significantly faster than `Hashtable` in multi-threaded environments.
- **Nulls**: Does **not** allow `null` keys or `null` values.

### 7. Hashtable
- **Legacy**: Synchronized and does not allow `null` keys or values. Generally replaced by `HashMap` or `ConcurrentHashMap`.