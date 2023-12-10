package com.example.springboot_vue.leetcode;

import com.example.springboot_vue.pojo.city.City;

import java.util.*;

public class LeetCodeMain13 {

    // 330. 按要求补齐数组
    public int minPatches(int[] nums, int n) {
        int res = 0;
        synchronized (this) {

        }

        return res;
    }

    // 70. 爬楼梯
    public int climbStairs(int n) {
        if (n == 1) {
            return 1;
        }

        if (n == 2) {
            return 2;
        }

        int[] dp = new int[n + 1];
        dp[0] = 0; dp[1] = 1; dp[2] = 2;
        for (int i = 3; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[n];
    }

    // 2048. 下一个更大的数值平衡数
    public int nextBeautifulNumber(int n) {
        for (int i = n + 1; i <= 1224444; ++i) {
            if (isBalance(i)) {
                return i;
            }
        }
        return -1;
    }

    private boolean isBalance(int x) {
        int[] count = new int[10];
        while (x > 0) {
            count[x % 10]++;
            x /= 10;
        }
        for (int d = 0; d < 10; ++d) {
            if (count[d] > 0 && count[d] != d) {
                return false;
            }
        }
        return true;
    }

    // 2008. 出租车的最大盈利
    public long maxTaxiEarnings(int n, int[][] rides) {
        Arrays.sort(rides, Comparator.comparingInt(a -> a[1]));
        int m = rides.length;
        long[] dp = new long[m + 1];
        for (int i = 0; i < m; i++) {
            // 这里其实就是背包问题的寻找不放入当前物品，那么可以放入的上一个物品的下标
            // 这里，当前乘客拉或者不拉，取决于拉了当前乘客，那么如何寻找上一个与该乘客区间不冲突的最大值
            // 这里可以利用二分的一点是，按照上一个乘客的区间是有序的，获取到当前乘客的区间，找到上一个不冲突的就是最大值，或者不拉当前乘客，取上一个最大值
            int j = binarySearch(rides, i, rides[i][0]);
            dp[i + 1] = Math.max(dp[i], dp[j] + rides[i][1] - rides[i][0] + rides[i][2]);
        }

        return dp[m];
    }

    public int binarySearch(int[][] rides, int high, int target) {
        int low = 0;
        while (low < high) {
            int mid = low + (high - low) / 2;
            if (rides[mid][1] > target) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }

        return low;
    }

    // 1466. 重新规划路线
    public int minReorder(int n, int[][] connections) {
        List<int[]>[] e = new List[n];
        for (int i = 0; i < n; i++) {
            e[i] = new ArrayList<>();
        }
        for (int[] edge : connections) {
            // 这里是我忽略的，即原边的方向，我想到了要加两条边，但是没想到要怎么记录方向
            // 题解的方案，1代表从原来的边，0代表反方向的边，即不存在的
            e[edge[0]].add(new int[]{edge[1], 1});
            e[edge[1]].add(new int[]{edge[0], 0});
        }
        return dfs(0, -1, e);
    }

    public int dfs(int x, int parent, List<int[]>[] e) {
        int res = 0;
        // 这里是如果发现现存的一条边指向了递归的父节点，就跳过
        // e[x]，是获取到当前x指向的所有节点,换句话说，获取到所有和x相关的节点
        // e[x]是一个List<int[]>
        // 然后这个edge，是一个list中的一个元素
        for (int[] edge : e[x]) {
            // edge[0]是节点的值，edge[1]是用于标记是否是原先存在的边
            // edge[0] == parent，说明该条边是重复的
            // 考虑一种情况，1 —> 3，虽然只有这一条边，但是和1相关的，会存在3，1 和1，3两个
            // 开始遍历1 -> 3时是正常的，但是会递归的遍历3，多了一条3 —> 1,即parent = edge[0]
            if (edge[0] == parent) {
                continue;
            }
            res += edge[1] + dfs(edge[0], x, e);
        }
        return res;
    }

//    int minReorderRes;
//    public int minReorder(int n, int[][] connections) {
//        List<Integer>[] lists = new ArrayList[n];
//        for (int i = 0; i < lists.length; i++) {
//            lists[i] = new ArrayList<>();
//        }
//
//        for (int[] connection : connections) {
//            lists[connection[0]].add(connection[1]);
//            lists[connection[1]].add(connection[0]);
//        }
//        dfs(lists, 0);
//
//        return minReorderRes;
//    }
//
//    public void dfs(List<Integer>[] lists, int parent) {
//        if (parent == -1) {
//            return;
//        }
//        System.out.println(parent);
//        // 父元素指向的所有元素都要改
//        minReorderRes += lists[parent].size();
//        for (int i = 0; i < lists[parent].size(); i++) {
//            if (lists[parent].get(i) == -1) {
//                continue;
//            }
//            lists[lists[parent].get(i)].add(parent);
//            lists[parent].set(i, -1);
//            dfs(lists, lists[parent].get(i));
//        }
//    }

    /**
     * 整体的思路，可以理解为将原问题拆分为两个问题，第一个就是深度优先遍历所有的节点，求出所有旅行的开销
     * 第二个问题就是考虑了对应的价格减半，然后进行dp求解。
     * 具体细节没看懂
     */
    // 2646. 最小化旅行的价格总和
    public int minimumTotalPrice(int n, int[][] edges, int[] price, int[][] trips) {
        List<Integer>[] next = new List[n];
        for (int i = 0; i < n; i++) {
            next[i] = new ArrayList<Integer>();
        }
        // 这里是将整个图进行转换
        for (int[] edge : edges) {
            next[edge[0]].add(edge[1]);
            next[edge[1]].add(edge[0]);
        }

        // 遍历每一次旅行
        // 这里的count，记录了每一条边被使用了多少次
        int[] count = new int[n];
        for (int[] trip : trips) {
            dfs(trip[0], -1, trip[1], next, count);
        }

        // 这里是考虑减半的情况
        int[] pair = dp(0, -1, price, next, count);
        return Math.min(pair[0], pair[1]);
    }

    public boolean dfs(int node, int parent, int end, List<Integer>[] next, int[] count) {
        if (node == end) {
            count[node]++;
            return true;
        }
        for (int child : next[node]) {
            if (child == parent) {
                continue;
            }
            if (dfs(child, node, end, next, count)) {
                count[node]++;
                return true;
            }
        }
        return false;
    }

    public int[] dp(int node, int parent, int[] price, List<Integer>[] next, int[] count) {
        int[] res = {price[node] * count[node], price[node] * count[node] / 2};
        for (int child : next[node]) {
            if (child == parent) {
                continue;
            }
            int[] pair = dp(child, node, price, next, count);
            res[0] += Math.min(pair[0], pair[1]); // node 没有减半，因此可以取子树的两种情况的最小值
            res[1] += pair[0]; // node 减半，只能取子树没有减半的情况
        }
        return res;
    }

    // 2477. 到达首都的最少油耗
    long minimumFuelCostRes = 0;
    public long minimumFuelCost(int[][] roads, int seats) {
        int n = roads.length;
        List<Integer>[] g = new List[n + 1];
        for (int i = 0; i <= n; i++) {
            g[i] = new ArrayList<>();
        }
        for (int[] e : roads) {
            g[e[0]].add(e[1]);
            g[e[1]].add(e[0]);
        }
        dfs(0, -1, seats, g);
        return minimumFuelCostRes;
    }

    public int dfs(int cur, int fa, int seats, List<Integer>[] g) {
        int peopleSum = 1;
        for (int ne : g[cur]) {
            if (ne != fa) {
                int peopleCnt = dfs(ne, cur, seats, g);
                peopleSum += peopleCnt;
                minimumFuelCostRes += (peopleCnt + seats - 1) / seats;
            }
        }
        return peopleSum;
    }

    /**
     * 该方法是我写的，在需要考虑节点的相关时，没有考虑，这种写法，在最终求每一层的时候，并不知道连接了多少个节点
     */
    public long minimumFuelCost(int[][] roads, int seats, int b) {
        if (roads.length == 0) {
            return 0;
        }
        long res = 0;
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < roads.length; i++) {
            setData(map, roads, i, 0);
            setData(map, roads, i, 1);
        }

        boolean[] isAdd = new boolean[roads.length + 1];
        isAdd[0] = true;
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(0);
        List<Integer> list = new ArrayList<>();
        while (!queue.isEmpty()) {
            int size = queue.size();
            int sum = 0;
            for (int i = 0; i < size; i++) {
                List<Integer> temp = map.get(queue.poll());
                int countSun = 0;
                for (int j = 0; j < temp.size(); j++) {
                    if (!isAdd[temp.get(j)]) {
                        isAdd[temp.get(j)] = true;
                        queue.add(temp.get(j));
                        countSun++;
                    }
                }
                sum += countSun;
            }
            // 这里记录了每一层的个数
            list.add(sum);
        }

        return res;
    }

