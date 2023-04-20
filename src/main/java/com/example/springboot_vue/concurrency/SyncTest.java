package com.example.springboot_vue.concurrency;

public class SyncTest implements Runnable {

    int count = 0;

    @Override
    public synchronized void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getName() + ":" + count++);
        }
    }

    public static void main(String[] args) {
        SyncTest syncTest = new SyncTest();
        SyncTest syncTest1 = new SyncTest();

        Thread thread = new Thread(syncTest, "thread1");
        Thread thread2 = new Thread(syncTest1, "thread2");

        thread.start();
        thread2.start();
    }
}
