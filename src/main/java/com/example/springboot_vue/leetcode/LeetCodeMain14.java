package com.example.springboot_vue.leetcode;

import java.util.*;

public class LeetCodeMain14 {

    // 746. 使用最小花费爬楼梯
    public int minCostClimbingStairs(int[] cost) {
        int[] dp = new int[cost.length + 1];
        dp[0] = cost[0];
        dp[1] = cost[1];
        for (int i = 2; i < cost.length; i++) {
            dp[i] = Math.min(dp[i - 1], dp[i - 2]) + cost[i];
        }
        dp[cost.length] = Math.min(dp[cost.length - 1], dp[cost.length - 2]);
        return dp[cost.length];
    }

    // 2415. 反转二叉树的奇数层
    public TreeNode reverseOddLevels(TreeNode root) {
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        boolean isOdd = false;
        while (!queue.isEmpty()) {
            int sz = queue.size();
            List<TreeNode> arr = new ArrayList<>();
            for (int i = 0; i < sz; i++) {
                TreeNode node = queue.poll();
                if (isOdd) {
                    arr.add(node);
                }
                if (node.left != null) {
                    queue.offer(node.left);
                    queue.offer(node.right);
                }
            }
            if (isOdd) {
                for (int l = 0, r = sz - 1; l < r; l++, r--) {
                    int temp = arr.get(l).val;
                    arr.get(l).val = arr.get(r).val;
                    arr.get(r).val = temp;
                }
            }
            isOdd ^= true;
        }
        return root;

//        if (root == null) {
//            return null;
//        }
//        Queue<TreeNode> queue = new ArrayDeque<>();
//        queue.add(root);
//        int height = 0;
//        while (!queue.isEmpty()) {
//            int size = queue.size();
//            List<TreeNode> list = new ArrayList<>();
//            for (int i = 0; i < size; i++) {
//                TreeNode node = queue.poll();
//                if (height % 2 != 0) {
//                    list.add(node);
//                }
//                list.add(node);
//                if (node.left != null) {
//                    queue.add(node.left);
//                }
//                if (node.right != null) {
//                    queue.add(node.right);
//                }
//            }
//            if (height % 2 != 0) {
//                int temp = size - 1;
//                int tempSize = (int) Math.ceil(size / 2);
//                for (int i = 0; i < tempSize; i++) {
//                    int tempVal = list.get(i).val;
//                    list.get(i).val = list.get(temp).val;
//                    list.get(temp).val = tempVal;
//                    temp--;
//                }
//            }
//
//            height++;
//        }
//
//        return root;
    }

    // 2415. 反转二叉树的奇数层,深度优先遍历的解
    public void dfs(TreeNode left, TreeNode right, boolean isOdd) {
        // 完美二叉树，左子树可能为空，left为空，right一定为空
        if (left == null) {
            return;
        }
        if (isOdd) {
            int tmp = left.val;
            left.val = right.val;
            right.val = tmp;
        }
        dfs(left.left, right.right, !isOdd);
        dfs(left.right, right.left, !isOdd);
    }

    // 2132. 用邮票贴满网格图
    public boolean possibleToStamp(int[][] grid, int stampHeight, int stampWidth) {
        int m = grid.length, n = grid[0].length;
        int[][] sum = new int[m + 2][n + 2];
        int[][] diff = new int[m + 2][n + 2];
        // 这里计算了一个前缀和数组
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                sum[i][j] = sum[i - 1][j] + sum[i][j - 1] - sum[i - 1][j - 1] + grid[i - 1][j - 1];
            }
        }

        // 这里计算差分数组
        for (int i = 1; i + stampHeight - 1 <= m; i++) {
            for (int j = 1; j + stampWidth - 1 <= n; j++) {
                int x = i + stampHeight - 1;
                int y = j + stampWidth - 1;
                if (sum[x][y] - sum[x][j - 1] - sum[i - 1][y] + sum[i - 1][j - 1] == 0) {
                    diff[i][j]++;
                    diff[i][y + 1]--;
                    diff[x + 1][j]--;
                    diff[x + 1][y + 1]++;
                }
            }
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                diff[i][j] += diff[i - 1][j] + diff[i][j - 1] - diff[i - 1][j - 1];
                if (diff[i][j] == 0 && grid[i - 1][j - 1] == 0) {
                    return false;
                }
            }
        }
        return true;

    }

    // 构建差分数组以及通过差分数复原数组
    public void diff(int[] arr) {
        // 构建差分数组
        int[] diff = new int[arr.length];
        diff[0] = arr[0];
        for (int i = 1; i < arr.length; i++) {
            diff[i] = arr[i] - arr[i - 1];
        }

        // 通过diff构建原数组
        int[] res = new int[arr.length];
        res[0] = diff[0];
        // 这里，res中存储的是原数组的值，diff[i] = res[i] - res[i - 1]
        // 那么res[i] = res[i - 1] + diff[i] = res[i -1] + res[i] - res[i - 1]
        for (int i = 1; i < res.length; i++) {
            res[i] = res[i - 1] + diff[i];
        }

        System.out.println(Arrays.toString(res));
    }

}
