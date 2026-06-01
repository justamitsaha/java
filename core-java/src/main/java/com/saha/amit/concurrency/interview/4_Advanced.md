# Concurrency Interview Questions: Advanced

- ## 1) What are **Virtual Threads** (Java 21)?
    - **Definition**: Lightweight threads provided by Project Loom that are not tied 1:1 to OS threads. Thousands or even millions of virtual threads can run on a small number of "Carrier" (Platform) threads.
    - **Why use them?**: They allow writing high-throughput, blocking I/O code with the simplicity of the "thread-per-request" model, without the memory overhead of platform threads.
    - **Performance**: They are extremely cheap to create and context switch, but they do **not** improve performance for CPU-bound tasks (only I/O-bound).

- ## 2) Explain the **Java Memory Model (JMM)** and **Happens-Before**.
    - **JMM**: A specification that defines how the JVM interacts with computer memory (RAM). It ensures that different threads see consistent values of shared variables.
    - **Happens-Before**: A relationship between two actions where the first action's result is guaranteed to be visible to the second action.
        *   **Program Order**: Each action in a single thread happens-before any subsequent action in that thread.
        *   **Volatile Write**: A write to a volatile field happens-before every subsequent read of that same field.
        *   **Locking**: Unlocking a monitor happens-before every subsequent locking of that same monitor.

- ## 3) What is **Structured Concurrency**?
    - Introduced in Java 21 (Preview/Incubator). It treats multiple tasks running in different threads as a single unit of work.
    - If a parent task fails, all sub-tasks are automatically cancelled.
    - **Mechanism**: `StructuredTaskScope`.

- ## 4) **Deadlock**, **Livelock**, and **Starvation**?
    - **Deadlock**: Two or more threads are stuck forever, each waiting for a lock held by the other.
    - **Livelock**: Threads keep changing their state in response to each other but make no progress (like two people trying to pass each other in a hallway and moving the same way).
    - **Starvation**: A thread is perpetually denied access to resources (locks/CPU) because other "greedier" threads are prioritized.

- ## 5) How do you detect a **Deadlock**?
    1.  **Programmatic**: Use `ThreadMXBean.findDeadlockedThreads()`.
    2.  **External**: Use `jstack` or `jconsole` to look for circular wait dependencies.
    3.  **Prevention**: Always acquire locks in a global, consistent order.

- ## 6) What is the **Fork/Join Framework**?
    - Designed for work that can be broken down into smaller pieces recursively (**Divide and Conquer**).
    - **Work-Stealing Algorithm**: Idle threads "steal" tasks from the back of the deques of busy threads to maximize CPU utilization.
    - **Main Class**: `ForkJoinPool` and `RecursiveTask` / `RecursiveAction`.

- ## 7) What is **CAS (Compare-and-Swap)**?
    - An atomic instruction used in lock-free algorithms. It compares the current value of a variable to an expected value; if they match, it updates the variable to a new value.
    - It is the foundation of the `java.util.concurrent.atomic` package.
    - **Pros**: Avoids the overhead of context switching and thread suspension.
    - **Cons**: Can lead to "Spinning" (high CPU usage) under heavy contention.

- ## 8) What is the **ABA Problem**?
    - A classic CAS issue where a value changes from A to B and back to A. A thread performing CAS sees "A" and thinks nothing changed, even though it did.
    - **Fix**: Use `AtomicStampedReference` (adds a version/stamp to the reference).
