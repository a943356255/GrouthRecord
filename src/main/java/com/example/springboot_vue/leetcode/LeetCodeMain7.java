package com.example.springboot_vue.leetcode;

import java.util.*;

public class LeetCodeMain7 {

    public static void main(String[] args) {

    }

    // 1186. 删除一次得到子数组最大和
    public int maximumSum(int[] arr) {
        // dp0表示在前i个元素删除0个的最大值，dp1表示前i个元素删除1个的最大值
        // 初始时dp[0][0] = arr[0]，表示不删除元素，取第一个，而dp[0][1]不存在（选取第一个元素，并删除一个元素）
        int dp0 = arr[0], dp1 = 0, res = arr[0];

        // 因为dp[i][0]与dp[i][1]只与前一个状体有关，所以可以取两个数字而不是数组
        // 完整的状态转移方程式如下：
        // dp[i][0] = Math.max(dp[i - 1][0], 0) + arr[i]
        // dp[i][1] = Math.max(dp[i - 1][1] + arr[i], dp[i - 1][0])
        for (int i = 1; i < arr.length; i++) {
            // 删除一个的最大值，要么是上一个删除一个的最大值加当前值，即 dp1 + arr[i]
            // 要么是上一个不删除的元素（这里默认删除了当前的arr[i]）
            // 其实这里是做了一个对比，删除的元素要么是之前已经计算过的可以达到最大值的，然后和再加上arr[i]，
            // 要么是当前的arr[i]，那么总和就是之前不删除元素时的总和
            dp1 = Math.max(dp0, dp1 + arr[i]);

            // 当dp[i][0]大于0时，最大值可以是dp[i][0] + arr[i]，如果小于0，则直接取arr[i]（此处不需要管arr[i]是多大，因为负数加负数也会变得更小）
            dp0 = Math.max(dp0, 0) + arr[i];
            res = Math.max(res, Math.max(dp0, dp1));
        }

        return res;
    }

    // 2485. 找出中枢整数
    public int pivotInteger(int n) {
        int[] arr = new int[n];
        arr[0] = 0;
        for (int i = 1; i <= n; i++) {
            arr[i] = i + arr[i - 1];
        }

        int sum = arr[n];
        for (int i = 1; i <= n; i++) {
            if (sum - arr[i] + i == arr[i]) {
                return i;
            }
        }

        return -1;
    }

    // 289. 生命游戏
    public void gameOfLife(int[][] board) {
        int[][] arr = new int[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                arr[i][j] = live(board, i, j, board[i][j]);
            }
        }

