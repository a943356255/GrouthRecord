package com.example.springboot_vue.leetcode_design;

import org.springframework.security.core.parameters.P;

import java.util.ArrayList;

public class MyCalendarTwo {

    ArrayList<int[]> list;
    ArrayList<int[]> over;

    public MyCalendarTwo() {
        list = new ArrayList<>();
        over = new ArrayList<>();
    }

    public boolean book(int start, int end) {
        int res = 0;
        for (int[] ints : list) {
            if (!(end <= ints[0] || start >= ints[1])) {
                over.add(new int[]{Math.max(start, ints[0]), Math.min(end, ints[1])});
            }
        }

        for (int i = 0; i < over.size(); i++) {
            if (start > over.get(i)[0] && end < over.get(i)[1]) {
                res++;
            }
            if (res == 2) {
                return false;
            }
        }

        int[] arr = new int[2];
        arr[0] = start;
        arr[1] = end;
        list.add(arr);

        return true;
    }

}
