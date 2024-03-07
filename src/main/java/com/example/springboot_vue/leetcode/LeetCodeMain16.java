package com.example.springboot_vue.leetcode;

import java.util.*;

public class LeetCodeMain16 {

    public static void main(String[] args) {
        String str = "1 + 199929992992";
        int[] arr = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
        LeetCodeMain16 leetCodeMain16 = new LeetCodeMain16();
        System.out.println(leetCodeMain16.findPeakElement(arr));
        /*
            默认情况下，初始堆内存大小为：电脑内存大小/64
            默认情况下，最大堆内存大小为：电脑内存大小/4
         */
        //返回 JVM 堆大小
//        long initalMemory = Runtime.getRuntime().totalMemory() / 1024 /1024;
//        //返回 JVM 堆的最大内存
//        long maxMemory = Runtime.getRuntime().maxMemory() / 1024 /1024;
//
//        System.out.println("-Xms : " + initalMemory + "M");
//        System.out.println("-Xmx : " + maxMemory + "M");
//
//        System.out.println("系统内存大小：" + initalMemory * 64 / 1024 + "G");
//        System.out.println("系统内存大小：" + maxMemory * 4 / 1024 + "G");
    }

//    public List<String> wordBreak(String s, List<String> wordDict) {
//        Map<String, String> map = new HashMap<>();
//        for (String value : wordDict) {
//            map.put(value, "1");
//        }

//        StringBuilder first = new StringBuilder();
//        for (int i = 0; i < s.length(); i++) {
//            char c = s.charAt(i);
//            first.append(c);
//            if (map.get(first.toString()) != null) {
//                StringBuilder temp = new StringBuilder(first);
//                dfs(temp, i + 1, s, map);
//            }
//        }

//        return wordBreak;
//    }

//    public void dfs(StringBuilder str, int index, String s, Map<String, String> map) {
//        str.append(" ");
//        for (int i = index; i < s.length(); i++) {
//            char c = s.charAt(i);
//
//        }
//    }

    // 2575. 找出字符串的可整除数组
    public int[] divisibilityArray(String word, int m) {
        long tmp = 0;
        int n = word.length();
        int[] res = new int[n];
        for (int i = 0; i < n; i++) {
            tmp = (tmp * 10 + word.charAt(i) - '0') % m;
            res[i] = tmp == 0 ? 1 : 0;
        }
        return res;
    }

    // 交换给定值(牛客)
    // 该题，原题意是交换m，n下标位置的元素，我理解为交换m 和 n 这两个值的元素
    public ListNode reverseBetween (ListNode head, int m, int n) {
        // write code here
        if (head == null || head. next == null) {
            return head;
        }
        ListNode beforeMNode = new ListNode(0);
        ListNode beforeNNode = new ListNode(0);
        ListNode res = new ListNode(0);
        res.next = head;
        while (head != null && head.next != null) {
            if (head.next.val == m) {
                beforeMNode.next = head;
            }
            if (head.next.val == n) {
                beforeNNode.next = head;
            }
            if (beforeMNode.next != null && beforeNNode.next != null) {
                break;
            }
            head = head.next;
        }
        if (beforeMNode.next == null || beforeNNode.next == null) {
            return res.next;
        }

        ListNode temp = beforeNNode.next.next;
        beforeNNode.next.next = beforeMNode.next.next;
        beforeMNode.next.next.next = temp.next;
        temp.next = beforeNNode.next;
        beforeMNode.next.next = temp;

        return res.next;
    }

    // 42. 接雨水
    public int trap(int[] height) {
        int res = 0;
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < height.length; i++) {
            int column = 0, last = 0;
            while (!stack.isEmpty() && height[i] > height[stack.peek()]) {
                int val = stack.pop();
                column += height[val];
                last = val;
            }
            if (stack.isEmpty()) {
                res += (i - last - 1) * height[last] - column;
            } else {
                res += (i - stack.peek() - 1) * height[i] - column;
            }
            System.out.println("i = " + i + " last = " + last + " column = " + column);
            stack.push(i);
        }

