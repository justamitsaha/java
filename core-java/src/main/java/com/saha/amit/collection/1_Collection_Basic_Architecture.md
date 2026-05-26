# Collections

The **`Collection` interface** is the root of the Java Collections Framework hierarchy, located in the `java.util` package.

Contains blueprint of many methods
-   **Modification:**  `add(E e)`,  `remove(Object o)`,  `clear()`.
-   **Querying:**  `size()`,  `isEmpty()`,  `contains(Object o)`.
-   **Bulk Operations:**  `addAll(Collection c)`,  `removeAll(Collection c)`,  `retainAll(Collection c)`.
-   **Conversion:**  `toArray()`,  `iterator()`, and Java 8+ features like  `stream()` and  `parallelStream()`

---

## 1. List
It allows you to maintain the insertion order of elements and supports the inclusion of duplicate values.    

**Key Characteristics**
-   **Ordered:** Elements are stored based on their insertion order.
-   **Index-based Access:** You can access, insert, or remove elements using their integer index (starting at 0).
-   **Duplicates Allowed:** Unlike a  `Set`, a  `List` can contain multiple identical elements.
-   **Null Elements:** Most implementations allow for  `null` elements.

**Essential Methods**
- `add(E element)` : Appends the specified element to the end of the list.
- `add(int index, E element)` : Inserts an element at a specific index.
- `get(int index)`: Returns the element at the specified position.
- `set(int index, E element)` : Replaces the element at the given index.
- `remove(int index)` : Removes the element at the specified index.
- `indexOf(Object o)`: Returns the index of the first occurrence of an element.
- `size()`: Returns the number of elements in the list.
- `listIterator()` : Returns a  ListIterator  to traverse the list in both directions.

### 1. ArrayList
An **ArrayList** in Java is a resizable, dynamic array found in the `java.util` package. Unlike standard arrays that have a fixed size, an `ArrayList` automatically grows or shrinks as you add or remove elements.

**Purpose**: To provide a dynamic array that offers fast random access to elements.

**Usage Guidelines**:
- **When to Use**: When you need frequent read operations (random access) and the list size changes dynamically.
- **Example**: Storing a list of products in an e-commerce catalog where you often look up items by their index.
- **When NOT to Use**: When the application requires frequent insertions or deletions at the beginning or middle of the list.
- **Example Anti-pattern**: A real-time message queue where items are constantly added to the front, causing expensive array shifts.

**Key Characteristics**
-   **Dynamic Resizing**: It adjusts its capacity automatically (default initial capacity of 10).
-   **Ordered**: It maintains the insertion order of elements.
-   **Index-Based Access**: Elements can be retrieved instantly using an index.
-   **Not Thread-Safe**: It is not synchronized by default. Use `Collections.synchronizedList()` or `CopyOnWriteArrayList` for concurrency.
-   **Reference Types Only**: Can only store objects. Use Wrapper Classes for primitives.

**Basic Operations for ArrayList**

| Operation | Method | Time Complexity | Why? |
| --- | --- | --- | --- |
| **Access** | `get(index)` | **O(1)** | Direct array index lookup. |
| **Search** | `indexOf(element)` | **O(n)** | Linear scan of the array. |
| **Insertion (End)** | `add(element)` | **O(1)*** | Amortized constant time; O(n) if resizing occurs. |
| **Insertion (Middle)** | `add(index, element)` | **O(n)** | Requires shifting elements to the right. |
| **Deletion (End)** | `remove(size - 1)` | **O(1)** | No shifting required. |
| **Deletion (Middle)** | `remove(index)` | **O(n)** | Requires shifting elements to the left. |
| **Modify** | `set(index, element)` | **O(1)** | Direct array index replacement. |
| **Size** | `size()` | **O(1)** | Returns a cached counter. |

---

### 2. Linked List
**LinkedList** is a linear data structure that stores elements in non-contiguous memory locations. It consists of "nodes" where each node contains data and references to the next and previous nodes.

**Purpose**: To provide a linear collection where elements can be efficiently added or removed from both ends.

**Usage Guidelines**:
- **When to Use**: When your application involves frequent additions and removals from the beginning or end of the list.
- **Example**: Implementing a browser history (back/forward) or a simple Undo/Redo mechanism.
- **When NOT to Use**: When you need frequent random access to elements by their index.
- **Example Anti-pattern**: Searching for a specific record in a large list of 1 million records using `list.get(500000)`.

