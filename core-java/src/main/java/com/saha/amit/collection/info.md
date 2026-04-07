## 🔹 Basics (Must know)

-   What is the **Java Collections Framework (JCF)**?
-   Difference between **Collection** and **Collections**?
-   What are the main interfaces: `List`, `Set`, `Map`, `Queue`?
-   Difference between **ArrayList vs LinkedList**?
-   Difference between **List vs Set**?
-   What is **HashMap**? How does it work internally?
-   Difference between **HashMap vs Hashtable**?
-   What is **load factor** and **initial capacity** in HashMap?
-   What happens when two keys have same hash? (collision handling)
-   Difference between **equals() and hashCode()**?

----------

## 🔹 Intermediate (Important for interviews)

-   Internal working of **HashMap in Java 8+**
-   What is **bucket, hashing, rehashing**?
-   Why does HashMap convert **LinkedList → Tree (Red-Black Tree)**?
-   Difference between **ConcurrentHashMap vs HashMap**?
-   How does **ConcurrentHashMap achieve thread safety?
-   What is **fail-fast vs fail-safe iterator**?
-   What is **Iterator vs ListIterator**?
-   Difference between **Comparable vs Comparator**?
-   What is **TreeMap**? How is it sorted?
-   What is **PriorityQueue** and how does it work?

----------

## 🔹 Advanced (Deep dive / senior level)

-   Explain internal structure of **HashMap (Node, TreeNode, resizing, threshold)**
-   Time complexity of operations in:
    -   ArrayList
    -   LinkedList
    -   HashMap
    -   TreeMap
-   How does **ConcurrentHashMap differ in Java 7 vs Java 8?
-   What is **CopyOnWriteArrayList**? When to use it?
-   What are **weak references** in **WeakHashMap?
-   What is **IdentityHashMap**?
-   How does **LinkedHashMap maintain order**?
-   What is **immutable collection**? How to create one?
-   What are **blocking queues**? (e.g., `ArrayBlockingQueue`, `LinkedBlockingQueue`)
-   How does **Garbage Collection interact with collections**?

----------

## 🔹 Scenario-Based / Practical

-   You need:
    -   Fast lookup → which collection?
    -   Ordered data → which collection?
    -   Sorted data → which collection?
-   Why is **HashMap not thread-safe**? Real impact?
-   How would you design a **LRU Cache** using collections?
-   How to remove duplicates from a list?
-   How to sort a list of custom objects?
-   Why does **HashSet use HashMap internally**?
-   When would you prefer **LinkedList over ArrayList**?
-   How to make a collection **thread-safe**?

----------

## 🔹 Coding Questions

-   Reverse a list without using extra space
-   Find frequency of elements using HashMap
-   Detect duplicate elements in array
-   Sort a map by value
-   Implement custom comparator
-   Find first non-repeating character
-   Implement LRU Cache (important)

----------

## 🔹 Trick / Edge Questions

-   Can a **HashMap have null keys and values**?
-   Why **HashMap is faster than TreeMap**?
-   What happens if `hashCode()` is constant?
-   Why is **TreeMap not allowed null key**?
-   Difference between **Collections.synchronizedMap vs ConcurrentHashMap**?
-   Can we modify a collection while iterating?