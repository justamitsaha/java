- ## 1) What is the Java Collections Framework (JCF)?

    - The Java Collections Framework (JCF) is a unified architecture within the java.util package that **provides a set of interfaces, classes, and algorithms to store and manipulate** groups of objects. 
    - It replaces the need for manual data structure implementation by developers.
    - The JCF includes core interfaces such as `Collection`, `List`, `Set`, `Map`, and `Queue`, along with their implementations like `ArrayList`, `HashSet`, and `HashMap` to store data.
    - It also provides algorithms for sorting, searching, and manipulating collections, making it easier for developers to work with data structures in a consistent and efficient manner.
    - It was introduced in JDK 1.2 to standardize how data structures are used, replacing older, disjointed classes like Vector and Hashtable.

- ## 2) Difference between Collection and Collections?
  The main difference lies in their nature: Collection is an interface for storing data, while Collections is a utility class for manipulating that data          
  Key Differences

    -   **Definition & Structure**:
        -   **Collection**: It is the root interface of the Java Collections Framework (JCF). It defines common methods like  `add()`,  `remove()`, and  `size()` for any object that groups elements.
        -   **Collections**: It is a  `final` utility class. It cannot be instantiated; you call its methods directly using the class name (e.g.,  `Collections.sort(myList)`).
    -   **Functionality**:
        -   **Collection**: Focuses on  **how data is stored**. For instance,  `ArrayList` or  `HashSet` implement this interface to define their specific storage logic.
        -   **Collections**: Focuses on  **operations on data**. It includes polymorphic algorithms for sorting, searching, reversing, and making collections thread-safe (e.g.,  `synchronizedList()`).
    -   **Implementation**:
        -   **Collection**: You cannot create an object of this interface directly; you must use a concrete implementation like  ArrayList  or  HashSet.
        -   **Collections**: You use it as a toolbox to manipulate your existing collection objects.

- ## 3) What are the main interfaces: `List`, `Set`, `Map`, `Queue`?
  In the Java Collections Framework, these four interfaces represent the fundamental ways to store and organize    data. While  `List`, `Set`, and `Queue` all extend the root Collection interface , **Map** is a standalone interface because it stores key-value pairs instead of individual elements        
  **Detailed Breakdown**
    - **List**: Best for scenarios where the  **order of elements** is important or when you need to access elements by their  **index** (e.g.,  `list.get(0)`).
    - **Set**: Ideal for ensuring  **uniqueness**. If you try to add a duplicate element, the operation will simply fail to add it.  `HashSet` is generally the fastest, while  `TreeSet` keeps elements sorted.
    -   **Map**: Used when you need to  **look up values** using a specific key (like a dictionary). It maps a unique key to exactly one value.
    -   **Queue**: Designed to hold elements  **prior to processing**. Most follow the First-In-First-Out (FIFO) principle, but  `PriorityQueue` sorts elements based on a custom priority

- ## 4) Difference between **ArrayList vs LinkedList**?
  ArrayList and LinkedList are both implementations of the List interface, but they differ primarily in data structure and performance.       
  Key Differences Breakdown
- **Internal Structure:**
  - **ArrayList:** Uses a resizable array to store elements. When the array is full, it creates a new, larger array.
  - **LinkedList:** Uses a doubly linked list, where each node contains the data and pointers to the previous and next nodes.
- **Performance (Time Complexity):**
  - **Access/Search (get(index)):** ArrayList is 0(1) , LinkedList is 0(n) because it must traverse the list.
  - **Insertion/Deletion (middle):** ArrayList is 0(n) due to shifting elements; LinkedList is 0(1) (if the position is already found).
  - **Insertion/Deletion (ends):** LinkedList excels at removing/adding from the front; ArrayList is efficient at appending to the end.
- **Memory Usage:**
  - **ArrayList:** Generally uses less memory as it only stores the data and a few additional fields (size, capacity).
  - **LinkedList:** Uses more memory due to storing additional pointers for each node (previous and next).