**Key Features**
-   **Doubly Linked Implementation**: Java's standard `LinkedList` is a **doubly linked list**.
-   **Dynamic Size**: Grows and shrinks at runtime without needing manual resizing.
-   **Performance Trade-off**: Faster for inserting/deleting at ends, but slower for random access compared to `ArrayList`.

**Basic Operations for LinkedList**

| Operation | Method | Time Complexity | Why?                                                              |
| --- | --- |-----------------|-------------------------------------------------------------------|
| **Access** | `get(index)` | **O(n)**        | Must traverse nodes sequentially to reach the index.              |
| **Search** | `indexOf(element)` | **O(n)**        | Must look through nodes from the beginning.                       |
| **Insertion (End)** | `add(element)` | **O(1)**        | Java keeps a direct pointer to the last node (`tail`).            |
| **Insertion (Middle)** | `add(index, element)` | **O(n)**        | **O(n)** to find the index + **O(1)** to flip pointers.           |
| **Deletion (End)** | `remove(size - 1)` | **O(1)**        | Java's list is doubly-linked; it instantly drops the `tail` node. |
| **Deletion (Middle)** | `remove(index)` | **O(n)**        | **O(n)** to find the index + **O(1)** to disconnect pointers.     |
| **Modify** | `set(index, element)` | **O(n)**        | Must traverse nodes to find the index before changing the value.  |
| **Size** | `size()` | **O(1)**        | Uses a cached variable; does not count nodes on the fly.          |

---

### 3. Vectors (Legacy)
In Java, a **Vector** is a growable array of objects. It is similar to an `ArrayList` but is **synchronized**.

**Suggested Alternatives**: Use `ArrayList` for non-thread-safe needs, or `Collections.synchronizedList(new ArrayList<>())` / `CopyOnWriteArrayList` for thread-safe scenarios.

**Purpose**: A thread-safe, dynamic array.

**Usage Guidelines**:
- **When to Use**: Only in legacy Java applications where thread safety is required on every operation.
- **Example**: Old Swing applications where UI components shared a common list.
- **When NOT to Use**: In modern applications where high performance is needed.
- **Example Anti-pattern**: Using Vector in a single-threaded loop to store local data.

**Key Characteristics**
-   **Thread Safety:** Every individual operation is synchronized.
-   **Dynamic Sizing:** Grows by doubling its size when full.
-   **Legacy Class:** Introduced in JDK 1.0.

| Operation | Method | Time Complexity | Why? |
| --- | --- | --- | --- |
| **Access** | `get(index)` | **O(1)** | Direct array index lookup. |
| **Insertion** | `add(e)` | **O(1)* / O(n)** | Amortized O(1) unless resizing is needed. |
| **Deletion** | `remove(index)` | **O(n)** | Requires shifting elements. |

---

### 4. Stack (Legacy)
A linear data structure following the **Last-In, First-Out (LIFO)** principle. It is thread-safe because it extends Vector, and all its major operations (like push, pop, and peek) are synchronized

**Suggested Alternatives**: Use `Deque` implementation like `ArrayDeque` (e.g., `Deque<Integer> stack = new ArrayDeque<>()`).

**Purpose**: To implement a LIFO (Last-In-First-Out) stack.

**Usage Guidelines**:
- **When to Use**: Simple LIFO operations where legacy compatibility is required.
- **Example**: Parsing simple expressions where only `push` and `pop` are needed.
- **When NOT to Use**: Modern performance-critical applications; `ArrayDeque` is faster.
- **Example Anti-pattern**: Using `Stack` in a loop where `ArrayDeque` would provide better throughput without synchronization overhead.

**Key Features**
-   **Legacy Design**: Extends `Vector`, which exposes non-stack methods like `get(index)`.
-   **Thread Safe**: Inherits synchronization from `Vector`.

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

**Purpose**: To process elements based on priority rather than their arrival order.

**Usage Guidelines**:
- **When to Use**: When you need to retrieve elements in a specific sorted order (e.g., lowest value first).
- **Example**: A CPU task scheduler where tasks with higher priority are processed first.
- **When NOT to Use**: When you need to preserve the exact insertion order of elements (FIFO).
- **Example Anti-pattern**: Using it for a standard FIFO printer queue.

**Key Characteristics**
-   **Ordering**: Elements are ordered by natural ordering or a `Comparator`.
-   **Nulls**: Does not permit `null`.
-   **Non-Thread-Safe**: Use `PriorityBlockingQueue` for concurrent access.

| Operation | Method | Time Complexity | Why? |
| --- | --- | --- | --- |
| **Offer/Add** | `offer(e)` | **O(log n)** | Must maintain the heap property (sift-up). |
| **Poll/Remove** | `poll()` | **O(log n)** | Requires heap "sift-down" after removal. |
| **Peek** | `peek()` | **O(1)** | Head of the heap is always at the root. |

