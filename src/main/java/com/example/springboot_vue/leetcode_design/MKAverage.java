package com.example.springboot_vue.leetcode_design;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class MKAverage {

    int m, k, count = 0;
    PriorityQueue<Integer> small;
    PriorityQueue<Integer> big;
    ArrayList<Integer> list;

    public MKAverage(int m, int k) {
        this.m = m;
        this.k = k;
        list = new ArrayList<>();
        big = new PriorityQueue<>();
        small = new PriorityQueue<>();
    }

    public void addElement(int num) {
        if (list.size() >= m) {

        }
    }

    public int calculateMKAverage() {
        return 1;
    }

}
