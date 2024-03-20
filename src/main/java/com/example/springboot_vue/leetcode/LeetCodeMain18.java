package com.example.springboot_vue.leetcode;

import java.util.*;

public class LeetCodeMain18 {

    public static void main(String[] args) {

    }

    // 72. 编辑距离，手写版
    public int minDistanceMy(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();

        if (m * n == 0) {
            return m + n;
        }

        // dp[i][j]代表了word1的前i个字符和word2的前j个字符的最短编辑距离
        int[][] dp = new int[m + 1][n + 1];
        // 当j为0时，i是多少，他们的编辑距离就是多少
        for (int i = 0; i < m + 1; i++) {
            dp[i][0] = i;
        }
        for (int i = 0; i < n + 1; i++) {
            dp[0][i] = i;
        }

        for (int i = 1; i < m + 1; i++) {
            for (int j = 1; j < n + 1; j++) {
                int first = dp[i - 1][j] + 1;
                int second = dp[i][j - 1] + 1;
                int third = dp[i - 1][j - 1];
                // 这里为什么是i - 1，j - 1
                if (word1.charAt(i - 1) != word2.charAt(j - 1)) {
                    third++;
                }

                dp[i][j] = Math.min(Math.min(first, second), third);
            }
        }

        return dp[m][n];
    }

    // 72. 编辑距离
    public int minDistance(String word1, String word2) {
        int n = word1.length();
        int m = word2.length();

        // 有一个字符串为空串
        if (n * m == 0) {
            return n + m;
        }

        // DP 数组
        int[][] D = new int[n + 1][m + 1];

        // 边界状态初始化
        for (int i = 0; i < n + 1; i++) {
            D[i][0] = i;
        }
        for (int j = 0; j < m + 1; j++) {
            D[0][j] = j;
        }

        System.out.println(Arrays.deepToString(D));

        // 对于DP的含义，dp[i][j]就代表了word1的前i个字符和word2的前j个字符的编辑距离
        // 这里dp[i][j] = Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1, dp[i - 1][j - 1] or dp[i - 1][j - 1] + 1)
        // 原因：我们得到了字符1前i个字符与字符2前j个字符的最短编辑距离后，那么i，j的值只需要将i - 1的字符加一个字符，就可以达到编辑距离
        // 而i，j - 1与其同理
        // 至于i - 1，j - 1，如果第i和j个字符本身就相同，那么就等于dp[i - 1][j - 1]，如果不相同，只需要将字符第i个字符变为第j个就可以了
        // 计算所有 DP 值
        for (int i = 1; i < n + 1; i++) {
            for (int j = 1; j < m + 1; j++) {
                int left = D[i - 1][j] + 1;
                int down = D[i][j - 1] + 1;
                int left_down = D[i - 1][j - 1];
                if (word1.charAt(i - 1) != word2.charAt(j - 1)) {
                    left_down += 1;
                }
                D[i][j] = Math.min(left, Math.min(down, left_down));
            }
        }

        return D[n][m];
    }

    // 2. 两数相加
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode res = new ListNode();
        ListNode tempNode = res;
        int temp = 0;
        while (l1 != null && l2 != null) {
            int first = l1.val, second = l2.val;
            ListNode node = new ListNode();
            node.val = (first + second + temp) % 10;
            temp = (first + second + temp) / 10;
            l1 = l1.next;
            l2 = l2.next;
            tempNode.next = node;
            tempNode = node;
        }

        while (l1 != null) {
            ListNode node = new ListNode();
            node.val = (l1.val + temp) % 10;
            temp = (l1.val + temp) / 10;
            l1 = l1.next;
            tempNode.next = node;
            tempNode = node;
        }

        while (l2 != null) {
            ListNode node = new ListNode();
            node.val = (l2.val + temp) % 10;
            temp = (l2.val + temp) / 10;
            l2 = l2.next;
            tempNode.next = node;
            tempNode = node;
        }

