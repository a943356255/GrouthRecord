package com.example.springboot_vue.leetcode_design;

// 307. 区域和检索 - 数组可修改
public class NumArray {

    int[] partNum;
    int[] addNum;
    public NumArray(int[] nums) {
//        addNum = new int[nums.length];
        partNum = nums;

//        addNum[0] = nums[0];
//        for (int i = 1; i < nums.length; i++) {
//            addNum[i] = addNum[i - 1] + nums[i];
//        }
    }

    public void update(int index, int val) {
//        int temp = val - partNum[index];
//        for (int i = index; i < addNum.length; i++) {
//            addNum[i] += temp;
//        }
        partNum[index] = val;
    }

    public int sumRange(int left, int right) {
//        if (left == 0) {
//            return addNum[right];
//        }
//        return addNum[right] - addNum[left - 1];
        int sum = 0;
        for (int i = left; i <= right; i++) {
            sum += partNum[i];
        }

        return sum;
    }

}
