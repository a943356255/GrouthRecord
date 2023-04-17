package com.example.springboot_vue.leetcode;

import java.io.IOException;
import java.util.*;

public class LeetCodeMain5 {

    public static void main(String[] args) throws IOException {
        LeetCodeMain5 leetCodeMain5 = new LeetCodeMain5();
        int[][] paths = {{1,2},{3,4}};
//        System.out.println(Arrays.toString(leetCodeMain5.gardenNoAdj(4, paths)));
        System.out.println(leetCodeMain5.getIndex("02-20"));
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