---

### 2. ArrayDeque (Deque)
A resizable-array implementation of the `Deque` interface.

**Purpose**: To provide a high-performance double-ended queue.

**Usage Guidelines**:
- **When to Use**: When you need a Stack (LIFO) or a Queue (FIFO) and don't need thread safety.
- **Example**: Implementing a "Recently Used" list where you add to the front and remove from the back.
- **When NOT to Use**: When you need to store `null` elements or require index-based access.
- **Example Anti-pattern**: Using it as a list where you need to get an element at index 5.

**Key Features**
-   **Double-Ended**: Efficient insertion and removal from both ends.
-   **No Nulls**: Prohibits `null` elements.
-   **Performance**: Usually faster than `Stack` and `LinkedList`.

| Operation | Method | Time Complexity | Why? |
| --- | --- | --- | --- |
| **Insert Front/Back** | `addFirst()` / `addLast()` | **O(1)** | Amortized constant time. |
| **Remove Front/Back**| `removeFirst()` / `removeLast()`| **O(1)** | Direct array head/tail manipulation. |
| **Peek** | `peekFirst()` / `peekLast()` | **O(1)** | Direct access. |

---

## 3. Set
A **Set** is a `Collection` that cannot contain duplicate elements.

### 1. HashSet
The standard implementation of a set, backed by a `HashMap`.

**Purpose**: To store unique elements with high performance.

**Usage Guidelines**:
- **When to Use**: When you need to ensure no duplicates and don't care about the order of elements.
- **Example**: Storing a collection of unique IP addresses that visited a website.
- **When NOT to Use**: When you need to maintain insertion order or a sorted order.
- **Example Anti-pattern**: Storing items in a menu where the display order must match the addition order.

**Key Characteristics**
- **Uniqueness**: Ensures no duplicates.
- **Ordering**: No guarantee of iteration order.
- **Nulls**: Allows one `null` element.

| Operation | Method | Time Complexity | Why? |
| --- | --- | --- | --- |
| **Add** | `add(e)` | **O(1)** | Based on `HashMap.put()` logic. |
| **Remove** | `remove(o)` | **O(1)** | Based on `HashMap.remove()` logic. |
| **Contains** | `contains(o)` | **O(1)** | Hash-based lookup. |

---

### 2. LinkedHashSet
Hash table and linked list implementation of the `Set` interface.

**Purpose**: To store unique elements while maintaining their insertion order.

**Usage Guidelines**:
- **When to Use**: When you need uniqueness and want to iterate in the order of insertion.
- **Example**: A "Recently Viewed" list where items appear in the order they were first seen.
- **When NOT to Use**: When memory is a primary concern, as it has more overhead than `HashSet`.
- **Example Anti-pattern**: Using it for a massive set of IDs where order doesn't matter.

**Key Features**
- **Ordering**: Maintains a **doubly-linked list** across all entries.
- **Predictable Iteration**: Iteration order is consistent.

| Operation | Method | Time Complexity | Why? |
| --- | --- | --- | --- |
| **Add/Remove** | `add()`, `remove()` | **O(1)** | Slightly slower than `HashSet` due to linked list maintenance. |
| **Contains** | `contains()` | **O(1)** | Hash-based lookup. |

---

### 3. TreeSet
A `NavigableSet` implementation backed by a `TreeMap`.

**Purpose**: To store unique elements in a sorted order.

**Usage Guidelines**:
- **When to Use**: When you need elements to be automatically sorted and need range-based queries.
- **Example**: Storing a list of high scores in a game displayed from highest to lowest.
- **When NOT to Use**: When you don't need sorting, as operations are slower (O(log n)).
- **Example Anti-pattern**: Storing a set of random session IDs where no sorting is required.

**Key Characteristics**
- **Ordering**: Elements are sorted according to natural ordering or a `Comparator`.
- **Navigation**: Provides methods to find closest matches (e.g., `ceiling`, `floor`).

| Operation | Method | Time Complexity | Why? |
| --- | --- | --- | --- |
| **Add/Remove** | `add()`, `remove()` | **O(log n)** | Balanced Red-Black tree structure. |
| **Contains** | `contains()` | **O(log n)** | Tree traversal. |

---

## 4. Map (Related Hierarchy)
Maps unique keys to values.

### 1. HashMap
The most commonly used `Map` implementation.

**Purpose**: To store key-value pairs for fast retrieval.

