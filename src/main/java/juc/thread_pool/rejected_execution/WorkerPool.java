package juc.thread_pool.rejected_execution;

import juc.thread_pool.simple_pool.WorkerThread;

import java.util.concurrent.*;

public class WorkerPool {

    public static void main(String[] args) {
        // RejectedExecutionHandler implementation
        RejectedExecutionHandlerImpl rejectionHandler = new RejectedExecutionHandlerImpl();

        // Get the ThreadFactory implementation to use
        ThreadFactory threadFactory = Executors.defaultThreadFactory();

        // creating the ThreadPoolExecutor
        // 这里在创建线程池时，指定了一个阻塞队列，队列指定了大小，而且传入了自定义的拒绝类
        ThreadPoolExecutor executorPool = new ThreadPoolExecutor(2, 4, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<>(2), threadFactory, rejectionHandler);

        // start the monitoring thread
        // 创建了监视的线程
        MyMonitorThread monitor = new MyMonitorThread(executorPool, 3);
        Thread monitorThread = new Thread(monitor);
        monitorThread.start();

        // submit work to the thread pool
        for (int i = 0; i < 10; i++) {
            // 这里是利用一个线程池，提交一个任务，也就是一个线程
            executorPool.execute(new WorkerThread("cmd" + i));
        }

        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // shut down the pool
        executorPool.shutdown();
        // shut down the monitor thread
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        monitor.shutdown();
    }

}
