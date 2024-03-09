package com.example.springboot_vue.leetcode;

public class LeetCodeMain17 {

    public static void main(String[] args) {

    }

    // BM63 跳台阶
    public int jumpFloor (int number) {
        // write code here
        if (number < 3) {
            return number;
        }

        int[] dp = new int[number + 1];
        dp[1] = 1;
        dp[2] = 2;

        for (int i = 3; i <= number; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[number];
    }
}