- **Best Use Cases:**
  - **ArrayList:** When you need fast random access and are primarily adding/removing elements at the end of the list.
  - **LinkedList:** When you need frequent insertions/deletions in the middle of the list or when you want to implement a queue or deque.

- ## 4) Difference between **List vs Set**?
  The primary difference between a  **List** and a **Set** lies in how they handle duplicate elements and the order of those elements.
    -   **Use a List** when you need to maintain a specific sequence of items (e.g., a message history) or when you expect and need to allow duplicate entries.
    -   **Use a Set** when the uniqueness of items is critical (e.g., a list of unique user IDs) and when you need to quickly check if an item exists in the collection.

- ## 5) What is **HashMap**? How does it work internally?
    - A HashMap is a data structure that stores data in key-value pairs.
    - Allows for nearly constant-time0(1) performance for basic operations like insertion, retrieval, and deletion on average.
    - Inside java.util and part of Collection framework but not related to collection interface.

  **Internal Data Structure**: Internally, a HashMap is implemented as an  **array of Nodes** (often called **buckets**). Each  `Node` contains four fields:
    -   `hash`: The processed hash code of the key.
    -   `key`: The actual key object.
    -   `value`: The value associated with the key.
    -   `next`: A reference to the next node in the same bucket (used during collisions).

  **How it Works Internally (Step-by-Step)** : When you perform a  `put(key, value)` or  `get(key)` operation, the following process occurs:

    - **Hashing and Index Calculation**
      - **Generate Hash Code**: Java calls the key's  `hashCode()` method to get an integer.
      - **Secondary Hash**: HashMap applies an additional internal hashing function (bit manipulation) to the raw hash code to ensure keys are spread evenly across the array.
      - **Determine Bucket Index**: The final index is calculated using a bitwise AND operation:  `index = (capacity - 1) & hash`

  - **Storing the Pair (Put Operation)**
    - If the calculated bucket is  **empty**, the new node is stored directly in that slot.
    - If the bucket is  **not empty**, a  **collision** has occurred

  - **Collision Handling** HashMap handles collisions using  **Chaining**.
    - **Linked List**: Initially, all entries at the same index are linked together in a single linked list.
    - **Treeification (Java 8 Improvement)**: If a single bucket exceeds a threshold (8 nodes) and the overall capacity is at least 64, the linked list is converted into a  **Balanced Red-Black Tree**. This improves search performance from O(n)  to O(log n) for that specific bucket.

  - **Retrieval (Get Operation)**
    - HashMap calculates the hash and index of the key.
    - It goes to that bucket and traverses the linked list or tree.
    - Inside the bucket, it uses  **`equals()`** to find the exact key. It compares both the  `hash` and the  `key` to ensure a match before returning the value

    - **Resizing (Rehashing)** When the number of entries exceeds a certain limit (Capacity X Load Factor), the map resizes
      - **Default Load Factor**: 0.75.
      - **Process**: A new array with  **double the capacity** is created. All existing entries are rehashed and redistributed into the new array, as their bucket indexes may change with the new size

  - **Key Characteristics**
      - **Null Handling**: Allows one  `null` key (stored at index 0) and multiple  `null` values.
      - **Ordering**: It does  **not** maintain any order of elements.
      - **Thread Safety**: It is  **not thread-safe**; use  `ConcurrentHashMap` for multi-threaded environments