    public void setData(Map<Integer, List<Integer>> map, int[][] roads, int i, int index) {
        if (map.get(roads[i][index]) == null) {
            List<Integer> list = new ArrayList<>();
            list.add(roads[i][1 - index]);
            map.put(roads[i][index], list);
        } else {
            map.get(roads[i][index]).add(roads[i][1 - index]);
        }
    }

    // 1038. 从二叉搜索树到更大和树
    int sum = 0;
    public TreeNode bstToGst(TreeNode root) {
        if (root != null) {
            bstToGst(root.right);
            sum += root.val;
            root.val = sum;
            bstToGst(root.left);
        }

        return root;
    }

    /**
     * 我的写法问题在于，这个sum每次是传递的，而题解的sum是一个总的
     * 如果采用先遍历右子树，那么只需要将所有右子树的值加在sum即可
     * 因为右子树一定比左子树大，没想到用一个全局sum
     */
//    public int dfs(TreeNode root, int sum) {
//        if (root == null) {
//            return 0;
//        }
//
//        int right = dfs(root.right, sum);
//        root.val = root.val + right;
//        int left = dfs(root.left, right);
//        System.out.println("sum = " + sum);
//        return root.val;
//    }

    // 1423. 可获得的最大点数
    public int maxScore(int[] cardPoints, int k) {
        int sum = 0, windowsSum = 0, windowLength = 0, min = Integer.MAX_VALUE;
        for (int i = 0; i < cardPoints.length; i++) {
            sum += cardPoints[i];
            if (windowLength < cardPoints.length - k) {
                windowLength ++;
            } else {
                windowsSum -= cardPoints[i - (cardPoints.length - k)];
            }
            windowsSum += cardPoints[i];
            if (windowLength == cardPoints.length - k) {
                min = Math.min(windowsSum, min);
            }
        }

        return sum - min;
    }