        for (int i = 0; i < board.length; i++) {
            System.arraycopy(arr[i], 0, board[i], 0, board[0].length);
        }
    }

    public boolean isLegal(int[][] board, int x, int y) {
        return x >= 0 && x < board.length && y >= 0 && y < board[0].length;
    }

    public int live(int[][] board, int x, int y, int now) {
        int count1 = 0, count0 = 0;
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (i == x && j == y) {
                    continue;
                }
                if (isLegal(board, i, j)) {
                    if (board[i][j] == 1) {
                        count1++;
                    } else {
                        count0++;
                    }
                }
            }
        }

        if (now == 1) {
            if (count1 < 2 || count1 > 3) {
                return 0;
            }

            return 1;
        } else {
            if (count1 == 3) {
                return 1;
            } else {
                return 0;
            }
        }
    }


    // 73. 矩阵置零
    public void setZeroes(int[][] matrix) {
        Set<Integer> x = new HashSet<>();
        Set<Integer> y = new HashSet<>();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == 0) {
                    x.add(i);
                    y.add(j);
                }
            }
        }

        for (Integer integer : x) {
            Arrays.fill(matrix[integer], 0);
        }

        for (Integer integer : y) {
            for (int i = 0; i < matrix.length; i++) {
                matrix[i][integer] = 0;
            }
        }
    }

    // 2496. 数组中字符串的最大值
    public int maximumValue(String[] strs) {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < strs.length; i++) {
            String str = strs[i];
            int mark = 1;
            for (int j = 0; j < str.length(); j++) {
                char c = str.charAt(j);
                if (c >= 'a' && c <= 'z') {
                    max = Math.max(max, str.length());
                    mark = 0;
                    break;
                }
            }

            // 纯数字
            if (mark == 1) {
                max = Math.max(max, Integer.parseInt(str));
            }
        }

        return max;
    }

    // 面试题 16.19. 水域大小
    public int[] pondSizes(int[][] land) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < land.length; i++) {
            for (int j = 0; j < land[0].length; j++) {
                if (land[i][j] == 0) {
                    list.add(newDfs(land, i, j));
                }
            }
        }

        int [] arr = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            arr[i] = list.get(i);
        }
        Arrays.sort(arr);

        return arr;
    }

    public int newDfs(int[][] land, int x, int y) {
        if (!isA(land, x, y) || land[x][y] != 0) {
            return 0;
        }

        int count = 1;
        land[x][y] = -1;

        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) {
                    continue;
                }
                count += newDfs(land, x + dx, y + dy);
            }
        }

        return count;
    }

    public boolean isA(int[][] arr, int x, int y) {
        return x < arr.length && x >= 0 && y >= 0 && y < arr[0].length;
    }

    // 101. 对称二叉树
    public boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return true;
        }

        boolean res = false;
        if (root.left != null && root.right != null) {
            res = isSame(root.left, root.right);
        }

        return res;
    }

    public boolean isSame(TreeNode left, TreeNode right) {
        boolean leftVal = false, rightVal = false;

        if (left.val == right.val) {
            if (left.left != null && right.left != null) {
                leftVal = isSame(left.left, right.left);
            } else if (left.left == null && right.left == null) {
                leftVal = true;
            }

            if (left.right != null && right.right != null) {
                rightVal = isSame(left.right, right.right);
            } else if (left.right == null && right.right == null) {
                leftVal = true;
            }
        }
        System.out.println("left = " + leftVal + "  right = " + rightVal);
        return leftVal && rightVal;
    }

    // 226. 翻转二叉树
    public TreeNode invertTree(TreeNode root) {
        if (root == null) {
            return null;
        }
        // 将二叉树中的节点逐层放入队列中，再迭代处理队列中的元素
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            // 每次都从队列中拿一个节点，并交换这个节点的左右子树
            TreeNode tmp = queue.poll();
            // 这一步交换了左右子树
            TreeNode left = tmp.left;
            tmp.left = tmp.right;
            tmp.right = left;

            // 如果当前节点的左子树不为空，则放入队列等待后续处理
            if (tmp.left != null) {
                queue.add(tmp.left);
            }

            // 如果当前节点的右子树不为空，则放入队列等待后续处理
            if (tmp.right != null) {
                queue.add(tmp.right);
            }
        }

        //返回处理完的根节点
        return root;
    }

    // 1262. 可被三整除的最大和
    public int maxSumDivThree(int[] nums) {
        return 0;
    }

    // 1254. 统计封闭岛屿的数目
    public int closedIsland(int[][] grid) {
        int res = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 0 && dfs(grid, i, j)) {
                    res++;
                }
            }
        }

        return res;
    }

    public boolean dfs(int[][] grid, int x, int y) {
        // 在这里，如果超过边界，说明此次遍历已经越界，说明存在与边缘接触的0，一定不是被包围的
        if (!inArea(grid, x, y)) {
            return false;
        }

        // 不等于0，不用管
        if (grid[x][y] != 0) {
            return true;
        }
        grid[x][y] = 2;

        // 这里就是一直深度遍历，如果每一个都是true，说明没有与边界接触的，一定是被包围的
        boolean a1 = dfs(grid, x + 1, y);
        boolean a2 = dfs(grid, x, y + 1);
        boolean a3 = dfs(grid, x - 1, y);
        boolean a4 = dfs(grid, x, y - 1);

        return a1 && a2 && a3 && a4;
    }

    boolean notSide(int[][] grid, int x, int y) {
        return x != 0 && y != 0 && y!= grid[0].length && x != grid.length;
    }

    boolean inArea(int[][] grid, int r, int c) {
        return 0 <= r && r < grid.length && 0 <= c && c < grid[0].length;
    }

    // 637. 二叉树的层平均值
    public List<Double> averageOfLevels(TreeNode root) {
        List<Double> list = new ArrayList<>();
        if (root == null) {
            return list;
        }

        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            double temp = 0.0;
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                temp += node.val;
                if (node.left != null) {
                    queue.add(node.left);
                }

                if (node.right != null) {
                    queue.add(node.right);
                }
            }
            list.add(temp / size);
        }

        return list;
    }

    // 2481. 分割圆的最少切割次数
    public int numberOfCuts(int n) {
        if (n == 1) {
            return 0;
        }

        if (n % 2 == 0) {
            return n / 2;
        }

        return n;
    }

    // 200. 岛屿数量
    public int numIslands(char[][] grid) {
        int res = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == '1') {
                    dfs(grid, i, j);
                    res++;
                }
            }
        }

        return res;
    }

    void dfs(char[][] grid, int r, int c) {
        // 判断 base case
        if (!inArea(grid, r, c)) {
            return;
        }
        // 如果这个格子不是岛屿，直接返回
        if (grid[r][c] != '1') {
            return;
        }
        grid[r][c] = '2'; // 将格子标记为「已遍历过」

        // 访问上、下、左、右四个相邻结点
        dfs(grid, r - 1, c);
        dfs(grid, r + 1, c);
        dfs(grid, r, c - 1);
        dfs(grid, r, c + 1);
    }

    // 判断坐标 (r, c) 是否在网格中
    boolean inArea(char[][] grid, int r, int c) {
        return 0 <= r && r < grid.length && 0 <= c && c < grid[0].length;
    }

    // 1177. 构建回文串检测
    public List<Boolean> canMakePaliQueries(String s, int[][] queries) {
        int[] arr = new int[s.length() + 1];
        arr[0] = 0;
        Set<Character> set = new HashSet<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (set.contains(c)) {
                arr[i + 1] = arr[i];
            } else {
                arr[i + 1] = arr[i] + 1;
                set.add(c);
            }
        }
