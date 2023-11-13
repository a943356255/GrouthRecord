package com.example.springboot_vue.leetcode_design;

// 307. 区域和检索 - 数组可修改
public class NumArray {

    int[] partNum;
    int[] addNum;
    public NumArray(int[] nums) {
        partNum = nums;
    }

    public void update(int index, int val) {
        partNum[index] = val;
    }

    public int sumRange(int left, int right) {
        int sum = 0;
        for (int i = left; i <= right; i++) {
            sum += partNum[i];
        }

        return sum;
    }

}