        if (temp != 0) {
            tempNode.next = new ListNode(1);
        }
        return res.next;
    }

    // 24. 两两交换链表中的节点
    public ListNode swapPairs(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode pre = new ListNode();
        pre.next = head;
        ListNode res = pre;
        ListNode slow = head, fast = head.next;
        while (fast != null ) {
            slow.next = fast.next;
            pre.next = fast;
            fast.next = slow;
            pre = slow;
            fast = slow.next;
            if (fast != null) {
                fast = fast.next;
            }
            slow = slow.next;
        }

        return res.next;
    }

    // 19. 删除链表的倒数第 N 个结点
    public ListNode removeNthFromEnd(ListNode head, int n) {
        if (head == null) {
            return null;
        }

        ListNode fast = head, slow, pre = new ListNode();
        pre.next = head;
        while (n > 0 && fast != null) {
            n--;
            fast = fast.next;
        }
        slow = pre;
        while (fast != null) {
            slow = slow.next;
            fast = fast.next;
        }

        slow.next = slow.next.next;

        return pre.next;
    }

    List<String> letterCombinationsList = new ArrayList<>();
    // 17. 电话号码的字母组合
    public List<String> letterCombinations(String digits) {
        if (digits.equals("")) {
            return letterCombinationsList;
        }
        Map<Character, Character[]> map = new HashMap<>();
        map.put('2', new Character[]{'a', 'b', 'c'});
        map.put('3', new Character[]{'d', 'e', 'f'});
        map.put('4', new Character[]{'g', 'h', 'i'});
        map.put('5', new Character[]{'j', 'k', 'l'});
        map.put('6', new Character[]{'m', 'n', 'o'});
        map.put('7', new Character[]{'p', 'q', 'r', 's'});
        map.put('8', new Character[]{'t', 'u', 'v'});
        map.put('9', new Character[]{'w', 'x', 'y', 'z'});

        StringBuilder stringBuilder = new StringBuilder();
        letterCombinationsDfs(map, 0, digits, stringBuilder);

        return letterCombinationsList;
    }

    public void letterCombinationsDfs(Map<Character, Character[]> map, int index, String digits, StringBuilder str) {
        if (str.length() == digits.length()) {
            letterCombinationsList.add(String.valueOf(str));
            return;
        }

        for (int i = index; i < index + 1; i++) {
            Character[] characters = map.get(digits.charAt(i));
            for (int j = 0; j < characters.length; j++) {
                str.append(characters[j]);
                letterCombinationsDfs(map, index + 1, digits, str);
                str.delete(str.length() - 1, str.length());
            }
        }
    }

    // 200. 岛屿数量
    public int numIslands(char[][] grid) {
        int[][] direct = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        boolean[][] get = new boolean[grid.length][grid[0].length];
        for (int i = 0; i < grid.length; i++) {
            Arrays.fill(get[i], false);
        }
        int count = 0;
        Queue<int[]> queue = new ArrayDeque<>();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (get[i][j] || grid[i][j] == '0') {
                    continue;
                }
                count++;
                queue.add(new int[]{i, j});
                get[i][j] = true;
                while (!queue.isEmpty()) {
                    int size = queue.size();
                    for (int k = 0; k < size; k++) {
                        int[] arr = queue.poll();
                        int x = arr[0], y = arr[1];
                        for (int l = 0; l < direct.length; l++) {
                            if (canGet(grid, x + direct[l][0], y + direct[l][1])) {
                                if (grid[x + direct[l][0]][y + direct[l][1]] == '1' && !get[x + direct[l][0]][y + direct[l][1]]) {
                                    queue.offer(new int[]{x + direct[l][0], y + direct[l][1]});
                                    get[x + direct[l][0]][y + direct[l][1]] = true;
                                }
                            }
                        }
                    }
                }
            }
        }

        return count;
    }

    public boolean canGet(char[][] grid, int x, int y) {
        return x < grid.length && x >= 0 && y < grid[0].length && y >= 0;
    }

    // LCR 016. 无重复字符的最长子串
    public int lengthOfLongestSubstring(String s) {
        int left = 0, right = 0, res = 0;
        Set<Character> set = new HashSet<>();
        while (right < s.length()) {
            char c = s.charAt(right++);
            if (set.contains(c)) {
                while (set.contains(c)) {
                    set.remove(s.charAt(left++));
                }
                set.add(c);
            } else {
                set.add(c);
                res = Math.max(res, set.size());
            }
        }

        return res;
    }

    // 179. 最大数
    public String largestNumber(int[] nums) {
        StringBuilder res = new StringBuilder();
        res.append(nums[0]);
        for (int i = 1; i < nums.length; i++) {
            if ((res.toString() + nums[i]).compareTo(nums[i] + res.toString()) > 0) {
                res.append(nums[i]);
            } else {
                res.insert(0, nums[i]);
            }
        }

        return res.toString();
    }

    // 245. 最短单词距离 III
    public int shortestWordDistance(String[] wordsDict, String word1, String word2) {
        int res = Integer.MAX_VALUE, index1 = -1, index2 = -1;
        for (int i = 0; i < wordsDict.length; i++) {
            if (wordsDict[i].equals(word1)) {
                if (word1.equals(word2)) {
                    if (index1 != -1) {
                        res = Math.min(res, i - index1);
                    }
                }
                index1 = i;
                if (index2 != -1) {
                    res = Math.min(res, Math.abs(index2 - index1));
                }
            } else if (wordsDict[i].equals(word2)) {
                index2 = i;
                if (index1 != -1) {
                    res = Math.min(res, Math.abs(index2 - index1));
                }
            }
        }

        return res;
    }

    // 243. 最短单词距离
    public int shortestDistance(String[] wordsDict, String word1, String word2) {
        int res = Integer.MAX_VALUE, index1 = -1, index2 = -1;
        for (int i = 0; i < wordsDict.length; i++) {
            if (wordsDict[i].equals(word1)) {
                index1 = i;
                if (index2 != -1) {
                    res = Math.min(res, Math.abs(index2 - index1));
                }
            }

            if (wordsDict[i].equals(word2)) {
                index2 = i;
                if (index1 != -1) {
                    res = Math.min(res, Math.abs(index2 - index1));
                }
            }
        }

        return res;
    }

    // 189. 轮转数组
    public void rotate(int[] nums, int k) {
        if (k == nums.length) {
            return;
        }

        int count = 0, index = 0, length = nums.length, temp, temp2 = nums[index];
        Set<Integer> set = new HashSet<>();
        set.add(index);
        while (count != length) {
            temp = nums[(index + k) % length];
            nums[(index + k) % length] = temp2;
            index = (index + k) % length;
            if (!set.contains(index)) {
                set.add(index);
            } else {
                while (set.contains(index)) {
                    index = (index + 1) % length;
                    temp = nums[index];
                }
            }
            temp2 = temp;
            count++;
        }
    }

    // rewrite quickSort
    public void quickSort(int[] arr, int left, int right) {
        if (left < right) {
            int i = left, j = right, x = arr[left];

            while (i < j) {

                while (i < j && arr[j] > x) {
                    j--;
                }
                if (i < j) {
                    arr[i++] = arr[j];
                }

                while (i < j && arr[i] < x) {
                    i++;
                }
                if (i < j) {
                    arr[j--] = arr[i];
                }
            }
            arr[i] = x;
            quickSort(arr, left, i - 1);
            quickSort(arr, i + 1, right);
        }
    }

    // 42. 接雨水
    public int trap(int[] height) {
        int ans = 0;
        Deque<Integer> stack = new LinkedList<>();
        int n = height.length;
        for (int i = 0; i < n; ++i) {
            while (!stack.isEmpty() && height[i] > height[stack.peek()]) {
                int top = stack.pop();
                if (stack.isEmpty()) {
                    break;
                }
                int left = stack.peek();
                int currWidth = i - left - 1;
                int currHeight = Math.min(height[left], height[i]) - height[top];
                ans += currWidth * currHeight;
            }
            stack.push(i);
        }
        return ans;
    }

    // 852. 山脉数组的峰顶索引
    public int peakIndexInMountainArray(int[] arr) {
        int left = 0, right = arr.length - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            int tempLeft = Math.max(mid - 1, 0);
            int tempRight = Math.min(mid + 1, arr.length - 1);
            if (arr[mid] > arr[tempLeft] && arr[mid] > arr[tempRight]) {
                return mid;
            } else if (arr[mid] > arr[tempLeft] && arr[mid] < arr[tempRight]) {
                left = mid;
            } else {
                right = mid;
            }
        }

        return 0;
    }

    // 25. K 个一组翻转链表
    public ListNode reverseKGroup(ListNode head, int k) {
        if (k == 1) {
            return head;
        }
        int count = 0;
        ListNode first = head;
        List<ListNode[]> list = new ArrayList<>();
        ListNode[] nodes = new ListNode[2];
        nodes[0] = head;
        list.add(nodes);
        int index = 0;
        while (head != null) {
            count++;
            System.out.println("count = " + count);
            if (count == k) {
                // 下标为0是尾节点，下标为1是头节点
                ListNode tail = head.next;
                list.get(index++)[1] = reserveList(first, tail);
                System.out.println("head.next" + head.next);
                count = 0;
                ListNode[] temp = new ListNode[2];
                temp[0] = head.next;
                list.add(temp);
                first = head.next;
            }
            head = head.next;
        }

        System.out.println(list.size());
        for (int i = 1; i < list.size(); i++) {
            list.get(i - 1)[0].next = list.get(i)[1];
        }

        return list.get(0)[0];
    }

    // 反转链表
    public ListNode reserveList(ListNode head, ListNode tail) {
        if (head == null) {
            return null;
        }

        ListNode pre = null;
        ListNode cur = head;
        ListNode next = head.next;
        while (next != tail) {
            cur.next = pre;
            pre = cur;
            cur = next;
            next = next.next;
        }

        cur.next = pre;
        return cur;
    }

    // 92. 反转链表 II
    public ListNode reverseBetween(ListNode head, int left, int right) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode temp = head;
        ListNode tempFirst = new ListNode(), tempSecond = new ListNode();
        while (temp != null) {
            if (temp.val == left) {
                tempFirst = temp;
            }

            if (temp.val == right) {
                tempSecond = temp;
            }
            temp = temp.next;
        }
        ListNode node = tempSecond.next;
        ListNode res = reserveList(tempFirst, tempSecond);
        tempFirst.next = node;
        return head;
    }

    // 206. 反转链表
    public ListNode reserveList(ListNode head) {
        if (head == null) {
            return null;
        }

        ListNode pre = null;
        ListNode cur = head;
        ListNode next = head.next;
        while (next != null) {
            cur.next = pre;
            pre = cur;
            cur = next;
            next = next.next;
        }

        cur.next = pre;
        return cur;
    }

    // 215. 数组中的第K个最大元素
    public int findKthLargest(int[] nums, int k) {
        int size = 0;
        PriorityQueue<Integer> queue = new PriorityQueue<>(k);
        for (int i = 0; i < nums.length; i++) {
            if (size < k) {
                queue.offer(nums[i]);
                size++;
            } else {
                if (nums[i] > queue.peek()) {
                    queue.poll();
                    queue.offer(nums[i]);
                }
            }
        }
        return queue.peek();
    }

    // 114. 二叉树展开为链表
    public void flatten(TreeNode root) {
        if (root == null) {
            return;
        }
        List<TreeNode> list = new ArrayList<>();
        flattenDfs(root, list);
        for (int i = 1; i < list.size(); i++) {
            root.left = null;
            root.right = list.get(i);
            root = root.right;
        }
    }

    public void flattenDfs(TreeNode root, List<TreeNode> list) {
        if (root == null) {
            return;
        }
        list.add(root);
        flattenDfs(root.left, list);
        flattenDfs(root.right, list);
    }

    // 49. 字母异位词分组
    public List<List<String>> groupAnagrams(String[] strs) {
        List<List<String>> res = new ArrayList<>();
        Map<String, List<String>> map = new HashMap<>();
        for (String str : strs) {
            int[] arr = new int[26];
            for (int j = 0; j < str.length(); j++) {
                char c = str.charAt(j);
                arr[c - 'a']++;
            }
            String temp = Arrays.toString(arr);
            if (map.get(temp) == null) {
                List<String> list = new ArrayList<>();
                map.put(temp, list);
            }
            map.get(temp).add(str);
        }

        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            res.add(entry.getValue());
        }

        return res;
    }

    // 1. 两数之和
    public int[] twoSumEasy(int[] nums, int target) {
        int[] res = new int[2];
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(target - nums[i])) {
                res[0] = i;
                res[1] = map.get(target - nums[i]);
            } else {
                map.put(nums[i], i);
            }
        }

        return res;
    }

    // 167. 两数之和 II - 输入有序数组
    public int[] twoSum(int[] numbers, int target) {
        int[] res = new int[2];
        int left = 0, right = numbers.length - 1;
        while (left < right) {
            if (numbers[left] + numbers[right] == target) {
                res[0] = left + 1;
                res[1] = right + 1;
                break;
            } else if (numbers[left] + numbers[right] > target) {
                right--;
            } else {
                left++;
            }
        }

        return res;
    }

    // 151. 反转字符串中的单词
    public String reverseWords(String s) {
        Stack<String> stack = new Stack<>();
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == ' ') {
                while (i < s.length() && s.charAt(i) == ' ') {
                    i++;
                }
                if (str.length() > 0) {
                    stack.push(str.toString());
                }
                str = new StringBuilder();
                if (i < s.length()) {
                    str.append(s.charAt(i));
                }
            } else {
                str.append(c);
            }
        }
        if (str.length() > 0) {
            stack.push(str.toString());
        }
        str = new StringBuilder();
        while (!stack.isEmpty()) {
            str.append(stack.pop()).append(" ");
        }
        str.delete(str.length() - 1, str.length());
        return str.toString();
    }

    // 238. 除自身以外数组的乘积
    public int[] productExceptSelf(int[] nums) {
        int[] res = new int[nums.length];
        long temp = 1, countZero = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) {
                countZero++;
            } else {
                temp *= nums[i];
            }
        }

        if (countZero > 1) {
            Arrays.fill(res, 0);
            return res;
        }

        for (int i = 0; i < nums.length; i++) {
            if (countZero > 0) {
                if (nums[i] != 0) {
                    res[i] = 0;
                } else {
                    res[i] = (int) temp;
                }
            } else {
                res[i] = (int) (temp / nums[i]);
            }
        }

        return res;
    }

    // 2684. 矩阵中移动的最大次数
    // 这个题，最多从左移动到右这么多次
    public int maxMoves(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        Set<Integer> q = new HashSet<>();

        for (int i = 0; i < m; i++) {
            q.add(i);
        }

        for (int j = 1; j < n; j++) {
            Set<Integer> q2 = new HashSet<>();
            for (int i : q) {
                for (int i2 = i - 1; i2 <= i + 1; i2++) {
                    if (0 <= i2 && i2 < m && grid[i][j - 1] < grid[i2][j]) {
                        q2.add(i2);
                    }
                }
            }
            q = q2;
            if (q.isEmpty()) {
                return j - 1;
            }
        }

        return n - 1;
//        int[][] dp = new int[grid.length][grid[0].length];
//        for (int[] ints : dp) {
//            Arrays.fill(ints, -1);
//        }
//        int max = 0;
//        Queue<int[]> queue = new ArrayDeque<>();
//        for (int i = 0; i < grid.length; i++) {
//            queue.add(new int[]{i, 0});
//            dp[i][0] = 0;
//        }
//
//        while (!queue.isEmpty()) {
//            int size = queue.size();
//            for (int k = 0; k < size; k++) {
//                int[] arr = queue.poll();
//                int i = arr[0];
//                int j = arr[1];
//                if (i - 1 >= 0 && j + 1 < grid[0].length) {
//                    if (grid[i][j] < grid[i - 1][j + 1]) {
//                        dp[i - 1][j + 1] = Math.max(dp[i - 1][j + 1], dp[i][j] + 1);
//                        max = Math.max(dp[i - 1][j + 1], max);
//                        queue.offer(new int[]{i - 1, j + 1});
//                    }
//                }
//
//                if (j + 1 < grid[0].length) {
//                    if (grid[i][j] < grid[i][j + 1]) {
//                        dp[i][j + 1] = Math.max(dp[i][j + 1], dp[i][j] + 1);
//                        max = Math.max(dp[i][j + 1], max);
//                        queue.offer(new int[]{i, j + 1});
//                    }
//                }
//
//                if (i + 1 < grid.length && j + 1 < grid[0].length) {
//                    if (grid[i][j] < grid[i + 1][j + 1]) {
//                        dp[i + 1][j + 1] = Math.max(dp[i + 1][j + 1], dp[i][j] + 1);
//                        max = Math.max(dp[i + 1][j + 1], max);
//                        queue.offer(new int[]{i + 1, j + 1});
//                    }
//                }
//            }
//        }
//        System.out.println(Arrays.deepToString(dp));
//        return max;
    }

    // 209. 长度最小的子数组
    public int minSubArrayLen(int target, int[] nums) {
        int left = 0, right = 0, sum = 0;
        int min = Integer.MAX_VALUE;
        while (right < nums.length) {
            sum += nums[right++];
            if (sum >= target) {
                min = Math.min(min, right - left);
                while (sum >= target) {
                    sum -= nums[left++];
                    if (sum >= target) {
                        min = Math.min(min, right - left);
                    }
                }
            }
        }

        if (min == Integer.MAX_VALUE) {
            min = 0;
        }
        return min;
    }

    // 55. 跳跃游戏，判断当前能到达的最远距离是否在大于等于下标i即可
    public boolean canJump(int[] nums) {
        boolean[] dp = new boolean[nums.length];
        Arrays.fill(dp, false);
        dp[0] = true;
        int maxIndex = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (i <= maxIndex) {
                dp[i] = true;
                maxIndex = Math.max(maxIndex, i + nums[i]);
            }
        }

        return dp[nums.length - 1];
    }

    // 122. 买卖股票的最佳时机 II
    public int maxProfitTwo(int[] prices) {
        int maxRes = 0, minIn = prices[0];
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] >= minIn) {
                maxRes += prices[i] - minIn;
            }
            minIn = prices[i];
        }

        return maxRes;
    }

    // 121. 买卖股票的最佳时机
    public int maxProfit(int[] prices) {
        int minIn = prices[0];
        int maxRes = 0;
        for (int i = 1; i < prices.length; i++) {
            // 之前的最小价格，今天卖出
            maxRes = Math.max(maxRes, prices[i] - minIn);
            minIn = Math.min(prices[i], minIn);
        }
        return maxRes;
    }

    // 88. 合并两个有序数组
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        if (n == 0) {
            return;
        }
        int left = m - 1, right = nums2.length - 1, index = nums1.length - 1;
        while (left >= 0 && right >= 0) {
            if (nums1[left] > nums2[right]) {
                nums1[index--] = nums1[left--];
            } else {
                nums1[index--] = nums2[right--];
            }
        }

        while (right >= 0) {
            nums1[index--] = nums2[right--];
        }
    }

    // 80. 删除有序数组中的重复项 II
    public int removeDuplicates(int[] nums) {
        if (nums.length <= 2) {
            return nums.length;
        }
        // slow 和 fast并不是一直相差二
        // 这里跟我的思路有点相似，就是前边一个指针，指向当前需要被覆盖的下标
        // 但是我的问题在于试图通过第一次遍历去找前一个指针的位置
        // 事实是，只需要将第一个指针设置到2即可。因为前边有两个数字，而比较时是那slow - 2 和fast进行比较
        // 这样还解决了我之前需要额外判断覆盖写是否超过2个
        // 当fast 和 slow - 2不相等的时候，slow指向的就是要被覆盖掉的
        int slow = 2, fast = 2;
        while (fast < nums.length) {
            if (nums[slow - 2] != nums[fast]) {
                nums[slow] = nums[fast];
                slow++;
            }
            fast++;
        }
        return slow;
    }

    // 27. 移除元素
    public int removeElement(int[] nums, int val) {
        int fast = 0, slow = 0;
        while (fast < nums.length) {
            if (nums[fast] != val) {
                nums[slow++] = nums[fast++];
            } else {
                while (fast < nums.length && nums[fast] == val) {
                    fast++;
                }
                if (fast < nums.length) {
                    nums[slow++] = nums[fast++];
                }
            }
        }

        return slow;
        // 这种写法，是不考虑顺序的写法
//        int ans=nums.length;
//        for(int i=0;i<ans;){
//            if(nums[i]==val){
//                nums[i]=nums[ans-1];
//                ans--;
//            }else{
//                i++;
//            }
//        }
//        return ans;
    }

    // 26. 删除有序数组中的重复项
    public int removeDuplicatesEasy(int[] nums) {
        if (nums.length == 1) {
            return 1;
        }
        int slow = 1, fast = 1;
        while (fast < nums.length) {
            if (nums[fast] == nums[slow - 1]) {
                fast++;
            } else {
                nums[slow] = nums[fast];
                fast++;
                slow++;
            }
        }

        return slow;
    }

    // 2789. 合并后数组中的最大元素
    public long maxArrayValue(int[] nums) {
        long lastNum = nums[nums.length - 1];
        long max = lastNum;
        for (int i = nums.length - 2; i >= 0; i--) {
            if (nums[i] <= lastNum) {
                lastNum += nums[i];
            } else {
                lastNum = nums[i];
            }
            max = Math.max(max, lastNum);
        }

        return max;
    }

    int maxPathSumRes = Integer.MIN_VALUE;
    // 124. 二叉树中的最大路径和
    public int maxPathSum(TreeNode root) {
        maxPathSumDfs(root);
        return maxPathSumRes;
    }

    public int maxPathSumDfs(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int left = Math.max(maxPathSumDfs(root.left), 0);
        int right = Math.max(maxPathSumDfs(root.right), 0);

        maxPathSumRes = Math.max(maxPathSumRes, left + right + root.val);

        // 这里，往父节点返回时，并不能将子节点的左右子树都走完，只能走一个，所以不是全部相加
        // return Math.max(0, left + right + root.val);
        // 这两个值只能取较大的
        // 如果是负数，这里为什么不返回0？其实不影响，为什么呢？看上面的left 和right，他在那里取了0，所以这里不用取
        return root.val + Math.max(left, right);
    }

    // LCR 024. 反转链表
    public ListNode reverseList(ListNode head) {
        if (head == null) {
            return null;
        }

        ListNode prev = null;
        ListNode current = head;
        while (current != null) {
            ListNode next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }

        return prev;
    }

    // 73. 矩阵置零
    public void setZeroes(int[][] matrix) {
        boolean column = false, line = false;
        for (int[] ints : matrix) {
            if (ints[0] == 0) {
                line = true;
                break;
            }
        }
        for (int i = 0; i < matrix[0].length; i++) {
            if (matrix[0][i] == 0) {
                column = true;
                break;
            }
        }

        for (int i = 1; i < matrix.length; i++) {
            for (int j = 1; j < matrix[0].length; j++) {
                if (matrix[i][j] == 0) {
                    matrix[i][0] = 0;
                    matrix[0][j] = 0;
                }
            }
        }

        for (int i = 1; i < matrix.length; i++) {
            for (int j = 1; j < matrix[0].length; j++) {
                if (matrix[i][0] == 0 || matrix[0][j] == 0) {
                    matrix[i][j] = 0;
                }
            }
        }

        if (column) {
            Arrays.fill(matrix[0], 0);
        }

        if (line) {
            for (int i = 0; i < matrix.length; i++) {
                matrix[i][0] = 0;
            }
        }
    }

    // 56. 合并区间
    public int[][] merge(int[][] intervals) {
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));

        List<int[]> list = new ArrayList<>();
        int lastStart = intervals[0][0];
        int lastEnd = intervals[0][1];
        list.add(new int[]{lastStart, lastEnd});

        for (int i = 1; i < intervals.length; i++) {
            int start = intervals[i][0];
            int end = intervals[i][1];
            if (start > lastEnd) {
                list.add(new int[]{start, end});
                lastEnd = end;
                lastStart = start;
            } else {
                list.remove(list.size() - 1);
                list.add(new int[]{lastStart, Math.max(lastEnd, end)});
                lastEnd = Math.max(end, lastEnd);
            }
        }

        int[][] arr = new int[list.size()][2];
        for (int i = 0; i < list.size(); i++) {
            arr[i][0] = list.get(i)[0];
            arr[i][1] = list.get(i)[1];
        }

        return arr;
    }

}
