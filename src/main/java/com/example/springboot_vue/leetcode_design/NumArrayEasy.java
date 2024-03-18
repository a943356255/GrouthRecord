package com.example.springboot_vue.leetcode_design;

import java.util.Arrays;

// 303. 区域和检索 - 数组不可变
public class NumArrayEasy {

    int[] arr;
    int[] sum;
    public NumArrayEasy(int[] nums) {
        arr = nums;
        sum = new int[nums.length + 1];
        sum[0] = 0;
        for (int i = 1; i <= nums.length; i++) {
            sum[i] = sum[i - 1] + nums[i];
        }
    }

    public int sumRange(int left, int right) {
        return sum[right] - sum[left - 1];
    }

}
