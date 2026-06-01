# Concurrency Interview Questions: Intermediate

- ## 1) What is the **Executor Framework**?
    - **Definition**: A framework provided by `java.util.concurrent` that decouples task submission from how the task is executed.
    - **Benefits**:
        *   **Thread Reuse**: Avoids the overhead of creating/destroying threads.
        *   **Task Management**: Handles task queuing and rejection.
        *   **Resource Control**: Prevents system overload by bounding the number of active threads.

- ## 2) Different types of **Thread Pools**?

| Pool Type | Behavior | Best Use Case |
| --- | --- | --- |
| **FixedThreadPool** | Fixed number of threads. | Stable, predictable loads. |
| **CachedThreadPool**| Creates threads as needed; reuses idle ones. | Many short-lived tasks. |
| **SingleThreadExecutor**| Exactly one thread. | Sequential tasks / Serializing. |
| **ScheduledThreadPool**| Supports delayed/periodic tasks. | Cron-like jobs / Polling. |

- ## 3) `Future` vs `CompletableFuture`?

| Feature | `Future` (Java 5) | `CompletableFuture` (Java 8) |
| --- | --- | --- |
| **Non-blocking** | No (requires `get()` which blocks). | Yes (callbacks like `thenApply`). |
| **Chaining** | No. | Yes (fluent API). |
| **Manual Completion**| No. | Yes. |
| **Exception Handling**| Basic (try-catch). | Advanced (`exceptionally`, `handle`). |

- ## 4) What is **ReentrantLock**? How is it different from `synchronized`?
    - **Definition**: A concrete implementation of the `Lock` interface that offers more features than implicit monitor locks.
    - **Key Differences**:
        *   **tryLock()**: Can attempt to acquire a lock without waiting forever.
        *   **Fairness**: Can be configured to give the lock to the longest-waiting thread.
        *   **Condition**: Supports multiple wait-sets per lock (via `newCondition()`).
        *   **Interruptible**: A thread can be interrupted while waiting for the lock.

- ## 5) How does **ConcurrentHashMap** work internally?
    - **Java 7**: Used **Lock Striping** (Segmented locking). Only the segment being accessed was locked.
    - **Java 8+**: Uses **CAS (Compare-and-Swap)** and `synchronized` on individual bucket heads. It allows multiple threads to write to different buckets simultaneously.
    - **Nulls**: Does **not** allow null keys or values (to avoid ambiguity in multi-threaded checks).

- ## 6) Explain **CountDownLatch** vs **CyclicBarrier**.

| Feature | **CountDownLatch** | **CyclicBarrier** |
| --- | --- | --- |
| **Reusability** | No (One-time use). | Yes (Can be reset). |
| **Mechanism** | Threads wait for count to reach 0. | Threads wait for each other at a point. |
| **Main Use Case** | Startup synchronization. | Multi-phase parallel algorithms. |

- ## 7) What is a **Semaphore**?
    - A synchronization utility that maintains a set of **permits**.
    - **Binary Semaphore**: A semaphore with only 1 permit (similar to a Mutex).
    - **Counting Semaphore**: Used to limit the number of concurrent threads accessing a resource (e.g., a connection pool).

- ## 8) What is **ThreadLocal**?
    - **Purpose**: Provides thread-local variables. Each thread has its own, independently initialized copy of the variable.
    - **Use Case**: Storing user sessions, transaction IDs, or non-thread-safe objects like `SimpleDateFormat`.
    - **Risk**: Memory leaks in managed thread pools if `remove()` is not called (threads are reused, but ThreadLocalMap entries might persist).
