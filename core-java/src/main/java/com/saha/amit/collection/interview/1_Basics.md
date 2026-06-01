- ## 1) What is the Java Collections Framework (JCF)?

    - The Java Collections Framework (JCF) is a unified architecture within the java.util package that **provides a set of interfaces, classes, and algorithms to store and manipulate** groups of objects. 
    - It replaces the need for manual data structure implementation by developers.
    - The JCF includes core interfaces such as `Collection`, `List`, `Set`, `Map`, and `Queue`, along with their implementations like `ArrayList`, `HashSet`, and `HashMap` to store data.
    - It also provides algorithms for sorting, searching, and manipulating collections, making it easier for developers to work with data structures in a consistent and efficient manner.
    - It was introduced in JDK 1.2 to standardize how data structures are used, replacing older, disjointed classes like Vector and Hashtable.

- ## 2) Difference between Collection and Collections?
  The main difference lies in their nature: **Collection** is an interface for storing data, while **Collections** is a utility class for manipulating that data.

| Feature | Collection (Interface) | Collections (Utility Class) |
| --- | --- | --- |
| **Type** | Root interface of the JCF. | Final utility class in `java.util`. |
| **Purpose** | Defines common methods for grouping elements (e.g., `add()`, `remove()`). | Provides algorithms for sorting, searching, and making collections thread-safe. |
| **Instantiable** | No (must use a concrete implementation like `ArrayList`). | No (private constructor; all methods are `static`). |
| **Implementation** | Focuses on **how data is stored**. | Focuses on **operations on data**. |
| **Example** | `List<String> list = new ArrayList<>();` | `Collections.sort(list);` |

- ## 3) What are the main interfaces: `List`, `Set`, `Map`, `Queue`?
  In the Java Collections Framework, these four interfaces represent the fundamental ways to store and organize data.

| Interface | Extension | Characteristic | Best Use Case |
| --- | --- | --- | --- |
| **List** | `Collection` | Ordered, allows duplicates, index-based access. | When order is important or you need to access items by index. |
| **Set** | `Collection` | Unordered (usually), no duplicates allowed. | When ensuring uniqueness is critical (e.g., unique user IDs). |
| **Map** | Standalone | Key-Value pairs, keys must be unique. | When you need to look up values using a specific key (dictionary). |
| **Queue** | `Collection` | Holds elements prior to processing (FIFO/LIFO). | When items need to be processed in a specific sequence. |

- ## 4) Difference between **ArrayList vs LinkedList**?
  ArrayList and LinkedList are both implementations of the `List` interface, but they differ primarily in data structure and performance.

| Feature | ArrayList | LinkedList |
| --- | --- | --- |
| **Internal Structure** | Resizable Dynamic Array. | Doubly Linked List. |
| **Access (get/set)** | **O(1)** - Fast random access. | **O(n)** - Must traverse nodes. |
| **Insertion (Start)** | **O(n)** - Must shift all elements. | **O(1)** - Just flip pointers. |
| **Insertion (End)** | **O(1)*** - Amortized constant time. | **O(1)** - Direct tail pointer. |
| **Memory Usage** | Less memory (stores data + index). | More memory (stores data + prev/next pointers). |
| **Best Case** | Frequent read operations. | Frequent insertions/deletions at ends. |