        return res;
    }

    // 2917. 找出数组中的 K-or 值
    public int findKOr(int[] nums, int k) {
        int ans = 0;
        for (int i = 0; i < 31; ++i) {
            int cnt = 0;
            for (int num : nums) {
                if (((num >> i) & 1) != 0) {
                    ++cnt;
                }
            }
            if (cnt >= k) {
                ans |= 1 << i;
            }
        }
        return ans;
    }

    // BM19 寻找峰值 （牛客）
    public int findPeakElement (int[] nums) {
        if (nums.length == 1) {
            return 0;
        }
        if (nums.length == 2) {
            if (nums[0] > nums[1]) {
                return 0;
            } else {
                return 1;
            }
        }
        // write code here
        int left = 0, right = nums.length - 1, mid;
        while (left <= right) {
            mid = (left + right) / 2;
            // 边界情况
            if (mid == 0) {
                if (mid + 1 < nums.length && nums[mid + 1] < nums[mid]) {
                    return mid;
                }
            }
            if (mid == nums.length - 1) {
                if (mid - 1 >= 0 && nums[mid - 1] < nums[mid]) {
                    return mid;
                }
            }
            if (nums[mid] > nums[mid - 1] && nums[mid] > nums[mid + 1]) {
                return mid;
            } else {
                if (nums[mid] < nums[mid + 1] && nums[mid] > nums[mid - 1]) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
        }

        return 0;
    }

    // 1774. 最接近目标价格的甜点成本
    int res;
    public int closestCost(int[] baseCosts, int[] toppingCosts, int target) {
        res = Arrays.stream(baseCosts).min().getAsInt();
        for (int b : baseCosts) {
            dfs(toppingCosts, 0, b, target);
        }
        return res;
    }

    public void dfs(int[] toppingCosts, int p, int curCost, int target) {
        if (Math.abs(res - target) < curCost - target) {
            return;
        } else if (Math.abs(res - target) >= Math.abs(curCost - target)) {
            if (Math.abs(res - target) > Math.abs(curCost - target)) {
                res = curCost;
            } else {
                res = Math.min(res, curCost);
            }
        }
        if (p == toppingCosts.length) {
            return;
        }
        dfs(toppingCosts, p + 1, curCost + toppingCosts[p] * 2, target);
        dfs(toppingCosts, p + 1, curCost + toppingCosts[p], target);
        dfs(toppingCosts, p + 1, curCost, target);
    }

    // 1019. 链表中的下一个更大节点
    public int[] nextLargerNodes(ListNode head) {
        if (head == null) {
            return new int[0];
        }

        List<Integer> list = new ArrayList<>();
        while (head != null) {
            list.add(head.val);
            head = head.next;
        }
        int[] arr = new int[list.size()];
        arr[arr.length - 1] = 0;
        Stack<Integer> stack = new Stack<>();
        stack.push(list.get(list.size() - 1));
        for (int i = list.size() - 2; i >= 0; i--) {
            if (stack.peek() > list.get(i)) {
                arr[i] = stack.peek();
            } else {
                while (!stack.isEmpty() && stack.peek() <= list.get(i)) {
                    stack.pop();
                }
                if (stack.isEmpty()) {
                    arr[i] = 0;
                } else {
                    arr[i] = stack.peek();
                }
            }
            stack.push(list.get(i));
            System.out.println(stack.toString());
        }

        return arr;
    }

    // 1976. 到达目的地的方案数
    public int countPaths(int n, int[][] roads) {
        int mod = 1000000007;
        List<int[]>[] e = new List[n];
        for (int i = 0; i < n; i++) {
            e[i] = new ArrayList<>();
        }
        for (int[] road : roads) {
            int x = road[0], y = road[1], t = road[2];
            e[x].add(new int[]{y, t});
            e[y].add(new int[]{x, t});
        }
        long[] dis = new long[n];
        Arrays.fill(dis, Long.MAX_VALUE);
        int[] ways = new int[n];

        PriorityQueue<long[]> pq = new PriorityQueue<>(Comparator.comparingLong(a -> a[0]));
        pq.offer(new long[]{0, 0});
        dis[0] = 0;
        ways[0] = 1;

        while (!pq.isEmpty()) {
            long[] arr = pq.poll();
            long t = arr[0];
            int u = (int) arr[1];
            if (t > dis[u]) {
                continue;
            }
            for (int[] next : e[u]) {
                int v = next[0], w = next[1];
                if (t + w < dis[v]) {
                    dis[v] = t + w;
                    ways[v] = ways[u];
                    pq.offer(new long[]{t + w, v});
                } else if (t + w == dis[v]) {
                    ways[v] = (ways[u] + ways[v]) % mod;
                }
            }
        }
        return ways[n - 1];
    }

    // 395. 至少有 K 个重复字符的最长子串
    public int longestSubstring(String s, int k) {

        return 0;
    }

    int longestSubstringRes = 0;
    // 687. 最长同值路径
    public int longestUnivaluePath(TreeNode root) {
        longestSubstringRes = longestSubstringDfs(root);
        return longestSubstringRes;
    }

    public int longestSubstringDfs(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int left = longestSubstringDfs(root.left);
        int right = longestSubstringDfs(root.right);

        int left1 = 0, right1 = 0;
        if (root.left != null && root.left.val == root.val) {
            left1 = left + 1;
        }

        if (root.right != null && root.val == root.right.val) {
            right1 = right + 1;
        }

        longestSubstringRes = Math.max(longestSubstringRes, left1 + right1);
        return Math.max(left1, right1);
    }

    // 594. 最长和谐子序列
    public int findLHS(int[] nums) {
        Arrays.sort(nums);
        int max = 0, last = nums[0], lastLength = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == last) {
                lastLength++;
            } else {
                if (nums[i] == last + 1) {
                    int currentLength = 0;
                    while (nums[i] == last + 1) {
                        currentLength++;
                        i++;
                        if (i == nums.length) {
                            break;
                        }
                    }
                    i--;
                    max = Math.max(max, currentLength + lastLength);
                    lastLength = currentLength;
                    last = nums[i];
                } else {
                    last = nums[i];
                    lastLength = 1;
                }
            }
        }

        return max;
    }

    // 825. 适龄的朋友
    public int numFriendRequests(int[] ages) {
        Arrays.sort(ages);
        int left = 0, res = 0;
        for (int i = 0; i < ages.length; i++) {
            if (ages[i] < 15) {
                continue;
            }

            while (left < i) {
                if (ages[i] * 0.5 + 7 >= ages[left]) {
                    left++;
                } else {
                    break;
                }
            }

            int temp = -1;
            if (i + 1 < ages.length) {
                if (ages[i + 1] == ages[i]) {
                    temp = i + 1;
                    while (temp < ages.length - 1 && ages[temp] == ages[i]) {
                        temp++;
                    }
                }
            }

            if (temp != -1) {
                if (ages[temp] != ages[i]) {
                    temp--;
                }
                res += temp - left - 1;
            } else {
                res += (i - left);
            }
        }

        return res;
    }

    // 525. 连续数组
    public int findMaxLength(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, -1);
        int count = 0, max = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 1) {
                count++;
            } else {
                count--;
            }

            if (!map.containsKey(count)) {
                map.put(count, i);
            } else {
                max = Math.max(max, i - map.get(count));
            }
        }

        return max;
    }

    // 229. 多数元素 II
    public List<Integer> majorityElement(int[] nums) {
        List<Integer> res = new ArrayList<>();
        int mark = nums.length / 3;
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            if (map.containsKey(num)) {
                map.put(num, map.get(num) + 1);
            } else {
                map.put(num, 1);
            }
        }

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getValue() >= mark) {
                res.add(entry.getKey());
            }
        }

        return res;
    }

    // 203. 移除链表元素
    public ListNode removeElements(ListNode head, int val) {
        ListNode res = new ListNode();
        ListNode temp = res;
        res.next = head;
        ListNode fast = head;
        while (fast != null) {
            if (fast.val != val) {
                fast = fast.next;
                res = res.next;
            } else {
                while (fast != null && fast.val == val) {
                    System.out.println(fast.val);
                    fast = fast.next;
                }
                res.next = fast;
            }
        }

        return temp;
    }

    // 2368. 受限条件下可到达节点的数目
    public int reachableNodes(int n, int[][] edges, int[] restricted) {
        // 这里的map可以优化为List数组
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < edges.length; i++) {
            if (map.get(edges[i][0]) != null) {
                map.get(edges[i][0]).add(edges[i][1]);
            } else {
                List<Integer> list = new ArrayList<>();
                list.add(edges[i][1]);
                map.put(edges[i][0], list);
            }

            if (map.get(edges[i][1]) != null) {
                map.get(edges[i][1]).add(edges[i][0]);
            } else {
                List<Integer> list = new ArrayList<>();
                list.add(edges[i][0]);
                map.put(edges[i][1], list);
            }
        }
        int res = 1;
        Set<Integer> wrongNode = new HashSet<>();
        for (int i : restricted) {
            wrongNode.add(i);
        }
        // 存储已经走过的节点
        Set<String> set = new HashSet<>();
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(0);
        // 从0开始广搜
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                // 初始节点
                int index = queue.poll();
                List<Integer> temp = map.get(index);
                for (int j = 0; j < temp.size(); j++) {
                    // 该节点不是错误节点，可以进入
                    if (!wrongNode.contains(temp.get(j))) {
                        // 用于标记路径是否走过
                        String str = index + "-" + temp.get(j);
                        String str2 = temp.get(j) + "-" + index;
                        // 不包含
                        if (!set.contains(str) && !set.contains(str2)) {
                            queue.offer(temp.get(j));
                            res++;
                            set.add(str);
                            set.add(str2);
                        }
                    }
                }
            }
        }

        return res;
    }

    // 833. 字符串中的查找与替换
    public String findReplaceString(String s, int[] indices, String[] sources, String[] targets) {
        StringBuilder res = new StringBuilder();
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < indices.length; i++) {
            map.put(indices[i], i);
        }

        for (int i = 0; i < s.length(); i++) {
            if (map.containsKey(i)) {
                int index = map.get(i);
                String str = sources[index];
                int mark = 0;
                for (int j = 0; j < str.length(); j++) {
                    char c;
                    if (j + i < s.length()) {
                        c = s.charAt(j + i);
                    } else {
                        mark = 1;
                        break;
                    }
                    char sI = str.charAt(j);
                    if (c != sI) {
                        mark = 1;
                        break;
                    }
                }
                if (mark == 0) {
                    res.append(targets[index]);
                    i += str.length() - 1;
                } else {
                    res.append(s.charAt(i));
                }
            } else {
                char c = s.charAt(i);
                res.append(c);
            }
        }

        return res.toString();
    }

    // LCP 03. 机器人大冒险,这道题2021.03.15 09:41没做出来，现在仍然没做出来
    public boolean robot(String command, int[][] obstacles, int x, int y) {
        boolean res = false;
        int indexX = 0, indexY = 0;
        for (int i = 0; i < command.length(); i++) {
            char c = command.charAt(i);
            if (c == 'U') {
                indexY++;
            } else {
                indexX++;
            }
            System.out.println("indexX = " + indexX + " indexY + " + indexY);
            // 对于每一个到达的位置x，和y都需要判断一下是否有障碍
            for (int j = 0; j < obstacles.length; j++) {
                if (obstacles[j][0] > x || obstacles[j][1] > y || indexX == 0 || indexY == 0) {
                    continue;
                }
                if (obstacles[j][0] % indexX == 0 && obstacles[j][1] % indexY == 0) {
                    return false;
                }
            }

            // 直接到达
            if (indexX == x && indexY == y) {
                res = true;
                break;
            }

            // 循环内可以到达
            if (indexX != 0 && indexY != 0) {
                if (x % indexX == 0 && y % indexY == 0) {
                    res = true;
                    break;
                }
            }

            // 一次循环已经超过,因为不能回退
            if (indexX > x || indexY > y) {
                return false;
            }
        }

        return res;
    }

    // LCR 127. 跳跃训练
    public int trainWays(int num) {
        if (num == 0 || num == 1) {
            return 1;
        }

        int temp = 1000000007;

        int[] dp = new int[num + 1];
        dp[0] = 0; dp[1] = 1; dp[2] = 2;
        for (int i = 3; i <= num; i++) {
            dp[i] = (dp[i - 1] + dp[i - 2]) % temp;
        }

        return dp[num];
    }

    // 2369. 检查数组是否存在有效划分
    public boolean validPartition(int[] nums) {
        int n = nums.length;
        boolean[] f = new boolean[n + 1];
        f[0] = true;
        for (int i = 1; i < n; i++) {
            if (f[i - 1] && nums[i] == nums[i - 1] ||
                    i > 1 && f[i - 2] && (nums[i] == nums[i - 1] && nums[i] == nums[i - 2] ||
                            nums[i] == nums[i - 1] + 1 && nums[i] == nums[i - 2] + 2)) {
                // f[i - 1] && nums[i] == nums[i - 1] 这里是前一个是true，然后本次的元素和前一个元素相等
                // i > 1 && f[i - 2] && (nums[i] == nums[i - 1] && nums[i] == nums[i - 2] 这里是判断三个连续相等的情况
                // nums[i] == nums[i - 1] + 1 && nums[i] == nums[i - 2] + 2 这里是判断三个递增的
                f[i + 1] = true;
            }
        }
        System.out.println(Arrays.toString(f));
        return f[n];
    }

    // 357. 统计各位数字都不同的数字个数
    public int countNumbersWithUniqueDigits(int n) {
        if (n == 0) {
            return 1;
        }
        if (n == 1) {
            return 10;
        }

        int res = 10, cur = 9;
        for (int i = 0; i < n - 1; i++) {
            cur *= 9 - i;
            res += cur;
        }
        return res;
    }

    // 441. 排列硬币
    public int arrangeCoins(int n) {
        if (n == 1 || n == 2) {
            return 1;
        }
        long res = 0;
        int index = 1;
        for (int i = 1; i < n; i++) {
            res += i;
            if (res > n) {
                index = i;
                break;
            }
            index = i;
        }

        if (res == n) {
            return index;
        } else {
            return index - 1;
        }
    }

    int sumOfLeftLeaves = 0;
    // 404. 左叶子之和
    public int sumOfLeftLeaves(TreeNode root) {
        dfsSumOfLeftLeaves(root);
        return sumOfLeftLeaves;
    }

    public void dfsSumOfLeftLeaves(TreeNode root) {
        if (root == null) {
            return;
        }

        if (root.left != null && root.left.left == null && root.left.right == null) {
            sumOfLeftLeaves += root.left.val;
        }

        dfsSumOfLeftLeaves(root.left);
        dfsSumOfLeftLeaves(root.right);
    }

    // 409. 最长回文串
    public int longestPalindrome(String s) {
        int res = 0;
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            map.merge(c, 1, Integer::sum);
        }

        int odd = 0;
        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            if (entry.getValue() % 2 != 0) {
                odd++;
            }
            res += entry.getValue();
        }

        if (odd >= 2) {
            res -= (odd - 1);
        }

        return res;
    }

    // 841. 钥匙和房间
    public boolean canVisitAllRooms(List<List<Integer>> rooms) {
        Set<Integer> set = new HashSet<>();
        set.add(0);
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(0);
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                // 这里是所有已经获得钥匙的房间
                int index = queue.poll();
                List<Integer> temp = rooms.get(index);
                for (int j = 0; j < rooms.get(index).size(); j++) {
                    if (!set.contains(rooms.get(index).get(j))) {
                        queue.offer(rooms.get(index).get(j));
                    }
                }
                set.addAll(temp);
            }
        }

        System.out.println(set.toString());
        return set.size() == rooms.size();
    }

    // 140. 单词拆分 II
    public List<String> wordBreak(String s, List<String> wordDict) {
        Map<Integer, List<List<String>>> map = new HashMap<>();
        List<List<String>> wordBreaks = backtrack(s, s.length(), new HashSet<>(wordDict), 0, map);
        List<String> breakList = new LinkedList<>();
        for (List<String> wordBreak : wordBreaks) {
            breakList.add(String.join(" ", wordBreak));
        }
        return breakList;
    }

    public List<List<String>> backtrack(String s, int length, Set<String> wordSet, int index, Map<Integer, List<List<String>>> map) {
        if (!map.containsKey(index)) {
            List<List<String>> wordBreaks = new LinkedList<>();
            if (index == length) {
                wordBreaks.add(new LinkedList<>());
            }
            for (int i = index + 1; i <= length; i++) {
                String word = s.substring(index, i);
                if (wordSet.contains(word)) {
                    List<List<String>> nextWordBreaks = backtrack(s, length, wordSet, i, map);
                    for (List<String> nextWordBreak : nextWordBreaks) {
                        LinkedList<String> wordBreak = new LinkedList<>(nextWordBreak);
                        wordBreak.offerFirst(word);
                        wordBreaks.add(wordBreak);
                    }
                }
            }
            map.put(index, wordBreaks);
        }
        return map.get(index);
    }
}
