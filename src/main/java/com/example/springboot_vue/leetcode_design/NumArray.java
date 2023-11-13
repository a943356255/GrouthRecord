package com.example.springboot_vue.leetcode_design;

// 307. 区域和检索 - 数组可修改
public class NumArray {

//    int[] partNum;
//    int[] addNum;
//    public NumArray(int[] nums) {
//        partNum = nums;
//    }
//
//    public void update(int index, int val) {
//        partNum[index] = val;
//    }
//
//    public int sumRange(int left, int right) {
//        int sum = 0;
//        for (int i = left; i <= right; i++) {
//            sum += partNum[i];
//        }
//
//        return sum;
//    }

    private final int[] sum; // sum[i] 表示第 i 个块的元素和
    private final int size; // 块的大小
    private final int[] nums;

    public NumArray(int[] nums) {
        this.nums = nums;
        int n = nums.length;
        size = (int) Math.sqrt(n);
        sum = new int[(n + size - 1) / size]; // n / size 向上取整
        for (int i = 0; i < n; i++) {
            sum[i / size] += nums[i];
        }
    }

    public void update(int index, int val) {
        sum[index / size] += val - nums[index];
        nums[index] = val;
    }

    public int sumRange(int left, int right) {
        int b1 = left / size, i1 = left % size, b2 = right / size, i2 = right % size;
        if (b1 == b2) {
            // 区间 [left, right] 在同一块中
            int sum = 0;
            for (int j = i1; j <= i2; j++) {
                sum += nums[b1 * size + j];
            }
            return sum;
        }

        // 下面这种情况,left和right不在同一个区间,那么就分别计算
        // 这里是计算left 到 left所在块的最后一个元素的和
        int sum1 = 0;
        for (int j = i1; j < size; j++) {
            sum1 += nums[b1 * size + j];
        }

        // 这里是从0到right,计算right所在块的和
        int sum2 = 0;
        for (int j = 0; j <= i2; j++) {
            sum2 += nums[b2 * size + j];
        }

        // 这里,是考虑left和right中间跨了多个块,计算这些块的和
        int sum3 = 0;
        for (int j = b1 + 1; j < b2; j++) {
            sum3 += sum[j];
        }

        return sum1 + sum2 + sum3;
    }
}
