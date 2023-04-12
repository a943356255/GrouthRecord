package com.example.springboot_vue.concurrency;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.FutureTask;
import java.util.concurrent.Semaphore;

// 测试闭锁
public class TestHarness {

    private final FutureTask<String> futureTask = new FutureTask<>(() -> {
        int a = 2;
        int b = 3;
        return "";
    });

    public static void main(String[] args) {

    }

    public long timeTasks(int nThreads, final Runnable task) throws InterruptedException {
        // 这是一种闭锁，用于实现满足某些场景后才开始执行
        final CountDownLatch startGate = new CountDownLatch(1);
        final CountDownLatch endGate = new CountDownLatch(nThreads);

        for (int i = 0; i < nThreads; i++) {
            Thread t = new Thread(() -> {
                try {
                    startGate.await();
                    try {
                        task.run();
                    } finally {
                        endGate.countDown();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

            t.start();
        }

        long start = System.nanoTime();
        startGate.countDown();
        endGate.await();
        long end = System.nanoTime();

        return end - start;
    }

    public void testSemaphore() {
        // 用于实现资源池
        Semaphore semaphore = new Semaphore(20);
    }

    // 栅栏
    public void testCyclicBarrier() {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(20);
    }
}
