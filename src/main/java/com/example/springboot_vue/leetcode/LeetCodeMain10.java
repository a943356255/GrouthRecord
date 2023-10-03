package com.example.springboot_vue.leetcode;

import java.util.Arrays;

public class LeetCodeMain10 {

    public static void main(String[] args) {

    }

    // 123. 买卖股票的最佳时机 III
    public int maxProfit3(int[] prices) {
        return 0;
    }

    // 394. 字符串解码
    public String decodeString(String s) {
        return null;
    }

    // 122. 买卖股票的最佳时机 II,贪心写法
    public int maxProfit2(int[] prices) {
        int buy = prices[0], max = 0;

        for (int i = 1; i < prices.length; i++) {
            // 之前买入的价格大于当天的价格
            if (buy <= prices[i]) {
                max += prices[i] - buy;
            }
            buy = prices[i];
        }

        return max;
    }

    // 122. 买卖股票的最佳时机 II,Dp写法
    public int maxProfit2ByDp(int[] prices) {
        int len = prices.length;
        if (len < 2) {
            return 0;
        }

        // 0：持有现金
        // 1：持有股票
        // 状态转移：0 → 1 → 0 → 1 → 0 → 1 → 0
        int[][] dp = new int[len][2];
        dp[0][0] = 0;
        dp[0][1] = -prices[0];

        for (int i = 1; i < len; i++) {
            // 这两行调换顺序也是可以的
            // 0代表持有现金，那么当前时刻持有的现金就等于上一时刻持有现金和在当前时刻卖出上一时刻持有股票价格的较大值
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
            // 1代表持有股票，取上一时刻的较大值或者拿上一时刻持有的现金买入当前价格的股票
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] - prices[i]);
        }

        return dp[len - 1][0];
    }

    // 121. 买卖股票的最佳时机
    public int maxProfit(int[] prices) {
        int max = 0, count = prices[0];
        // 只能买卖一次，那么就每个价格都尝试买入和卖出，看看最多是多少
        for (int i = 1; i < prices.length; i++) {
            // 买入
            count = Math.min(count, prices[i]);
            // 卖出
            max = Math.max(max, prices[i] - count);
        }

        return max;
    }

    // 2136. 全部开花的最早一天
    public int earliestFullBloom(int[] plantTime, int[] growTime) {
        int[][] arr = new int[plantTime.length][2];
        for (int i = 0; i < plantTime.length; i++) {
            arr[i][0] = plantTime[i];
            arr[i][1] = growTime[i];
        }
        Arrays.sort(arr, (a, b) -> (b[1] - a[1]));

        int[] res = new int[plantTime.length];
        int max = -1, plant = 0;
        for (int i = 0; i < arr.length; i++) {
            res[i] = plant + arr[i][0] + arr[i][1];
            plant = plant + arr[i][0];
            max = Math.max(res[i], max);
        }

        return max;
    }

    // 605. 种花问题
    public boolean canPlaceFlowers(int[] flowerbed, int n) {

        if (flowerbed.length == 1) {
            if (flowerbed[0] == 0) {
                return true;
            } else {
                return flowerbed[0] != n;
            }
        }

        int count = 0;
        if (flowerbed.length > 1) {
            if (flowerbed[0] == 0 && flowerbed[1] == 0) {
                count = 1;
                flowerbed[0] = 1;
            }
        }
        for (int i = 1; i < flowerbed.length; i++) {
            if (flowerbed[i] == 1) {
                continue;
            }
            if (i != flowerbed.length - 1) {
                if (flowerbed[i + 1] == 0 && flowerbed[i - 1] == 0) {
                    flowerbed[i] = 1;
                    count++;
                }
            } else {
                if (flowerbed[i - 1] == 0) {
                    flowerbed[i] = 1;
                    count++;
                }
            }
        }

        return count >= n;
    }

}
