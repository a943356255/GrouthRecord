package com.example.springboot_vue.leetcode;

import java.io.IOException;
import java.util.*;

public class LeetCodeMain5 {

    public static void main(String[] args) throws IOException {
        LeetCodeMain5 leetCodeMain5 = new LeetCodeMain5();

        int[] arr = {5,4,3,2,1};
        System.out.println(leetCodeMain5.candy(arr));
    }

    // 970. 强整数
    public List<Integer> powerfulIntegers(int x, int y, int bound) {
        List<Integer> list = new ArrayList<>();

        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; Math.pow(x, i) < bound; i++) {
            for (int j = 0; Math.pow(y, j) + Math.pow(x, i) <= bound; j++) {
                if (!list.contains((int) (Math.pow(y, j) + Math.pow(x, i)))) {
                    list.add((int) (Math.pow(y, j) + Math.pow(x, i)));
                    if (y == 1) {
                        break;
                    }
                }
            }
            if (x == 1) {
                break;
            }
        }

        return list;
    }

    // 1376. 通知所有员工所需的时间
    public int numOfMinutes(int n, int headID, int[] manager, int[] informTime) {
        if (n == 1) {
            return 0;
        }

        int res = 0;
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < manager.length; i++) {
            if (manager[i] == -1) {
                continue;
            }

            if (map.get(manager[i]) == null) {
                List<Integer> list = new ArrayList<>();
                list.add(i);
                map.put(manager[i], list);
            } else {
                map.get(manager[i]).add(i);
            }
        }

        Queue<Integer> queue = new LinkedList<>();
        queue.add(headID);
        while (!queue.isEmpty()) {
            int max = 0;
            // 该list保存每一层的元素
            List<Integer> list = new ArrayList<>();
            int val, mark = 0;
            while (!queue.isEmpty()) {
                val = queue.poll();
                if (map.get(val) != null) {
                    mark = 1;
                    list.add(val);
                }
//                max = Math.max(max, informTime[val]);
                if (max > informTime[val]) {
                    informTime[val] = informTime[val] - (max - informTime[val]);
                } else {
                    max = informTime[val];
                }
            }

            // 如果有子集，才加
            if (mark == 1) {
                res += max;
            }

            for (Integer integer : list) {
                if (map.get(integer) != null) {
                    for (int i = 0; i < map.get(integer).size(); i++) {
                        queue.offer(map.get(integer).get(i));
                    }
                }
            }
        }

        return res;
    }

    // 1033. 移动石子直到连续
    public int[] numMovesStones(int a, int b, int c) {
        int[] sort = {a, b, c};
        Arrays.sort(sort);
        int mark = 0;
        for (int i = 1; i < 3; i++) {
            if (sort[i] - sort[i - 1] != 1) {
                mark = 1;
                break;
            }
        }
        if (mark == 0) {
            return new int[]{0, 0};
        }

        int[] arr = new int[2];
        arr[0] = 2;
        if (sort[2] - sort[1] == 1 && sort[1] - sort[0] == 1) {
            arr[0] = 0;
        } else if (sort[1] - sort[0] <= 2 || sort[2] - sort[1] <= 2) {
            arr[0] = 1;
        }

        arr[1] = sort[2] - sort[0] - 2;

        return arr;
    }

    // 2423. 删除字符使频率相同
    public boolean equalFrequency(String word) {
        int[] charCount = new int[26];
        int n = word.length();
        for (int i = 0; i < n; i++) {
            charCount[word.charAt(i) - 'a']++;
        }

        for (int i = 0; i < 26; i++) {
            if (charCount[i] == 0) {
                continue;
            }
            charCount[i]--;

            HashSet<Integer> frequency = new HashSet<>();
            for (int f : charCount) {
                if (f > 0) {
                    frequency.add(f);
                }
            }
            if (frequency.size() == 1) {
                return true;
            }
            charCount[i]++;
        }
        return false;
    }

    // 1048. 最长字符串链
    public int longestStrChain(String[] words) {
        Arrays.sort(words, Comparator.comparingInt(String::length));

        int res = 0;
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < words.length; i++) {
            map.put(words[i], 1);
            for (int j = 0; j < words[i].length(); j++) {
                String prev = words[i].substring(0, i) + words[i].substring(i + 1);
                // 包含前一个
                if (map.containsKey(prev)) {
                    // 这里相当于遍历每一种情况，找到最大的
                    map.put(words[i], Math.max(map.get(prev) + 1, map.get(words[i])));
                }
            }

            res = Math.max(res, map.get(words[i]));
        }

        return res;
    }

    // 274. H 指数
    public int hIndex(int[] citations) {
        int res = 0;
        Arrays.sort(citations);

        int min;
        for (int i = 0; i < citations.length; i++) {
            if (citations[i] >= citations.length - i) {
                min = Math.min(citations.length - i, citations[i]);
                res = Math.max(min, res);
            }
        }

        return res;
    }

    // 128. 最长连续序列
    public int longestConsecutive(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }

        int max = 0;
        for (int i : set) {
            if (!set.contains(i - 1)) {
                int temp = i + 1, tempVal = 1;
                while (set.contains(temp)) {
                    tempVal++;
                    temp += 1;
                }
                max = Math.max(max, tempVal);
            }
        }

        return max;
    }

    // 2418. 按身高排序
    public String[] sortPeople(String[] names, int[] heights) {
        ArrayList<Student> list = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            Student student = new Student();
            student.height = heights[i];
            student.name = names[i];
            list.add(student);
        }

        list.sort(Comparator.comparing(Student::getHeight));

        int index = 0;
        String[] arr = new String[names.length];
        for (int i = list.size() - 1; i >= 0; i--) {
            arr[index++] = list.get(i).name;
        }

        return arr;
    }

    // 15. 三数之和
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();

        Arrays.sort(nums);
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == nums[Math.max(0, i - 1)]) {
                continue;
            }
            for (int j = i + 1; j < nums.length; j++) {
                for (int k = j + 1; k < nums.length; k++) {
                    if (nums[i] + nums[j] + nums[k] == 0) {
                        List<Integer> list = new ArrayList<>();
                        list.add(nums[i]);
                        list.add(nums[j]);
                        list.add(nums[k]);
                        res.add(list);
                    }
                }
            }
        }

        return res;
    }

    // 134. 加油站
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int res = 0;
        int[] arr = new int[gas.length];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = gas[i] - cost[i];
            res += arr[i];
        }

        if (res < 0) {
            return -1;
        }

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] < 0) {
                continue;
            }
            int count = 0, sum = 0, j = i;
            while (count != arr.length) {
                count++;
                sum += arr[j];
                if (sum < 0) {
                    break;
                }
                j = (j + 1) % arr.length;
            }

            if (count == arr.length) {
                return i;
            }
            i += count - 1;
        }

        return -1;
    }

    // 151. 反转字符串中的单词
    public String reverseWords(String s) {
        Stack<String> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != ' ') {
                i++;
                StringBuilder stringBuilder = new StringBuilder();
                while (i < s.length() && s.charAt(i) != ' ') {
                    stringBuilder.append(s.charAt(i));
                    i++;
                }
                stack.add(stringBuilder.toString());
            }
        }

        StringBuilder res = new StringBuilder();
        while (!stack.isEmpty()) {
            res.append(stack.pop());
            if (!stack.isEmpty()) {
                res.append(" ");
            }
        }

        return res.toString();
    }

    // 1027. 最长等差数列
    public int longestArithSeqLength(int[] nums) {
//        Map<Integer, Set<Integer>> map = new HashMap<>();
//        for (int i = 1; i < nums.length; i++) {
//            for (int j = 0; j < i; j++) {
//                if (map.get(nums[j] - nums[i]) == null) {
//                    Set<Integer> set = new HashSet<>();
//                    set.add(nums[j]);
//                    set.add(nums[i]);
//                    map.put(nums[j] - nums[i], set);
//                } else {
//                    map.get(nums[j] - nums[i]).add(nums[j]);
//                    map.get(nums[j] - nums[i]).add(nums[i]);
//                }
//            }
//        }
//
//        int res = 0;
//        for (Map.Entry<Integer, Set<Integer>> entry : map.entrySet()) {
//            res = Math.max(res, entry.getValue().size());
//            System.out.println(entry.getValue().toString());
//            System.out.println(entry.getValue().size());
//        }
//
//        return res;

        int n = nums.length;
        int[][] dp = new int[n][1001];

        // 保存结果
        int maxLen = 0;
        // 这里的思路是，目前统计到第k行，那么我从第0行统计到第k行，
        for (int k = 1; k < n; k++) {
            for (int j = 0; j < k; j++) {
                // 统一加偏移量，使下标非负
                int d = nums[k] - nums[j] + 500;
                // 根据 d 去填充dp[k][d]
                // 这里的dp[k][d] 是从dp[j][d]得到的，当限制了d这一列以后，就不存在断层这种情况了
                dp[k][d] = dp[j][d] + 1;
                // 维护最大值
                maxLen = Math.max(maxLen, dp[k][d]);
            }
        }

        return maxLen + 1;
    }

    // 135. 分发糖果
    public int candy(int[] ratings) {
        int[] candy = new int[ratings.length];
        Arrays.fill(candy, 1);

        for (int i = 1; i < ratings.length; i++) {
            if (ratings[i] > ratings[i - 1]) {
                candy[i] = candy[i - 1] + 1;
            } else if (ratings[i] < ratings[i - 1]) {
                candy[i - 1] = Math.max(candy[i] + 1, candy[i - 1]);
                int part = i;
                while (part > 0 && ratings[part] < ratings[part - 1]) {
                    candy[part - 1] = Math.max(candy[part] + 1, candy[part - 1]);
                    part--;
                }
            }
        }

        return Arrays.stream(candy).sum();
    }

    // 2413. 最小偶倍数
    public int smallestEvenMultiple(int n) {
        for (int i = n; i <= 2 * n; i++) {
            if (i % 2 == 0 && i % n == 0) {
                return i;
            }
        }

        return 0;
    }

    // 189. 轮转数组
    public void rotate(int[] nums, int k) {
        int[] res = new int[nums.length];
        System.arraycopy(nums, 0, res, 0, nums.length);

        for (int i = 0; i < nums.length; i++) {
            System.out.println((i + k) % nums.length);
            nums[(i + k) % nums.length] = res[i];
        }
    }

    // 1043. 分隔数组以得到最大和
    public int maxSumAfterPartitioning(int[] arr, int k) {
        int[] dp = new int[arr.length];
        int max = arr[0];
        for (int i = 1; i <= k; i++) {
            max = Math.max(max, arr[i - 1]);
            dp[i - 1] = Math.max(arr[i - 1], max) * i;
        }

        for (int i = k; i < arr.length; i++) {
            for (int j = 1; j <= k; j++) {
                // 这里还是要找从i往前的k个数的最大值。
                dp[i] = Math.max(dp[i - j] + getMaxValue(arr, i - j + 1, i) * j, dp[i]);
            }
        }

        return dp[arr.length - 1];
    }

    private int getMaxValue(int[] arr, int start, int end) {
        int res = 0;
        for (int i = start; i <= end; i++) {
            res = Math.max(res, arr[i]);
        }
        return res;
    }

    // 1026. 节点与其祖先之间的最大差值
    public int maxAncestorDiff(TreeNode root) {
        if (root == null) {
            return 0;
        }

        return myXaxAncestorDiff(root, root.val, root.val);
    }

    public int myXaxAncestorDiff(TreeNode root, int max, int min) {
        if (root == null) {
            return 0;
        }

        int partMax = Math.max(root.val, max);
        int partMin = Math.min(root.val, min);

        if (root.left == null && root.right == null) {
            return partMax - partMin;
        }

        int leftDiffer = myXaxAncestorDiff(root.left, partMax, partMin);
        int rightDiffer = myXaxAncestorDiff(root.right, partMax, partMin);

        return Math.max(leftDiffer, rightDiffer);
    }

    // 2409. 统计共同度过的日子数
    public int countDaysTogether(String arriveAlice, String leaveAlice, String arriveBob, String leaveBob) {
        int[] dayArr = new int[365];
        Arrays.fill(dayArr, 0);

        for (int i = getIndex(arriveAlice); i <= getIndex(leaveAlice); i++) {
            dayArr[i] = 1;
        }

        int res = 0;
        for (int i = getIndex(arriveBob); i <= getIndex(leaveBob); i++) {
            if (dayArr[i] == 1) {
                res++;
            }
        }

        return res;
    }

    public int getIndex(String str) {
        int[] arr = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        String[] monthAndDay = str.split("-");
        int month = Integer.parseInt(monthAndDay[0]);
        int days = 0;
        for (int i = 1; i < month; i++) {
            days += arr[i];
        }
        int day = Integer.parseInt(monthAndDay[1]);

        return day + days;
    }

    // 1042. 不邻接植花
    public int[] gardenNoAdj(int n, int[][] paths) {
        Map<Integer, Set<Integer>> map = new HashMap<>();
        for (int[] path : paths) {
            if (map.get(path[0]) == null) {
                Set<Integer> list = new HashSet<>();
                list.add(path[1]);
                map.put(path[0], list);
            } else {
                map.get(path[0]).add(path[1]);
            }

            if (map.get(path[1]) == null) {
                Set<Integer> list = new HashSet<>();
                list.add(path[0]);
                map.put(path[1], list);
            } else {
                map.get(path[1]).add(path[0]);
            }
        }

        int[] res = new int[n];
        Arrays.fill(res, 1);
        for (Map.Entry<Integer, Set<Integer>> entry : map.entrySet()) {
            int i = entry.getKey();

            boolean[] booleans = new boolean[5];
            Arrays.fill(booleans, false);

            for (Integer integer : entry.getValue()) {
                // 不为0说明设置过颜色
                if (res[integer - 1] != 0) {
                    booleans[res[integer - 1]] = true;
                }
            }

            for (int j = 1; j < booleans.length; j++) {
                if (!booleans[j]) {
                    res[i - 1] = j;
                    break;
                }
            }
        }

        return res;
    }

    // 1023. 驼峰式匹配 （有误）
    public List<Boolean> camelMatch(String[] queries, String pattern) {
        List<String> part = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder(pattern.charAt(0) + "");
        for (int i = 1; i < pattern.length(); i++) {
            if (pattern.charAt(i) >= 'A' && pattern.charAt(i) <= 'Z') {
                part.add(stringBuilder.toString());
                stringBuilder = new StringBuilder(pattern.charAt(i) + "");
            } else {
                stringBuilder.append(pattern.charAt(i));
            }
        }
        part.add(stringBuilder.toString());

        List<Boolean> list = new ArrayList<>();
        for (int i = 0; i < queries.length; i++) {
            int lastIndex = queries[i].lastIndexOf(part.get(0));
            if (lastIndex == -1) {
                list.add(false);
                continue;
            }

            int mark = 0;
            for (int j = 1; j < part.size(); j++) {
                int val = queries[i].lastIndexOf(part.get(j));
                if (val != -1 && val > lastIndex) {
                    lastIndex = val;
                } else {
                    mark = 1;
                    list.add(false);
                }
            }

            for (int j = lastIndex + part.get(part.size() - 1).length(); j < queries[i].length(); j++) {
                if (queries[i].charAt(j) >= 'A' && queries[i].charAt(j) <= 'Z') {
                    mark = 1;
                    list.add(false);
                    break;
                }
            }
            if (mark == 0) {
                list.add(true);
            }
        }

        return list;
    }

    // 2404. 出现最频繁的偶数元素
    public int mostFrequentEven(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            if (num % 2 != 0) {
                continue;
            }
            map.merge(num, 1, Integer::sum);
        }

        int max = -1, res = -1;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getValue() > max) {
                max = entry.getValue();
                res = entry.getKey();
            } else if (entry.getValue() == max) {
                if (entry.getKey() < res) {
                    res = entry.getKey();
                }
            }
        }

        return res;
    }

    // 1041. 困于环中的机器人
    public boolean isRobotBounded(String instructions) {
        int x = 0, y = 0;
        String direction = "up";

        for (int i = 0; i < instructions.length(); i++) {
            char c = instructions.charAt(i);
            if (c == 'G') {
                switch (direction) {
                    case "up":
                        y += 1;
                        break;
                    case "down":
                        y -= 1;
                        break;
                    case "left":
                        x -= 1;
                        break;
                    default:
                        x += 1;
                        break;
                }
            } else if (c == 'L') {
                switch (direction) {
                    case "up":
                        direction = "left";
                        break;
                    case "left":
                        direction = "down";
                        break;
                    case "down":
                        direction = "right";
                        break;
                    default:
                        direction = "up";
                        break;
                }
            } else {
                switch (direction) {
                    case "up":
                        direction = "right";
                        break;
                    case "left":
                        direction = "up";
                        break;
                    case "down":
                        direction = "left";
                        break;
                    default:
                        direction = "down";
                        break;
                }
            }
        }

        return (x == 0 && y == 0) || (!direction.equals("up"));
    }

    // 1019. 链表中的下一个更大节点
    public int[] nextLargerNodes(ListNode head) {
        ListNode node = new ListNode();
        node.next = head;

        int count = 0;
        while (node.next != null) {
            count++;
            node = node.next;
        }

        ListNode node1 = head;
        PriorityQueue<Integer> queue = new PriorityQueue<>((a, b) -> b - a);
        while (node1 != null) {
            queue.add(node1.val);
            node1 = node1.next;
        }

        int[] res = new int[count];
        int resIndex = 0;
        while (head != null) {
            if (!queue.isEmpty()) {
                if (head.val != queue.peek()) {
                    res[resIndex++] = queue.peek();
                    queue.remove(head.val);
                } else {
                    queue.poll();
                    res[resIndex++] = 0;
                }
            }
            head = head.next;
        }

        return res;
    }

    // 2399. 检查相同字母间的距离
    public boolean checkDistances(String s, int[] distance) {
        Map<Character, Integer> map = new HashMap<>();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (map.get(c) != null) {
                if (i - map.get(c) - 1 != distance[c - 'a']) {
                    return false;
                }
            } else {
                map.put(c, i);
            }
        }

        return true;
    }

    // 991. 坏了的计算器
    public int brokenCalc(int startValue, int target) {

        if (target < startValue) {
            return startValue - target;
        }

        return 2;
    }

    // 2427. 公因子的数目
    public int commonFactors(int a, int b) {
        int res = 0;

        for (int i = 1; i <= Math.min(a, b); i++)  {
            if (a % i == 0 && b % i == 0) {
                res++;
            }
        }

        return res;
    }

    // 1053. 交换一次的先前排列
    public int[] prevPermOpt1(int[] arr) {
        int n = arr.length;
        for (int i = n - 2; i >= 0; i--) {
            // 因为是找到一个比i小的才会进入下面循环，所以最差的情况就是找到i - 1
            if (arr[i] > arr[i + 1]) {
                int j = n - 1;
                // 从后往前找第一个比i小的数字
                while (arr[j] >= arr[i] || arr[j] == arr[j - 1]) {
                    j--;
                }
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                break;
            }
        }
        return arr;
    }

    public void swap(int[] arr, int i, int j) {
        int b = arr[i];
        arr[i] = arr[j];
        arr[j] = b;
    }

    // 831. 隐藏个人信息
    public String maskPII(String s) {
        StringBuilder stringBuilder = new StringBuilder();

        if (s.contains("@")) {
            stringBuilder.append(String.valueOf(s.charAt(0)).toLowerCase());
            for (int i = 0; i < 5; i++) {
                stringBuilder.append('*');
            }
            int index = 0;
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == '@') {
                    stringBuilder.append(String.valueOf(s.charAt(i - 1)).toLowerCase());
                    stringBuilder.append("@");
                    index = i;
                    break;
                }
            }

            for (int i = index + 1; i < s.length(); i++) {
                stringBuilder.append(String.valueOf(s.charAt(i)).toLowerCase());
            }

        } else {
            int count = 0;
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == '-') {
                    count++;
                }
            }
        }

        return stringBuilder.toString();
    }

    // 2367. 算术三元组的数目
    public int arithmeticTriplets(int[] nums, int diff) {
        int res = 0;

        Map<Integer, Integer> map = new HashMap<>();
        for (int j : nums) {
            map.put(j, 1);
        }

        for (int num : nums) {
            if (map.get(num + diff) != null && map.get(num + diff * 2) != null) {
                res++;
            }
        }

        return res;
    }

    // 1637. 两点之间不包含任何点的最宽垂直区域
    public int maxWidthOfVerticalArea(int[][] points) {
        int res = 0;

        Set<Integer> set = new HashSet<>();
        for (int[] point : points) {
            set.add(point[0]);
        }

        int index = 0;
        int[] arr = new int[set.size()];
        for (Integer integer : set) {
            arr[index++] = integer;
        }

        Arrays.sort(arr);
        for (int i = 1; i < arr.length; i++) {
            res = Math.max(arr[i] - arr[i - 1], res);
        }

        return res;
    }

    // 1641. 统计字典序元音字符串的数目
    public int countVowelStrings(int n) {
        int[] dp = new int[5];
        Arrays.fill(dp, 1);
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < 5; j++) {
                dp[j] += dp[j - 1];
            }
        }
        return Arrays.stream(dp).sum();
    }

    // 1092. 最短公共超序列
    public String shortestCommonSupersequence(String str1, String str2) {
        StringBuilder stringBuilder = new StringBuilder();


        return stringBuilder.toString();
    }

    // 1638. 统计只差一个字符的子串数目
    public int countSubstrings(String s, String t) {
        int res = 0;
        for (int i = 0; i < s.length(); i++) {
            for (int j = 0; j < t.length(); j++) {

            }
        }
        return res;
    }

    public int getNum(int[] arr) {
        int n = arr.length;
        int markZeroIndex = -1;
        int[] markZeroIndexArr = new int[n];
        int[] xor = new int[n];
        int[] multiplication = new int[n];
        xor[0] = arr[0];
        if (arr[0] == 0) {
            markZeroIndexArr[++markZeroIndex] = 0;
            multiplication[0] = 1;
        }
        multiplication[0] = arr[0];
        for (int i = 1; i < arr.length; i++) {
            xor[i] = xor[i - 1] ^ arr[i];
            if (arr[i] == 0) {
                markZeroIndexArr[++markZeroIndex] = i;
                multiplication[i] = 1;
            } else {
                multiplication[i] = multiplication[i - 1] * arr[i];
            }
        }

        int res = n;
        if (markZeroIndex == -1) {
            if (xor[n - 1] == multiplication[n - 1]) {
                res++;
            }
        } else {
            if (xor[n - 1] == 0) {
                res++;
            }
        }

        System.out.println(Arrays.toString(multiplication));

        for (int i = 0; i < xor.length; i++) {
            for (int j = i + 2; j < xor.length; j++) {
                int mark = 0;
                for (int k = 0; k <= markZeroIndex; k++) {
                    if (i <= markZeroIndexArr[k] && j >= markZeroIndexArr[k]) {
                        mark = 1;
                        break;
                    }
                }
                if (mark == 1) {
                    if ((xor[j] ^ xor[i]) == 0) {
                        res++;
                    }
                } else {
                    if ((xor[j] ^ xor[i]) == multiplication[j] / multiplication[i]) {
                        res++;
                    }
                }
            }
        }

        return res;
    }
}

class Student {
    String name;
    int height;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}