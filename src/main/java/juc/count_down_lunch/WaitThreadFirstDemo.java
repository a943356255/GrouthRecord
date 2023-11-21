package juc.count_down_lunch;

import java.util.concurrent.CountDownLatch;

public class WaitThreadFirstDemo {

    public static void main(String[] args) throws InterruptedException {
        int numThreads = 5;
        CountDownLatch latch = new CountDownLatch(numThreads);

        // 创建线程并指定CountDownLatch
        // 创建 5 个线程
        for (int i = 0; i < numThreads; i++) {
            MyThread thread = new MyThread();
            thread.run();
        }

        System.out.println("所有线程执行完成之前");

        // 等待所有线程执行完毕
        latch.await();

        System.out.println("执行完成");

        // 执行后续代码
        System.out.println("All threads have finished executing.");
    }

}

class MyThread implements Runnable {

    private CountDownLatch latch;

    public MyThread() {

    }

    public MyThread(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
            // 线程执行一些操作
            // ...
            System.out.println("Thread " + Thread.currentThread().getId() + " has finished executing.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        finally {
//            // 线程执行完毕后调用 countDown() 方法
//            latch.countDown();
//        }
    }
}