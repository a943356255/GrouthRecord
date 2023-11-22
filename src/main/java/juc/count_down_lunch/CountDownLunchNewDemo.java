package juc.count_down_lunch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountDownLunchNewDemo {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        int threadNumber = 13;
        final CountDownLatch countDownLatch = new CountDownLatch(threadNumber);
        for (int i = 0; i < threadNumber; i++) {
            final int threadID = i;
            executor.execute(
                    () -> {
                        try {
                            Thread.sleep(1000);
                            System.out.println(String.format("threadID:[%s] finished!!", Thread.currentThread().getId()) + " i = " + threadID);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } finally {
                            countDownLatch.countDown();  //这个不管是否异常都需要数量减,否则会被堵塞无法结束
                        }
                    }
            );
        }

        // 保证之前的所有的线程都执行完成，才会走下面的
        countDownLatch.await();

        System.out.println(countDownLatch.getCount());
        System.out.println("main thread finished!!");
    }

}