- ##  5) Practical Performance Benchmarking
  
  - ### Benchmarking for insertions at different positions (front, middle, end) with a large number of elements (e.g., 900,000):

  ```java
    public static ArrayList<String> arrayList = new ArrayList<>();
    public static LinkedList<String> linkedList = new LinkedList<>();
    public static int counter = 900_000;
    public static int mode;

    static {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the counter value");
        mode = scanner.nextInt();
        for (int i = 0; i < 1000; i++) {
            switch (mode) {
                case 1:
                    arrayList.add(i, "Hello");
                    linkedList.add(i, "Hello");
                    break;
                case 2:
                    arrayList.addFirst("Hello");
                    linkedList.addFirst("Hello");
                    break;
                case 3:
                    arrayList.addLast("Hello");
                    linkedList.addLast("Hello");
                    break;
                default:
                    arrayList.add(i, "Hello");
                    linkedList.add(i, "Hello");
            }
        }
    }
  
    public static void main(String[] args) {
        updateListWithCounter(counter);
    }
  
      public static void updateListWithCounter(int counter) {

        arrayList.add("Amit");
        arrayList.add("Amit");
        arrayList.add("Amit");
        int insertLocation = Math.round((float) arrayList.size() / 2);
        long start1 = System.currentTimeMillis();
        for (int i = 0; i < counter; i++) {
            switch (mode) {
                case 1 -> arrayList.add(insertLocation, "Hello");
                case 2 -> arrayList.addFirst("Hello");
                case 3 -> arrayList.addLast("Hello");
                default -> arrayList.add(i, "Hello");
            }
        }
        long end1 = System.currentTimeMillis();
        System.out.println("AL With counter " + counter + " Time taken " + (end1 - start1));


        linkedList.add("Amit");
        linkedList.add("Amit");
        linkedList.add("Amit");
        insertLocation = Math.round((float) linkedList.size() / 2);
        long start2 = System.currentTimeMillis();
        for (int i = 0; i < counter; i++) {
            switch (mode) {
                case 1 -> linkedList.add(insertLocation, "Hello");
                case 2 -> linkedList.addFirst("Hello");
                case 3 -> linkedList.addLast("Hello");
                default -> linkedList.add(i, "Hello");
            }
        }
        long end2 = System.currentTimeMillis();
        System.out.println("LL With counter " + counter + " Time taken " + (end2 - start2));
    }
  ```

  **Benchmark Results (Typical):**
  - **Add to Front**: `ArrayList` (~4000ms) vs `LinkedList` (~5ms).
  - **Random Access**: `ArrayList` (~1ms) vs `LinkedList` (>10,000ms).

  - ### Benchmarking for sorting and random access with a large dataset (e.g., 90,000 employees):
```java
    static final int SIZE = 90_000;
    static final int RANDOM_ACCESS_OPS = 20_000;
    static List<Employee> baseData = new ArrayList<>();


    public static void main(String[] args) {
        generateData();
        // Warm-up (important for JIT)
        for (int i = 0; i < 3; i++) {
            runAllTests(false);
        }
        System.out.println("==== FINAL RUN ====");
        runAllTests(true);
    }

    static void runAllTests(boolean print) {
        testSort(print);
        testRandomAccess(print);
        System.out.println();
    }


    // ---------------- SORT ----------------
    static void testSort(boolean print) {
        List<Employee> arrayList = new ArrayList<>(baseData);
        List<Employee> linkedList = new LinkedList<>(baseData);

        Comparator<Employee> comparator = Comparator.comparingInt(e -> e.salary);

        long start = System.nanoTime();
        arrayList.sort(comparator);
        long end = System.nanoTime();
        if (print) System.out.println("ArrayList sort: " + (end - start) / 1_000_000 + " ms");

        start = System.nanoTime();
        linkedList.sort(comparator);
        end = System.nanoTime();
        if (print) System.out.println("LinkedList sort: " + (end - start) / 1_000_000 + " ms");
    }

    // ---------------- RANDOM ACCESS ----------------
    static void testRandomAccess(boolean print) {
        List<Employee> arrayList = new ArrayList<>(baseData);
        List<Employee> linkedList = new LinkedList<>(baseData);
        Random random = new Random(42);

        long sum = 0;
        long start = System.nanoTime();
        for (int i = 0; i < RANDOM_ACCESS_OPS; i++) {
            sum += arrayList.get(random.nextInt(SIZE)).salary;
        }
        long end = System.nanoTime();
        if (print) System.out.println(sum + " ArrayList random access: " + (end - start) / 1_000_000 + " ms");

        sum = 0;
        start = System.nanoTime();
        for (int i = 0; i < RANDOM_ACCESS_OPS; i++) {
            sum += linkedList.get(random.nextInt(SIZE)).salary;
        }
        end = System.nanoTime();
        if (print) System.out.println(sum + " LinkedList random access: " + (end - start) / 1_000_000 + " ms");
    }

    // ---------------- DATA ----------------
    static void generateData() {
        Random random = new Random(42);

        for (int i = 0; i < SIZE; i++) {
            baseData.add(new Employee(i, "Emp" + i, random.nextInt(100, 100_000)));
        }
    }

    // ---------------- MODEL ----------------
    static class Employee {
        int id;
        String name;
        int salary;

        Employee(int id, String name, int salary) {
            this.id = id;
            this.name = name;
            this.salary = salary;
        }
    }
```

