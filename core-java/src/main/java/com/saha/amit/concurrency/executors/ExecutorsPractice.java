package com.saha.amit.concurrency.executors;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;

public class ExecutorsPractice {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n===== Executors & Thread Pools =====");
            System.out.println("1. Fixed Thread Pool + Runnable");
            System.out.println("2. Callable + Future (return values)");
            System.out.println("3. invokeAll vs invokeAny");
            System.out.println("4. ScheduledExecutorService (delay & rate)");
            System.out.println("5. Shutdown vs ShutdownNow");
            System.out.println("0. Exit");
            System.out.print("Choose: ");
            int c = readInt(sc);
            switch (c) {
                case 1 -> fixedPoolRunnable();
                case 2 -> callableWithFuture();
                case 3 -> invokeAllAny();
                case 4 -> scheduledExecutorDemo();
                case 5 -> shutdownDemo();
                case 0 -> { System.out.println("Bye!"); return; }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static int readInt(Scanner sc) {
        while (!sc.hasNextInt()) { sc.next(); System.out.print("Enter a number: "); }
        return sc.nextInt();
    }

    // 1) Runnable tasks on a fixed pool
    static void fixedPoolRunnable() {
        ExecutorService pool = Executors.newFixedThreadPool(3);
        Runnable r = () -> System.out.println(LocalTime.now() + " " +
                Thread.currentThread().getName() + " ran task");
        for (int i = 0; i < 6; i++) pool.submit(r);
        pool.shutdown();
        await(pool);
    }

    // 2) Callable returning values with Future
    static void callableWithFuture() {
        ExecutorService pool = Executors.newFixedThreadPool(2);
        Callable<Integer> c1 = () -> {
            TimeUnit.MILLISECONDS.sleep(200);
            return 42;
        };
        Future<Integer> f = pool.submit(c1);
        try {
            System.out.println("Result: " + f.get()); // blocks
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally { pool.shutdown(); await(pool); }
    }

    // 3) invokeAll vs invokeAny
    static void invokeAllAny() {
        ExecutorService pool = Executors.newCachedThreadPool();
        List<Callable<String>> tasks = List.of(
                slow("A", 400),
                slow("B", 200),
                slow("C", 300)
        );
        try {
            System.out.println("-- invokeAll (waits all) --");
            List<Future<String>> all = pool.invokeAll(tasks);
            for (Future<String> f : all) System.out.println(f.get());

            System.out.println("-- invokeAny (first wins) --");
            String any = pool.invokeAny(tasks);
            System.out.println("Winner: " + any);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally { pool.shutdown(); await(pool); }
    }

    static Callable<String> slow(String name, long ms) {
        return () -> {
            TimeUnit.MILLISECONDS.sleep(ms);
            return "Task " + name + " done on " + Thread.currentThread().getName();
        };
    }

    // 4) Scheduled executor: delay + fixed rate
    static void scheduledExecutorDemo() {
        ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);
        Runnable tick = () -> System.out.println(LocalTime.now() + " tick");

        System.out.println("Scheduling one-shot after 500ms and a fixed-rate every 300ms (3 ticks)...");
        ses.schedule(() -> System.out.println("One-shot!"), 500, TimeUnit.MILLISECONDS);
        ScheduledFuture<?> handle = ses.scheduleAtFixedRate(tick, 0, 300, TimeUnit.MILLISECONDS);

        sleep(1100);
        handle.cancel(true);
        ses.shutdown();
        await(ses);
    }

    // 5) Shutdown vs ShutdownNow
    static void shutdownDemo() {
        ExecutorService pool = Executors.newFixedThreadPool(2);
        Runnable longTask = () -> {
            try {
                for (int i = 0; i < 10; i++) {
                    System.out.println("Working " + i + " in " + Thread.currentThread().getName());
                    TimeUnit.MILLISECONDS.sleep(150);
                }
            } catch (InterruptedException e) {
                System.out.println("Interrupted: " + Thread.currentThread().getName());
            }
        };
        pool.submit(longTask);
        pool.submit(longTask);

        sleep(300);
        // Try: pool.shutdown(); // graceful
        List<Runnable> neverStarted = pool.shutdownNow(); // forceful interrupt
        System.out.println("Not started tasks: " + neverStarted.size());
        await(pool);
    }

    private static void sleep(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException ignored) {}
    }

    private static void await(ExecutorService svc) {
        try { svc.awaitTermination(5, TimeUnit.SECONDS); } catch (InterruptedException ignored) {}
    }
}
