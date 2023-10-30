package com.example.springboot_vue.leetcode;

import java.util.*;

public class LeetCodeMain11 {

    public static void main(String[] args) {
        long a = 2;
        int b = 3;
    }

    // LCR 077. 排序链表(采用归并排序)
    public ListNode sortListByMerge(ListNode head) {
        return null;
    }

    // 31. 下一个排列
    public void nextPermutation(int[] nums) {

    }

    // 275. H 指数 II
    public int hIndex2(int[] citations) {
        int total = citations.length;
        int left = 0, right = total - 1;
        // n−mid 篇论文被引用了至少citations[mid]
        while (left <= right) {
            int mid = (left + right) >> 1;
            // int mid = left + (right - left) / 2;
            if (citations[mid] >= total - mid) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return total - left;
    }
//    public int hIndex2(int[] citations) {
//        int total = citations.length;
//        int left = 0, right = total - 1, max = -1;
//        // n−mid 篇论文被引用了至少citations[mid]
//        while (left < right) {
//            int mid = (left + right) >> 1;
//            if (citations[mid] > total || citations[mid] > total - mid) {
//                right = mid - 1;
//            } else {
//                max = Math.max(max, Math.min(mid + 1, citations[mid]));
//                left = mid + 1;
//            }
//        }
//
//        return total - left;
//    }

    // 556. 下一个更大元素 III
    public int nextGreaterElement(int n) {
        char[] nums = Integer.toString(n).toCharArray();
        int i = nums.length - 2;
        // 这里，从倒数第二个和倒数第一个元素进行比较，找到第一个满足 i + 1 > i的元素
        // 这里就是找到第一个不是正序的   比如说54321，他就会一直执行，直到i = -1
        // 但是12345,第一次就会结束，i = 3
        while (i >= 0 && nums[i] >= nums[i + 1]) {
            i--;
        }
        if (i < 0) {
            return -1;
        }

        int j = nums.length - 1;
        // 这里是从后往前找到第一个比num[i]大的元素
        while (j >= 0 && nums[i] >= nums[j]) {
            j--;
        }
        swap(nums, i, j);
        // 这一步，交换从i + 1 到数组结尾的所有元素
        reverse(nums, i + 1);
        long ans = Long.parseLong(new String(nums));
        return ans > Integer.MAX_VALUE ? -1 : (int) ans;
    }

    public void reverse(char[] nums, int begin) {
        int i = begin, j = nums.length - 1;
        while (i < j) {
            swap(nums, i, j);
            i++;
            j--;
        }
    }

    public void swap(char[] nums, int i, int j) {
        char temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    // 556. 下一个更大元素 III
//    public int nextGreaterElement(int n) {
//        List<Integer> list = new ArrayList<>();
//        int temp = n;
//        while (temp > 0) {
//            list.add(0, temp % 10);
//            temp /= 10;
//        }
//
//        int mark = 0;
//        for (int i = list.size() - 2; i >= 0; i--) {
//            for (int j = i + 1; j < list.size(); j++) {
//                if (list.get(j) < list.get(i)) {
//                    mark = 1;
//                    temp = list.get(j);
//                    list.set(j, list.get(i));
//                    list.set(i, temp);
//                    break;
//                }
//            }
//
//            if (mark == 1) {
//                break;
//            }
//        }
//
//        if (mark == 0) {
//            return -1;
//        }
//
//        long res = 0;
//        for (int i = 0; i < list.size(); i++) {
//            res = res * 10 + list.get(i);
//        }
//
//        if (res > Integer.MAX_VALUE) {
//            return -1;
//        }
//        return (int) res;
//    }

    // 279. 完全平方数
    public int numSquares(int n) {
        // 用f[i]表示最少需要多少个数字来表示i
        int[] f = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            int minn = Integer.MAX_VALUE;
            // 这里是遍历从1到根号i的所有值，
            for (int j = 1; j * j <= i; j++) {
                // 这一步，i - j * j 有点类似于背包问题
                // i的最小值，一次考虑去除j之后的最小值
                // 比如j = 1和j = 2，i - 1就是不考虑加入1
                // i - 4就是不考虑加入4
                minn = Math.min(minn, f[i - j * j]);
            }
            f[i] = minn + 1;
        }

        return f[n];
    public void dfs(List<Integer> list, int index) {
        int mark = 0;
        for (int i = index; i >= 0; i--) {
            for (int j = i + 1; j < list.size(); j++) {
                if (list.get(j) < list.get(i)) {
                    mark = 1;
                    int temp = list.get(j);
                    list.set(j, list.get(i));
                    list.set(i, temp);
                }
            }

            if (mark == 1) {
                break;
            }
        }
    }

    public void swap(List<Integer> list, int i, int j) {
        int temp = list.get(j);
        list.set(j, list.get(i));
        list.set(i, temp);
    }

    public long getResult(List<Integer> list) {
        long res = 0;
        for (int i = 0; i < list.size(); i++) {
            res = res * 10 + list.get(i);
        }
        return res;
    }

    // 274. H 指数
    public int hIndex(int[] citations) {
        int n = citations.length, tot = 0;
        int[] counter = new int[n + 1];
        for (int citation : citations) {
            // 这里是关键，因为指数h无法超过发表的论文总数n，所以引用次数大于n的就直接按照n来计算
            // 就是这里，导致无法算出来
            if (citation >= n) {
                counter[n]++;
            } else {
                counter[citation]++;
            }
        }
        for (int i = n; i >= 0; i--) {
            tot += counter[i];
            if (tot >= i) {
                return i;
            }
        }

        return 0;
//        if (citations.length == 1) {
//            return citations[0] == 0 ? 0 : 1;
//        }
//        int[] arr = new int[1001];
//        for (int citation : citations) {
//            arr[citation]++;
//        }
//
//        int max = Integer.MIN_VALUE, count = 0;
//        // 这里的i是对应值不为0的引用次数
//        for (int i = 0; i < arr.length; i++) {
//            if (arr[i] != 0) {
//                if (citations.length - count >= i) {
//                    max = Math.max(max, i);
//                } else if (arr[i] >= count) {
//                    max = Math.max(count, max);
//                }
//                // 统计到目前位置所有论文被引用了多少次
//                count += arr[i];
//            }
//        }
//        if (max == Integer.MIN_VALUE) {
//            return citations.length;
//        }
//        return max;
    }

    // 25. K 个一组翻转链表
    public ListNode reverseKGroup(ListNode head, int k) {
        if (k == 1) {
            return head;
        }
        int countNode = 0;
        ListNode tempNode = head;
        while (tempNode != null) {
            countNode++;
            tempNode = tempNode.next;
        }

        ListNode temp = new ListNode();
        Map<Integer, ListNode> map = new HashMap<>();
        map.put(0, temp);
        int count = 0;
        while (head != null) {
            if (countNode < k) {
                map.get(count).next = head;
                break;
            } else {
                countNode -= k;
            }

            ListNode tempHead = new ListNode();
            ListNode tail = new ListNode(head.val);
            tempHead.next = tail;
            head = head.next;
            for (int i = 1; i < k; i++) {
                if (head != null) {
                    ListNode node = new ListNode(head.val);
                    node.next = tempHead.next;
                    tempHead.next = node;
                    head = head.next;
                } else {
                    break;
                }
            }

            map.get(count).next = tempHead.next;
            count++;
            map.put(count, tail);
        }

        return temp.next;
    }

    // 279. 完全平方数
    public int numSquares(int n) {
        // 用f[i]表示最少需要多少个数字来表示i
        int[] f = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            int minn = Integer.MAX_VALUE;
            // 这里是遍历从1到根号i的所有值，
            for (int j = 1; j * j <= i; j++) {
                // 这一步，i - j * j 有点类似于背包问题
                // i的最小值，一次考虑去除j之后的最小值
                // 比如j = 1和j = 2，i - 1就是不考虑加入1
                // i - 4就是不考虑加入4
                minn = Math.min(minn, f[i - j * j]);
            }
            f[i] = minn + 1;
        }

        return f[n];
    }

    // 2558. 从数量最多的堆取走礼物
    public long pickGifts(int[] gifts, int k) {
        PriorityQueue<Integer> queue = new PriorityQueue<>((a, b) -> b - a);
        for (int gift : gifts) {
            queue.offer(gift);
        }
        if (queue.size() == 0) {
            return 0;
        }

        for (int i = 0; i < k; i++) {
            queue.offer((int) Math.floor(Math.sqrt(queue.poll())));
        }

        long res = 0;
        while (!queue.isEmpty()) {
            res += queue.poll();
        }

        return res;
    }

    // 1465. 切割后面积最大的蛋糕
    public int maxArea(int h, int w, int[] horizontalCuts, int[] verticalCuts) {
        Arrays.sort(horizontalCuts);
        Arrays.sort(verticalCuts);
        int lastIndex = 0, max = Integer.MIN_VALUE, temp, MOD = 1000000007;
        // 找到水平切的最大值
        for (int horizontalCut : horizontalCuts) {
            temp = horizontalCut - lastIndex;
            lastIndex = horizontalCut;
            max = Math.max(max, temp);
        }
        max = Math.max(h - horizontalCuts[horizontalCuts.length - 1], max);

        long res = Integer.MIN_VALUE;
        lastIndex = 0;
        for (int verticalCut : verticalCuts) {
            res = Math.max(res, (long) (verticalCut - lastIndex) * max);
            lastIndex = verticalCut;
        }
        res = Math.max(res, (long) (w - verticalCuts[verticalCuts.length - 1]) * max);

        return (int) (res % MOD);
    }

    // 725. 分隔链表
    public ListNode[] splitListToParts(ListNode head, int k) {
        ListNode temp = head;
        int count = 0;
        while (temp != null) {
            count++;
            temp = temp.next;
        }

        ListNode[] res = new ListNode[k];
        if (count <= k) {
            for (int i = 0; i < k; i++) {
                if (head != null) {
                    ListNode node = new ListNode(head.val);
                    head = head.next;
                    res[i] = node;
                } else {
                    res[i] = null;
                }
            }
        } else {
            int result = count % k, single = count / k;
            for (int i = 0; i < k; i++) {
                int tempSingle = single;
                if (result > 0) {
                    tempSingle++;
                    result--;
                }
                ListNode resNode = new ListNode();
                if (head != null) {
                    resNode.val = head.val;
                    head = head.next;
                }
                res[i] = resNode;
                for (int j = 1; j < tempSingle; j++) {
                    ListNode partNode = null;
                    if (head != null) {
                        partNode = new ListNode(head.val);
                        head = head.next;
                    }
                    resNode.next = partNode;
                    resNode = resNode.next;
                }
            }
        }

        return res;
    }

    // 2520. 统计能整除数字的位数
    public int countDigits(int num) {
        int res = 0;
        int temp = num;
        while (temp > 0) {
            int tempNum = temp % 10;
            if (num % tempNum == 0) {
                res++;
            }

            temp /= 10;
        }

        return res;
    }

    // 2698. 求一个整数的惩罚数
    public int punishmentNumber(int n) {
        int res = 1;
        for (int i = 9; i <= n; i++) {
            String temp = String.valueOf(i * i);
            if (spiltNumber(temp, i * i)) {
                res += i * i;
            }
        }

        return res;
    }

    public boolean spiltNumber(String str, int target) {
        boolean result, temp = false;
        for (int i = 1; i < str.length(); i++) {
            int val = Integer.parseInt(str.substring(0, i));
            String last = str.substring(i);
            target -= val;
            if (target == Integer.parseInt(last)) {
                return true;
            }
            result = spiltNumber(last, target);
            if (result) {
                temp = true;
            }
            target += val;
        }

        return temp;
    }

    // 322. 零钱兑换
    public int coinChange(int[] coins, int amount) {
        Arrays.sort(coins);
        int count = 0;
        for (int i = coins.length - 1; i >= 0; i--) {

        }

        return 0;
    }

    // 160. 相交链表
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        Map<ListNode, String> map = new HashMap<>();
        while (headA != null) {
            map.put(headA, "1");
            headA = headA.next;
        }

        while (headB != null) {
            if (map.get(headB) != null) {
                return headB;
            }

            headB = headB.next;
        }

        return null;
    }