- ## 6) Difference between **HashMap vs Hashtable**?
  HashMap and Hashtable both store key-value pairs in Java, but differ primarily in synchronization and null handling. HashMap is non-synchronized (not thread-safe), allows one null key and multiple null values, and is faster. Hashtable is synchronized (thread-safe), prohibits null keys or values, and is considered legacy code      
  **Key Differences:**

  -   **Synchronization:** Hashtable is synchronized, meaning only one thread can access it at a time, making it safe for multi-threaded environments but slower. HashMap is not synchronized, making it faster and preferred for single-threaded or modern, externally synchronized scenarios.
      -   **Null Keys/Values:** HashMap allows one  `null` key and multiple  `null` values. Hashtable does not permit  `null` keys or  `null` values, throwing a  `NullPointerException` if attempted.
      -   **Legacy vs. New:** Hashtable is a legacy class (pre-Java 1.2). HashMap is part of the Java Collection Framework introduced in Java 1.2.
      -   **Iteration:** HashMap uses  `Iterator` (fail-fast), while Hashtable uses  `Enumeration` (not fail-safe).
      -   **Performance:** Due to the lack of synchronization overhead, HashMap generally provides better performance

      **When to Use Which:**
        - **Use HashMap:** For almost all scenarios, particularly single-threaded, due to better performance.
        - **Use Hashtable:** Generally, it should not be used in new code. Use  **`ConcurrentHashMap`** for high-concurrency needs

- ## 7) What is **load factor** and **initial capacity** in HashMap?
1. Initial Capacity

-   **Definition:**  The number of buckets (internal storage slots) created when the  [HashMap](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/HashMap.html)  is first initialized.
-   **Default Value:**  16.
-   **Internal Behavior:**  Regardless of the value you provide in the constructor, the  [HashMap](https://docs.oracle.com/javase/7/docs/api/java/util/HashMap.html)  will internally round the initial capacity up to the nearest  **power of two**  (e.g., if you set it to 10, it will actually be 16).
-   **Performance Impact:**  A higher initial capacity reduces the frequency of "resizing" operations but consumes more memory immediately.
2. Load Factor

-   **Definition:**  A measure of how full the  HashMap  is allowed to get before its capacity is automatically increased.
-   **Default Value:**  0.75 (or 75%).
-   **Trade-off:**
    -   **Higher Load Factor (> 0.75):**  Decreases memory overhead but increases lookup time (higher chance of hash collisions).
    -   **Lower Load Factor (< 0.75):**  Increases memory consumption but leads to faster lookups as buckets remain relatively empty.

- ## 8) What happens when two keys have same hash? (collision handling)
  In Java's `HashMap`, when two keys have the same hash code (or map to the same bucket index), it is called a **collision**. The `HashMap` handles this by allowing multiple entries to coexist in the same bucket using a technique called  **chaining**.
  1. The Chaining Process
        When a collision occurs, the  `HashMap`  follows these internal steps:

     -   **Identification:**  It calculates the bucket index for the key using its hash.
     -   **Traversing the Bucket:**  If the bucket already contains entries, it traverses the existing chain (a linked list or tree).
     -   **Checking for Equality:**  For each entry in the chain, it compares the keys using the  `equals()`  method.
         -   **If a match is found:**  The existing value for that key is replaced with the new value.
         -   **If no match is found:**  The new key-value pair is added to the end of the chain.

  2. Evolution of Collision Handling (Java 8+)
     The way  `HashMap`  manages long chains has changed to ensure high performance even when many keys collide:

      -   **Linked List (Pre-Java 8):**  All collisions in a bucket were stored in a simple  **linked list**. In the worst case, retrieval time could degrade to  **O(n)**.
      -   **Treeification (Java 8 & later):**  When a bucket's linked list exceeds a certain threshold (default is  **8 entries**), the list is converted into a  **Red-Black Tree**. This optimizes the worst-case retrieval time from  **O(n)**  to  **O(log n)**.
      -   **Detreeification:**  If the number of entries in a bucket drops below a certain threshold (usually 6) due to removals, the tree is converted back into a linked list to save memory.
  3. Key Factors for Efficient Handling
     -   **hashCode() and equals() Contract:**  For collision handling to work correctly, you must override both methods.  `hashCode()`  determines the bucket, while  `equals()`  distinguishes between keys within that bucket.
     -   **Load Factor and Resizing:**  When the number of entries exceeds a certain threshold (default load factor of  **0.75**), the  `HashMap`  doubles its bucket array size and rehashes all elements to redistribute them and reduce collisions

    

- ## 9) Difference between **equals() and hashCode()**?