**Usage Guidelines**:
- **When to Use**: Standard key-value storage where performance is critical.
- **Example**: A user profile cache where the User ID is the key.
- **When NOT to Use**: When you need to maintain insertion order or thread safety.
- **Example Anti-pattern**: Storing configuration settings that need to be displayed in a specific order in a UI.

**Key Characteristics**
- **Storage**: Array of buckets (nodes).
- **Nulls**: Allows one `null` key and multiple `null` values.
- **Performance**: Treeification (Java 8+) converts long lists to trees in buckets.

| Operation | Method | Time Complexity | Why? |
| --- | --- | --- | --- |
| **Put/Get** | `put()`, `get()` | **O(1) / O(log n)** | Average constant time; logarithmic in worst-case (collisions).|
| **Remove** | `remove()` | **O(1)** | Hash-based lookup and removal. |

---

### 2. LinkedHashMap
Extends `HashMap` with linked list capabilities.

**Purpose**: To store key-value pairs while maintaining insertion or access order.

**Usage Guidelines**:
- **When to Use**: When you need a map that iterates in the order keys were added, or for building an LRU cache.
- **Example**: Storing a history of search queries.
- **When NOT to Use**: When memory usage is a primary concern and order is irrelevant.
- **Example Anti-pattern**: A massive lookup table for static IDs where order is never used.

**Key Features**
- **Ordering**: Maintains **insertion order** or **access order**.
- **Iteration**: Iterates in the order elements were added.

| Operation | Method | Time Complexity | Why? |
| --- | --- | --- | --- |
| **Put/Get** | `put()`, `get()` | **O(1)** | Constant time with slightly more overhead than `HashMap`. |

---

### 3. TreeMap
A implementation of `NavigableMap` based on a Red-Black tree.

**Purpose**: To store key-value pairs in a sorted order of keys.

**Usage Guidelines**:
- **When to Use**: When you need keys to be sorted or need to perform range-based searches.
- **Example**: A dictionary where entries are sorted alphabetically.
- **When NOT to Use**: When you only need basic key-value lookups, as `HashMap` is faster.
- **Example Anti-pattern**: Storing web session data by session ID where no sorting is needed.

**Key Characteristics**
- **Ordering**: Maintains **sorted order** of keys.
- **Navigation**: Provides `lowerEntry()`, `floorKey()`, `ceilingKey()`, `higherEntry()`.

| Operation | Method | Time Complexity | Why? |
| --- | --- | --- | --- |
| **Put/Get** | `put()`, `get()` | **O(log n)** | Balanced Red-Black tree traversal. |
| **Range Queries**| `subMap()`, `headMap()`| **O(log n)** | Efficient tree-based range finding. |
| **First/Last** | `firstKey()`, `lastKey()`| **O(log n)** | Finding extremes in a tree. |

---

### 4. ConcurrentHashMap
A high-concurrency, thread-safe implementation.

**Purpose**: To provide a highly efficient thread-safe map for concurrent environments.

**Usage Guidelines**:
- **When to Use**: In multi-threaded applications where multiple threads read and write to the same map.
- **Example**: A shared session store in a web server.
- **When NOT to Use**: In single-threaded applications where `HashMap` would be faster.
- **Example Anti-pattern**: A local variable map used only within a single method execution.

**Key Features**
- **Mechanism**: Fine-grained locking and CAS operations.
- **Nulls**: Does **not** allow `null` keys or values.

| Operation | Method | Time Complexity | Why? |
| --- | --- | --- | --- |
| **Put/Get** | `put()`, `get()` | **O(1)** | Concurrent access with minimal blocking. |

---

### 5. Hashtable (Legacy)
A synchronized, legacy implementation.

**Suggested Alternatives**: Use `HashMap` for non-thread-safe needs, or `ConcurrentHashMap` for high-concurrency thread-safe needs.

**Purpose**: A thread-safe key-value store.

**Usage Guidelines**:
- **When to Use**: Only in legacy code where replacement is not feasible.
- **Example**: Maintaining old codebases from Java 1.0 era.
- **When NOT to Use**: In any new Java application.
- **Example Anti-pattern**: Using Hashtable in a modern Spring Boot microservice.

**Key Characteristics**
- **Thread Safety**: Synchronizes every method (locking the whole map).
- **Legacy**: Generally replaced by `HashMap` or `ConcurrentHashMap`.

| Operation | Method | Time Complexity | Why? |
| --- | --- | --- | --- |
| **Put/Get** | `put()`, `get()` | **O(1)** | Fast but suffers high contention in multi-threaded apps. |
