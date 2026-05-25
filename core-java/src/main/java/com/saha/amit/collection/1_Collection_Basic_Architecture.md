

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
-   **Thread Safety:** Every individual operation is synchronized.
-   **Dynamic Sizing:** Grows by doubling its size (or by a specific increment) when full.
-   **Legacy Class:** Introduced in JDK 1.0; later retrofitted to implement `List`.

| Operation | Method | Time Complexity | Why? |
| --- | --- | --- | --- |
| **Access** | `get(index)` | **O(1)** | Direct array index lookup. |
| **Insertion** | `add(e)` | **O(1)* / O(n)** | Amortized O(1) unless resizing is needed. |
| **Deletion** | `remove(index)` | **O(n)** | Requires shifting elements. |

### 4. Stack
A linear data structure following the **Last-In, First-Out (LIFO)** principle.

Key Features
-   **Legacy Design:** Extends `Vector`, which is considered a design flaw as it exposes non-stack methods.
-   **Thread Safe:** Inherits synchronization from `Vector`.

| Operation | Method | Time Complexity | Why? |
| --- | --- | --- | --- |
| **Push** | `push(e)` | **O(1)** | Adds to the end of the internal array. |
| **Pop/Peek** | `pop()` / `peek()` | **O(1)** | Accesses the last element. |
| **Search** | `search(o)` | **O(n)** | Traverses to find the object. |

---

## 2. Queue
A collection designed for holding elements prior to processing.

### 1. PriorityQueue
An unbounded queue based on a **priority heap**.

Key Characteristics
-   **Ordering**: Elements are ordered by natural ordering or a `Comparator`.
-   **Nulls**: Does not permit `null`.
-   **Non-Thread-Safe**: Use `PriorityBlockingQueue` for concurrent access.

| Operation | Method | Time Complexity | Why? |
| --- | --- | --- | --- |
| **Offer/Add** | `offer(e)` | **O(log n)** | Must maintain the heap property. |
| **Poll/Remove** | `poll()` | **O(log n)** | Requires heap "sift-down" after removal. |
| **Peek** | `peek()` | **O(1)** | Head of the heap is always at the root. |

### 2. ArrayDeque (Deque)
A resizable-array implementation of the `Deque` interface.

Key Features
-   **Double-Ended**: Efficient insertion and removal from both ends.
-   **No Nulls**: Prohibits `null` elements.
-   **Performance**: Usually faster than `Stack` and `LinkedList`.

| Operation | Method | Time Complexity | Why? |
| --- | --- | --- | --- |
| **Insert Front/Back** | `addFirst()` / `addLast()` | **O(1)** | Amortized constant time. |
| **Remove Front/Back**| `removeFirst()` / `removeLast()`| **O(1)** | Direct array head/tail manipulation. |
| **Peek** | `peekFirst()` / `peekLast()` | **O(1)** | Direct access. |

## 3. Set
A **Set** is a `Collection` that cannot contain duplicate elements. It models the mathematical set abstraction.

### 1. HashSet
The standard implementation of a set, backed by a `HashMap`.

Key Characteristics
- **Uniqueness**: Ensures no duplicates.
- **Ordering**: No guarantee of iteration order; it can change over time.
- **Nulls**: Allows one `null` element.

| Operation | Method | Time Complexity | Why? |
| --- | --- | --- | --- |
| **Add** | `add(e)` | **O(1)** | Based on `HashMap.put()` logic. |
| **Remove** | `remove(o)` | **O(1)** | Based on `HashMap.remove()` logic. |
| **Contains** | `contains(o)` | **O(1)** | Hash-based lookup. |

### 2. LinkedHashSet
Hash table and linked list implementation of the `Set` interface.

Key Features
- **Ordering**: Maintains a **doubly-linked list** across all entries, preserving **insertion order**.
- **Predictable Iteration**: Unlike `HashSet`, iteration order is consistent.

| Operation | Method | Time Complexity | Why? |
| --- | --- | --- | --- |
| **Add/Remove** | `add()`, `remove()` | **O(1)** | Slightly slower than `HashSet` due to linked list maintenance. |
| **Contains** | `contains()` | **O(1)** | Hash-based lookup. |

### 3. TreeSet
A `NavigableSet` implementation backed by a `TreeMap`.

Key Characteristics
- **Ordering**: Elements are sorted according to their **natural ordering** or a custom `Comparator`.
- **Navigation**: Provides methods to find closest matches (e.g., `ceiling`, `floor`).

| Operation | Method | Time Complexity | Why? |
| --- | --- | --- | --- |
| **Add/Remove** | `add()`, `remove()` | **O(log n)** | Balanced Red-Black tree structure. |
| **Contains** | `contains()` | **O(log n)** | Tree traversal. |

---

## 4. Map (Related Hierarchy)
Maps unique keys to values. Not a true `Collection` but part of the framework.

### 1. HashMap
The most commonly used `Map` implementation.

Key Characteristics
- **Storage**: Array of buckets (nodes).
- **Nulls**: Allows one `null` key and multiple `null` values.
- **Performance**: Treeification (Java 8+) converts long lists to trees in buckets.

| Operation | Method | Time Complexity | Why? |
| --- | --- | --- | --- |
| **Put/Get** | `put()`, `get()` | **O(1) / O(log n)** | Average constant time; logarithmic in worst-case (collisions).|
| **Remove** | `remove()` | **O(1)** | Hash-based lookup and removal. |

### 2. LinkedHashMap
Extends `HashMap` with linked list capabilities.

Key Features
- **Ordering**: Maintains **insertion order** or **access order** (LRU cache capability).
- **Iteration**: Iterates in the order elements were added.

| Operation | Method | Time Complexity | Why? |
| --- | --- | --- | --- |
| **Put/Get** | `put()`, `get()` | **O(1)** | Constant time with slightly more overhead than `HashMap`. |

### 3. TreeMap
A implementation of `NavigableMap` (which extends `SortedMap`) based on a Red-Black tree.

Key Characteristics
- **Ordering**: Maintains **sorted order** of keys (natural or comparator).
- **SortedMap Interface**: Provides methods like `firstKey()`, `lastKey()`, `headMap()`, `tailMap()`.
- **NavigableMap Interface**: Provides `lowerEntry()`, `floorKey()`, `ceilingKey()`, `higherEntry()`.

| Operation | Method | Time Complexity | Why? |
| --- | --- | --- | --- |
| **Put/Get** | `put()`, `get()` | **O(log n)** | Balanced Red-Black tree traversal. |
| **Range Queries**| `subMap()`, `headMap()`| **O(log n)** | Efficient tree-based range finding. |
| **First/Last** | `firstKey()`, `lastKey()`| **O(log n)** | Finding extremes in a tree. |

### 4. ConcurrentHashMap
A high-concurrency, thread-safe implementation.

Key Features
- **Mechanism**: Fine-grained locking and CAS operations.
- **Nulls**: Does **not** allow `null` keys or values.

| Operation | Method | Time Complexity | Why? |
| --- | --- | --- | --- |
| **Put/Get** | `put()`, `get()` | **O(1)** | Concurrent access with minimal blocking. |

### 5. Hashtable (Legacy)
A synchronized, legacy implementation.

Key Characteristics
- **Thread Safety**: Synchronizes every method (locking the whole map).
- **Legacy**: Generally replaced by `HashMap` or `ConcurrentHashMap`.

| Operation | Method | Time Complexity | Why? |
| --- | --- | --- | --- |
| **Put/Get** | `put()`, `get()` | **O(1)** | Fast but suffers high contention in multi-threaded apps. |