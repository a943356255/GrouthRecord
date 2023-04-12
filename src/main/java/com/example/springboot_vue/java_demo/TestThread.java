package com.example.springboot_vue.java_demo;

import java.util.ArrayList;

public class TestThread implements Runnable{

    public static ArrayList<String> list = new ArrayList<>();

    public static void main(String[] args) {
        int size = 10;
        for (int i = 0; i < size; i++) {
            Thread thread = new Thread(String.valueOf(i));
            thread.start();
        }

        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
    }

    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();
        System.out.println(threadName);

        synchronized (threadName) {
            System.out.println(threadName);
            if (!list.contains(threadName)) {
                list.add(threadName);
            }
        }
    }
}
