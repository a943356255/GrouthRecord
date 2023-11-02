package com.example.springboot_vue.leetcode;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class LeetCodeMain10 {

    public static void main(String[] args) {
//        new LeetCodeMain10().simplifyPath("/../");
        int a = 5;
        int n = 3;

        System.out.println(4 & 12);
    }

    // 530. 二叉搜索树的最小绝对差
    public int getMinimumDifference(TreeNode root) {
        int res = Integer.MAX_VALUE;
        List<Integer> list = new ArrayList<>();
        dfs(root, list);
        list.sort(Comparator.comparing(a -> a));
        for (int i = 1; i < list.size(); i++) {
            res = Math.min(list.get(i) - list.get(i - 1), res);
        }

        return res;
    }

    public void dfs(TreeNode treeNode, List<Integer> list) {
        if (treeNode == null) {
            return;
        }

        list.add(treeNode.val);
        dfs(treeNode.left, list);
        dfs(treeNode.right, list);
    }

    // 8. 字符串转换整数 (atoi)
    public int myAtoi(String s) {
        if (s.equals("")) {
            return 0;
        }
        // 去除前导空格
        int i = 0, mark = 1;
        if (s.charAt(i) == ' ') {
            while (i < s.length() && s.charAt(i) == ' ') {
                i++;
            }
        }
        if (s.charAt(i) == '-' || s.charAt(i) == '+') {
            if (s.charAt(i) == '-') {
                mark = -1;
            } else {
                mark = 1;
            }
            i++;
        }
        long res = 0;
        StringBuilder stringBuilder = new StringBuilder();
        int markZero = 0;
        for (; i < s.length(); i++) {
            if (s.charAt(i) < '0' || s.charAt(i) > '9') {
                break;
            }
            if (s.charAt(i) == '0' && markZero == 0) {
                while (s.charAt(i) == '0' && i < s.length()) {
                    i++;
                }
                markZero = 1;
            }

            stringBuilder.append(s.charAt(i));
        }
        if (stringBuilder.length() > 10) {
            if (mark == 1) {
                return Integer.MAX_VALUE;
            } else {
                return Integer.MIN_VALUE;
            }
        }

        stringBuilder.reverse();
        int index = 0;
        for (int j = 0; j < stringBuilder.length(); j++) {
            res += (stringBuilder.charAt(j) - '0') * Math.pow(10, index);
            index++;
        }

        System.out.println("String = " + stringBuilder.toString() + " res = " + res);

        res *= mark;
        if (res > Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        }
        if (res < Integer.MIN_VALUE) {
            return Integer.MIN_VALUE;
        }

        return (int) res;
    }

    // 2652. 倍数求和
    public int sumOfMultiples(int n) {
        int res = 0;
        for (int i = 1; i <= n; i++) {
            if (i % 3 == 0 || i % 5 == 0 || i % 7 == 0) {
                res += i;
            }
        }
        return res;
    }

    // 117. 填充每个节点的下一个右侧节点指针 II
    public Node connect(Node root) {
        if (root == null) {
            return null;
        }

        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                Node temp = queue.poll();
                assert temp != null;
                if (i < size - 1) {
                    temp.next = queue.peek();
                }
                if (temp.left != null) {
                    queue.offer(temp.left);
                }
                if (temp.right != null) {
                    queue.offer(temp.right);
                }
            }
        }

        return root;
    }

    // 260. 只出现一次的数字 III
    public int[] singleNumber3(int[] nums) {
        int[] res = new int[2];
        int temp = 0;
        for (int num : nums) {
            temp ^= num;
        }

        // 这里是防止数据溢出，如果temp正好是最小值，它取负数就溢出了
        int lowOne = temp == Integer.MIN_VALUE ? temp : temp & (-temp);
        int first = 0, second = 0;
        for (int num : nums) {
            // 这里，lowOne只有1位的值是1，而lowOne是两个不相同的数做^得到的
            // 最低位的1说明这俩数字在该位上一个为1，一个为0
            if ((lowOne & num) != 0) {
                first ^= num;
            } else {
                second ^= num;
            }
        }

        res[0] = first;
        res[1] = second;
        return res;
    }

    private Map<Integer, Integer> indexMap;
    // 105. 从前序与中序遍历序列构造二叉树
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        int n = preorder.length;
        // 构造哈希映射，帮助我们快速定位根节点
        indexMap = new HashMap<Integer, Integer>();
        for (int i = 0; i < n; i++) {
            indexMap.put(inorder[i], i);
        }
        return myBuildTree(preorder, inorder, 0, n - 1, 0, n - 1);
    }

    public TreeNode myBuildTree(int[] preorder, int[] inorder, int preorder_left, int preorder_right, int inorder_left, int inorder_right) {
        if (preorder_left > preorder_right) {
            return null;
        }

        // 前序遍历中的第一个节点就是根节点
        int preorder_root = preorder_left;
        // 在中序遍历中定位根节点
        int inorder_root = indexMap.get(preorder[preorder_root]);

        // 先把根节点建立出来
        TreeNode root = new TreeNode(preorder[preorder_root]);
        // 得到左子树中的节点数目
        int size_left_subtree = inorder_root - inorder_left;
        // 递归地构造左子树，并连接到根节点
        // 先序遍历中「从 左边界+1 开始的 size_left_subtree」个元素就对应了中序遍历中「从 左边界 开始到 根节点定位-1」的元素
        root.left = myBuildTree(preorder, inorder, preorder_left + 1, preorder_left + size_left_subtree, inorder_left, inorder_root - 1);
        // 递归地构造右子树，并连接到根节点
        // 先序遍历中「从 左边界+1+左子树节点数目 开始到 右边界」的元素就对应了中序遍历中「从 根节点定位+1 到 右边界」的元素
        root.right = myBuildTree(preorder, inorder, preorder_left + size_left_subtree + 1, preorder_right, inorder_root + 1, inorder_right);
        return root;
    }

