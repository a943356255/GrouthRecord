package com.example.springboot_vue.leetcode;

import java.util.Arrays;

public class LeetCodeMain14 {

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
