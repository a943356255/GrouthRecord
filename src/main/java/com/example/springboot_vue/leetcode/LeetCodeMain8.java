package com.example.springboot_vue.leetcode;

import java.util.*;

public class LeetCodeMain8 {

    public static void main(String[] args) {
        var list = new ArrayList<Integer>();
        list.add(1);
        System.out.println(list.get(0));
    }

    // 1851. 包含每个查询的最小区间
    public int[] minInterval(int[][] intervals, int[] queries) {
        int n = intervals.length, m = queries.length;
        // 按照左端点的大小进行排序
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));
        int[][] qs = new int[m][0];

        for (int i = 0; i < m; ++i) {
            // 存储了每个查询的值以及它对应的下标
            qs[i] = new int[] {queries[i], i};
        }

        // 对查询进行排序，然后通过下标来确定原来的顺序
        Arrays.sort(qs, Comparator.comparingInt(a -> a[0]));

        int[] ans = new int[m];
        Arrays.fill(ans, -1);

        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        int i = 0;

        for (int[] q : qs) {
            // intervals[i][0] < q[0]说明目前查询的数据大于给定区间下标i的值
            // 这里是将所有区间开头小于当前查询的元素全部存储进一个队列
            // 因为如果区间的开头已经大于查询元素，则肯定不在该区间内
            // 比如查询为3，而区间开头元素已经是4,比如[4, 8]，那么3一定不在该区间
            // 而且区间排序是按照区间的最小值排的，后续区间也不可能存在包含3的
            while (i < n && intervals[i][0] <= q[0]) {
                int a = intervals[i][0], b = intervals[i][1];
                // 优先级队列存储一个数组，包含了区间大小以及区间的最大值，队列按照区间大小进行排序，小区间在前
                pq.offer(new int[] {b - a + 1, b});
                ++i;
            }

            // 这里是遍历已经存储于队列的元素，而且区间是按照由小到大排的，只要找到第一个满足它的右区间大于查询元素，则就是答案。
            // 所以这里右区间小于q[0]，即右区间小于当前查询元素的都要出队
            while (!pq.isEmpty() && pq.peek()[1] < q[0]) {
                pq.poll();
            }

            // 如果队列不为空，则将元素添加到对应下标的位置
            // 如果队列为空，因为已经提前全部存储为-1，则不需要处理
            if (!pq.isEmpty()) {
                ans[q[1]] = pq.peek()[0];
            }
        }

        return ans;
    }

    // 415. 字符串相加
    public String addStrings(String num1, String num2) {
        int temp = 0, sum = 0;
        int length1 = num1.length() - 1, length2 = num2.length() - 1;
        StringBuilder res = new StringBuilder();

        while (length1 >= 0 || length2 >= 0) {
            if (length1 >= 0) {
                sum += Integer.parseInt(String.valueOf(num1.charAt(length1)));
                length1--;
            }

            if (length2 >= 0) {
                sum += Integer.parseInt(String.valueOf(num2.charAt(length2)));
                length2--;
            }
            sum += temp;
            temp = sum / 10;
            res.insert(0, sum % 10);
            sum = 0;
        }

        if (temp == 1) {
            res.insert(0, 1);
        }

        return res.toString();
    }

    // 834. 树中距离之和
    public int[] sumOfDistancesInTree(int n, int[][] edges) {
        List<List<Integer>> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            list.add(new ArrayList<>());
        }

        for (int[] edge : edges) {
            list.get(edge[0]).add(edge[1]);
        }

        int[] res = new int[n];
        for (int i = 0; i < edges.length; i++) {

        }

        return res;
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
