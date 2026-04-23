package com.saha.amit.concurrency;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadCreation {

    static class MyThread extends Thread {

        public MyThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            System.out.println("Working in new Thread from Thread class:" + Thread.currentThread().getName());
        }
    }

    static class MyRunnable implements Runnable {
        @Override
        public void run() {
            System.out.println("Working in new Thread from Runnable interface:" + Thread.currentThread().getName());
        }
    }

    public static void main(String[] args) {
        System.out.println("Starting main :" + Thread.currentThread().getName());
        MyThread thread = new MyThread("From_Thread_Class");
        thread.start();

        MyRunnable myRunnable = new MyRunnable();
        Thread th = new Thread(myRunnable, "From_Runnable_interface");
        th.start();

        Thread thread1 = new Thread(() -> {
            System.out.println("Working in new Thread from lambda:" + Thread.currentThread().getName());
        }, "Thread_Lambda");
        thread1.start();

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(() -> {
            System.out.println("Working in new Thread from ExecutorService" + Thread.currentThread().getName());
        });
        executorService.submit(() -> {
            System.out.println("Working in new Thread from ExecutorService" + Thread.currentThread().getName());
        });
        executorService.shutdown();

        ExecutorService ex = Executors.newSingleThreadExecutor();
        Future<Integer> f = ex.submit(() -> 42);
        try {
            System.out.println(f.get() +Thread.currentThread().getName());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        ex.shutdown();
    }
}