    // 148. 排序链表
    public ListNode sortList(ListNode head) {
        List<Integer> list = new ArrayList<>();
        if (head == null) {
            return null;
        }
        while (head != null) {
            list.add(head.val);
            head = head.next;
        }

        list.sort(Comparator.comparingInt(integer -> integer));
        ListNode resHead = new ListNode(list.get(0));
        ListNode temp = resHead;
        for (int i = 1; i < list.size(); i++) {
            temp.next = new ListNode(list.get(i));
            temp = temp.next;
        }

        return resHead;
    }

    // 1155. 掷骰子等于目标和的方法数
    public int numRollsToTarget(int d, int f, int target) {
        int MOD = 1000000007;
        int[][] dp = new int[31][1001];
        int min = Math.min(f, target);
        for (int i = 1; i <= min; i++) {
            dp[1][i] = 1;
        }

        int targetMax = d * f;
        for (int i = 2; i <= d; i++) {
            for (int j = i; j <= targetMax; j++) {
                for (int k = 1; j - k >= 0 && k <= f; k++) {
                    dp[i][j] = (dp[i][j] + dp[i - 1][j - k]) % MOD;
                }
            }
        }

        return dp[d][target];
    }

    int[][] direction = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    // 994. 腐烂的橘子
    public int orangesRotting(int[][] grid) {
        int res = 0, count = 0;
        Queue<int[]> queue = new ArrayDeque<>();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 2) {
                    queue.offer(new int[]{i, j});
                    count++;
                } else if (grid[i][j] == 1) {
                    count++;
                }
            }
        }

        if (count == 0) {
            return 0;
        }
        int temp = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            temp += size;
            for (int i = 0; i < size; i++) {
                int[] arr = queue.poll();
                for (int j = 0; j < direction.length; j++) {
                    int x = arr[0] + direction[j][0];
                    int y = arr[1] + direction[j][1];
                    if (legal(x, y, grid)) {
                        // 腐蚀橘子
                        if (grid[x][y] == 1) {
                            grid[x][y] = 2;
                            queue.offer(new int[]{x, y});
                        }
                    }
                }
            }
            res++;
        }

        if (temp != count) {
            return -1;
        }

        return res - 1;
    }

    public boolean legal(int x, int y, int[][] board) {
        return x >= 0 && y >= 0 && x < board.length && y < board[0].length;
    }

    // 84. 柱状图中最大的矩形
    public int largestRectangleArea(int[] heights) {
        int n = heights.length;
        int[] left = new int[n];
        int[] right = new int[n];

        // 单调栈存储的是由小到大的
        Deque<Integer> monoStack = new ArrayDeque<>();

        // 左边界和有边界都是指在该范围内，heights[i]的高度是最小的
        // 记录每一个柱子可以到达的最左边界
        for (int i = 0; i < n; ++i) {
            while (!monoStack.isEmpty() && heights[monoStack.peek()] >= heights[i]) {
                monoStack.pop();
            }
            left[i] = (monoStack.isEmpty() ? -1 : monoStack.peek());
            monoStack.push(i);
        }

        // 记录右边界
        monoStack.clear();
        for (int i = n - 1; i >= 0; --i) {
            while (!monoStack.isEmpty() && heights[monoStack.peek()] >= heights[i]) {
                monoStack.pop();
            }
            right[i] = (monoStack.isEmpty() ? n : monoStack.peek());
            monoStack.push(i);
        }

        // 这里是计算最终结果
        int ans = 0;
        for (int i = 0; i < n; ++i) {
            ans = Math.max(ans, (right[i] - left[i] - 1) * heights[i]);
        }
        return ans;
    }

    // 84. 柱状图中最大的矩形，错误写法
