package com.example.springboot_vue.leetcode;

import java.util.*;

class ScoreAndPerson {
    int age;
    int score;

    public ScoreAndPerson(int age, int score) {
        this.age = age;
        this.score = score;
    }
}

public class LeetCodeMain4 {

    public static void main(String[] args) {
        String str = "ababbbb";
        LeetCodeMain4 leetCodeMain4 = new LeetCodeMain4();
        int[] arr = {1,2,3,10,0,7,8,9};
        System.out.println(leetCodeMain4.findLengthOfShortestSubarray(arr));
    }

    // 2395. 和相等的子数组
    public boolean findSubarrays(int[] nums) {
        Set<Integer> set = new HashSet<>();

        for (int i = 1; i < nums.length; i++) {
            if (set.contains(nums[i] + nums[i - 1])) {
                return true;
            }
            set.add(nums[i - 1] + nums[i]);
        }

        return false;
    }

    // 1574. 删除最短的子数组使剩余数组有序
    public int findLengthOfShortestSubarray(int[] arr) {
//        int res = 0;
//        Stack<Integer> stack = new Stack<>();
//        stack.push(arr[0]);
//
//        for (int i = 1; i < arr.length; i++) {
//            if (arr[i] < stack.peek()) {
//                while (!stack.isEmpty() && stack.peek() > arr[i]) {
//                    stack.pop();
//                    res++;
//                }
//            }
//            stack.push(arr[i]);
//        }
        int n = arr.length, j = n - 1;
        // 这里是从后往前遍历，找到第一个j-1大于j的元素，然后用res记录从0-j，意思是这段全删
        // 因为是从后往前遍历，所以后边这一段是符合题意的
        while (j > 0 && arr[j - 1] <= arr[j]) {
            j--;
        }
        if (j == 0) {
            return 0;
        }
        int res = j;
        // 这里的思路是，i从前往后走，看看有多少是符合题意的，把i往后移的过程会缩短i到j的距离，所以
        for (int i = 0; i < n; i++) {
            while (j < n && arr[j] < arr[i]) {
                j++;
            }
            res = Math.min(res, j - i - 1);
            // 这里跳出是因为i + 1已经比i大，后边不可能有更短的了
            if (i + 1 < n && arr[i] > arr[i + 1]) {
                break;
            }
        }

        return res;
    }

    public int getMaxValue(String str, int k) {
        int n = str.length();
        if (k >= n) {
            return n * countDistinctChars(str);
        }
        int[][] dp = new int[k + 1][n + 1];
        for (int i = 1; i <= k; i++) {
            for (int j = i; j <= n; j++) {
                if (i == 1) {
                    dp[i][j] = j * countDistinctChars(str.substring(0, j));
                } else {
                    dp[i][j] = Integer.MAX_VALUE;
                    for (int m = i - 2; m < j; m++) {
                        int val = dp[i - 1][m] + (j - m) * countDistinctChars(str.substring(m, j));
                        dp[i][j] = Math.min(dp[i][j], val);
                    }
                }
            }
        }
        return dp[k][n];
    }

    private int countDistinctChars(String str) {
        Set<Character> set = new HashSet<>();
        for (char c : str.toCharArray()) {
            set.add(c);
        }
        return set.size();
    }

    // 1630. 等差子数组
    public List<Boolean> checkArithmeticSubarrays(int[] nums, int[] l, int[] r) {
        List<Boolean> res = new ArrayList<>();

        for (int i = 0; i < l.length; i++) {
            int[] arr = Arrays.copyOfRange(nums, l[i], r[i] + 1);
            Arrays.sort(arr);
            if (arr.length == 1 || arr.length == 2) {
                res.add(true);
                continue;
            }

            int mark = arr[1] - arr[0], test = 0;
            for (int j = 2; j < arr.length; j++) {
                if (arr[j] - arr[j - 1] != mark) {
                    res.add(false);
                    test = 1;
                    break;
                }
            }

            if (test == 0) {
                res.add(true);
            }
        }

        return res;
    }

