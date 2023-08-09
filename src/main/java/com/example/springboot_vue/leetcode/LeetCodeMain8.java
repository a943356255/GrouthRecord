package com.example.springboot_vue.leetcode;

import java.util.*;

public class LeetCodeMain8 {

    public static void main(String[] args) {
        var list = new ArrayList<Integer>();
        list.add(1);
        System.out.println(list.get(0));
    }

    // 1281. 整数的各位积和之差
    public int subtractProductAndSum(int n) {
        int add = 0, mix = 1;
        int length = String.valueOf(n).length() - 1;
        while (length > 0) {
            int temp = (int) (n / Math.pow(10, length));
            add += temp;
            mix *= temp;
            n = (int) (n % Math.pow(10, length));
            length --;
        }

        return mix - add;
    }

    // 1749. 任意子数组和的绝对值的最大值
    public int maxAbsoluteSum(int[] nums) {
        // 子数组和是连续的，那么可以求前缀和，然后找前缀和的最大值减去最小值，就是答案
        int s = 0, mx = 0, mn = 0;
        for (int x : nums) {
            s += x;
            if (s > mx) mx = s;
            else if (s < mn) mn = s; // 效率更高的写法
        }

        return mx - mn;
    }

    // 344. 反转字符串
    public void reverseString(char[] s) {
        int first = 0, last = s.length - 1;
        char temp;
        while (first < last) {
            temp = s[first];
            s[first] = s[last];
            s[last] = temp;
            first++;
            last--;
        }
    }

    // 24. 两两交换链表中的节点
    public ListNode swapPairs(ListNode head) {
        ListNode dummyHead = new ListNode(0);
        dummyHead.next = head;
        ListNode temp = dummyHead;

        while (temp.next != null && temp.next.next != null) {
            ListNode node1 = temp.next;
            ListNode node2 = temp.next.next;
            temp.next = node2;
            node1.next = node2.next;
            node2.next = node1;
            temp = node1;
        }

        return dummyHead.next;
    }

    // 21. 合并两个有序链表
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        ListNode res = new ListNode();
        ListNode temp = new ListNode();
        res.next = temp;
        while (list1 != null && list2 != null) {
            ListNode node = new ListNode();
            if (list1.val < list2.val) {
                node.val = list1.val;
                list1 = list1.next;
            } else {
                node.val = list2.val;
                list2 = list2.next;
            }

            temp.next = node;
            temp = temp.next;
        }

        while (list1 != null) {
            ListNode node = new ListNode();
            node.val = list1.val;
            list1 = list1.next;
            temp.next = node;
            temp = temp.next;
        }

        while (list2 != null) {
            ListNode node = new ListNode();
            node.val = list2.val;
            list2 = list2.next;
            temp.next = node;
            temp = temp.next;
        }