//        [0, 1, 2, 3, 4, 5, 6, 7, 8, 8, 8, 9]
        System.out.println(Arrays.toString(arr));
        List<Boolean> res = new ArrayList<>();
        for (int i = 0; i < queries.length; i++) {
            int length = queries[i][1] - queries[i][0] + 1;
            if (length % 2 == 0) {
                if ((arr[queries[i][1] + 1] - arr[queries[i][0]]) / 2 <= queries[i][2]) {
                    res.add(true);
                } else {
                    res.add(false);
                }
            } else {
                if ((arr[queries[i][1] + 1] - arr[queries[i][0]] - 1) / 2 <= queries[i][2]) {
                    res.add(true);
                } else {
                    res.add(false);
                }
            }
        }

        return res;
    }

    // 1375. 二进制字符串前缀一致的次数
    public int numTimesAllBlue(int[] flips) {
        int n = flips.length;
        int ans = 0, right = 0;
        for (int i = 0; i < n; ++i) {
            right = Math.max(right, flips[i]);
            if (right == i + 1) {
                ++ans;
            }
        }
        return ans;
    }

    // 2475. 数组中不等三元组的数目
    public int unequalTriplets(int[] nums) {
        int res = 0;
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                for (int k = j + 1; k < nums.length; k++) {
                    if (nums[i] != nums[j] && nums[i] != nums[k] && nums[k] != nums[j]) {
                        res++;
                    }
                }
            }
        }

        return res;
    }

    // 1171. 从链表中删去总和值为零的连续节点
    public ListNode removeZeroSumSublists(ListNode head) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        Map<Integer, ListNode> seen = new HashMap<>();
        int prefix = 0;
        for (ListNode node = dummy; node != null; node = node.next) {
            prefix += node.val;
            // 前缀和，以及当前节点的值
            seen.put(prefix, node);
        }

        prefix = 0;
        for (ListNode node = dummy; node != null; node = node.next) {
            prefix += node.val;
            // 这里修改了节点的后继节点，会影响dummy的结构
            node.next = seen.get(prefix).next;
        }

        return dummy.next;
    }

    // 1170. 比较字符串最小字母出现频次
    public int[] numSmallerByFrequency(String[] queries, String[] words) {
        int[] count = new int[12];
        // 记录个数
        for (String s : words) {
            count[getCount(s)]++;
        }
        // 倒置的前缀和，统计个数和
        // 利用数组这样计算可以把中间缺失的也填充为上一个存在的数字，我自己用的map，没法填充
        for (int i = 9; i >= 1; i--) {
            count[i] += count[i + 1];
        }

        int[] res = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            String s = queries[i];
            res[i] = count[getCount(s) + 1];
        }
        return res;
