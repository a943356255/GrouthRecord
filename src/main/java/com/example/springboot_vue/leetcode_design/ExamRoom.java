package com.example.springboot_vue.leetcode_design;

public class ExamRoom {

    int[] arr;
    int length;

    public ExamRoom(int n) {
        arr = new int[n];
        length = n;
    }

    public int seat() {
        if (arr[0] == 0) {
            arr[0] = 1;
            return 0;
        }

        if (arr[length - 1] == 0) {
            arr[length - 1] = 1;
            return length - 1;
        }

        int left = 0, right = length - 1;
        while (left < right) {
            int mid = (left + right) / 2;
        }

        return 20;
    }

    public void leave(int p) {

    }

}
