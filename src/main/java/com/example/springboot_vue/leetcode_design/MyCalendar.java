package com.example.springboot_vue.leetcode_design;

import lombok.val;

import java.util.Map;
import java.util.TreeMap;

// 729. 我的日程安排表 I
public class MyCalendar {

    TreeMap<Integer, Integer> treeMap;

    public MyCalendar() {
        treeMap = new TreeMap<>();
    }

    public boolean book(int start, int end) {
        // 前两种情况是最左端或者最右端
        Map.Entry<Integer, Integer> lastEntry = treeMap.lastEntry();
        if (lastEntry == null || lastEntry.getValue() <= start) {
            treeMap.put(start, end);
            return true;
        }

        Map.Entry<Integer, Integer> firstEntry = treeMap.firstEntry();
        if (firstEntry == null || firstEntry.getKey() >= end) {
            treeMap.put(start, end);
            return true;
        }

        if (treeMap.get(start) != null) {
            return false;
        }

        // 这里开始处理处于中间的情况
        Map.Entry<Integer, Integer> lowerEntry = treeMap.lowerEntry(start);
        Map.Entry<Integer, Integer> higherEntry = treeMap.higherEntry(start);
        // 说明处于边界，且交叉
        if (higherEntry == null || lowerEntry == null) {
            return false;
        }

        if (start >= lastEntry.getValue() && end <= higherEntry.getKey()) {
            treeMap.put(start, end);
            return true;
        } else {
            return false;
        }
    }

}