//        int[] res = new int[queries.length];
//
//        Map<Integer, Integer> map = new HashMap<>();
//        for (int i = 0; i < queries.length; i++) {
//            map.put(i, getCount(queries[i]));
//        }
//
//        List<Integer> list = new ArrayList<>();
//        for (String word : words) {
//            int temp = getCount(word);
//            list.add(temp);
//        }
//        Collections.sort(list);
//
//        Map<Integer, Integer> word = new HashMap<>();
//        int[] arr = new int[list.size()];
//        int index = 1;
//        arr[0] = 1;
//        word.put(list.get(list.size() - 1), 1);
//        for (int i = list.size() - 2; i >= 0; i--) {
//            arr[index] = arr[index - 1] + 1;
//            word.put(list.get(i), arr[index]);
//            index++;
//        }
//
//        return res;
    }

    public int getCount(String str) {
        int temp = 0;
        char c = 'z';
        for (int j = 0; j < str.length(); j++) {
            char q = str.charAt(j);
            if (q == c) {
                temp++;
            } else if (q < c) {
                c = q;
                temp = 1;
            }
        }

        return temp;
    }

    // 129. 求根节点到叶节点数字之和
    public int sumNumbers(TreeNode root) {
        List<String> list = new ArrayList<>();
        StringBuilder str = new StringBuilder();
        dfs(root, str, list);

        int res = 0;
        for (String s : list) {
            res += Integer.parseInt(s);
        }

        return res;
    }

    public void dfs(TreeNode root, StringBuilder last, List<String> res) {
        if (root == null) {
            return;
        }

        StringBuilder temp = new StringBuilder(last);
        temp.append(root.val);
        if (root.left == null && root.right == null) {
            res.add(temp.toString());
        }

        dfs(root.left, temp, res);
        dfs(root.right, temp, res);
    }

    // 222. 完全二叉树的节点个数
    public int countNodes(TreeNode root) {
        int res = 0;
        Queue<TreeNode> queue = new ArrayDeque<>();
        if (root != null) {
            queue.offer(root);
        }

        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            res++;
            if (node.left != null) {
                queue.offer(node.left);
            }

            if (node.right != null) {
                queue.offer(node.right);
            }
        }

        return res;
    }

    // 2611. 老鼠和奶酪
    public int miceAndCheese(int[] reward1, int[] reward2, int k) {
//        PriorityQueue<Integer> first = new PriorityQueue<>();
//        PriorityQueue<Integer> second = new PriorityQueue<>();
//        int res = 0;
//        for (int i = 0; i < reward1.length; i++) {
//            if (reward1[i] > reward2[i]) {
//                if (first.size() < k) {
//                    first.add(reward1[i]);
//                    res += reward1[i];
//                } else {
//
//                }
//            }
//        }
        int ans = 0;
        int n = reward1.length;
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int i = 0; i < n; i++) {
            ans += reward2[i];
            pq.offer(reward1[i] - reward2[i]);
            if (pq.size() > k) {
                pq.poll();
            }
        }
        while (!pq.isEmpty()) {
            ans += pq.poll();
        }
        return ans;
    }

    // 2352. 相等行列对
    public int equalPairs(int[][] grid) {
        Map<String, Integer> map = new HashMap<>();
        int res = 0;
        for (int[] ints : grid) {
            StringBuilder str = new StringBuilder();
            for (int anInt : ints) {
                str.append(anInt).append("-");
            }
            map.merge(str.toString(), 1, Integer::sum);
        }

        for (int i = 0; i < grid[0].length; i++) {
            StringBuilder str = new StringBuilder();
            for (int j = 0; j < grid.length; j++) {
                str.append(grid[j][i]).append("-");
            }

            if (map.get(str.toString()) != null) {
                res += map.get(str.toString());
            }
        }

        return res;
    }

    // 2460. 对数组执行操作
    public int[] applyOperations(int[] nums) {
        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i] == nums[i + 1]) {
                nums[i] *= 2;
                nums[i + 1] = 0;
            }
        }

        int index = 0;
        int[] res = new int[nums.length];
        for (int j : nums) {
            if (j != 0) {
                res[index++] = j;
            }
        }

        for (int i = index; i < res.length; i++) {
            res[i] = 0;
        }

        return res;
    }

    // 2465. 不同的平均值数目
    public int distinctAverages(int[] nums) {
        Arrays.sort(nums);
        int left = 0, right = nums.length - 1;
        Set<Double> map = new HashSet<>();
        while (left < right) {
            double val = (nums[left++] + nums[right--]) / 2.0;
            map.add(val);
        }

        return map.size();
    }

    // 1156. 单字符重复子串的最大长度
    public int maxRepOpt1(String text) {
//        int res = 1;
//
//        Map<Character, List<Integer>> map = new HashMap<>();
//        int[] arr = new int[text.length()];
//        arr[0] = 1;
//        for (int i = 1; i < text.length(); i++) {
//            char a = text.charAt(i);
//            char b = text.charAt(i - 1);
//            if (a == b) {
//                arr[i] = arr[i - 1] + 1;
//            } else {
//                if (map.get(b) == null) {
//                    List<Integer> list = new ArrayList<>();
//                    list.add(i - 1);
//                    map.put(b, list);
//                } else {
//                    map.get(b).add(i - 1);
//                }
//                arr[i] = 1;
//            }
//        }
//
//        System.out.println(Arrays.toString(arr));
//        for (Map.Entry<Character, List<Integer>> entry : map.entrySet()) {
//            System.out.println(entry.getKey() + " " + entry.getValue().toString());
//        }
//
//        return res;
        Map<Character, Integer> count = new HashMap<Character, Integer>();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            count.put(c, count.getOrDefault(c, 0) + 1);
        }

        int res = 0;
        for (int i = 0; i < text.length(); ) {
            // step1: 找出当前连续的一段 [i, j)
            int j = i;
            while (j < text.length() && text.charAt(j) == text.charAt(i)) {
                j++;
            }
            int curCnt = j - i;

            // step2: 如果这一段长度小于该字符出现的总数，并且前面或后面有空位，则使用 curCnt + 1 更新答案
            // (j < text.length() || i > 0)判断不是第一个也不是最后一个，说明一定可以换一个位置
            if (curCnt < count.getOrDefault(text.charAt(i), 0) && (j < text.length() || i > 0)) {
                res = Math.max(res, curCnt + 1);
            }

            // step3: 找到这一段后面与之相隔一个不同字符的另一段 [j + 1, k)，如果不存在则 k = j + 1
            // 因为第一次结束循环时，当前的j指向的字符与i指向的字符一定不同，所以k = j + 1，然后往后遍历
            // 这里其实就是找j + 1 ~ k这个区间，是否与当前i相同，如果相同，则可以取区间i ~ k，因为之前的j不同，但是只有1个字符，可以通过替换变为相同
            int k = j + 1;
            while (k < text.length() && text.charAt(k) == text.charAt(i)) {
                k++;
            }
            res = Math.max(res, Math.min(k - i, count.getOrDefault(text.charAt(i), 0)));
            i = j;
        }

        return res;
    }
}