- ## 6) Difference between **List vs Set**?
  The primary difference between a **List** and a **Set** lies in how they handle duplicate elements and the order of those elements.

| Feature | List | Set |
| --- | --- | --- |
| **Duplicates** | Allowed. | Not allowed. |
| **Ordering** | Maintains insertion order. | No guarantee of order (except `LinkedHashSet`/`TreeSet`). |
| **Access** | Index-based (`get(index)`). | No index-based access (iterator/foreach only). |
| **Use Case** | Message history, audit logs. | Unique user IDs, unique IP addresses. |

- ## 7) What is **HashMap**? How does it work internally?
    - A HashMap is a data structure that stores data in key-value pairs.
    - Allows for nearly constant-time **O(1)** performance for basic operations on average.
    - Part of `java.util` package but does NOT extend the `Collection` interface.

  **Internal Data Structure**: Internally, a HashMap is an **array of Nodes** (buckets). Each `Node` contains:
  1. `hash`: The processed hash code of the key.
  2. `key`: The actual key object.
  3. `value`: The value associated with the key.
  4. `next`: Reference to the next node in the same bucket (collision handling).

  **How it Works (Step-by-Step)**:
  - **Hashing**: `index = (capacity - 1) & hash`.
  - **Put Operation**:
    - If bucket is empty, store node directly.
    - If not empty, check for key equality via `equals()`.
    - If keys match, overwrite value. If not, add to the chain.
  - **Collision Handling**: 
    - **Chaining**: Entries at the same index are linked.
    - **Treeification (Java 8+)**: If a bucket exceeds **8 nodes**, it converts to a **Red-Black Tree** (search improves from O(n) to **O(log n)**).
  - **Resizing**: When size > (Capacity * Load Factor), the array doubles in size and rehashes all elements.

- ## 8) Difference between **HashMap vs Hashtable**?
  Both store key-value pairs, but they differ in synchronization and null handling.

| Feature | HashMap | Hashtable (Legacy) |
| --- | --- | --- |
| **Thread Safety** | Not thread-safe. | Thread-safe (synchronized methods). |
| **Null Keys/Values** | Allows 1 null key, multiple null values. | Does not allow any null keys or values. |
| **Performance** | High (no synchronization overhead). | Low (due to global locking). |
| **Modern Choice** | `HashMap` or `ConcurrentHashMap`. | Obsolete; avoid in new code. |

- ## 9) What is **load factor** and **initial capacity** in HashMap?
  - **Initial Capacity**: The number of buckets created when the map is initialized (Default: **16**). Always a power of 2.
  - **Load Factor**: A measure of how full the map gets before resizing (Default: **0.75**).
    - **Threshold** = Capacity * Load Factor (e.g., 16 * 0.75 = 12).
    - Resizing triggers when the 13th element is added.

- ## 10) What happens when two keys have same hash? (collision handling)
  When two keys map to the same bucket index, it's called a **collision**.
  1. **Identification**: Calculate bucket index via hash.
  2. **Traversing**: Scan the linked list or tree in that bucket.
  3. **Equality Check**: Use `equals()` to find the exact key.
     - Match found -> Replace value.
     - No match found -> Add new node to the end.
  4. **Treeification**: If the list gets too long (>8), it becomes a tree for O(log n) lookups.

- ## 11) Difference between **equals() and hashCode()**?
  - **`equals(Object obj)`**: Determines if two objects are logically equal (compares meaningful fields).
  - **`hashCode()`**: Returns an integer representing the object's "address" in a hash table.

  **The Strict Contract**:
  - If `a.equals(b)`, then `a.hashCode() == b.hashCode()`.
  - If `a.hashCode() == b.hashCode()`, it does **NOT** mean `a.equals(b)` (this is a collision).
  - If you override `equals()`, you **MUST** override `hashCode()`. Breaking this causes hash-based collections to lose or duplicate your data.