    // 1094. 拼车
    public boolean carPooling(int[][] trips, int capacity) {
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(a -> a[2]));
        Arrays.sort(trips, Comparator.comparingInt(a -> a[1]));

        for (int[] trip : trips) {
            while (!priorityQueue.isEmpty() && priorityQueue.peek()[2] <= trip[1]) {
                int[] temp = priorityQueue.poll();
                capacity += temp[0];
            }
            if (capacity >= trip[0]) {
                capacity -= trip[0];
                priorityQueue.add(trip);
            } else {
                return false;
            }
        }

        return true;
    }

    // 2661. 找出叠涂元素
    public int firstCompleteIndex(int[] arr, int[][] mat) {
        Map<Integer, int[]> map = new HashMap<>();
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[0].length; j++) {
                map.put(mat[i][j], new int[]{i, j});
            }
        }

        Map<Integer, Map<Integer, List<Integer>>> resultMap = new HashMap<>();
        int res = Integer.MAX_VALUE;
        for (int i = 0; i < arr.length; i++) {
            int[] tempArr = map.get(arr[i]);
            // 0只记录行，1记录列
            setData(resultMap, tempArr, i, 0);
            setData(resultMap, tempArr, i, 1);

            if (resultMap.get(0).get(tempArr[0]).size() == mat[0].length) {
                return i;
            }

            if (resultMap.get(1).get(tempArr[1]).size() == mat.length) {
                return i;
            }
        }

        return res;
    }

    public void setData(Map<Integer, Map<Integer, List<Integer>>> resultMap, int[] tempArr, int index, int mark) {
        if (resultMap.get(mark) == null) {
            // 这个temp，它的key是每一行的下标，value是对应填充的元素
            Map<Integer, List<Integer>> temp = new HashMap<>();
            List<Integer> hang = new ArrayList<>();
            hang.add(index);
            temp.put(tempArr[mark], hang);
            resultMap.put(mark, temp);
        } else {
            if (resultMap.get(mark).get(tempArr[mark]) == null) {
                List<Integer> hang = new ArrayList<>();
                hang.add(index);
                resultMap.get(mark).put(tempArr[mark], hang);
            } else {
                resultMap.get(mark).get(tempArr[mark]).add(index);
            }
        }
    }

    // 1657. 确定两个字符串是否接近
    public boolean closeStrings(String word1, String word2) {
        if (word1.length() != word2.length()) {
            return false;
        }

        int[] char1 = new int[26];
        int[] char2 = new int[26];

        for (int i = 0; i < word1.length(); i++) {
            char1[word1.charAt(i) - 'a']++;
            char2[word2.charAt(i) - 'a']++;
        }
        for (int i = 0; i < 26; i++) {
            if (char1[i] > 0 && char2[i] == 0 || char1[i] == 0 && char2[i] > 0) {
                return false;
            }
        }

        Arrays.sort(char1);
        Arrays.sort(char2);
        return Arrays.equals(char1, char2);
    }

    // 907. 子数组的最小值之和
    public int sumSubarrayMins(int[] arr) {
        int n = arr.length;
        Deque<Integer> monoStack = new ArrayDeque<>();
        int[] left = new int[n];
        int[] right = new int[n];

        // 以数组[3, 1, 2, 4]为例子
        // left[i] = [1, 2, 1, 1] 代表了以arr[i]为最右边，且arr[i]最小的子序列数目。
        // 3只有他自己时是最小，1的话有它自己，外加3，1这个组合
        // 2，4因为左边有比它小的1，所以他们也只包含了他们自己，所以是1
        for (int i = 0; i < n; i++) {
            // 这里的单调栈，是小元素在下，大元素在上，如果一直没有比该元素小的，那么该元素就一直处于最低端
            while (!monoStack.isEmpty() && arr[i] <= arr[monoStack.peek()]) {
                monoStack.pop();
            }
            left[i] = i - (monoStack.isEmpty() ? -1 : monoStack.peek());
            monoStack.push(i);
        }
        monoStack.clear();

        // right = [1, 3, 2, 1],代表了以该元素为最左且该元素为最小的子序列数目
        // 3的话，只有它本身，而1，不仅包括他本身，而且还有1，2和1，2，4这两个组合
        // 2包括了2，4和2本身，4只有他本身
        for (int i = n - 1; i >= 0; i--) {
            while (!monoStack.isEmpty() && arr[i] < arr[monoStack.peek()]) {
                monoStack.pop();
            }
            right[i] = (monoStack.isEmpty() ? n : monoStack.peek()) - i;
            monoStack.push(i);
        }

        long ans = 0;
        final int MOD = 1000000007;
        for (int i = 0; i < n; i++) {
            ans = (ans + (long) left[i] * right[i] * arr[i]) % MOD;
        }

        return (int) ans;
    }

    // 828. 统计子串中的唯一字符
    public int uniqueLetterString(String s) {
        Map<Character, List<Integer>> index = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            // 注意，这里在list中添加的是下标
            if (!index.containsKey(c)) {
                index.put(c, new ArrayList<>());
                index.get(c).add(-1);
            }
            index.get(c).add(i);
        }

        int res = 0;
        for (Map.Entry<Character, List<Integer>> entry : index.entrySet()) {
            List<Integer> arr = entry.getValue();
            arr.add(s.length());
            for (int i = 1; i < arr.size() - 1; i++) {
                res += (arr.get(i) - arr.get(i - 1)) * (arr.get(i + 1) - arr.get(i));
            }
        }

        return res;
    }

    // 1457. 二叉树中的伪回文路径
    int res = 0;
    public int pseudoPalindromicPaths (TreeNode root) {
        Map<Integer, Integer> map = new HashMap<>();
        dfs(root, map);
        return res;
    }

    public void dfs(TreeNode root, Map<Integer, Integer> map) {
        if (root == null) {
            return;
        }
        map.merge(root.val, 1, Integer::sum);
        if (root.left == null && root.right == null) {
            int temp = 0;
            for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                if (entry.getValue() % 2 != 0) {
                    temp++;
                }
            }
            if (temp == 1 || temp == 0) {
                res++;
            }
        }

        dfs(root.left, map);
        dfs(root.right, map);

        if (map.get(root.val) > 1) {
            map.put(root.val, map.get(root.val) - 1);
        } else {
            map.remove(root.val);
        }
    }

    // 2824. 统计和小于目标的下标对数目
    public int countPairs(List<Integer> nums, int target) {
        int res = 0;
        for (int i = 0; i < nums.size(); i++) {
            for (int j = i + 1; j < nums.size(); j++) {
                if (nums.get(i) + nums.get(j) < target) {
                    res++;
                }
            }
        }

        return res;
    }

    // 1410. HTML 实体解析器
    public String entityParser(String text) {
        StringBuilder res = new StringBuilder();
        Map<String, Character> map = new HashMap<>();
        map.put("&quot;", '"');
        map.put("&apos;", '\'');
        map.put("&amp;", '&');
        map.put("&gt;", '>');
        map.put("&lt;", '<');
        map.put("&frasl;", '/');

        int mark = 0;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c == '&' && i + 1 < text.length() && text.charAt(i + 1) != '&') {
                int count = 0;
                StringBuilder temp = new StringBuilder();
                temp.append('&');
                for (int j = i + 1; j < text.length(); j++) {
                    count++;
                    char str = text.charAt(j);
                    temp.append(str);
                    if (str == ';') {
                        break;
                    }
                    if (j + 1 < text.length()) {
                        if (text.charAt(j + 1) == '&') {
                            break;
                        }
                    }
                }
                System.out.println(temp.toString());
                if (map.get(temp.toString()) != null) {
                    res.append(map.get(temp.toString()));
                } else {
                    res.append(temp.toString());
                }
                i += count;
                mark = 1;
            }
            if (mark == 0) {
                res.append(c);
            }
            mark = 0;
        }

        return res.toString();
    }

    // 2216. 美化数组的最少删除数
    public int minDeletion(int[] nums) {
        int res = 0;
        for (int i = 0; i < nums.length; i++) {
            if ((i - res) % 2 == 0) {
                if (i + 1 < nums.length) {
                    if (nums[i] == nums[i + 1]) {
                        res++;
                    }
                }
            }
        }

        if ((nums.length - res) % 2 != 0) {
            res++;
        }

        return res;
    }

    // 53. 最大子数组和
    public int maxSubArray(int[] nums) {
        int max = nums[0];
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            dp[i] = Math.max(nums[i], dp[i - 1] + nums[i]);
            max = Math.max(dp[i], max);
        }

        return max;
    }

    /**
     * 我自己的写法用了一个滑动窗口，但实际上三个窗口不交叉的滑动，一次遍历就过了
     */
    // 689.三个无重叠子数组组大和
    public int[] maxSumOfThreeSubarrays(int[] nums, int k) {
        int[] ans = new int[3];
        int sum1 = 0, maxSum1 = 0, maxSum1Idx = 0;
        int sum2 = 0, maxSum12 = 0, maxSum12Idx1 = 0, maxSum12Idx2 = 0;
        int sum3 = 0, maxTotal = 0;
        for (int i = k * 2; i < nums.length; ++i) {
            sum1 += nums[i - k * 2];
            sum2 += nums[i - k];
            sum3 += nums[i];
            if (i >= k * 3 - 1) {
                if (sum1 > maxSum1) {
                    maxSum1 = sum1;
                    maxSum1Idx = i - k * 3 + 1;
                }
                if (maxSum1 + sum2 > maxSum12) {
                    maxSum12 = maxSum1 + sum2;
                    maxSum12Idx1 = maxSum1Idx;
                    maxSum12Idx2 = i - k * 2 + 1;
                }
                if (maxSum12 + sum3 > maxTotal) {
                    maxTotal = maxSum12 + sum3;
                    ans[0] = maxSum12Idx1;
                    ans[1] = maxSum12Idx2;
                    ans[2] = i - k + 1;
                }
                sum1 -= nums[i - k * 3 + 1];
                sum2 -= nums[i - k * 2 + 1];
                sum3 -= nums[i - k + 1];
            }
        }

        return ans;

//        int[] res = new int[3];
//        int[] addSum = new int[nums.length - k + 1];
//        int index = 0, sum = 0;
//        for (int i = 0; i < nums.length; i++) {
//            if (i < k - 1) {
//                sum += nums[i];
//                if (i == k - 1) {
//                    addSum[index] = sum;
//                }
//            } else {
//                index ++;
//                addSum[index] = addSum[index - 1] + nums[i] - nums[index - 1];
//            }
//        }
//
//        return res;
    }

    // 2342.数位和相等数对的最大和
    public int maximumSum(int[] nums) {
        Map<Integer, PriorityQueue<Integer>> map = new HashMap<>();
        int max = 0;
        for (int i = 0; i < nums.length; i++) {
            int sum = 0, temp = nums[i];
            while (temp > 0) {
                sum += temp % 10;
                temp /= 10;
            }

            if (map.get(sum) == null) {
                PriorityQueue<Integer> queue = new PriorityQueue<>((a, b) -> b - a);
                queue.offer(nums[i]);
                map.put(sum, queue);
            } else {
                map.get(sum).offer(nums[i]);
            }
        }

        for (Map.Entry<Integer, PriorityQueue<Integer>> entry : map.entrySet()) {
            PriorityQueue<Integer> queue = entry.getValue();
            System.out.println(queue.toString());
            if (queue.size() < 2) {
                continue;
            }

            int first = queue.poll();
            int second = queue.poll();
            max = Math.max(max, first + second);
        }
        return max;
    }



    // 2656. K 个元素的最大和
    public int maximizeSum(int[] nums, int k) {

//        PriorityQueue<Integer> queue = new PriorityQueue<>((a, b) -> b - a);
//        for (int num : nums) {
//            queue.add(num);
//        }
//
//        for (int i = 0; i < k; i++) {
//            int temp = queue.poll();
//            sum += temp;
//            queue.add(temp + 1);
//        }
        int sum = 0, max = 0;
        for (int num : nums) {
            if (max < num) {
                max = num;
            }
        }
        sum += max * k + (k * (k - 1)) / 2;
        return sum;
    }

    // 2736. 最大和查询
    public int[] maximumSumQueries(int[] nums1, int[] nums2, int[][] queries) {
        int n = nums1.length;

        // 这一段代码，是按照nums1的值降序排列
        int[][] sortedNums = new int[n][2];
        for (int i = 0; i < n; i++) {
            sortedNums[i][0] = nums1[i];
            sortedNums[i][1] = nums2[i];
        }
        Arrays.sort(sortedNums, (a, b) -> b[0] - a[0]);

        // 这一段，是按照查询的范围的第一个值降序排列
        // 降序排列可以确保后续查询的第一个坐标都在前一个查询的范围内，这样也可以确保不回退sortedNums
        int q = queries.length;
        int[][] sortedQueries = new int[q][3];
        for (int i = 0; i < q; i++) {
            // 这里记录i，是因为排过序后，答案需要记录原先的顺序
            sortedQueries[i][0] = i;
            sortedQueries[i][1] = queries[i][0];
            sortedQueries[i][2] = queries[i][1];
        }
        Arrays.sort(sortedQueries, (a, b) -> b[1] - a[1]);


        List<int[]> stack = new ArrayList<>();
        int[] answer = new int[q];
        Arrays.fill(answer, -1);

        int j = 0;
        for (int[] query : sortedQueries) {
            int i = query[0], x = query[1], y = query[2];
            // 这里是遍历sortedNums，对于所有满足nums[i] > query[i][0]的，进行处理
            while (j < n && sortedNums[j][0] >= x) {
                // 取出满足条件的数组
                int[] pair = sortedNums[j];
                int num1 = pair[0], num2 = pair[1];

                // 这里，如果栈顶的末位元素的 nums1 + nums2 < 此次计算的num1 + nums2，将栈顶元素移除
                // 这个栈是按照nums1 + nums2 的大小由大到小的
                // 这个栈，是按照nums[2]的值单调递增的
                // 这里需要注意，因为nums1的值是变得越来越小的，当nums1 + nums2 > 之前的和，只可能是nums2变得更大
                // 所以栈中的nums2一定是单调递增的。
                while (!stack.isEmpty() && stack.get(stack.size() - 1)[1] <= num1 + num2) {
                    stack.remove(stack.size() - 1);
                }

                // 这里是先不管nums2是否满足条件，先放入栈中，然后在二分查找的时候去判断是否满足
                if (stack.isEmpty() || stack.get(stack.size() - 1)[0] < num2) {
                    stack.add(new int[]{num2, num1 + num2});
                }
                j++;
            }

            // 真正看y也就是nums2是否满足条件是在二分查找这里判断的，上述出栈和进栈的过程只是保证nums1 + num2的值
            // 二分查找是根据y来查找的，
            int k = binarySearch(stack, y);
            if (k < stack.size()) {
                answer[i] = stack.get(k)[1];
            }
        }
        return answer;
    }

    public int binarySearch(List<int[]> list, int target) {
        int low = 0, high = list.size();
        while (low < high) {
            int mid = low + (high - low) / 2;
            if (list.get(mid)[0] >= target) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }
        return low;
    }

    // 2760. 最长奇偶子数组
    public int longestAlternatingSubarray(int[] nums, int threshold) {
        int left = 0, max = 0;
        while (left != nums.length) {
            if (nums[left] % 2 == 0 && nums[left] <= threshold) {
                int right = left + 1;
                int temp = 0;
                while (right != nums.length && nums[right] % 2 != temp && nums[right] <= threshold) {
                    temp = nums[right] % 2;
                    right++;
                }
                max = Math.max(max, right - left);
            }
            left ++;
        }

        return max;
    }

    // 1334. 阈值距离内邻居最少的城市
    public int findTheCity(int n, int[][] edges, int distanceThreshold) {
        int[] ans = {Integer.MAX_VALUE / 2, -1};
        int[][] dis = new int[n][n];
        boolean[][] vis = new boolean[n][n];
        int[][] mp = new int[n][n];
        for (int i = 0; i < n; ++i) {
            Arrays.fill(dis[i], Integer.MAX_VALUE / 2);
            Arrays.fill(mp[i], Integer.MAX_VALUE / 2);
        }

        for (int[] eg : edges) {
            int from = eg[0], to = eg[1], weight = eg[2];
            mp[from][to] = mp[to][from] = weight;
        }

        for (int i = 0; i < n; ++i) {
            dis[i][i] = 0;
            for (int j = 0; j < n; ++j) {
                int t = -1;
                for (int k = 0; k < n; ++k) {
                    if (!vis[i][k] && (t == -1 || dis[i][k] < dis[i][t])) {
                        t = k;
                    }
                }
                for (int k = 0; k < n; ++k) {
                    dis[i][k] = Math.min(dis[i][k], dis[i][t] + mp[t][k]);
                }
                vis[i][t] = true;
            }
        }

        for (int i = 0; i < n; ++i) {
            int cnt = 0;
            for (int j = 0; j < n; ++j) {
                if (dis[i][j] <= distanceThreshold) {
                    cnt++;
                }
            }
            if (cnt <= ans[0]) {
                ans[0] = cnt;
                ans[1] = i;
            }
        }

        return ans[1];
//        Map<Integer, List<Map<Integer, Integer>>> map = new HashMap<>();
//        for (int i = 0; i < edges.length; i++) {
//            Map<Integer, Integer> partMap = new HashMap<>();
//            partMap.put(edges[i][1], edges[i][2]);
//            if (map.get(edges[i][0]) == null) {
//                List<Map<Integer, Integer>> list = new ArrayList<>();
//                list.add(partMap);
//                map.put(edges[i][0], list);
//            } else {
//                map.get(edges[i][0]).add(partMap);
//            }
//        }
//
//        int res = 0;
//        Queue<Integer> queue = new ArrayDeque<>();
//        for (int i = 0; i < n; i++) {
//            queue.add(i);
//            int[] arr = new int[n];
//            Arrays.fill(arr, 0);
//            while (!queue.isEmpty()) {
//                int size = queue.size();
//                for (int j = 0; j < size; j++) {
//                    int node = queue.poll();
//                    if (arr[node] == 1) {
//                        continue;
//                    }
//                    arr[node] = 1;
//                    List<Map<Integer, Integer>> list = map.get(node);
//                }
//            }
//        }
//
//        return res;
    }

}
