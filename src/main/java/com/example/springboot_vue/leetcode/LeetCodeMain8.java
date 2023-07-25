package com.example.springboot_vue.leetcode;

import java.util.*;

public class LeetCodeMain8 {

    public static void main(String[] args) {
        var list = new ArrayList<Integer>();
        list.add(1);
        System.out.println(list.get(0));
    }

    // 2208. 将数组和减半的最少操作次数
    public int halveArray(int[] nums) {
        int res = 0;
        long sum = 0;
        double temp = 0.0;
        PriorityQueue<Double> queue = new PriorityQueue<>(Comparator.reverseOrder());
        for (int num : nums) {
            sum += num;
            queue.offer((double) num);
        }

        while (!queue.isEmpty()) {
            double tempQue = queue.poll();
            temp += tempQue / 2;
            res++;
            if (temp >= sum / 2.0) {
                break;
            }
            queue.offer(tempQue / 2.0);
        }

        return res;
    }

    // 771. 宝石与石头
    public int numJewelsInStones(String jewels, String stones) {
        int res = 0;
        for (int i = 0; i < stones.length(); i++) {
            char c = stones.charAt(i);
            if (jewels.indexOf(c) != -1) {
                res++;
            }
        }

        return res;
    }

    // 860. 柠檬水找零
    public boolean lemonadeChange(int[] bills) {
        int five = 0, ten = 0;
        for (int i = 0; i < bills.length; i++) {
            if (bills[i] == 5) {
                five++;
            } else if (bills[i] == 10) {
                if (five <= 0) {
                    return false;
                }
                five--;
                ten++;
            } else {
                if (five < 1) {
                    return false;
                }

                if (ten > 0) {
                    ten--;
                    five--;
                } else {
                    if (five < 3) {
                        return false;
                    } else {
                        five -= 3;
                    }
                }
            }
        }

        return true;
    }

    // 918. 环形子数组的最大和
    public int maxSubarraySumCircular(int[] nums) {
        int n = nums.length;
        // 这个队列里面存放的是前缀和，然后前缀和越小的，越在前面
        Deque<int[]> queue = new ArrayDeque<>();
        int pre = nums[0], res = nums[0];
        queue.offerLast(new int[]{0, pre});
        for (int i = 1; i < 2 * n; i++) {
            // 这里是为了避免队列中最前边的元素的下标到当前i的距离大于数组长度，如果大于的话则有元素被加了两次
            while (!queue.isEmpty() && queue.peekFirst()[0] < i - n) {
                queue.pollFirst();
            }
            // 这里，pre永远记录的是从0到i的前缀和
            pre += nums[i % n];
            // 队列的最前面存放的是小于i的前缀和的最小值
            res = Math.max(res, pre - queue.peekFirst()[1]);
            // 这里，找到了比当前元素最后一个值还要小的，就要出队
            while (!queue.isEmpty() && queue.peekLast()[1] >= pre) {
                queue.pollLast();
            }
            queue.offerLast(new int[]{i, pre});
        }

        return res;
    }

    // 874. 模拟行走机器人
    public int robotSim(int[] commands, int[][] obstacles) {
        int res = 0;
        int[][] dirs = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
        int x = 0, y = 0, dir = 1;
        Set<Integer> set = new HashSet<>();

        for (int[] obstacle : obstacles) {
            // 这里是添加障碍的位置，这里*60001是为了防止不同坐标的x和y相加后结果一样，比如-1，1 和1，-1虽然不在一个点，但是直接相加结果一样
            set.add(obstacle[0] * 60001 + obstacle[1]);
        }

        for (int command : commands) {
            if (command < 0) {
                dir += command == -1 ? 1 : -1;
                // 这里是为了循环dirs数组,有可能加完1后超出边界
                dir = dir % 4;
                // 这里是为了处理-1后可能小于0
                if (dir < 0) {
                    dir += 4;
                }
            } else {
                for (int j = 0; j < command; j++) {
                    // 这里是因为遇到了障碍物，不能到达，下面已经计算过到达障碍物前的最远距离
                    // 这里是一步一步加的，每个点走一次，然后判断是否和障碍物重合，如果重合就直接结束，否则计算一次距离
                    // 因为dir已经确定了走的方向，所以直接x和y一起加就可以了
                    if (set.contains((x + dirs[dir][0]) * 60001 + y + dirs[dir][1])) {
                        break;
                    }

                    x += dirs[dir][0];
                    y += dirs[dir][1];
                    res = Math.max(res, x * x + y * y);
                }
            }
        }

        return res;
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
