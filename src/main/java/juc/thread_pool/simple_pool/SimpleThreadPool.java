package juc.thread_pool.simple_pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimpleThreadPool {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(5);

        for (int i = 0; i < 10; i++) {
            // 这里是创建工作线程，然后去执行任务
            Runnable worker = new WorkerThread("这是第" + i + "个任务");
            executor.execute(worker);
        }

        // This will make the executor accept no new threads and finish all existing threads in the queue
        executor.shutdown();
        while (!executor.isTerminated()) {
            // Wait until all threads are finish,and also you can use "executor.awaitTermination();" to wait
        }

        System.out.println("Finished all threads");
    }

}
