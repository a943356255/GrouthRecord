package com.example.springboot_vue.leetcode_design;

import java.util.PriorityQueue;

public class SmallestInfiniteSet {

    PriorityQueue<Integer> priorityQueue;

    public static void main(String[] args) {
        int a = 4, b = 2;
        System.out.println(a ^ b);
    }

    public SmallestInfiniteSet() {
        priorityQueue = new PriorityQueue<>();
        for (int i = 1; i <= 1000; i++) {
            priorityQueue.add(i);

        }
    }

    // 没pop一个，添加到优先级队列里
    public int popSmallest() {
        return priorityQueue.poll();
    }

    public void addBack(int num) {
        // 如果包含num， 说明num已经被移除，现在又添加回来了
        if (priorityQueue.contains(num)) {
            return;
        }
        priorityQueue.add(num);
    }

}
