package com.saha.amit.concurrency.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CoreThreadsPractice {

    private static int raceCounter = 0;
    private static int syncCounter = 0;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== Core Threads & Concurrency (Java 21) =====");
            System.out.println("1. Create & Start Threads (Runnable/Lambda)");
            System.out.println("2. Sleep, Join & Thread States");
            System.out.println("3. Race Condition Demo (Incorrect)");
            System.out.println("4. Fix Race Condition with synchronized");
            System.out.println("5. Daemon Thread Demo");
            System.out.println("0. Exit");
            System.out.print("Choose: ");

            int choice = readInt(sc);
            switch (choice) {
                case 1 -> createAndStartThreads();
                case 2 -> sleepJoinStates();
                case 3 -> raceCondition();
                case 4 -> raceConditionFixed();
                case 5 -> daemonThreadDemo();
                case 0 -> { System.out.println("Bye!"); return; }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static int readInt(Scanner sc) {
        while (!sc.hasNextInt()) { sc.next(); System.out.print("Enter a number: "); }
        return sc.nextInt();
    }

    // 1) Basic threads via lambda Runnable
    static void createAndStartThreads() {
        Runnable task = () -> System.out.println(Thread.currentThread().getName() + " says hello");
        Thread t1 = new Thread(task, "T-1");
        Thread t2 = new Thread(task, "T-2");
        t1.start(); t2.start();
        joinQuietly(t1, t2);
    }

    // 2) Sleep, join, and observe states
    static void sleepJoinStates() {
        Thread t = new Thread(() -> {
            try {
                System.out.println("State in run(): " + Thread.currentThread().getState());
                Thread.sleep(300);
                System.out.println("After sleep");
            } catch (InterruptedException ignored) {}
        }, "Sleeper");
        System.out.println("Before start: " + t.getState());
        t.start();
        System.out.println("After start: " + t.getState());
        joinQuietly(t);
        System.out.println("After join: " + t.getState());
    }

    // 3) Race condition: shared variable without sync
    static void raceCondition() {
        raceCounter = 0;
        List<Thread> threads = new ArrayList<>();
        Runnable r = () -> {
            for (int i = 0; i < 100_000; i++) raceCounter++; // data race
        };
        for (int i = 0; i < 4; i++) threads.add(new Thread(r, "Inc-" + i));
        threads.forEach(Thread::start);
        threads.forEach(CoreThreadsPractice::joinQuietly);
        System.out.println("Expected 400000, got " + raceCounter + " (race condition!)");
    }

    // 4) Fixed using synchronized block (coarse-grained)
    static void raceConditionFixed() {
        syncCounter = 0;
        Object lock = new Object();
        List<Thread> threads = new ArrayList<>();
        Runnable r = () -> {
            for (int i = 0; i < 100_000; i++) {
                synchronized (lock) {
                    syncCounter++;
                }
            }
        };
        for (int i = 0; i < 4; i++) threads.add(new Thread(r, "SyncInc-" + i));
        threads.forEach(Thread::start);
        threads.forEach(CoreThreadsPractice::joinQuietly);
        System.out.println("Synchronized result = " + syncCounter);
    }

    // 5) Daemon thread — ends when only daemons remain
    static void daemonThreadDemo() {
        Thread daemon = new Thread(() -> {
            try {
                while (true) {
                    System.out.println("[daemon] heartbeat");
                    Thread.sleep(200);
                }
            } catch (InterruptedException ignored) {}
        }, "Daemon-Beat");
        daemon.setDaemon(true);
        daemon.start();

        Thread worker = new Thread(() -> {
            try { Thread.sleep(600); } catch (InterruptedException ignored) {}
            System.out.println("Worker done. JVM will exit; daemon stops automatically.");
        }, "Worker");
        worker.start();
        joinQuietly(worker);
    }

    private static void joinQuietly(Thread... ts) {
        for (Thread t : ts) {
            try { t.join(); } catch (InterruptedException ignored) {}
        }
    }
}
