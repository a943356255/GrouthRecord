package com.example.springboot_vue.concurrency;

public class NoVisibility {

    private static boolean ready;

    private static int number;

    private static class ReaderThread extends Thread {
        public void run() {
            while (!ready) {
                // 暂停当前正在执行的线程，放弃CPU资源，并执行其他线程。
                Thread.yield();
            }
            System.out.println(number);
        }
    }

    public static void main(String[] args) {
        new ReaderThread().start();
        number = 42;
        ready = true;
    }
}