        return res.next.next;
    }

    static int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    // 980. 不同路径 III
    public int uniquePathsIII(int[][] grid) {
        int r = grid.length, c = grid[0].length;
        int si = 0, sj = 0, n = 0;
        // 这里是先遍历grid，统计里面有多少个0，并且找到1所在的位置。
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if (grid[i][j] == 0) {
                    n++;
                } else if (grid[i][j] == 1) {
                    n++;
                    si = i;
                    sj = j;
                }
            }
        }

        return dfs(grid, si, sj, n);
    }

    public int dfs(int[][] grid, int i, int j, int n) {
        // 这里，每次传递进来的n都是等于上一次的n - 1，如果到达2的时候n == 0，说明有一条路，否则没有
        if (grid[i][j] == 2) {
            return n == 0 ? 1 : 0;
        }

        int r = grid.length, c = grid[0].length;
        // 这里是先记录i，j位置上的值，用于回溯完再将值赋回去
        int t = grid[i][j];
        // 将i，j的位置设置为-1，意思是不能再访问
        grid[i][j] = -1;
        int res = 0;

        // 遍历dirs，模拟当前位置往上下左右四个方向移动
        for (int[] dir : dirs) {
            // 确定下一个位置
            int ni = i + dir[0], nj = j + dir[1];
            // 如果当前坐标在合理的范围内，并且值不是-1
            if (ni >= 0 && ni < r && nj >= 0 && nj < c && (grid[ni][nj] == 0 || grid[ni][nj] == 2)) {
                // 这里是累加的总过程
                res += dfs(grid, ni, nj, n - 1);
            }
        }
        grid[i][j] = t;

        return res;
    }

    // 722. 删除注释
    public List<String> removeComments(String[] source) {
        List<String> res = new ArrayList<>();
        StringBuilder newLine = new StringBuilder();
        boolean inBlock = false;
        for (String line : source) {
            for (int i = 0; i < line.length(); i++) {
                if (inBlock) {
                    // 之前有/*了，现在要一找到 */,然后中间的元素一律不追加到新的字符后边
                    if (i + 1 < line.length() && line.charAt(i) == '*' && line.charAt(i + 1) == '/') {
                        inBlock = false;
                        i++;
                    }
                } else {
                    // 找到 /* 的位置
                    if (i + 1 < line.length() && line.charAt(i) == '/' && line.charAt(i + 1) == '*') {
                        inBlock = true;
                        i++;
                    } else if (i + 1 < line.length() && line.charAt(i) == '/' && line.charAt(i + 1) == '/') {
                        // 如果是//，则后边的都不要
                        break;
                    } else {
                        // 否则将他追加到新的字符后边
                        newLine.append(line.charAt(i));
                    }
                }
            }
            if (!inBlock && newLine.length() > 0) {
                res.add(newLine.toString());
                newLine.setLength(0);
            }
        }
        return res;
    }

    // 822. 翻转卡片游戏
    public int flipgame(int[] fronts, int[] backs) {
        Set<Integer> noneSet = new HashSet<>();
        int res = 2001;

        // 不交换的最小值
        for (int i = 0; i < fronts.length; i++) {
            if (backs[i] == fronts[i]) {
                noneSet.add(backs[i]);
            }
        }

        for (int front : fronts) {
            if (front < res && !noneSet.contains(front)) {
                res = front;
            }
        }

        for (int back : backs) {
            if (!noneSet.contains(back) && back < res) {
                res = back;
            }
        }

        return res % 2001;
    }

    // 139. 单词拆分
    public boolean wordBreak(String s, List<String> wordDict) {
        Set<String> wordDictSet = new HashSet<>(wordDict);
        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true;
        for (int i = 1; i <= s.length(); i++) {
            // 每一次从0到i来进行截取，只要存在就为true
            for (int j = 0; j < i; j++) {
                // 如果之前是true，同时新截取的单词还包含在集合当中，则当前i为true
                if (dp[j] && wordDictSet.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }

        return dp[s.length()];
    }

    // 143. 重排链表
    public void reorderList(ListNode head) {
        List<ListNode> list = new ArrayList<>();
        ListNode temp = head;
        while (temp != null) {
            list.add(temp);
            temp = temp.next;
        }

        head = new ListNode();
        temp = new ListNode();
        head.next = temp;
        int index = list.size() - 1;
        for (int i = 0; i < list.size(); i++) {
            temp.next = list.get(i);
            list.get(i).next = null;
            temp = temp.next;

            if (index > 0) {
                temp.next = list.get(index);
                list.get(index).next = null;
                temp = temp.next;
                index --;
            }

            if (index <= i) {
                break;
            }
        }

        head = head.next;
    }

    // 142. 环形链表 II
    public ListNode detectCycle(ListNode head) {
        Map<ListNode, String> map = new HashMap<>();
        while (head != null) {
            if (map.get(head) == null) {
                map.put(head, "1");
            } else {
                return head;
            }
            head = head.next;
        }

        return null;
    }

    // 141. 环形链表
    public boolean hasCycle(ListNode head) {
        // 一个更好的思路是快慢指针
        Map<ListNode, String> map = new HashMap<>();
        while (head != null) {
            if (map.get(head) == null) {
                map.put(head, "1");
            } else {
                return true;
            }
            head = head.next;
        }

        return false;

        // 快慢指针做法
//        if (head == null) return false;
//        ListNode slow = head, fast = head;
//        while (fast.next != null && fast.next.next != null){
//            slow = slow.next;
//            fast = fast.next.next;
//            if (slow == fast) return true;
//        }
//        return false;
    }

    // 2050. 并行课程 III
    public int minimumTime(int n, int[][] relations, int[] time) {
        int mx = 0;
        List<Integer>[] prev = new List[n + 1];
        // 存储邻接矩阵，即每个list[i]所存储的就是它的先学课程
        for (int i = 0; i <= n; i++) {
            prev[i] = new ArrayList<>();
        }
        for (int[] relation : relations) {
            int x = relation[0], y = relation[1];
            prev[y].add(x);
        }


        Map<Integer, Integer> memo = new HashMap<>();
        for (int i = 1; i <= n; i++) {
            mx = Math.max(mx, dp(i, time, prev, memo));
        }
        return mx;
    }

    public int dp(int i, int[] time, List<Integer>[] prev, Map<Integer, Integer> memo) {
        if (!memo.containsKey(i)) {
            int cur = 0;
            for (int p : prev[i]) {
                // 这里可以解决那种嵌套了很多层的课程,我自己的写法没办法解决这种情况
                cur = Math.max(cur, dp(p, time, prev, memo));
            }
            cur += time[i - 1];
            // 已经计算过学习当前课程包括他前边课程的时间，不需要后续计算，直接返回即可。
            memo.put(i, cur);
        }
        return memo.get(i);
    }

    // 2050. 并行课程 III
//    public int minimumTime(int n, int[][] relations, int[] time) {
//        int res = 0;
//
//        // 用于标记它是否是某个课程的先修课程
//        boolean[] mark = new boolean[n];
//        // 标记一门课程是否学习
//        boolean[] learn = new boolean[n];
//        Arrays.fill(mark, false);
//        Arrays.fill(learn, false);
//
//        Map<Integer, Integer> timeToIndex = new HashMap<>();
//        for (int i = 0; i < time.length; i++) {
//            timeToIndex.put(time[i], i);
//        }
//
//        Map<Integer, PriorityQueue<Integer>> map = new HashMap<>();
//        for (int i = 0; i < relations.length; i++) {
//            if (map.get(relations[i][1]) == null) {
//                PriorityQueue<Integer> queue = new PriorityQueue<>((a, b) -> b - a);
//                queue.offer(time[relations[i][0] - 1]);
//                map.put(relations[i][1], queue);
//            } else {
//                map.get(relations[i][1]).offer(time[relations[i][0] - 1]);
//            }
//            mark[relations[i][0] - 1] = true;
//        }
//
//        for (Map.Entry<Integer, PriorityQueue<Integer>> entry : map.entrySet()) {
//            PriorityQueue<Integer> queue = entry.getValue();
//            int temp = 0;
//            while (!queue.isEmpty()) {
//                int val = queue.poll();
//                if (temp == 0) {
//                    res += val;
//                    temp = 1;
//                }
//                learn[timeToIndex.get(val)] = true;
////                learn[val + 1] = true;
//            }
//
//            if (!mark[entry.getKey() - 1]) {
//                res += time[entry.getKey() - 1];
//                learn[entry.getKey() - 1] = true;
//            }
//        }
//
//        int max = -1;
//        for (int i = 0; i < learn.length; i++) {
//            if (!learn[i]) {
//                if (res < time[i]) {
//                    max = Math.max(max, time[i]);
//                }
//            }
//        }
//
//        if (max != -1) {
//            res += (max - res);
//        }
//
//        return res;
//    }

    // 2500. 删除每行中的最大值
    public int deleteGreatestValue(int[][] grid) {
        for (int[] ints : grid) {
            Arrays.sort(ints);
        }

        int sum = 0;
        for (int i = 0; i < grid[0].length; i++) {
            int temp = 0;
            for (int[] ints : grid) {
                if (ints[i] > temp) {
                    temp = ints[i];
                }
            }

            sum += temp;
        }

        return sum;
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
        for (int bill : bills) {
            if (bill == 5) {
                five++;
            } else if (bill == 10) {
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
