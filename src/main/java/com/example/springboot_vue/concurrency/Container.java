package com.example.springboot_vue.concurrency;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class Container {

    public static void main(String[] args) {

    }

    //
    public void testConcurrentHashMap() {
        ConcurrentHashMap<String,String> map = new ConcurrentHashMap<>();
    }

    // 写时复制
    public void testCopyOnWriteArrayList() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
    }
}