    // 1626. 无矛盾的最佳球队
    public int bestTeamScore(int[] scores, int[] ages) {
        int res, score, age;

        ArrayList<ScoreAndPerson> list = new ArrayList<>();
        for (int i = 0; i < scores.length; i++) {
            list.add(new ScoreAndPerson(ages[i], scores[i]));
        }
        list.sort(Comparator.comparingInt(a -> a.age));

        for (int i = 0; i < list.size(); i++) {
            System.out.println("score = " + list.get(i).score + " age = " + list.get(i).age);
        }

        int[] dp = new int[scores.length];
        dp[0] = list.get(0).score;
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        queue.offer(list.get(0).score);
        for (int i = 1; i < list.size(); i++) {
            System.out.println("queue = " + queue.toString());
            System.out.println("dp = " + Arrays.toString(dp));
            age = list.get(i).age;
            score = list.get(i).score;
            res = dp[i - 1];
            if (age == list.get(i - 1).age) {
                dp[i] = res + score;
                queue.offer(list.get(i).score);
            } else {
                if (!queue.isEmpty() && score < queue.peek()) {
                    res -= queue.poll();
                } else {
                    queue.offer(list.get(i).score);
                }
                dp[i] = Math.max(dp[i - 1], res + score);
            }
        }

        return dp[scores.length - 1];
    }

    // 2469. 温度转换
    public double[] convertTemperature(double celsius) {
        double[] doubles = new double[2];

        doubles[0] = celsius + 273.15;
        doubles[1] = celsius * 1.80 + 32.0;

        return doubles;
    }

    // 1012. 至少有 1 位重复的数字
    public int numDupDigitsAtMostN(int n) {
        int res = 0;

        return res;
    }

    // 1625. 执行操作后字典序最小的字符串
    public String findLexSmallestString(String s, int a, int b) {
        StringBuilder stringBuilder = new StringBuilder();

        if (b % 2 != 0) {
            for (int i = 0; i < s.length(); i++) {
                int c = s.charAt(i) - '0';
                stringBuilder.append(getMin(c, a));
            }
        } else {
            for (int i = 0; i < s.length(); i ++) {
                int c = s.charAt(i) - '0';
                if (i % 2 == 0) {
                    stringBuilder.append(c);
                    continue;
                }
                stringBuilder.append(getMin(c, a));
            }
        }



        return stringBuilder.toString();
    }

    int getMin(int c, int a) {
        Set<Integer> set = new HashSet<>();
        int mark = (c + a) % 10, min = Math.min(mark, c);
        set.add(c);
        while (!set.contains(mark)) {
            set.add(mark);
            mark = (mark + a) % 10;
            min = Math.min(mark, min);
        }
        return min;
    }

    // 1616. 分割两个字符串得到回文串
    public boolean checkPalindromeFormation(String a, String b) {
        int left = 0, right = b.length() - 1;
        // 下标为0的情况
        if (judge(a) || judge(b)) {
            return true;
        }

        return true;
    }

    boolean judge(String str) {
        int left = 0, right = str.length() - 1;
        while (left < right) {
            if (str.charAt(left) == str.charAt(right)) {
                left++;
                right--;
            } else {
                return false;
            }
        }
        return true;
    }

    // 2389. 和有限的最长子序列
    public int[] answerQueries(int[] nums, int[] queries) {
        int[] res = new int[queries.length];
        int index = 0;

        Arrays.sort(nums);
        for (int query : queries) {
            int part = 0, count = 0, mark = 0;
            for (int num : nums) {
                if (part + num <= query) {
                    part += num;
                    count++;
                } else {
                    res[index++] = count;
                    mark = 1;
                    break;
                }
            }

            if (mark == 0) {
                res[index++] = count;
            }
        }

        return res;
    }