//    public int largestRectangleArea(int[] heights) {
//        Stack<Integer> stack = new Stack<>();
//        int max = 0, min = Integer.MAX_VALUE, lastMin = 0;
//        for (int i = 0; i < heights.length; i++) {
//            if (stack.isEmpty()) {
//                stack.push(i);
//            } else {
//                while (!stack.isEmpty() && heights[stack.peek()] < heights[i]) {
//                    int index = stack.pop();
//                    min = Math.min(min, heights[index]);
//                    max = Math.max(max, heights[i]);
//                    max = Math.max(max, min * (i - index + 1));
//                }
//                stack.push(i);
//                lastMin = Math.min(min, heights[i]);
//                min = Integer.MAX_VALUE;
//            }
//        }
//        System.out.println(stack.toString());
//        while (!stack.isEmpty()) {
//            int index = stack.pop();
//            min = Math.max(min, heights[index]);
//            max = Math.max(max, lastMin * (heights.length - index));
//        }
//
//        return max;
//    }

    // 2678. 老人的数目
    public int countSeniors(String[] details) {
        int res = 0;
        for (int i = 0; i < details.length; i++) {
            if (details[i].charAt(11) > '6') {
                res++;
            } else {
                if (details[i].charAt(11) == '6') {
                    if (details[i].charAt(12) != '0') {
                        res++;
                    }
                }
            }
        }
        return res;
    }

    // 1402. 做菜顺序
    public int maxSatisfaction(int[] satisfaction) {
        Arrays.sort(satisfaction);
        int index = -1;
        for (int i = 0; i < satisfaction.length; i++) {
            if (satisfaction[i] >= 0) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            return 0;
        }

        int sum = 0, max = 0, count = 1;
        for (int i = index; i < satisfaction.length; i++) {
            sum += satisfaction[i];
            max += count * satisfaction[i];
            count++;
        }

        int temp = max, tempSum = 0;
        for (int i = index - 1; i >= 0; i--) {
            temp = temp + satisfaction[i] + sum + tempSum;
            max = Math.max(max, temp);
            tempSum += satisfaction[i];
        }

        return max;
    }

    // 2316. 统计无向图中无法互相到达点对数
    int[] arr;
    public long countPairs(int n, int[][] edges) {
        arr = new int[n];
        for (int i = 0; i < n; i++) {
            // arr[i] = i 表示该节点已经是根节点，这是一个设定
            arr[i] = i;
        }

        // 遍历所有元素，将他们加入并查集
        for (int[] edge : edges) {
            union(edge[0], edge[1]);
        }

        List<List<Integer>> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            list.add(new ArrayList<>());
        }

        int temp;
        for (int i = 0; i < n; i++) {
            temp = find(i);
            list.get(temp).add(i);
        }

        List<Integer> result = new ArrayList<>();
        for (List<Integer> integers : list) {
            if (integers.size() != 0) {
                result.add(integers.size());
            }
        }

        int[] houArr = new int[result.size()];
        houArr[result.size() - 1] = result.get(result.size() - 1);
        for (int i = result.size() - 2; i >= 0; i--) {
            houArr[i] = houArr[i + 1] + result.get(i);
        }

        long res = 0;
        for (int i = 1; i < houArr.length; i++) {
            res += (long) result.get(i - 1) * houArr[i];
        }
        return res;
    }

    public void union(int x, int y) {
        int a = find(x);
        int b = find(y);
        arr[b] = a;
    }

    public int find(int x) {
        if (arr[x] == x) {
            return x;
        }

        // 这一步，在递归的过程中赋值，会一直减少并查集的高度
        // 这里要传递arr[x]，即找x的父节点
        return arr[x] = find(arr[x]);
    }


    boolean result = false;
    // 79. 单词搜索
    public boolean exist(char[][] board, String word) {
        int h = board.length, w = board[0].length;
        boolean[][] visited = new boolean[h][w];
        // 这一块思路和我一样，遍历board，以每个点为起点进行一次深度遍历，看是否可以组合成对应单词
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                boolean flag = check(board, visited, i, j, word, 0);
                if (flag) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean check(char[][] board, boolean[][] visited, int i, int j, String s, int k) {
        // 这个k记录str对应的下标，如果s的对应下标k与当前需要遍历的字符i,j不对应，则直接返回
        if (board[i][j] != s.charAt(k)) {
            return false;
        } else if (k == s.length() - 1) {
            return true;
        }
        visited[i][j] = true;
        int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        boolean result = false;
        for (int[] dir : directions) {
            int newi = i + dir[0], newj = j + dir[1];
            if (newi >= 0 && newi < board.length && newj >= 0 && newj < board[0].length) {
                // 这里，每次判断下一个位置是否被访问过
                // 我自己写的存在反复横跳导致无限递归的问题，主要是回溯写的有问题，所以还没出现，但是写对后会出现这个问题
                if (!visited[newi][newj]) {
                    // 没有访问过，则递归的进行访问
                    boolean flag = check(board, visited, newi, newj, s, k + 1);
                    if (flag) {
                        result = true;
                        break;
                    }
                }
            }
        }
        // 这里，每一次递归的访问结束后，对应的位置会被改为false
        visited[i][j] = false;
        return result;
    }

    /**
     * 79. 单词搜索 错误代码
     */
//    public boolean exist(char[][] board, String word) {
//        StringBuilder stringBuilder;
//        for (int i = 0; i < board.length; i++) {
//            for (int j = 0; j < board[0].length; j++) {
//                stringBuilder = new StringBuilder();
//                dfsExist(board, word, stringBuilder, i, j);
//            }
//        }
//
//        return result;
//    }
//
//    public void dfsExist(char[][] board, String word, StringBuilder res, int x, int y) {
//        for (int i = 0; i < direction.length; i++) {
//            if (legal(x, y, board)) {
//                res.append(board[x][y]);
//                System.out.println("res = " + res);
//                if (res.length() > word.length()) {
//                    return;
//                }
//                if (res.toString().equals(word)) {
//                    result = true;
//                    return;
//                }
//                dfsExist(board, word, res, x + direction[i][0], y + direction[i][1]);
//                res.deleteCharAt(res.length() - 1);
//            }
//        }
//    }

    // 2525. 根据规则将箱子分类
    public String categorizeBox(int length, int width, int height, int mass) {
        int Heavy = 0, Bulky = 0, temp = 10000;
        long res = (long) length * width * height;
        if (res >= Math.pow(10, 9) || length >= temp || width >= temp || height >= temp) {
            Bulky = 1;
        }

        if (mass >= 100) {
            Heavy = 1;
        }

        if (Heavy == 1 && Bulky == 1) {
            return "Both";
        } else if (Heavy == 0 && Bulky == 0) {
            return "Neither";
        } else {
            if (Heavy == 1) {
                return "Heavy";
            } else {
                return "Bulky";
            }
        }
    }

    // 78. 子集
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        res.add(new ArrayList<>());
        LinkedList<Integer> list = new LinkedList<>();
        traceBack(nums, 0, res, list);
        return res;
    }

    public void traceBack(int[] nums, int index, List<List<Integer>> res, LinkedList<Integer> temp) {
        for (int i = index; i < nums.length; i++) {
            if (!temp.contains(nums[i])) {
                temp.add(nums[i]);
                res.add(new ArrayList<>(temp));
                traceBack(nums, i, res, temp);
                temp.removeLast();
            }
        }
    }

    // 437. 路径总和 III
    public int pathSum(TreeNode root, int targetSum) {
        if (root == null) {
            return 0;
        }
        int res;

        res = dfsPathSum(root, targetSum);
        res += pathSum(root.left, targetSum);
        res += pathSum(root.right, targetSum);

        return res;
    }

    public int dfsPathSum(TreeNode root, long targetSum) {
        if (root == null) {
            return 0;
        }

        int res = 0;

        int val = root.val;
        if (val == targetSum) {
            res++;
        }

        res += dfsPathSum(root.left, targetSum - val);
        res += dfsPathSum(root.right, targetSum - val);
        return res;
    }

    // 1004. 最大连续1的个数 III
    public int longestOnes(int[] nums, int k) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) {
                list.add(i);
            }
        }
        if (list.size() == 0) {
            return nums.length;
        }

        for (int i = list.get(0); i < list.size() - k; i++) {
            for (int j = i; j < i + k; j++) {

            }
        }

        return 0;
    }

    // 1726. 同积元组
    public int tupleSameProduct(int[] nums) {
        int res = 0;
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (map.get(nums[i] * nums[j]) == null) {
                    map.put(nums[i] * nums[j], 1);
                } else {
                    map.put(nums[i] * nums[j], map.get(nums[i] * nums[j]) + 1);
                }
            }
        }

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            res += entry.getValue() * (entry.getValue() - 1) * 4;
        }

        return res;
    }

    // 61. 旋转链表
    public ListNode rotateRight(ListNode head, int k) {
        if (head == null) {
            return null;
        }

        int count = 0;
        ListNode tempNode = head;
        while (tempNode != null) {
            count++;
            tempNode = tempNode.next;
        }

        k = k % count;

        ListNode fast = head;
        ListNode slow = head;
        int temp = 0;
        while (temp < k) {
            fast = fast.next;
            temp++;
            if (fast == null) {
                fast = head;
            }
        }

        if (fast == slow) {
            return head;
        }

        while (fast.next != null) {
            fast = fast.next;
            slow = slow.next;
        }

        fast.next = head;
        head = slow.next;
        slow.next = null;
        return head;
    }

    // 103. 二叉树的锯齿形层序遍历
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) {
            return res;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        int direction = 1;
        while (!queue.isEmpty()) {
            int size = queue.size();
            LinkedList<Integer> temp = new LinkedList<>();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }

                if (direction == 1) {
                    temp.add(node.val);
                } else {
                    temp.addFirst(node.val);
                }
            }

            res.add(temp);
            direction = 1 - direction;
        }

        return res;
    }

    // 2530. 执行 K 次操作后的最大分数
    public long maxKelements(int[] nums, int k) {
        PriorityQueue<Integer> queue = new PriorityQueue<>((a, b) -> b - a);
        for (int num : nums) {
            queue.offer(num);
        }

        long res = 0;
        for (int i = 0; i < k; i++) {
            int temp = queue.poll();
            res += temp;
            queue.offer((int) Math.ceil(temp / 3.0));
        }

        return res;
    }

}
