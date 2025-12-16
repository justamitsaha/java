package com.saha.amit.concurrency.advanced;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class AdvancedConcurrencyPractice {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n===== Advanced Concurrency =====");
            System.out.println("1. ReentrantLock & tryLock");
            System.out.println("2. AtomicInteger vs non-atomic");
            System.out.println("3. CountDownLatch Demo");
            System.out.println("4. CyclicBarrier Demo");
            System.out.println("5. Semaphore (limit concurrency)");
            System.out.println("6. ThreadLocal Demo");
            System.out.println("7. Producer-Consumer with BlockingQueue");
            System.out.println("8. CompletableFuture Basics (supplyAsync/thenApply)");
            System.out.println("0. Exit");
            System.out.print("Choose: ");

            int c = readInt(sc);
            switch (c) {
                case 1 -> reentrantLockDemo();
                case 2 -> atomicIntegerDemo();
                case 3 -> countDownLatchDemo();
                case 4 -> cyclicBarrierDemo();
                case 5 -> semaphoreDemo();
                case 6 -> threadLocalDemo();
                case 7 -> producerConsumerDemo();
                case 8 -> completableFutureBasics();
                case 0 -> { System.out.println("Bye!"); return; }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static int readInt(Scanner sc) {
        while (!sc.hasNextInt()) { sc.next(); System.out.print("Enter number: "); }
        return sc.nextInt();
    }

    // 1) ReentrantLock
    static void reentrantLockDemo() {
        ReentrantLock lock = new ReentrantLock();
        Runnable critical = () -> {
            String n = Thread.currentThread().getName();
            if (lock.tryLock()) {
                try {
                    System.out.println(n + " acquired lock");
                    Thread.sleep(300);
                } catch (InterruptedException ignored) {} finally {
                    lock.unlock();
                    System.out.println(n + " released lock");
                }
            } else {
                System.out.println(n + " could not acquire lock");
            }
        };
        Thread t1 = new Thread(critical, "L1");
        Thread t2 = new Thread(critical, "L2");
        t1.start(); t2.start();
        join(t1, t2);
    }

    // 2) AtomicInteger vs non-atomic
    static void atomicIntegerDemo() {
        final int N = 200_000;
        int[] plain = {0};
        AtomicInteger atomic = new AtomicInteger(0);

        Runnable r1 = () -> { for (int i = 0; i < N; i++) plain[0]++; };
        Runnable r2 = () -> { for (int i = 0; i < N; i++) atomic.incrementAndGet(); };

        Thread a = new Thread(r1); Thread b = new Thread(r1);
        Thread c = new Thread(r2); Thread d = new Thread(r2);

        a.start(); b.start(); c.start(); d.start();
        join(a, b, c, d);
        System.out.println("Plain (racy) = " + plain[0] + " vs Atomic = " + atomic.get());
    }

    // 3) CountDownLatch — wait for N tasks
    static void countDownLatchDemo() {
        CountDownLatch latch = new CountDownLatch(3);
        ExecutorService pool = Executors.newFixedThreadPool(3);
        for (int i = 1; i <= 3; i++) {
            int id = i;
            pool.submit(() -> {
                try { Thread.sleep(200L * id); } catch (InterruptedException ignored) {}
                System.out.println("Worker " + id + " done");
                latch.countDown();
            });
        }
        try { latch.await(); } catch (InterruptedException ignored) {}
        System.out.println("All workers done!");
        pool.shutdown();
    }

    // 4) CyclicBarrier — all meet, then proceed
    static void cyclicBarrierDemo() {
        CyclicBarrier barrier = new CyclicBarrier(3, () -> System.out.println("All reached barrier — go!"));
        Runnable r = () -> {
            try {
                Thread.sleep((long) (Math.random()*300));
                System.out.println(Thread.currentThread().getName() + " waiting");
                barrier.await();
                System.out.println(Thread.currentThread().getName() + " proceeding");
            } catch (InterruptedException | BrokenBarrierException ignored) {}
        };
        new Thread(r, "P1").start();
        new Thread(r, "P2").start();
        new Thread(r, "P3").start();
        sleep(800);
    }

    // 5) Semaphore — limit permits
    static void semaphoreDemo() {
        Semaphore sem = new Semaphore(2); // only 2 at a time
        Runnable r = () -> {
            try {
                sem.acquire();
                System.out.println(Thread.currentThread().getName() + " acquired");
                Thread.sleep(300);
            } catch (InterruptedException ignored) {} finally {
                sem.release();
                System.out.println(Thread.currentThread().getName() + " released");
            }
        };
        for (int i = 0; i < 5; i++) new Thread(r, "S" + i).start();
        sleep(1500);
    }

    // 6) ThreadLocal — per-thread data
    static void threadLocalDemo() {
        ThreadLocal<String> ctx = ThreadLocal.withInitial(() -> "INIT");
        Runnable r = () -> {
            ctx.set("User-" + Thread.currentThread().getName());
            System.out.println(Thread.currentThread().getName() + " sees " + ctx.get());
        };
        new Thread(r, "TL-1").start();
        new Thread(r, "TL-2").start();
        sleep(300);
    }

    // 7) Producer-Consumer with BlockingQueue
    static void producerConsumerDemo() {
        BlockingQueue<Integer> q = new ArrayBlockingQueue<>(3);
        Runnable producer = () -> {
            for (int i = 1; i <= 5; i++) {
                try {
                    q.put(i);
                    System.out.println("Produced " + i);
                    Thread.sleep(100);
                } catch (InterruptedException ignored) {}
            }
        };
        Runnable consumer = () -> {
            for (int i = 1; i <= 5; i++) {
                try {
                    Integer x = q.take();
                    System.out.println("Consumed " + x);
                    Thread.sleep(150);
                } catch (InterruptedException ignored) {}
            }
        };
        new Thread(producer).start();
        new Thread(consumer).start();
        sleep(1200);
    }

    // 8) CompletableFuture Basics
    static void completableFutureBasics() {
        CompletableFuture<Integer> cf =
                CompletableFuture.supplyAsync(() -> {
                            sleep(200);
                            return 10;
                        }).thenApply(x -> x * 3)
                        .thenApply(x -> x + 1);

        System.out.println("Result = " + cf.join()); // non-checked wait
    }

    private static void join(Thread... ts) {
        for (Thread t : ts) try { t.join(); } catch (InterruptedException ignored) {}
    }
    private static void sleep(long ms) { try { Thread.sleep(ms); } catch (InterruptedException ignored) {} }
}
