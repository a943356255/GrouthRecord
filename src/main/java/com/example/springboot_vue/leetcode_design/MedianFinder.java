package com.example.springboot_vue.leetcode_design;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MedianFinder {
    List<Integer> list = new ArrayList<>();

    public MedianFinder() {

    }

    public void addNum(int num) {
        list.add(num);
    }

    public double findMedian() {
        int size = list.size();
        list.sort(Comparator.comparingInt(a -> a));

        if (size % 2 == 0) {
            return (double) (list.get(size / 2) + list.get(size / 2 - 1)) / 2.0;
        } else {
            return (double) list.get(size / 2);
        }
    }
}
