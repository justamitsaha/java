# Java Concurrency

Java Concurrency provides a framework for multi-threaded programming, allowing applications to perform multiple tasks simultaneously, improving performance and responsiveness.

---

## 1. Thread Creation
In Java, there are several ways to define and run a task in a separate thread.

| Feature | Thread Class | Runnable Interface | Callable Interface | **Virtual Threads (J21)** |
| --- | --- | --- | --- | --- |
| **Return Value** | No | No | Yes (via `Future`) | Depends on Wrapper |
| **Thread Type** | Platform Thread | Platform Thread | Platform Thread | Lightweight (Carrier) |
| **Overhead** | High (1MB stack) | High | High | Very Low (KB stack) |
| **Scaling** | Limited (Thousands) | Limited | Limited | Millions |
| **Main Use Case**| Legacy tasks | Async tasks | Result-driven tasks| High-concurrency I/O |

### **Practical Examples (`ThreadCreation.java` & `ExpertConcurrencyPractice.java`)**

```java
// 1. Implementing Runnable (Modern Lambda)
Thread thread = new Thread(() -> {
    System.out.println("Running on Platform Thread");
}).start();

// 2. Virtual Threads (Java 21+)
Thread vThread = Thread.ofVirtual().name("v-1").start(() -> {
    System.out.println("Running on Virtual Thread");
});

// 3. Virtual Thread Executor
try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
    executor.submit(() -> "Task on virtual thread");
}
```

---

## 2. Synchronization & Atomics
Mechanisms to control access to shared resources and prevent race conditions.

| Feature | `synchronized` | `ReentrantLock` | `AtomicInteger` |
| --- | --- | --- | --- |
| **Mechanism** | Monitor / Blocking | Explicit / Blocking | CAS (Compare And Swap) |
| **Fairness** | No | Optional | N/A (Lock-free) |
| **Complexity** | Simple | Moderate | Moderate |
| **Performance** | Best (No contention) | Better (Contention) | Best (High contention) |
| **Main Use Case** | Simple critical sections | Advanced locking (tryLock) | Global counters / IDs |

### **Lock-Free vs Synchronized (`AdvancedConcurrencyPractice.java`)**

```java
// 1. Atomic Variable (Lock-free)
AtomicInteger atomicCounter = new AtomicInteger(0);
atomicCounter.incrementAndGet(); // Thread-safe via CAS

// 2. ReentrantLock with tryLock
ReentrantLock lock = new ReentrantLock();
if (lock.tryLock()) {
    try {
        // Critical Section
    } finally {
        lock.unlock();
    }
}
```

---

## 3. Executor Framework
Managed thread pools for efficient task execution.

| Feature | Fixed Thread Pool | Cached Thread Pool | Scheduled Executor |
| --- | --- | --- | --- |
| **Thread Count** | Constant | Dynamic | Constant |
| **Performance** | Predictable | High (Short tasks) | High (Timed tasks) |
| **Main Use Case** | Stable load / API | Spike-heavy traffic | Cron jobs / Polling |

### **Advanced Executor Usage (`ExecutorsPractice.java`)**

```java
ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);
// Run task every 300ms regardless of previous task completion
ses.scheduleAtFixedRate(task, 0, 300, TimeUnit.MILLISECONDS);

// invokeAny: Return result of the FIRST task that finishes successfully
String firstFinished = pool.invokeAny(List.of(taskA, taskB, taskC));
```

---

## 4. CompletableFuture (Asynchronous Pipelines)
A powerful tool for writing non-blocking asynchronous code.

| Operation | Purpose | Non-Blocking |
| --- | --- | --- |
| `supplyAsync` | Starts async task | Yes |
| `thenApply` | Transforms result | Yes |
| `thenCombine` | Combines two CFs | Yes |
| `exceptionally` | Handles errors | Yes |
| `join()` | Blocks until done | **No** |

### **Async Pipeline Example (`ExpertConcurrencyPractice.java`)**

```java
CompletableFuture<Integer> pipeline = CompletableFuture.supplyAsync(() -> fetchPrice())
    .thenApply(price -> price * 1.1) // Add tax
    .thenCombine(fetchDiscount(), (price, disc) -> price - disc)
    .exceptionally(ex -> 0.0) // Fallback on error
    .orTimeout(1, TimeUnit.SECONDS);

System.out.println("Final Price: " + pipeline.join());
```

---

## 5. Concurrent Collections & Utilities
Optimized data structures and coordination classes.

| Feature | `ConcurrentHashMap` | `CopyOnWriteArrayList` | `CountDownLatch` | `Semaphore` |
| --- | --- | --- | --- | --- |
| **Purpose** | Thread-safe Map | Thread-safe List | One-time Sync | Resource Permits |
| **Performance** | High (Stripped lock) | High Read / Slow Write | High | High |
| **Main Use Case** | Shared Caches | Listeners / Config | Startup sync | Rate limiting |

### **Practical Scenario**

- **Semaphore**: "I only have 3 physical printer connections. Only 3 threads can `acquire()` at a time; others must wait."
- **CountDownLatch**: "I am a Microservice. I will not start serving traffic until my 5 internal dependencies (DB, Redis, MQ, etc.) have sent a `countDown()` signal."
- **CopyOnWriteArrayList**: "I have 10,000 threads reading my 'AppSettings' list. I only update it once a week. Copying the array on write is worth the 100% lock-free reads."
