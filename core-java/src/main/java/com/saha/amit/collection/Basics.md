



## Basics (Must know)

- ### What is the Java Collections Framework (JCF)?

  The Java Collections Framework (JCF) is a unified architecture within the java.util package that provides a set of interfaces, classes, and algorithms to store and manipulate groups of objects. It was introduced in JDK 1.2 to standardize how data structures are used, replacing older, disjointed classes like Vector and Hashtable. The JCF includes core interfaces such as `Collection`, `List`, `Set`, `Map`, and `Queue`, along with their implementations like `ArrayList`, `HashSet`, and `HashMap`. It also provides algorithms for sorting, searching, and manipulating collections, making it easier for developers to work with data structures in a consistent and efficient manner.

- ### Difference between Collection and Collections?
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

- ### What are the main interfaces: `List`, `Set`, `Map`, `Queue`?
  In the Java Collections Framework, these four interfaces represent the fundamental ways to store and organize    data. While  `List`, `Set`, and `Queue` all extend the root Collection interface , **Map** is a standalone interface because it stores key-value pairs instead of individual elements      
  **Detailed Breakdown** 
    - **List**: Best for scenarios where the  **order of elements** is important or when you need to access elements by their  **index** (e.g.,  `list.get(0)`).
    - **Set**: Ideal for ensuring  **uniqueness**. If you try to add a duplicate element, the operation will simply fail to add it.  `HashSet` is generally the fastest, while  `TreeSet` keeps elements sorted.  
    -   **Map**: Used when you need to  **look up values** using a specific key (like a dictionary). It maps a unique key to exactly one value.  
    -   **Queue**: Designed to hold elements  **prior to processing**. Most follow the First-In-First-Out (FIFO) principle, but  `PriorityQueue` sorts elements based on a custom priority

- ### Difference between **ArrayList vs LinkedList**?
  The primary difference is their  **internal data structure**: **ArrayList** is backed by a dynamic array, while  **LinkedList** is backed by a doubly-linked list      
  ArrayList and LinkedList are both implementations of the List interface, but they differ primarily in data structure and performance.  ArrayList uses a dynamic array for fast random access but slow insertions/removals, while  LinkedList uses a doubly linked list for fast insertions/removals but slow random access. Use ArrayList for frequent reads, LinkedList for frequent modifications.      
  Key Differences Breakdown
    - **Internal Structure:**
- **ArrayList:** Uses a resizable array to store elements. When the array is full, it creates a new, larger array.
    -  **LinkedList:** Uses a doubly linked list, where each node contains the data and pointers to the previous and next nodes.
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

- ### Difference between **List vs Set**?
  The primary difference between a  **List** and a **Set** lies in how they handle duplicate elements and the order of those elements.
    -   **Use a List** when you need to maintain a specific sequence of items (e.g., a message history) or when you expect and need to allow duplicate entries.
    -   **Use a Set** when the uniqueness of items is critical (e.g., a list of unique user IDs) and when you need to quickly check if an item exists in the collection.

- ### What is **HashMap**? How does it work internally?
  A HashMap is a data structure that stores data in key-value pairs. It is part of the java.util package and allows for nearly constant-time0(1) performance for basic operations like insertion, retrieval, and deletion on average.
  
    **Internal Data Structure**: Internally, a HashMap is implemented as an  **array of Nodes**  (often called "buckets"). Each  `Node`  contains four fields:
  -   `hash`: The processed hash code of the key.
  -   `key`: The actual key object.
  -   `value`: The value associated with the key.
  -   `next`: A reference to the next node in the same bucket (used during collisions).

    **How it Works Internally (Step-by-Step)** : When you perform a  `put(key, value)`  or  `get(key)`  operation, the following process occurs:

    - **Hashing and Index Calculation**
      - **Generate Hash Code**: Java calls the key's  `hashCode()`  method to get an integer.
      - **Secondary Hash**: HashMap applies an additional internal hashing function (bit manipulation) to the raw hash code to ensure keys are spread evenly across the array.
      - **Determine Bucket Index**: The final index is calculated using a bitwise AND operation:  `index = (capacity - 1) & hash`

    - **Storing the Pair (Put Operation)**
      - If the calculated bucket is  **empty**, the new node is stored directly in that slot. 
      - If the bucket is  **not empty**, a  **collision**  has occurred

    - **Collision Handling** HashMap handles collisions using  **Chaining**.
      - **Linked List**: Initially, all entries at the same index are linked together in a single linked list.
      - **Treeification (Java 8 Improvement)**: If a single bucket exceeds a threshold (8 nodes) and the overall capacity is at least 64, the linked list is converted into a  **Balanced Red-Black Tree**. This improves search performance from O(n)  to O(log n) for that specific bucket.

    - **Retrieval (Get Operation)**
      - HashMap calculates the hash and index of the key. 
      - It goes to that bucket and traverses the linked list or tree. 
      - Inside the bucket, it uses  **`equals()`**  to find the exact key. It compares both the  `hash`  and the  `key`  to ensure a match before returning the value

    - **Resizing (Rehashing)** When the number of entries exceeds a certain limit (Capacity X Load Factor), the map resizes
      - **Default Load Factor**: 0.75.
      - **Process**: A new array with  **double the capacity**  is created. All existing entries are rehashed and redistributed into the new array, as their bucket indexes may change with the new size

    **Key Characteristics**
    - **Null Handling**: Allows one  `null`  key (stored at index 0) and multiple  `null`  values.
    - **Ordering**: It does  **not**  maintain any order of elements.
    - **Thread Safety**: It is  **not thread-safe**; use  `ConcurrentHashMap`  for multi-threaded environments

- ### Difference between **HashMap vs Hashtable**?
  HashMap and Hashtable both store key-value pairs in Java, but differ primarily in synchronization and null handling. HashMap is non-synchronized (not thread-safe), allows one null key and multiple null values, and is faster. Hashtable is synchronized (thread-safe), prohibits null keys or values, and is considered legacy code    
  **Key Differences:**

-   **Synchronization:** Hashtable is synchronized, meaning only one thread can access it at a time, making it safe for multi-threaded environments but slower. HashMap is not synchronized, making it faster and preferred for single-threaded or modern, externally synchronized scenarios.
    -   **Null Keys/Values:** HashMap allows one  `null` key and multiple  `null` values. Hashtable does not permit  `null` keys or  `null` values, throwing a  `NullPointerException` if attempted.
    -   **Legacy vs. New:** Hashtable is a legacy class (pre-Java 1.2). HashMap is part of the Java Collection Framework introduced in Java 1.2.
    -   **Iteration:** HashMap uses  `Iterator` (fail-fast), while Hashtable uses  `Enumeration` (not fail-safe).
    -   **Performance:** Due to the lack of synchronization overhead, HashMap generally provides better performance

    **When to Use Which:**
    - **Use HashMap:** For almost all scenarios, particularly single-threaded, due to better performance.
    -   **Use Hashtable:** Generally, it should not be used in new code. Use  `ConcurrentHashMap` for high-concurrency needs
- ### What is **load factor** and **initial capacity** in HashMap?
- ### What happens when two keys have same hash? (collision handling)
- ### Difference between **equals() and hashCode()**?