package com.example.springboot_vue.leetcode_design;

import java.util.HashMap;
import java.util.PriorityQueue;

// 2034. 股票价格波动
public class StockPrice {

//    Map<Integer, Integer> price;
//    PriorityQueue<Integer> maxQueue;
//    PriorityQueue<Integer> minQueue;
//
//    int maxTime = Integer.MIN_VALUE;
//
//    public StockPrice() {
//        price = new HashMap<>();
//        maxQueue = new PriorityQueue<>((a, b) -> b - a);
//        minQueue = new PriorityQueue<>();
//    }
//
//    public void update(int timestamp, int price) {
//        // 说明之前存在价格
//        if (this.price.get(timestamp) != null) {
//            if (this.price.get(timestamp) == maxQueue.peek()) {
//                maxQueue.poll();
//            }
//
//            if (this.price.get(timestamp) == minQueue.peek()) {
//                minQueue.poll();
//            }
//        }
//        minQueue.offer(price);
//        maxQueue.offer(price);
//        this.price.put(timestamp, price);
//        if (timestamp > maxTime) {
//            maxTime = timestamp;
//        }
//
//    }
//
//    public int current() {
//        return price.get(maxTime);
//    }
//
//    public int maximum() {
//        return maxQueue.peek();
//    }
//
//    public int minimum() {
//        return minQueue.peek();
//    }

    int maxTimestamp;
    HashMap<Integer, Integer> timePriceMap;
    PriorityQueue<int[]> pqMax;
    PriorityQueue<int[]> pqMin;

    public StockPrice() {
        maxTimestamp = 0;
        timePriceMap = new HashMap<Integer, Integer>();
        pqMax = new PriorityQueue<int[]>((a, b) -> b[0] - a[0]);
        pqMin = new PriorityQueue<int[]>((a, b) -> a[0] - b[0]);
    }

    public void update(int timestamp, int price) {
        maxTimestamp = Math.max(maxTimestamp, timestamp);
        timePriceMap.put(timestamp, price);
        pqMax.offer(new int[]{price, timestamp});
        pqMin.offer(new int[]{price, timestamp});
    }

    public int current() {
        return timePriceMap.get(maxTimestamp);
    }

    public int maximum() {
        // 这里是从队列中取出队头元素，然后判断它的价格是否被更改过,如果没有更改就返回
        while (true) {
            int[] priceTime = pqMax.peek();
            int price = priceTime[0], timestamp = priceTime[1];
            if (timePriceMap.get(timestamp) == price) {
                return price;
            }
            pqMax.poll();
        }
    }

    public int minimum() {
        while (true) {
            int[] priceTime = pqMin.peek();
            int price = priceTime[0], timestamp = priceTime[1];
            if (timePriceMap.get(timestamp) == price) {
                return price;
            }
            pqMin.poll();
        }
    }

}
