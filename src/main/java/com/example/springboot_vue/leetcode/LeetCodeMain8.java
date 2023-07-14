package com.example.springboot_vue.leetcode;

import java.util.*;

public class LeetCodeMain8 {

    public static void main(String[] args) {

    }

    int move = 0;

    // 979. 在二叉树中分配硬币
    public int distributeCoins(TreeNode root) {
        dfs(root);
        return move;
    }

    public int dfs(TreeNode root) {
        int moveleft = 0;
        int moveright = 0;
        if (root == null) {
            return 0;
        }

        if (root.left != null) {
            moveleft = dfs(root.left);
        }
        if (root.right != null) {
            moveright = dfs(root.right);
        }

        move += Math.abs(moveleft) + Math.abs(moveright);
        return moveleft + moveright + root.val - 1;
    }

    // 931. 下降路径最小和
    public int minFallingPathSum(int[][] matrix) {
        int[][] arr = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (i == 0) {
                    arr[i][j] = matrix[i][j];
                } else {
                    arr[i][j] = arr[i - 1][j] + matrix[i][j];
                    for (int temp = j - 1; temp <= j + 1; temp++) {
                        if (temp >= 0 && temp < matrix[0].length) {
                            arr[i][j] = Math.min(arr[i - 1][temp] + matrix[i][j], arr[i][j]);
                        }
                    }
                }
            }
        }

//        for (int i = 0; i < arr.length; i++) {
//            for (int j = 0; j < arr[0].length; j++) {
//                System.out.print(arr[i][j] + " ");
//            }
//            System.out.println();
//        }

        int min = 100 * 100 + 1;
        for (int i = 0; i < arr[0].length; i++) {
            min = Math.min(arr[arr.length - 1][i], min);
        }

        return min;
    }

    // 2544. 交替数字和
    public int alternateDigitSum(int n) {
        int res = 0;
        String str = String.valueOf(n);

        int temp = 1;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            res += temp * Integer.parseInt(String.valueOf(c));
            temp *= -1;
        }

        return res;
    }

    // 1911. 最大子序列交替和
    public long maxAlternatingSum(int[] nums) {
//        long ou = 0, ji = 0;
//        long[] dp = new long[nums.length];
//        dp[0] = nums[0];
//        for (int i = 1; i < nums.length; i++) {
//            if (i % 2 == 0) {
//                long temp = dp[i - 1] + ou - ji + nums[i];
//                if (temp > dp[i - 1]) {
//                    dp[i] = temp;
//                } else {
//                    dp[i] = dp[i - 1];
//                    ou += nums[i];
//                }
//            } else {
//                dp[i] = dp[i - 1];
//                ji += nums[i];
//            }
//        }
//        return dp[nums.length - 1];
        long even = nums[0], odd = 0;
        for (int i = 1; i < nums.length; i++) {
            even = Math.max(even, odd + nums[i]);
            odd = Math.max(odd, even - nums[i]);
        }
        return even;
    }

    List<List<Integer>> edges;
    int[] visited;
    boolean valid = true;

    // 207. 课程表
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        edges = new ArrayList<>();
        for (int i = 0; i < numCourses; ++i) {
            edges.add(new ArrayList<>());
        }

        // 遍历数组，将每个节点的前置节点加入对应下标的集合中，比如课程2的前置课程是3，4，那么下标2的集合中就存3，4
        for (int[] prerequisite : prerequisites) {
            edges.get(prerequisite[1]).add(prerequisite[0]);
        }

        visited = new int[numCourses];
        for (int i = 0; i < numCourses && valid; i++) {
            if (visited[i] == 0) {
                dfs(i);
            }
        }

        return valid;
    }

    public void dfs(int u) {
        visited[u] = 1;
        // 遍历他的每一门前置课程是否已修
        for (int i = 0; i < edges.get(u).size(); i++) {
            // 前置课程没修，则先修前置课程，递归遍历前置课程
            if (visited[edges.get(u).get(i)] == 0) {
                dfs(edges.get(u).get(i));
                if (!valid) {
                    return;
                }
            } else if (visited[edges.get(u).get(i)] == 1) {
                // 这里，如果前置课程的是否访问为1，说明出现了环，已经不可能了
                valid = false;
                return;
            }

            // 这里如果集合中的元素是2的话是不进行处理的
        }

        visited[u] = 2;
    }
}
