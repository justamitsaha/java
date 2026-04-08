

## Basics (Must know)

- ### What is the Java Collections Framework (JCF)?

The Java Collections Framework (JCF) is a unified architecture within the java.util package that provides a set of interfaces, classes, and algorithms to store and manipulate groups of objects. It was introduced in JDK 1.2 to standardize how data structures are used, replacing older, disjointed classes like Vector and Hashtable. The JCF includes core interfaces such as `Collection`, `List`, `Set`, `Map`, and `Queue`, along with their implementations like `ArrayList`, `HashSet`, and `HashMap`. It also provides algorithms for sorting, searching, and manipulating collections, making it easier for developers to work with data structures in a consistent and efficient manner.

- ### Difference between Collection and Collections?
  The main difference lies in their nature: Collection is an interface for storing data, while Collections is a utility class for manipulating that data    
  Key Differences

-   *Definition & Structure**:
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
-   **List**: Best for scenarios where the  **order of elements** is important or when you need to access elements by their  **index** (e.g.,  `list.get(0)`).  
    -   **Set**: Ideal for ensuring  **uniqueness**. If you try to add a duplicate element, the operation will simply fail to add it.  `HashSet` is generally the fastest, while  `TreeSet` keeps elements sorted.  
    -   **Map**: Used when you need to  **look up values** using a specific key (like a dictionary). It maps a unique key to exactly one value.  
    -   **Queue**: Designed to hold elements  **prior to processing**. Most follow the First-In-First-Out (FIFO) principle, but  `PriorityQueue` sorts elements based on a custom priority
- ### Difference between **ArrayList vs LinkedList**?
  The primary difference is their  **internal data structure**: **ArrayList** is backed by a dynamic array, while  **LinkedList** is backed by a doubly-linked list  
  ArrayList and LinkedList are both implementations of the List interface, but they differ primarily in data structure and performance.  ArrayList uses a dynamic array for fast random access but slow insertions/removals, while  LinkedList uses a doubly linked list for fast insertions/removals but slow random access. Use ArrayList for frequent reads, LinkedList for frequent modifications.  
  Key Differences Breakdown

    -   **Internal Structure:**
-   **ArrayList:** Uses a resizable array to store elements. When the array is full, it creates a new, larger array.
    -   **LinkedList:** Uses a doubly linked list, where each node contains the data and pointers to the previous and next nodes.  
        -   **Performance (Time Complexity):**
-   **Access/Search (get(index)):** ArrayList is 0(1) , LinkedList is 0(n) because it must traverse the list.
    -   **Insertion/Deletion (middle):** ArrayList is 0(n) due to shifting elements; LinkedList is 0(1) (if the position is already found).
    -   **Insertion/Deletion (ends):** LinkedList excels at removing/adding from the front; ArrayList is efficient at appending to the end.  
        -   **Memory Usage:**
-   **ArrayList:** Generally more memory-efficient as it only stores the elements.
    -   **LinkedList:** Uses more memory because it stores the data  _plus_ two references (pointers) for each node.  
        -   **Best Use Cases:**
-   **ArrayList:** Best when you need to access elements frequently (fast read-only applications).
    -   **LinkedList:** Best when you need to add or remove elements frequently from the list, particularly at the beginning or middle
- ### Difference between **List vs Set**?
  The primary difference between a  **List** and a **Set** lies in how they handle duplicate elements and the order of those elements.
    -   **Use a List** when you need to maintain a specific sequence of items (e.g., a message history) or when you expect and need to allow duplicate entries.
    -   **Use a Set** when the uniqueness of items is critical (e.g., a list of unique user IDs) and when you need to quickly check if an item exists in the collection.

- ### What is **HashMap**? How does it work internally?
  A HashMap is a data structure that stores data in key-value pairs, providing average 0(1) time complexity for insertion, retrieval, and removal. It uses hashing to compute an index into an array of buckets, handling collisions by chaining entries in a linked list or, in modern Java, a balanced tree.

  Key Components

    -   **Array of Nodes/Buckets:**  The internal structure is an array (default capacity 16) where each index represents a bucket.
    -   **Entry/Node Object:**  Stores the  `<K,V>`  pair, the  `hash`  value, and a reference to the  `next`  node (for chaining).
    -   **Hash Function:**  Converts the key's hash code into a specific index within the array (`index = hashCode(key) & (n - 1)`)
        Internal Working: `put(K key, V value)`
    1.  **Hash Calculation:**  The  `hashCode()`  of the key is calculated to find the appropriate bucket index.
    2.  **Collision Handling:**
        -   If the bucket is empty, the entry is inserted.
        -   If the bucket is already occupied, the  `equals()`  method compares the new key with existing keys in the chain.
        -   If a matching key exists, the old value is replaced.
        -   If no matching key exists, the new node is added to the linked list/tree.
    3.  **Resizing:**  When the number of entries exceeds the threshold (`capacity * load factor`, default is 16*0.75=12 ), the array is resized (usually doubled) and all entries are rehashed
        **Internal Working: `get(K key)`**
        Important Details

    -   **Null Keys:**  Generally allowed (typically stored in the first bucket).
    -   **Immutability:**  Keys should be immutable (like String) to ensure the hash code remains consistent.
    -   **Not Thread-Safe:**  HashMap is not synchronized; external synchronization is required for concurrent use.
    1.  **Hash Calculation:**  Computes the same hash code and determines the index.
    2.  **Search:**  Traverses the bucket's list/tree, using  `equals()`  to find the matching key
- ### Difference between **HashMap vs Hashtable**?
  HashMap and Hashtable both store key-value pairs in Java, but differ primarily in synchronization and null handling. HashMap is non-synchronized (not thread-safe), allows one null key and multiple null values, and is faster. Hashtable is synchronized (thread-safe), prohibits null keys or values, and is considered legacy code
  **Key Differences:**

    -   **Synchronization:**  Hashtable is synchronized, meaning only one thread can access it at a time, making it safe for multi-threaded environments but slower. HashMap is not synchronized, making it faster and preferred for single-threaded or modern, externally synchronized scenarios.
    -   **Null Keys/Values:**  HashMap allows one  `null`  key and multiple  `null`  values. Hashtable does not permit  `null`  keys or  `null`  values, throwing a  `NullPointerException`  if attempted.
    -   **Legacy vs. New:**  Hashtable is a legacy class (pre-Java 1.2). HashMap is part of the Java Collection Framework introduced in Java 1.2.
    -   **Iteration:**  HashMap uses  `Iterator`  (fail-fast), while Hashtable uses  `Enumeration`  (not fail-safe).
    -   **Performance:**  Due to the lack of synchronization overhead, HashMap generally provides better performance

  **When to Use Which:**

    -   **Use HashMap:**  For almost all scenarios, particularly single-threaded, due to better performance.
    -   **Use Hashtable:**  Generally, it should not be used in new code. Use  `ConcurrentHashMap`  for high-concurrency needs
- ### What is **load factor** and **initial capacity** in HashMap?
- ### What happens when two keys have same hash? (collision handling)
- ### Difference between **equals() and hashCode()**?