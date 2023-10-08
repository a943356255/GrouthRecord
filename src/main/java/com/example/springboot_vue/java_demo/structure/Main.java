package com.example.springboot_vue.java_demo.structure;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        new Main().testTreeMap();
    }

    /**
     * treeMap默认根据key有序，特殊写法可以根据value有序
     */
    public void testTreeMap() {
        TreeMap<Integer, Integer> treeMap = new TreeMap<>();

        for (int i = 0; i < 10; i++) {
            treeMap.put(i, 10 - i);
        }

        for (Map.Entry<Integer, Integer> entry : treeMap.entrySet()) {
            System.out.println("key = " + entry.getKey() + " value = " + entry.getValue());
        }

        treeMap.forEach((key, value) -> {
            System.out.println("key = " + key);
            System.out.println("value = " + value);
        });
    }

}
