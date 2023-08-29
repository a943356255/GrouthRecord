package com.example.springboot_vue.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LeetCodeMain9 {

    // 823. 带因子的二叉树
    public int numFactoredBinaryTrees(int[] arr) {
        Arrays.sort(arr);
        int n = arr.length;
        long[] dp = new long[n];
        long res = 0, mod = 1000000007;
        for (int i = 0; i < n; i++) {
            dp[i] = 1;
            // 内层循环每次都是从0到i - 1
            for (int left = 0, right = i - 1; left <= right; left++) {
                // 这里是找到第一个满足arr[left] * arr[right]小于当前arr[i]的元素
                while (right >= left && (long) arr[left] * arr[right] > arr[i]) {
                    right--;
                }
                if (right >= left && (long) arr[left] * arr[right] == arr[i]) {
                    // 这里，是以di[i]为根节点的二叉树的个数
                    // 如果左子树不等于右子树，那么两者交换就多了一种情况，如果相等则无法交换，个数就是左子树的种类*右子树种类
                    if (right != left) {
                        dp[i] = (dp[i] + dp[left] * dp[right] * 2) % mod;
                    } else {
                        dp[i] = (dp[i] + dp[left] * dp[right]) % mod;
                    }
                }
            }
            res = (res + dp[i]) % mod;
        }

        return (int) res;
    }

    // 228. 汇总区间
    public List<String> summaryRanges(int[] nums) {
        List<String> res = new ArrayList<>();
        if (nums.length == 0) {
            return res;
        }

        StringBuilder str = new StringBuilder(String.valueOf(nums[0]));
        int length = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == nums[i - 1] + 1) {
                length++;
            } else {
                if (length > 1) {
                    str.append("->").append(nums[i - 1]);
                }
                res.add(str.toString());
                length = 1;
                str = new StringBuilder(nums[i]);
            }
        }
        if (length > 1) {
            res.add(str.append("->").append(nums[nums.length - 1]).toString());
        } else {
            res.add(str.toString());
        }

        return res;
    }

}