    // 2488. 统计中位数为 K 的子数组
    public int countSubarrays(int[] nums, int k) {
        int res = 1;
        int indexK = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == k) {
                indexK = i;
                break;
            }
        }

        Map<Integer, Integer> map = new HashMap<>();
        int[] leftArr = new int[indexK + 1];
        leftArr[indexK] = 0;
        for (int i = indexK - 1; i >= 0; i--) {
            if (nums[i] > k) {
                leftArr[i] = leftArr[i + 1] + 1;
            } else {
                leftArr[i] = leftArr[i + 1] - 1;
            }
            if (leftArr[i] == 1 || leftArr[i] == 0) {
                res ++;
            }
            if (map.get(leftArr[i]) == null) {
                map.put(leftArr[i], 1);
            } else {
                map.put(leftArr[i], map.get(leftArr[i]) + 1);
            }
        }

        int[] rightArr = new int[nums.length - indexK];
        rightArr[0] = 0;
        for (int i = indexK + 1; i < nums.length; i++) {
            if (nums[i] > k) {
                rightArr[i - indexK] = rightArr[i - 1 - indexK] + 1;
            } else {
                rightArr[i - indexK] = rightArr[i - 1 - indexK] - 1;
            }
            if (rightArr[i - indexK] == 0 || rightArr[i - indexK] == 1) {
                res ++;
            }
        }

        for (int i = 1; i < rightArr.length; i++) {
            if (map.get(-rightArr[i]) != null) {
                res += map.get(-rightArr[i]);
            }
            if (map.get(1 - rightArr[i]) != null) {
                res += map.get(1 - rightArr[i]);
            }
        }

        return res;
    }

    // 786. 第 K 个最小的素数分数
    public int[] kthSmallestPrimeFraction(int[] arr, int k) {
        double[] part = new double[arr.length * (arr.length - 1) / 2];
        Map<Double, int[]> map = new HashMap<>();

        int index = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                part[index++] = (double) arr[i] / arr[j];
                map.put(part[index - 1], new int[]{arr[i], arr[j]});
            }
        }

        Arrays.sort(part);
        return map.get(part[k - 1]);
    }

    // 2109. 向字符串添加空格
    public String addSpaces(String s, int[] spaces) {
        StringBuilder stringBuilder = new StringBuilder(s);

        int index = 0;
        for (int i = 0; i < s.length(); i++) {
            if (i == spaces[index]) {
                stringBuilder.insert(i + index, " ");
                index++;

                if (index == spaces.length) {
                    break;
                }
            }
        }

        return stringBuilder.toString();
    }

    // 1615. 最大网络秩
    public int maximalNetworkRank(int n, int[][] roads) {
        int res = 0;
        boolean[][] bool = new boolean[n][n];
        int[] arr = new int[n];

        for (int[] road : roads) {
            bool[road[0]][road[1]] = true;
            bool[road[1]][road[0]] = true;
            arr[road[0]]++;
            arr[road[1]]++;
        }

        int part = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                res = Math.max(arr[i] + arr[j] - ((bool[i][j]) ? 1 : 0), res);
            }
        }

        return res;
    }

    // 1605. 给定行和列的和求可行矩阵
    public int[][] restoreMatrix(int[] rowSum, int[] colSum) {
        int[][] res = new int[rowSum.length][colSum.length];

        for (int i = 0; i < rowSum.length; i++) {
            int partSum = 0;
            for (int j = 0; j < colSum.length; j++) {

            }
        }

        return res;
    }

    // 2383. 赢得比赛需要的最少训练时长
    public int minNumberOfHours(int initialEnergy, int initialExperience, int[] energy, int[] experience) {
        int res = 0;
        int sum = Arrays.stream(energy).sum();
        if (initialEnergy <= sum) {
            res += sum - initialEnergy + 1;
        }

        if (initialExperience < experience[0]) {
            res += experience[0] - initialExperience + 1;
            initialExperience = experience[0] + 1;
        }

        for (int i = 0; i < energy.length; i++) {
            if (initialExperience <= experience[i]) {
                res += (experience[i] - initialExperience + 1);
                initialExperience += (experience[i] - initialExperience + 1);
            }
            initialExperience += experience[i];
        }

        return res;
    }

    // 1590. 使数组和能被 P 整除
    public int minSubarray(int[] nums, int p) {
        int[] arr = new int[nums.length + 1];
        arr[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            arr[i] += arr[i - 1] + nums[i];
        }

        if (arr[nums.length - 1] % p == 0) {
            return 0;
        }

        return 1;
    }

    // 2379. 得到 K 个黑块的最少涂色次数
    public int minimumRecolors(String blocks, int k) {
        int res = 0, min = 101;
        Queue<Character> queue = new ArrayDeque<>();

        for (int i = 0; i < blocks.length(); i++) {
            char c = blocks.charAt(i);
            if (c == 'W') {
                if (queue.size() == k) {
                    min = Math.min(res, min);
                }
                res++;
            }
            queue.offer(c);

            // 队列中数据元素大于k，说明已经存在连续k个
            if (queue.size() > k) {
                if (queue.poll() == 'W') {
                    res--;
                    min = Math.min(res, min);
                }
            }

        }
        min = Math.min(res, min);
        return min;
    }

    // 剑指 Offer 47. 礼物的最大价值
    public int maxValue(int[][] grid) {
        int[][] dp = new int[grid.length][grid[0].length];
        dp[0][0] = grid[0][0];
        // 首先统计第一行
        for (int i = 1; i < grid[0].length; i++) {
            dp[0][i] = grid[0][i] + dp[0][i - 1];
        }
        // 统计第一列
        for (int i = 1; i < grid.length; i++) {
            dp[i][0] += grid[i][0] + dp[i - 1][0];
        }

        for (int i = 1; i < grid.length; i++) {
            for (int j = 1; j < grid[0].length; j++) {
                dp[i][j] = Math.max(dp[i - 1][j] + grid[i][j], dp[i][j - 1] + grid[i][j]);
            }
        }

        return dp[grid.length - 1][grid[0].length - 1];
    }
    
    // 42. 接雨水
    public int trap(int[] height) {
        int res = 0;

        return res;
    }

    // 1653. 使字符串平衡的最少删除次数
    public int minimumDeletions(String s) {
        int n = s.length();
        // 这里f表示遍历到当前位置，删除最少的
        int[] f = new int[n + 1];
        int b = 0;
        for (int i = 1; i <= n; ++i) {
            // b记录遍历到当前位置，统计了多少个b字符的个数
            // 如果当前字符为b，那么按照这种思路，并不影响之前的平衡
            if (s.charAt(i - 1) == 'b') {
                f[i] = f[i - 1];
                ++b;
            } else {
                // 如果为a，则选择删除当前a，或者删除之前的所有b的个数
                // 所以取f[i - 1] + 1 意思是多删除一个，或者删除所有的b
                f[i] = Math.min(f[i - 1] + 1, b);
            }
        }

        return f[n];
    }

    // 1599. 经营摩天轮的最大利润
    public int minOperationsMaxProfit(int[] customers, int boardingCost, int runningCost) {
        int res = 0, resultCustomer = 0, max = (int) -1e9, count = 0;
        for (int i = 0; i < customers.length; i++) {
            if (customers[i] + resultCustomer >= 4) {
                res = res + 4 * boardingCost - runningCost;
                resultCustomer = customers[i] + resultCustomer - 4;
            } else {
                res += customers[i] * boardingCost - runningCost;
            }

            if (max < res) {
                count = i + 1;
            }
            max = Math.max(res, max);
        }

        if (resultCustomer > 0) {
            count = customers.length;
        }

        while (resultCustomer > 0) {
            if (resultCustomer > 4) {
                res += 4 * boardingCost - runningCost;
                resultCustomer -= 4;
            } else {
                res += boardingCost * resultCustomer - runningCost;
                resultCustomer = 0;
            }
            if (res > max) {
                max = res;
                count++;
            }
        }
        if (max > 0) {
            return count;
        } else {
            return -1;
        }
    }

    // 982. 按位与为零的三元组
    public int countTriplets(int[] nums) {
        int res = 0;

        for (int j : nums) {
            for (int k : nums) {
                for (int num : nums) {
                    if ((j & k & num) == 0) {
                        res++;
                    }
                }
            }
        }

        return res;
    }

    //
    public int countSegments(String s) {
        if (s == null || s.equals("")) {
            return 0;
        }

        int res = s.split(" ").length;

        return res;
    }

    // 354. 俄罗斯套娃信封问题
    public int maxEnvelopes(int[][] envelopes) {
        int res = 1;

        return res;
    }

    // 1487. 保证文件名唯一
    public String[] getFolderNames(String[] names) {
        String[] res = new String[names.length];
        StringBuilder stringBuilder;
        int index, resIndex = 0;
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < names.length; i++) {
            if (!map.containsKey(names[i])) {
                map.put(names[i], 1);
                res[resIndex++] = names[i];
            } else {
                stringBuilder = new StringBuilder(names[i]);
                index = map.get(names[i]);
                map.put(names[i], index);
                stringBuilder.append("(").append(index).append(")");
                while (map.containsKey(stringBuilder.toString())) {
                    index++;
                    stringBuilder.replace(stringBuilder.lastIndexOf("(") + 1, stringBuilder.lastIndexOf(")"), String.valueOf(index));

                }
                map.put(stringBuilder.toString(), 1);
                res[resIndex++] = stringBuilder.toString();
            }
        }

        return res;
    }

    // 面试题 17.06. 2出现的次数
    public int numberOf2sInRange(int n) {
        int res = 0;

        for (int i = 2; i <= n; i++) {

        }

        return res;
    }

    // 面试题 05.02. 二进制数转字符串
    public String printBin(double num) {

        if (num > 1) {
            return "ERROR";
        }

        StringBuilder stringBuilder = new StringBuilder();
        String[] strings = String.valueOf(num).split("\\.");

        int length = strings[1].length(), count = 0;
        Queue<Integer> queue = new ArrayDeque<>();
        int val = Integer.parseInt(strings[1]);
        int division = (int) Math.pow(10, length);
        while (val != 0) {
            count++;
            if (count > 32) {
                return "ERROR";
            }
            queue.offer(val * 2 / division);
            val = ((val * 2) % division);
        }
        if (stringBuilder.length() == 0) {
            stringBuilder.append("0");
        }
        stringBuilder.append(".");
        while (!queue.isEmpty()) {
            stringBuilder.append(queue.poll());
        }
        return stringBuilder.toString();
    }

    // 25. K 个一组翻转链表
    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode countNode = head;
        int count = 0;
        while (countNode != null) {
            count++;
            countNode = countNode.next;
        }

        ListNode res = new ListNode();
        int size = 0;
        while (head != null) {
            size++;
            count--;
            ListNode node = new ListNode();
            node.val = head.val;
            head = head.next;
            if (count < k) {

            }
        }

        return res.next;
    }

    // 17. 电话号码的字母组合
    public List<String> letterCombinations(String digits) {
        List<String> list = new ArrayList<>();
        String[][] map = {{}, {}, {"a", "b", "c"}, {"d", "e", "f"}, {"g", "h", "i"}, {"j", "k", "l"}, {"m", "n", "o"},
                {"p", "q", "r", "s"}, {"t", "u", "v"}, {"w", "x", "y", "z"}};

        if (digits.length() == 1) {
            Collections.addAll(list, map[digits.charAt(0) - '0']);
        }

        char[] arr = new char[digits.length()];
        for (int i = 0; i < digits.length(); i++) {
            arr[i] = digits.charAt(i);
        }

        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                for (int k = 0; k < map[arr[i] - '0'].length; k++) {
                    for (int l = 0; l < map[arr[j] - '0'].length; l++) {
                        list.add(map[arr[i] - '0'][k] + map[arr[j] - '0'][l]);
                    }
                }
            }
        }

        return list;
    }

    void dfs(char[] arr, List<String> list) {

    }

    // 2373. 矩阵中的局部最大值
    public int[][] largestLocal(int[][] grid) {
        int[][] res = new int[grid.length - 2][grid[0].length - 2];
        int index = 0;
        int n = grid.length;
        for (int i = 0; i < n - 2; i++) {
            for (int j = 0; j < n - 2; j++) {
                int max = 0;
                for (int k = i; k <= i + 2 && k < n; k++) {
                    for (int l = j; l <= j + 2 && l < n; l++) {
                        if (max < grid[k][l]) {
                            max = grid[k][l];
                        }
                    }
                }
                res[i][j] = max;
            }
        }

        return res;
    }

    // 899. 有序队列
    public String orderlyQueue(String s, int k) {
        char[] str = s.toCharArray();
        if (k >= 2) {
            Arrays.sort(str);
            return String.valueOf(str);
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            stringBuilder.append(s.charAt(i));
        }

        StringBuilder second = new StringBuilder(s);
        for (int i = 0; i < str.length; i++) {
            String part = s.substring(1, str.length);
            char c = s.charAt(0);
            String p = part + c;
            s = p;
            if (second.toString().compareTo(p) > 0) {
                second = new StringBuilder(p);
            }
        }

        return second.toString();
    }

    // 2363. 合并相似的物品
    public List<List<Integer>> mergeSimilarItems(int[][] items1, int[][] items2) {
        List<List<Integer>> res = new ArrayList<>();

        Map<Integer, Integer> map = new HashMap<>();

        for (int[] ints : items1) {
            map.put(ints[0], ints[1]);
        }

        for (int[] ints : items2) {
            List<Integer> list = new ArrayList<>();
            if (!map.containsKey(ints[0])) {
                map.put(ints[0], ints[1]);
            } else {
                map.put(ints[0], map.get(ints[0]) + ints[1]);
            }
            res.add(list);
        }

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            List<Integer> list = new ArrayList<>();
            list.add(entry.getKey());
            list.add(entry.getValue());
            res.add(list);
        }

        res.sort(Comparator.comparingInt(a -> a.get(0)));

        return res;
    }

    // 1144. 递减元素使数组呈锯齿状
    public int movesToMakeZigzag(int[] nums) {
        int res, single = 0, two = 0;
        int[] second = new int[nums.length];
        System.arraycopy(nums, 0, second, 0, nums.length);

        // nums[0] > nums[1]的情况
        for (int i = 0; i < nums.length - 1; i++) {
            if (i % 2 == 0) {
                if (nums[i] < nums[i + 1]) {
                    single += nums[i + 1] - (nums[i] - 1);
                    nums[i + 1] -= nums[i + 1] - (nums[i] - 1);
                }
            } else {
                if (nums[i] > nums[i + 1]) {
                    single += nums[i] - (nums[i + 1] - 1);
                    nums[i] -= nums[i] - (nums[i + 1] - 1);
                }
            }
        }

        for (int i = 0; i < second.length - 1; i++) {
            if (i % 2 == 0) {
                if (second[i] > second[i + 1]) {
                    two += second[i] - (second[i + 1] - 1);
                    second[i] -= second[i] - (second[i + 1] - 1);
                }
            } else {
                if (second[i] < second[i + 1]) {
                    two += second[i + 1] - (second[i] - 1);
                    second[i + 1] -= second[i + 1] - (second[i] - 1);
                }
            }
        }

        res = Math.min(single, two);
        return res;
    }

    // 1247. 交换字符使得字符串相同
    public int minimumSwap(String s1, String s2) {
        int countX = 0, countY = 0, diff = 0;
        char first, second;
        for (int i = 0; i < s1.length(); i++) {
            first = s1.charAt(i);
            second = s2.charAt(i);

            if (first == 'x') {
                countX++;
            } else {
                countY++;
            }

            if (second == 'x') {
                countX++;
            } else {
                countY++;
            }

            if (first != second) {
                diff++;
            }
        }

        if (countX % 2 != 0 || countY % 2 != 0 || diff % 2 != 0) {
            return -1;
        }

        int res = diff / 4;
        res = res * 2;
        int left = diff % 4;

        if (left != 0) {
            res += 2;
        }
        return res;
    }

    // 2357. 使数组中所有元素都等于零
    public int minimumOperations(int[] nums) {
        int res = 0, min = 101, sum = 0, mark = 0;

        for (int num : nums) {
            sum += num;
        }

        while (sum != 0) {
            for (int j : nums) {
                if (j == 0) {
                    continue;
                }
                if (j <= min) {
                    min = j;
                }
            }

            for (int i = 0; i < nums.length; i++) {
                if (nums[i] != 0) {
                    nums[i] -= min;
                    mark = 1;
                }
            }
            min = 101;
            if (mark == 1) {
                res++;
            }

            sum = Arrays.stream(nums).sum();
        }
//        Arrays.sort(nums);
//        int index = 0;
//        while (index != nums.length) {
//            if (nums[index] == 0) {
//                index++;
//                continue;
//            }
//            res++;
//            mark = nums[index];
//            for (int i = index; i < nums.length; i++) {
//                nums[i] -= mark;
//            }
//            index++;
//        }
        return res;
    }

    // 239. 滑动窗口最大值
    public int[] maxSlidingWindow(int[] nums, int k) {
        PriorityQueue<int[]> queue = new PriorityQueue<>((a, b) -> b[0] - a[0]);

        for (int i = 0; i < k && i < nums.length; i++) {
            queue.offer(new int[]{nums[i], i});
        }

        int[] res = new int[nums.length - k + 1];
        int index = 0;

        if (queue.peek() != null) {
            res[index++] = queue.peek()[0];
        }

        // 第一次优化，其实并不需要每一次都移除上一个元素，如果他并不是窗口中的最大元素，不需要移除
        // 所以这里优先级队列中添加了对应元素的下标，队列中的第一个元素是最大元素，只需要考虑最大元素是否在窗口中即可。
        for (int i = k; i < nums.length; i++) {
            queue.offer(new int[]{nums[i], i});
            if (queue.peek() != null) {
                while (queue.peek()[1] <= i - k) {
                    queue.poll();
                }
            }

            if (queue.peek() != null) {
                res[index++] = queue.peek()[0];
            }
        }

        return res;
    }

    // 1326. 灌溉花园的最少水龙头数目
    public int minTaps(int n, int[] ranges) {
        int[] last = new int[n + 1];
        for (int i = 0; i < n + 1; ++i) {
            int l = Math.max(0, i - ranges[i]), r = i + ranges[i];
            // last[l]表示l点可以到达的最远的右边
            last[l] = Math.max(last[l], r);
        }

        int ans = 0, mx = 0, pre = 0;
        for (int i = 0; i < n; ++i) {
            // last[i]表示第i个点可以到达的最右端点
            // mx是之前的可以到达的最右边的点
            // 从0开始
            mx = Math.max(mx, last[i]);
            // 如果当前的mx小于i，说明当前点可以覆盖的点是在当前点的左边或相等点
            // 因为上一步已经统计了所有的节点可以到达的最右端点，此时如果小于或等于，说明当前节点区间已经被漏掉了
            // 这里漏掉的就说明无法覆盖，就返回-1
            // 注意这个mx，他记录了之前节点可以到达的最远距离，如果当前节点被覆盖，则mx是大于当前i的
            if (mx <= i) {
                return -1;
            }
            // pre == i说明
            if (pre == i) {
                ++ans;
                pre = mx;
            }
        }
        return ans;
    }
}

