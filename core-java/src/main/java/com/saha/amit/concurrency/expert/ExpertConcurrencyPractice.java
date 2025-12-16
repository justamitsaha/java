package com.saha.amit.concurrency.expert;

import java.time.Duration;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ExpertConcurrencyPractice {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n===== Expert Concurrency (Java 21) =====");
            System.out.println("1. Virtual Threads: Thread.ofVirtual()");
            System.out.println("2. Virtual Thread Per Task Executor");
            System.out.println("3. Structured Concurrency: StructuredTaskScope (ShutdownOnFailure)");
            System.out.println("4. CompletableFuture Advanced (combine/exceptionally)");
            System.out.println("5. ForkJoin RecursiveTask Demo");
            System.out.println("0. Exit");
            System.out.print("Choose: ");

            int c = readInt(sc);
            switch (c) {
                case 1 -> virtualThreadsBasic();
                case 2 -> virtualThreadPerTaskExecutor();
                case 3 -> structuredTaskScopeDemo();
                case 4 -> completableFutureAdvanced();
                case 5 -> forkJoinDemo();
                case 0 -> { System.out.println("Bye!"); return; }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static int readInt(Scanner sc) {
        while (!sc.hasNextInt()) { sc.next(); System.out.print("Enter number: "); }
        return sc.nextInt();
    }

    // 1) Virtual threads basics
    static void virtualThreadsBasic() {
        Runnable r = () -> System.out.println(Thread.currentThread() + " says hi");
        Thread t1 = Thread.ofVirtual().name("v-1").unstarted(r);
        t1.start();
        try { t1.join(); } catch (InterruptedException ignored) {}
    }

    // 2) Virtual thread per task executor
    static void virtualThreadPerTaskExecutor() {
        try (ExecutorService vexec = Executors.newVirtualThreadPerTaskExecutor()) {
            AtomicInteger counter = new AtomicInteger();
            List<Callable<Integer>> tasks = java.util.stream.IntStream.range(0, 10)
                    .mapToObj(i -> (Callable<Integer>) () -> {
                        Thread.sleep(100);
                        return counter.incrementAndGet();
                    }).toList();
            try {
                List<Future<Integer>> futures = vexec.invokeAll(tasks);
                System.out.println("Completed: " + futures.stream().map(f -> safeGet(f)).toList());
            } catch (InterruptedException e) { e.printStackTrace(); }
        }
    }

    private static Integer safeGet(Future<Integer> f) {
        try { return f.get(); } catch (Exception e) { throw new RuntimeException(e); }
    }

    // 3) Structured concurrency (ShutdownOnFailure)
    static void structuredTaskScopeDemo() {
        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            Subtask s1 = new Subtask("A", 200, false);
            Subtask s2 = new Subtask("B", 400, false);
            Subtask s3 = new Subtask("C", 250, true); // will fail

//            Future<String> f1 = scope.fork(s1);
//            Future<String> f2 = scope.fork(s2);
//            Future<String> f3 = scope.fork(s3);
//
//            try {
//                scope.join();     // wait for all
//                scope.throwIfFailed(); // throws if any failed and cancels the rest
//                System.out.println(f1.resultNow() + " " + f2.resultNow() + " " + f3.resultNow());
//            } catch (Exception e) {
//                System.out.println("One task failed; others cancelled: " + e.getMessage());
//            }
        }
    }

    static class Subtask implements Callable<String> {
        final String name; final long delay; final boolean fail;
        Subtask(String name, long delay, boolean fail) { this.name = name; this.delay = delay; this.fail = fail; }
        @Override public String call() throws Exception {
            Thread.sleep(delay);
            if (fail) throw new RuntimeException("Subtask " + name + " failed");
            return "Subtask " + name + " ok on " + Thread.currentThread();
        }
    }

    // 4) CompletableFuture advanced: combine & exception handling
    static void completableFutureAdvanced() {
        CompletableFuture<Integer> left = CompletableFuture.supplyAsync(() -> slow(200, 10));
        CompletableFuture<Integer> right = CompletableFuture.supplyAsync(() -> slow(300, 5));

        Integer result = left.thenCombine(right, Integer::sum)
                .thenApply(sum -> sum * 2)
                .orTimeout(1, TimeUnit.SECONDS)
                .exceptionally(ex -> {
                    System.out.println("Exception: " + ex);
                    return -1;
                })
                .join();

        System.out.println("Result = " + result);
    }

    static int slow(long ms, int v) {
        try { Thread.sleep(ms); } catch (InterruptedException ignored) {}
        return v;
    }

    // 5) ForkJoin RecursiveTask (sum array)
    static void forkJoinDemo() {
        ForkJoinPool fj = ForkJoinPool.commonPool();
        int[] arr = java.util.stream.IntStream.rangeClosed(1, 1_000).toArray();
        SumTask task = new SumTask(arr, 0, arr.length);
        long sum = fj.invoke(task);
        System.out.println("Sum 1..1000 = " + sum);
    }

    static class SumTask extends RecursiveTask<Long> {
        static final int THRESHOLD = 100;
        final int[] a; final int lo, hi;
        SumTask(int[] a, int lo, int hi) { this.a = a; this.lo = lo; this.hi = hi; }
        @Override protected Long compute() {
            int len = hi - lo;
            if (len <= THRESHOLD) {
                long s = 0; for (int i = lo; i < hi; i++) s += a[i]; return s;
            }
            int mid = lo + len/2;
            SumTask left = new SumTask(a, lo, mid);
            SumTask right = new SumTask(a, mid, hi);
            left.fork();
            long r = right.compute();
            long l = left.join();
            return l + r;
        }
    }
}
