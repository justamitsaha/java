# Concurrency Interview Preparation

This folder contains a curated list of Java Concurrency interview questions and answers, categorized by difficulty and topic.

## 📂 Folder Structure

1.  **[1_Basics.md](./1_Basics.md)**
    *   Thread vs Process
    *   Thread Lifecycle & States
    *   Creation (Runnable vs Thread vs Callable)
    *   `synchronized`, `volatile`, and Basic Thread Safety
    *   `wait()`, `notify()`, `join()`, and `sleep()`

2.  **[3_Intermediate.md](./3_Intermediate.md)**
    *   Executor Framework & Thread Pools
    *   Explicit Locks (`ReentrantLock`, `ReadWriteLock`)
    *   Concurrent Collections (`ConcurrentHashMap`, `BlockingQueue`)
    *   Synchronization Utilities (`CountDownLatch`, `Semaphore`, `CyclicBarrier`)
    *   ThreadLocal & Atomic Variables

3.  **[4_Advanced.md](./4_Advanced.md)**
    *   Java Memory Model (JMM) & Happens-Before
    *   `CompletableFuture` & Asynchronous Programming
    *   Virtual Threads & Structured Concurrency (Java 21+)
    *   Fork/Join Framework
    *   Deadlocks, Livelocks, and Starvation
    *   Optimizing Concurrency (Lock Striping, CAS)

## 💡 How to Use
*   Start with the **Basics** to solidify your foundation of the JVM thread model.
*   The **Intermediate** section is the "sweet spot" for most senior engineer interviews.
*   Use the **Advanced** section to stand out, especially with modern Java 21+ features.
*   Refer to the parent directory's practice files (e.g., `CoreThreadsPractice.java`) for runnable demonstrations of these concepts.
