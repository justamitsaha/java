# Concurrency Interview Questions: Basics

- ## 1) Difference between a **Process** and a **Thread**?

| Feature | Process | Thread |
| --- | --- | --- |
| **Definition** | An instance of a program in execution. | A subset of a process (lightweight). |
| **Memory** | Has its own independent address space. | Shares memory/resources of the process. |
| **Creation** | Heavyweight (more overhead). | Lightweight (less overhead). |
| **Communication** | IPC (Inter-process communication). | Direct (shares objects/variables). |
| **Failure** | One process crash doesn't affect others. | One thread crash can kill the process. |

- ## 2) What are the different **Thread States** in Java?
  Java threads follow a lifecycle managed by the JVM and OS.

| State | Description |
| --- | --- |
| **NEW** | Thread is created but `start()` hasn't been called. |
| **RUNNABLE** | Executing in the JVM (might be waiting for OS CPU time). |
| **BLOCKED** | Waiting for a monitor lock (entering a `synchronized` block). |
| **WAITING** | Waiting indefinitely for another thread (`join()`, `wait()`). |
| **TIMED_WAITING** | Waiting for a specified time (`sleep()`, `wait(ms)`). |
| **TERMINATED** | Thread has finished execution. |

- ## 3) `Runnable` vs `Thread` vs `Callable`?
  Which one should you prefer for task definition?

| Feature | `Thread` (Class) | `Runnable` (Interface) | `Callable` (Interface) |
| --- | --- | --- | --- |
| **Approach** | Inheritance | Composition | Composition |
| **Return Value** | No | No | Yes (`Future<T>`) |
| **Exceptions** | Unchecked only | Unchecked only | Checked & Unchecked |
| **Prefer?** | Rarely | For background tasks | For result-driven tasks |

- ## 4) What is a **Race Condition**? How do you prevent it?
    - **Definition**: A situation where multiple threads access shared data concurrently, and the final result depends on the timing of their execution.
    - **Prevention**:
        1.  **Synchronization**: Use `synchronized` blocks/methods.
        2.  **Atomics**: Use `AtomicInteger`, `AtomicReference`, etc.
        3.  **Immutable Objects**: Making data final and unchangeable.
        4.  **Thread Confinement**: Giving each thread its own copy of data.

  ### **Practical Example (`CoreThreadsPractice.java`)**
  ```java
  // RACE CONDITION
  for (int i = 0; i < 100_000; i++) counter++; 

  // FIXED
  synchronized(lock) {
      counter++;
  }
  ```

- ## 5) Difference between `sleep()` and `wait()`?

| Feature | `Thread.sleep()` | `Object.wait()` |
| --- | --- | --- |
| **Release Lock?** | **No** | **Yes** (releases the monitor) |
| **Class** | `Thread` static method | `Object` instance method |
| **Usage** | Pause current thread | Thread communication |
| **Condition** | Resumes after time | Needs `notify()`/`notifyAll()` |
| **Requirement** | None | Must be in `synchronized` context |

- ## 6) What is the `volatile` keyword?
    - **Visibility**: It ensures that a variable is always read from and written to the **main memory**, not the CPU cache. This guarantees that all threads see the most recent value.
    - **Atomicity**: `volatile` does **NOT** guarantee atomicity (e.g., `volatileCount++` is still not thread-safe). It only guarantees visibility.
    - **Use Case**: Flag indicators (e.g., `volatile boolean running = true;`).

- ## 7) What are **Daemon Threads**?
    - **Definition**: Low-priority threads that provide services to user threads (e.g., Garbage Collector).
    - **JVM Lifecycle**: The JVM exits when only daemon threads remain. If all non-daemon threads finish, the JVM kills all daemons and exits.
    - **Usage**: `thread.setDaemon(true);` (must be called before `start()`).

- ## 8) What is the purpose of `join()`?
    - It allows one thread to wait for the completion of another.
    - **Example**: `t1.join()` ensures that the current thread pauses until `t1` finishes.