//    Map<Integer, Integer> map;
//    TreeNode res;
//    // 105. 从前序与中序遍历序列构造二叉树
//    public TreeNode buildTree(int[] preorder, int[] inorder) {
//        // 树根
//        res = new TreeNode(preorder[0]);
//
//        // 通过前序来判断节点位置
//        map = new HashMap<>();
//        for (int i = 0; i < preorder.length; i++) {
//            map.put(preorder[i], i);
//        }
//
//        build(inorder, 0, inorder.length / 2);
//        build(inorder, (inorder.length / 2), inorder.length);
//
//        return res;
//    }

    // 137. 只出现一次的数字 II（题解写法）
    public int singleNumber2(int[] nums) {
        int ans = 0;
        for (int i = 0; i < 32; ++i) {
            int total = 0;
            // 这里是遍历所有nums，然后取他们的第i位的值，是0还是1，然后把所有的都加一起
            for (int num: nums) {
                total += ((num >> i) & 1);
            }
            // 因为只有一个元素是唯一的，其他都是3的倍数，把他们加一起然后%3，得到的就是唯一的那个元素在第i位的值
            if (total % 3 != 0) {
                ans |= (1 << i);
            }
        }

        return ans;
    }

    // 137. 只出现一次的数字 II
//    public int singleNumber2(int[] nums) {
//        Map<Integer, Integer> map = new HashMap<>();
//
//        for (int i = 0; i < nums.length; i++) {
//            if (map.get(nums[i]) == null) {
//                map.put(nums[i], 1);
//            } else {
//                map.put(nums[i], map.get(nums[i]) + 1);
//            }
//        }
//
//        int[] mark = new int[1];
//        map.forEach((key, val) -> {
//            if (val == 1) {
//                mark[0] = key;
//            }
//        });
//
//        return mark[0];
//    }

    // 138. 随机链表的复制
    public Node copyRandomList(Node head) {
        if (head == null) {
            return null;
        }

        Map<Node, Node> map = new HashMap<>();

        Node res = new Node(1);
        Node temp = head;
        Node resTemp = res;
        Node resVal = res;

        int index = 0;
        while (head != null) {
            res.val = head.val;
            map.put(head, res);
            index++;
            head = head.next;
            if (head != null) {
                res.next = new Node(1);
                res = res.next;
            }
        }

        index = 0;
        while (temp != null) {
            if (temp.random != null) {
                resTemp.random = map.get(temp.random);
            } else {
                resTemp.random = null;
            }
            index++;
            temp = temp.next;
            resTemp = resTemp.next;
        }

        return resVal;
    }

    public void copyNode(Node newNode, Node oldNode) {
        newNode.val = oldNode.val;
        Node next = new Node(0);
        Node random = new Node(0);
        if (oldNode.next != null) {
            next.val = oldNode.next.val;
            newNode.next = next;
            newNode = newNode.next;
        }

        if (oldNode.random != null) {
            random.val = oldNode.random.val;
            newNode.random = random;
        }
    }

    /**
     * 这题要求使用常量的空间，即不能使用map等集合
     * 所以使用位运算
     * a ⊕ a = 0 , a ⊕ 0 = a
     * 而且 ⊕ 支持交换律和结合律
     * 那么在本题中，只有一个元素出现了1次，当所有元素全部做⊕时，最终留下来的就是该元素
     */
    // 136. 只出现一次的数字
    public int singleNumber(int[] nums) {
        int single = 0;
        for (int num : nums) {
            single ^= num;
        }
        return single;
    }

    // 1488. 避免洪水泛滥
    public int[] avoidFlood(int[] rains) {
//        int[] res = new int[rains.length];
//        Set<Integer> set = new HashSet<>();
//        int count = 0, temp = 0;
//        // 拿来存储0的下标
//        List<Integer> list = new LinkedList<>();
//        for (int i = 0; i < rains.length; i++) {
//            if (rains[i] != 0) {
//                if (set.contains(rains[i])) {
//                    if (count <= 0) {
//                        return new int[0];
//                    } else {
//                        set.remove(rains[i]);
//                        res[i] = -1;
//                        count--;
//                        res[list.get(0)] = rains[i];
//                        list.remove(0);
//                    }
//                } else {
//                    if (temp == 0) {
//                        temp = rains[i];
//                    }
//                    set.add(rains[i]);
//                    res[i] = -1;
//                }
//            } else {
//                if (set.size() > 0) {
//                    count++;
//                    list.add(i);
//                }
//            }
//        }
//
//        // 说明多了几个没下雨的天，随便抽干哪一个
//        while (count >= 0) {
//            count--;
//            res[list.get(0)] = temp;
//        }
//        return res;
        int[] ans = new int[rains.length];
        Arrays.fill(ans, 1);
        // 这里按顺序记录了出现0的下标
        TreeSet<Integer> st = new TreeSet<>();
        Map<Integer, Integer> mp = new HashMap<>();
        for (int i = 0; i < rains.length; ++i) {
            if (rains[i] == 0) {
                st.add(i);
            } else {
                ans[i] = -1;
                if (mp.containsKey(rains[i])) {
                    // ceiling是为了找到比传入的数据大或等于的元素
                    // 这里是找到最接近的一个下标，mp.get(rains[i])获取到当前下雨的下标，然后获取离他最近的一个下标为0的元素的下标
                    // 这个it，是获取到的下标为0的元素的元素的下标
                    Integer it = st.ceiling(mp.get(rains[i]));
                    if (it == null) {
                        return new int[0];
                    }
                    ans[it] = rains[i];
                    st.remove(it);
                }
                mp.put(rains[i], i);
            }
        }

        return ans;
    }

    // 2562. 找出数组的串联值
    public long findTheArrayConcVal(int[] nums) {
        long res = 0;
        int left = 0, right = nums.length - 1;
        String temp;
        while (left < right) {
            temp = String.valueOf(nums[left]) + String.valueOf(nums[right]);
            res += Long.parseLong(temp);
            left++;
            right--;
        }

        if (nums.length % 2 != 0) {
            res += nums[nums.length / 2];
        }

        return res;
    }

    // 150. 逆波兰表达式求值
    public int evalRPN(String[] tokens) {
        int res = 0;
        Set<String> set = new HashSet<>();
        set.add("+");set.add("-");set.add("*");set.add("/");

        Stack<String> stack = new Stack<>();
        for (String token : tokens) {
            if (!set.contains(token)) {
                stack.push(token);
            } else {
                int first = Integer.parseInt(stack.pop());
                int second = Integer.parseInt(stack.pop());
                switch (token) {
                    case "+":
                        stack.push(String.valueOf(first + second));
                        break;
                    case "-":
                        stack.push(String.valueOf(first - second));
                        break;
                    case "*":
                        stack.push(String.valueOf(first * second));
                        break;
                    default:
                        stack.push(String.valueOf(second / first));
                        break;
                }
            }
        }

        return Integer.parseInt(stack.peek());
    }

    // LCR 161. 连续天数的最高销售额
    public int maxSales(int[] sales) {
        if (sales.length == 1) {
            return sales[0];
        }

        int max = sales[0];
        int[] dp = new int[sales.length];
        dp[0] = sales[0];
        for (int i = 1; i < sales.length; i++) {
            dp[i] = Math.max(dp[i - 1] + sales[i], sales[i]);
            max = Math.max(dp[i], max);
        }

        return max;
    }

    // 2512. 奖励最顶尖的 K 名学生
    public List<Integer> topStudents(String[] positive_feedback, String[] negative_feedback, String[] report, int[] student_id, int k) {
        PriorityQueue<int[]> queue = new PriorityQueue<>(k, (a, b) -> {
            // 分数相等，按照id由小到大排，否则按照分数由大到小排序
            if (a[1] != b[1]) {
                return b[1] - a[1];
            } else {
                return a[0] - b[0];
            }
        });

        Set<String> good = new HashSet<>(Arrays.asList(positive_feedback));
        Set<String> bad = new HashSet<>(Arrays.asList(negative_feedback));

        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < report.length; i++) {
            int id = student_id[i];
            int score = 0;
            String[] word = report[i].split(" ");

            for (String s : word) {
                if (good.contains(s)) {
                    score += 3;
                }

                if (bad.contains(s)) {
                    score -= 1;
                }
            }

            int[] temp = new int[2];
            temp[0] = id;
            temp[1] = score;
            queue.add(temp);
        }

        while (!queue.isEmpty() && k > 0) {
            System.out.println(Arrays.toString(queue.peek()));
            res.add(queue.poll()[0]);
            k--;
        }

        return res;
    }

    /**
     * 没想到使用/进行分割，自己写的解决不了多个/./穿插的问题
     */
    // 71. 简化路径
    public String simplifyPath(String path) {
//        Stack<Character> stack = new Stack<>();
//        for (int i = 0; i < path.length(); i++) {
//            char c = path.charAt(i);
//            if (c == '.') {
//                if (stack.peek() == '.') {
//                    int count = 0, mark = 0;
//                    while (!stack.isEmpty()) {
//                        char temp = stack.pop();
//                        if (temp == '/') {
//                            count++;
//                        }
//                        if (temp >= 'a' && temp <= 'z' || temp >= 'A' && temp <= 'Z') {
//                            mark = 1;
//                        }
//
//                        if (count >= 2 && mark != 0) {
//                            break;
//                        }
//                    }
//                } else {
//                    stack.push(c);
//                }
//            } else if (c == '/') {
//                if (stack.isEmpty()) {
//                    stack.push(c);
//                } else {
//                    if (stack.peek() != '/' && i != path.length() - 1) {
//                        stack.push(c);
//                    }
//                }
//            } else {
//                stack.push(c);
//            }
//        }
//
//        StringBuilder res = new StringBuilder();
//        while (!stack.isEmpty()) {
//            res.append(stack.pop());
//        }
//        return res.reverse().toString();

        // 官方写法，用split分割
        String[] names = path.split("/");
        System.out.println(Arrays.toString(names));
        Deque<String> stack = new ArrayDeque<>();
        for (String name : names) {
            if ("..".equals(name)) {
                if (!stack.isEmpty()) {
                    stack.pollLast();
                }
            } else if (name.length() > 0 && !".".equals(name)) {
                stack.offerLast(name);
            }
        }

        StringBuffer ans = new StringBuffer();
        if (stack.isEmpty()) {
            ans.append('/');
        } else {
            while (!stack.isEmpty()) {
                ans.append('/');
                ans.append(stack.pollFirst());
            }
        }

        return ans.toString();
    }

    // 13. 罗马数字转整数
    public int romanToInt(String s) {
        Map<Character, Integer> map = new HashMap<>();
        map.put('I', 1);
        map.put('V', 5);
        map.put('X', 10);
        map.put('L', 50);
        map.put('C', 100);
        map.put('D', 500);
        map.put('M', 1000);

        int res = 0, mark;
        for (int i = 0; i < s.length(); i++) {
            mark = 0;
            char c = s.charAt(i);
            if (i + 1 < s.length()) {
                char temp = s.charAt(i + 1);
                if (c == 'I') {
                    if (temp == 'V') {
                        res += 4;
                        i++;
                        mark = 1;
                    } else if (temp == 'X') {
                        res += 9;
                        i++;
                        mark = 1;
                    }
                } else if (c == 'X') {
                    if (temp == 'L') {
                        res += 40;
                        i++;
                        mark = 1;
                    } else if (temp == 'C') {
                        res += 90;
                        i++;
                        mark = 1;
                    }
                } else if (c == 'C') {
                    if (temp == 'D') {
                        res += 400;
                        i++;
                        mark = 1;
                    } else if (temp == 'M') {
                        res += 900;
                        i++;
                        mark = 1;
                    }
                }
            }

            if (mark == 0) {
                res += map.get(c);
            }
        }

        return res;
    }

    // 2731. 移动机器人
    public int sumDistance(int[] nums, String s, int d) {
//        int res = 0, temp = 1000000007;
//
//        for (int i = 0; i < nums.length; i++) {
//            if (s.charAt(i) == 'L') {
//                nums[i] = (nums[i] - d) % temp;
//            } else {
//                nums[i] = (nums[i] + d) % temp;
//            }
//
//            if (i >= 1) {
//                res += (Math.abs(nums[i] - nums[i - 1]) % temp) * (i % temp);
//            }
//        }
//
//        return res;
        int MOD = 1000000007;
        int n = nums.length;
        long[] pos = new long[n];
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == 'L') {
                pos[i] = (long) nums[i] - d;
            } else {
                pos[i] = (long) nums[i] + d;
            }
        }
        Arrays.sort(pos);
        long res = 0;
        for (int i = 1; i < n; i++) {
            res += 1L * (pos[i] - pos[i - 1]) * i % MOD * (n - i) % MOD;
            res %= MOD;
        }

        return (int) res;
    }

    // 54. 螺旋矩阵
    public List<Integer> spiralOrder(int[][] matrix) {
        // 右，下，左，上
        int[][] arr = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        List<Integer> res = new ArrayList<>();
        int xLength = 0, yLength = 0;

        int x = 0, y = 0, direction = 0;
        while (res.size() != matrix.length * matrix[0].length) {
            res.add(matrix[x][y]);

            if ((x == matrix.length - xLength - 1 || x == xLength + 1)) {
                direction = (direction + 1) % 4;
                if (x == xLength + 1) {
                    xLength++;
                    yLength++;
                }
            }

            x += arr[direction][0];
            y += arr[direction][1];
            System.out.println("x = " + x + " y = " + y);
        }

        return res;
    }

    // 2578. 最小和分割
    public int splitNum(int num) {
        List<Integer> list = new ArrayList<>();
        while (num > 0) {
            list.add(num % 10);
            num /= 10;
        }
        list.sort(Comparator.comparingInt(a -> a));

        StringBuilder first = new StringBuilder();
        StringBuilder second = new StringBuilder();
        int mark = 0;
        for (int i = 0; i < list.size(); i++) {
            if (mark == 0) {
                first.append(list.get(i));
                mark = 1;
            } else {
                second.append(list.get(i));
                mark = 0;
            }
        }

        return Integer.parseInt(first.toString()) + Integer.parseInt(second.toString());
    }

    // 452. 用最少数量的箭引爆气球
    public int findMinArrowShots(int[][] points) {
        Arrays.sort(points, Comparator.comparingInt(a -> a[0]));
        int res = 1;
        int right = points[0][1];
        for (int i = 1; i < points.length; i++) {
            if (points[i][0] > right) {
                res++;
                right = points[i][1];
            } else {
                right = Math.min(right, points[i][1]);
            }
        }

        return res;
    }

    // 219. 存在重复元素 II
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (map.get(nums[i]) != null) {
                if (Math.abs(map.get(nums[i]) - i) <= k) {
                    return true;
                }
            }
            map.put(nums[i], i);
        }

        return false;
    }

    // 714. 买卖股票的最佳时机含手续费
    public int maxProfit(int[] prices, int fee) {
        int[][] dp = new int[prices.length][2];
        // 持有股票的最大收益
        dp[0][0] = -prices[0];
        // 持有现金的最大收益
        dp[0][1] = 0;
        for (int i = 1; i < prices.length; i++) {
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] - prices[i]);
            dp[i][1] = Math.max(dp[i - 1][0] + prices[i] - fee, dp[i - 1][1]);
        }

        return dp[prices.length - 1][1];
    }

    // 309. 买卖股票的最佳时机含冷冻期
    public int maxProfitCold(int[] prices) {
        if (prices.length < 2) {
            return 0;
        }

        int[][] dp = new int[prices.length][3];
        // 0代表持有现金，1代表持有股票，2代表在冷冻期
        dp[0][1] = -prices[0];
        for (int i = 1; i < prices.length; i++) {
            // 手上持有股票的最大利益
            // dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] - prices[i]);
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] - prices[i]);

            // 没有股票，处于冷冻期的最大利益
            // dp[i][2] = Math.max(dp[i][2], dp[i - 1][1]);
            dp[i][2] = dp[i - 1][1] + prices[i];

            // 没有股票，不处于冷冻期的最大利益
            // dp[i][0] = Math.max(dp[i - 1][0], dp[i - 2][1] + prices[i]);
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][2]);
        }

        return Math.max(dp[prices.length - 1][0], dp[prices.length - 1][2]);
    }

    int ans;
    // 543. 二叉树的直径
    public int diameterOfBinaryTree(TreeNode root) {
        ans = 1;
        getLength(root);
        return ans - 1;
    }

    public int getLength(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int left = getLength(root.left);
        int right = getLength(root.right);
        ans = Math.max(ans, left + right + 1);
        return Math.max(left, right) + 1;
    }

    // 188. 买卖股票的最佳时机 IV
    public int maxProfit(int k, int[] prices) {
        if (prices.length == 0) {
            return 0;
        }

        int n = prices.length;
        // 当k大于prices长度的一半时，多出去的部分是没有意义的
        k = Math.min(k, n / 2);
        // buy和sell 都表示进行j笔交易后的最大利润,不同的是buy此时还持有一只股票，而sell是不持有
        int[][] buy = new int[n][k + 1];
        int[][] sell = new int[n][k + 1];

        // 后续都是取较大值，所以这里先给第0天的每一笔交易都赋予最小值
        buy[0][0] = -prices[0];
        sell[0][0] = 0;
        for (int i = 1; i <= k; ++i) {
            buy[0][i] = sell[0][i] = Integer.MIN_VALUE / 2;
        }

        for (int i = 1; i < n; ++i) {
            // 这里是记录第i天不进行交易时，他们的最大值是多少
            // buy代表持有一只股票，那么它的取值就是i - 1天的值（i - 1天持有股票）
            // 或者i - 1天卖出股票后，再买入今天股票的值(因为buy要求必须持有一只股票)
            buy[i][0] = Math.max(buy[i - 1][0], sell[i - 1][0] - prices[i]);
            // 后续模拟在第i天进行k笔交易
            for (int j = 1; j <= k; ++j) {
                // 这里的buy和上面的一样
                buy[i][j] = Math.max(buy[i - 1][j], sell[i - 1][j] - prices[i]);
                // 这里的第一个值也很好理解，因为sell的i - 1肯定也是卖出股票后的价格
                // buy[i - 1][j - 1]是上一个持有股票时的最大值，加上今天卖出股票的收益
                // 两者取最大值
                // 这里为什么不是buy[i - 1][j]呢？因为buy[i - 1][j]是第j次交易的最大值
                sell[i][j] = Math.max(sell[i - 1][j], buy[i - 1][j - 1] + prices[i]);
            }
        }

        return Arrays.stream(sell[n - 1]).max().getAsInt();
    }

    // 123. 买卖股票的最佳时机 III
    public int maxProfit3(int[] prices) {
        int n = prices.length;
        // 这里是记录每一天结束后的四个状态
        // 1.买入股票，但未卖出 buy1
        // 2.将状态1买入的股票卖出，即完成了一笔交易，但是没有买入下一个股票 sell1
        // 3.买入第二支股票 buy2
        // 4.卖出股票，即完成两次交易 sell2
        int buy1 = -prices[0], sell1 = 0;
        int buy2 = -prices[0], sell2 = 0;

        for (int i = 1; i < n; ++i) {
            // 第i天，如果只考虑买入但不卖出，那么最大值就是i-1天buy1的状态和当天买入比较，取较大的
            // 这里的状态是只买入一支股票
            buy1 = Math.max(buy1, -prices[i]);
            // 这里是只卖出，但是不买入，
            sell1 = Math.max(sell1, buy1 + prices[i]);
            buy2 = Math.max(buy2, sell1 - prices[i]);
            sell2 = Math.max(sell2, buy2 + prices[i]);
        }

        return sell2;
    }

    // 394. 字符串解码
    public String decodeString(String s) {
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c != ']') {
                stack.push(c);
            } else {
                StringBuilder tempStr = new StringBuilder();
                // 这里是出现右括号后，找到第一个与之匹配的左括号
                while (!stack.isEmpty()) {
                    char temp = stack.pop();
                    if (temp != '[') {
                        tempStr.insert(0, temp);
                    } else {
                        break;
                    }
                }
                int num = 0, count = 0;
                // 取出所有的数字
                while (!stack.isEmpty()) {
                    if (stack.peek() >= '0' && stack.peek() <= '9') {
                        num += Math.pow(10, count) * (stack.pop() - '0');
                        count++;
                    } else {
                        break;
                    }
                }

                // 重新放入
                for (int j = 0; j < num; j++) {
                    for (int k = 0; k < tempStr.length(); k++) {
                        stack.push(tempStr.charAt(k));
                    }
                }
            }
        }

        StringBuilder res = new StringBuilder();
        while (!stack.isEmpty()) {
            res.insert(0, stack.pop());
        }
        return res.toString();
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
