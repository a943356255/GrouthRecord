package com.example.springboot_vue.leetcode;

import org.antlr.v4.runtime.misc.Array2DHashSet;

import java.util.*;

public class LeetCodeMain3 {

    public static void main(String[] args) {
        int[] arr = {1, 3};
        LeetCodeMain3 leetCodeMain3 = new LeetCodeMain3();
        leetCodeMain3.getMaximumConsecutive(arr);
        System.out.println(Integer.parseInt("01"));
    }

    // 面试题 16.19. 水域大小
    public int[] pondSizes(int[][] land) {
        List<Integer> set = new ArrayList<>();

        int[][] arr = new int[land.length][];
        for (int i = 0; i < land.length; i++) {
            arr[i] = new int[land[0].length];
            Arrays.fill(arr[i], 0);
        }

        Queue<int[]> queue = new ArrayDeque<>();
        for (int i = 0; i < land.length; i++) {
            for (int j = 0; j < land[i].length; j++) {
                if (land[i][j] == 0) {
                    if (arr[i][j] == 0) {
                        queue.offer(new int[]{i, j});
                        arr[i][j] = 1;
                    }
                }

                int res = 0;
                while (!queue.isEmpty()) {
                    int x = queue.peek()[0];
                    int y = queue.peek()[1];
                    queue.poll();
                    res++;

                    if (x + 1 < land.length && y + 1 < land[i].length) {
                        // 右下角
                        if (land[x + 1][y + 1] == 0) {
                            if (arr[x + 1][y + 1] == 0) {
                                queue.offer(new int[]{x + 1, y + 1});
                                arr[x + 1][y + 1] = 1;
                            }
                        }
                        // 下
                        if (land[x + 1][y] == 0) {
                            if (arr[x + 1][y] == 0) {
                                arr[x + 1][y] = 1;
                                queue.offer(new int[]{x + 1, y});
                            }
                        }
                        // 右
                        if (land[x][y + 1] == 0) {
                            if (arr[x][y + 1] == 0) {
                                arr[x][y + 1] = 1;
                                queue.offer(new int[]{x, y + 1});
                            }
                        }
                    } else if (x + 1 < land.length && y + 1 == land[i].length) {
                        if (land[x + 1][y] == 0) {
                            if (arr[x + 1][y] == 0) {
                                arr[x + 1][y] = 1;
                                queue.offer(new int[]{x + 1, y});
                            }
                        }
                    } else if (x + 1 == land.length && y + 1 < land[i].length) {
                        if (land[x][y + 1] == 0) {
                            if (arr[x][y + 1] == 0) {
                                arr[x][y + 1] = 1;
                                queue.offer(new int[]{x, y + 1});
                            }
                        }
                    }
                    if (y > 0 && x + 1 < land.length) {
                        // 往左
                        if (land[x][y - 1] == 0) {
                            if (arr[x][y - 1] == 0) {
                                queue.offer(new int[]{x, y - 1});
                                arr[x][y - 1] = 1;
                            }
                        }
                        // 左下角
                        if (land[x + 1][y - 1] == 0) {
                            if (arr[x + 1][y - 1] == 0) {
                                queue.offer(new int[]{x + 1, y - 1});
                                arr[x + 1][y - 1] = 1;
                            }
                        }
                        // 左上角
                        if (x > 0) {
                            if (land[x - 1][y - 1] == 0) {
                                if (arr[x - 1][y - 1] == 0) {
                                    queue.offer(new int[]{x -1, y - 1});
                                    arr[x - 1][y - 1] = 1;
                                }
                            }
                        }
                    }
                    // 右上角
                    if (x > 0 && y + 1 < land[i].length) {
                        if (land[x - 1][y + 1] == 0) {
                            if (arr[x - 1][y + 1] == 0) {
                                queue.offer(new int[]{x - 1, y + 1});
                                arr[x - 1][y + 1] = 1;
                            }
                        }
                    }
                    // 上
                    if (x > 0) {
                        if (land[x - 1][y] == 0) {
                            if (arr[x - 1][y] == 0) {
                                queue.offer(new int[]{x - 1, y});
                                arr[x - 1][y] = 1;
                            }
                        }
                    }
                }

                if (res != 0) {
                    set.add(res);
                }
            }
        }

        int[] res = new int[set.size()];
        int index = 0;
        for (int i : set) {
            res[index++] = i;
        }
        Arrays.sort(res);

        return res;
    }

    // 2341. 数组能形成多少数对
    public int[] numberOfPairs(int[] nums) {
        int[] res = new int[2];

        int[] arr = new int[100];
        for (int num : nums) {
            arr[num]++;
        }

        int count = 0;
        for (int j : arr) {
            count += j / 2;
        }

        res[0] = count;
        res[1] = nums.length - count * 2;
        return res;
    }

    // 1124. 表现良好的最长时间段
    public int longestWPI(int[] hours) {
        int res = 0;

        int[] arr = new int[hours.length];
        for (int i = 0; i < hours.length; i++) {
            if (hours[i] >= 9) {
                arr[i] = 1;
            } else {
                arr[i] = -1;
            }
        }

        for (int i = 1; i < arr.length; i++) {
            arr[i] = arr[i - 1] + arr[i];
        }

        Queue<Integer> stack = new ArrayDeque<>();

        for (int i = arr.length - 1; i >= 0; i--) {

        }

        System.out.println(Arrays.toString(arr));
        return res;
    }

    public int balancedString(String s) {
        int res = 0, count = s.length() / 4;
        int[] arr = new int[4];

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == 'Q') {
                arr[0]++;
            } else if (c == 'W') {
                arr[1]++;
            } else if (c == 'E') {
                arr[2]++;
            } else {
                arr[3]++;
            }
        }

        for (int j : arr) {
            System.out.println(j);
            if (j < count) {
                res += count - j;
            }
        }

        return res;
    }

    // 1138. 字母板上的路径
    public String alphabetBoardPath(String target) {
        int index = 0;
        Map<Character, List<Integer>> map = new HashMap<>();
        char[] str = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                List<Integer> list = new ArrayList<>();
                list.add(i);
                list.add(j);
                map.put(str[index], list);
                index++;
            }
        }
        // 单独添加z
        List<Integer> list = new ArrayList<>();
        list.add(5); list.add(0);
        map.put('z', list);

        StringBuilder res = new StringBuilder();
        int x = 0, y = 0, targetX, targetY;
        for (int i = 0; i < target.length(); i++) {
            char c = target.charAt(i);
            targetX = map.get(c).get(0);
            targetY = map.get(c).get(1);

            while (targetY != x) {
                if (x < targetY) {
                    x++;
                    res.append("R");
                } else {
                    x--;
                    res.append("L");
                }
            }

            while (targetX != y) {
                if (y < targetX) {
                    y++;
                    res.append("D");
                } else {
                    y--;
                    res.append("U");
                }
            }

            res.append("!");
        }

        return res.toString();
    }

    // 2335. 装满杯子需要的最短总时长
    public int fillCups(int[] amount) {
        int res = 0, max, min, middle, sum;
//        sum = amount[0] + amount[1] + amount[2];
//
//        while (sum != 0) {
//            max = Math.max(amount[0], Math.max(amount[1], amount[2]));
//            min = Math.min(amount[0], Math.min(amount[1], amount[2]));
//            middle = sum - max - min;
//        }

        return res;
    }

    // 1233. 删除子文件夹
    public List<String> removeSubfolders(String[] folder) {
        List<String> res = new ArrayList<>();

        Arrays.sort(folder);
        res.add(folder[0]);

        System.out.println(Arrays.toString(folder));

        int index = 0;
        for (int i = 1; i < folder.length; i++) {
            if (!folder[i].startsWith(folder[index])) {
                res.add(folder[i]);
                index = i;
            }
        }

        return res;
    }

    // 1604. 警告一小时内使用相同员工卡大于等于三次的人
    // 这一题，他的时间并不是有序的，所以需要利用map<String, List<String>>的形式先存储
    // 然后再自己排序
    public List<String> alertNames(String[] keyName, String[] keyTime) {
        List<String> list = new ArrayList<>();

        String lastTime = keyTime[0];
        int count = 1;
        for (int i = 1; i < keyName.length; i++) {
            if (keyName[i].equals(keyName[i - 1])) {
                if (sameHour(lastTime, keyTime[i])) {
                    count++;
                    if (count == 3) {
                        list.add(keyName[i]);
                    }
                } else {
                    if (count == 2) {
                        lastTime = keyTime[i - 1];
                    } else {
                        lastTime = keyTime[i];
                    }
                    count = 1;
                }
            } else {
                lastTime = keyTime[i];
                count = 1;
            }
        }

        Collections.sort(list);

        return list;
    }

    public boolean sameHour(String time, String time2) {
        String[] first = time.split(":");
        String[] second = time2.split(":");

        int val = Integer.parseInt(second[0]) - Integer.parseInt(first[0]);
        if (val > 1 || val < 0) {
            return false;
        } else if (Integer.parseInt(second[0]) - Integer.parseInt(first[0]) == 1) {
            return Integer.parseInt(first[1]) >= Integer.parseInt(second[1]);
        } else {
            return true;
        }
    }

    // 2331. 计算布尔二叉树的值
    public boolean evaluateTree(TreeNode root) {
        Stack<Integer> stack = new Stack<>();
        dfs(root, stack);

        while (stack.size() > 1) {
            int val = stack.pop();
            int val2 = stack.pop();
            int val3 = stack.pop();

            if (val2 == 2) {
                stack.push(val | val3);
            } else {
                stack.push(val & val3);
            }
        }

        return stack.peek() == 1;
    }

    public void dfs(TreeNode root, Stack<Integer> stack) {
        if (root != null) {
            dfs(root.left, stack);
            stack.push(root.val);
            dfs(root.right, stack);
        }
    }

    // 1210. 穿过迷宫的最少移动次数
    public int minimumMoves(int[][] grid) {
        int n = grid.length;
        int[][][] dist = new int[n][n][2];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                Arrays.fill(dist[i][j], -1);
            }
        }
        dist[0][0][0] = 0;
        Queue<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[]{0, 0, 0});

        while (!queue.isEmpty()) {
            int[] arr = queue.poll();
            // 这里的x, y代表在数组中的位置
            int x = arr[0], y = arr[1], status = arr[2];
            // status = 0说明目前是水平方向，可以顺时针转，变成竖直方向
            if (status == 0) {
                // 向右移动一个单元格
                // x和y记录的是蛇尾的位置，蛇占2格，水平移动一格时y+2
                if (y + 2 < n && dist[x][y + 1][0] == -1 && grid[x][y + 2] == 0) {
                    dist[x][y + 1][0] = dist[x][y][0] + 1;
                    queue.offer(new int[]{x, y + 1, 0});
                }
                // 向下移动一个单元格
                if (x + 1 < n && dist[x + 1][y][0] == -1 && grid[x + 1][y] == 0 && grid[x + 1][y + 1] == 0) {
                    dist[x + 1][y][0] = dist[x][y][0] + 1;
                    queue.offer(new int[]{x + 1, y, 0});
                }
                // 顺时针旋转 90 度
                if (x + 1 < n && y + 1 < n && dist[x][y][1] == -1 && grid[x + 1][y] == 0 && grid[x + 1][y + 1] == 0) {
                    dist[x][y][1] = dist[x][y][0] + 1;
                    queue.offer(new int[]{x, y, 1});
                }
            } else {
                // 向右移动一个单元格
                if (y + 1 < n && dist[x][y + 1][1] == -1 && grid[x][y + 1] == 0 && grid[x + 1][y + 1] == 0) {
                    dist[x][y + 1][1] = dist[x][y][1] + 1;
                    queue.offer(new int[]{x, y + 1, 1});
                }
                // 向下移动一个单元格
                if (x + 2 < n && dist[x + 1][y][1] == -1 && grid[x + 2][y] == 0) {
                    dist[x + 1][y][1] = dist[x][y][1] + 1;
                    queue.offer(new int[]{x + 1, y, 1});
                }
                // 逆时针旋转 90 度
                if (x + 1 < n && y + 1 < n && dist[x][y][0] == -1 && grid[x][y + 1] == 0 && grid[x + 1][y + 1] == 0) {
                    dist[x][y][0] = dist[x][y][1] + 1;
                    queue.offer(new int[]{x, y, 0});
                }
            }
        }

        return dist[n - 1][n - 2][0];
    }

    // 1798. 你能构造出连续值的最大数目
    public int getMaximumConsecutive(int[] coins) {
        int res = 1;
        Arrays.sort(coins);

        for (int i = 0; i < coins.length; i++) {

        }

        while (true) {
            int part = 0;

            for (int i = 0; i < coins.length; i++) {
                if (coins[i] + res == res + 1 || coins[i] == res + 1) {
                    part = 1;
                    res++;
                    break;
                }
            }

            if (part == 0) {
                break;
            }
        }

        return res;
    }

    // 1624. 两个相同字符之间的最长子字符串
    public int maxLengthBetweenEqualCharacters(String s) {
        int res = -1;

        int[] arr = new int[26];
        Arrays.fill(arr, -1);
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            System.out.println(arr[c - 'a']);
            if (arr[c - 'a'] == -1) {
                arr[c - 'a'] = i;
            } else {
                // 第二次出现
                res = Math.min(res, i - arr[c - 'a']);
            }
        }

        return res;
    }

    // 2325. 解密消息
    public String decodeMessage(String key, String message) {
        StringBuilder res = new StringBuilder();

        int[] map = new int[26];
        Arrays.fill(map, -1);

        char[] str = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        int index = 0;
        for (int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);
            if (c == ' ') {
                continue;
            }
            if (map[c - 'a'] == -1) {
                map[c - 'a'] = index;
                index++;
            }
        }

        for (int i = 0; i < message.length(); i++) {
            char c = message.charAt(i);
            if (c == ' ') {
                res.append(" ");
                continue;
            }
            res.append(str[map[c - 'a']]);
        }

        return res.toString();
    }

    // 2319. 判断矩阵是否是一个 X 矩阵
    public boolean checkXMatrix(int[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (i == j || i + j == grid.length) {
                    if (grid[i][j] == 0) {
                        return false;
                    }
                } else {
                    if (grid[i][j] != 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    // 1669. 合并两个链表
    public ListNode mergeInBetween(ListNode list1, int a, int b, ListNode list2) {
        ListNode nodeA = new ListNode();
        ListNode nodeB = new ListNode();

        ListNode head = list1;
        int index1 = 0;
        while (head.next != null && index1 != a - 1) {
            head = head.next;
            index1++;
        }
        nodeA.next = head;

        while (head.next != null && index1 != b) {
            head = head.next;
            index1++;
        }
        nodeB.next = head;

        nodeA.next.next = list2;
        while (list2.next != null) {
            list2 = list2.next;
        }
        list2.next = nodeB.next;

        return list1;
    }

    // 2315. 统计星号
    public int countAsterisks(String s) {
        int res = 0, count = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '|') {
                count++;
            }

            if (count % 2 == 0) {
                if (c == '*') {
                    res++;
                }
            }
        }
        return res;
    }

    // 1664. 生成平衡数组的方案数
    public int waysToMakeFair(int[] nums) {
        int old, couple;
        if (nums.length % 2 == 0) {
            old = nums.length - 1;
            couple = nums.length - 2;
        } else {
            old = nums.length - 2;
            couple = nums.length - 1;
        }

        int sum = Arrays.stream(nums).sum();

        int[] beforeArr = new int[nums.length];
        beforeArr[0] = nums[0]; beforeArr[1] = nums[1];
        for (int i = 2; i < nums.length; i++) {
            beforeArr[i] = beforeArr[i - 2] + nums[i];
        }

        int res = 0, newCouple = 0, newOld = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i % 2 == 0) {
                newCouple = beforeArr[i] - nums[i];
                if (i != 0) {
                    newOld = beforeArr[i - 1];
                }

                if ((newCouple + beforeArr[old] - newOld) == (sum - nums[i]) / 2) {
                    return i;
                }
            } else {
                newOld = beforeArr[i] - nums[i];
                newCouple = beforeArr[i - 1];
                if (newOld + beforeArr[couple] - newCouple == (sum - nums[i]) / 2) {
                    return i;
                }
            }
        }

        return res;
    }

    // 2309. 兼具大小写的最好英文字母
    public String greatestLetter(String s) {
        int[] small = new int[26];
        int[] big = new int[26];

        String res = "";
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= 'a' && c <= 'z') {
                small[c - 'a']++;
            } else {
                big[c - 'A']++;
            }
        }

        char[] str = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        for (int i = 25; i >= 0; i--) {
            if (small[i] > 0 && big[i] > 0) {
                res = String.valueOf(str[i]);
                return res;
            }
        }

        return res;
    }

    // 1663. 具有给定数值的最小字符串
    public String getSmallestString(int n, int k) {
        char[] str = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

        StringBuilder res = new StringBuilder();
        for (int i = 0; i < n; i++) {
            res.append('a');
        }

        int count = n, index = n - 1;
        while (count != k) {
            if (k - count >= 25) {
                count += 25;
                res.replace(index, index, "z");
                index--;
            } else {
                res.replace(index, index, String.valueOf(str[k - count]));
                break;
            }
        }

        return res.toString();
    }

    // 650. 只有两个键的键盘
    public int minSteps(int n) {
        int[] dp = new int[n + 1];
        dp[1] = 1;

        int res = n, past = 1;
        for (int i = 2; i <= n; i++) {

        }

        return res;
    }

    // 1828. 统计一个圆中点的数目
    public int[] countPoints(int[][] points, int[][] queries) {
        int[] res = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            int r = queries[i][2];
            int x = queries[i][0];
            int y = queries[i][1];
            for (int j = 0; j < points.length; j++) {
                if ((points[j][0] - x) * (points[j][0] - x) + (points[j][1] - y) * (points[j][1] - y) <= r * r) {
                    res[i]++;
                }
            }
        }
        return res;
    }

    // 2303. 计算应缴税款总额
    public double calculateTax(int[][] brackets, int income) {
        double res = 0.0;

        res += Math.min(brackets[0][0], income) * brackets[0][1] / 100.0;
        for (int i = 1; i < brackets.length; i++) {
            res += (Math.min(brackets[i][0], income) - brackets[i - 1][0]) * brackets[i][1] / 100.0;
            if (income < brackets[i][0] && income > brackets[i - 1][0]) {
                break;
            }
        }

        return res;
    }

    // 2249. 统计圆内格点数目
    public int countLatticePoints(int[][] circles) {
//        Set<String> set = new HashSet<>();
//        for (int i = 0; i < circles.length; i++) {
//            for (int j = )
//        }
        return 1;
    }

    // 1824. 最少侧跳次数
    public int minSideJumps(int[] obstacles) {
        int[][] dp = new int[4][obstacles.length];
        dp[1][0] = 1;
        dp[2][0] = 0;
        dp[3][0] = 1;

        for (int i = 1; i < obstacles.length; i++) {
            if (obstacles[i] == 0) {
                // 没有路障
                dp[1][i] = dp[1][i - 1];
                dp[2][i] = dp[2][i - 1];
                dp[3][i] = dp[3][i - 1];
                // 第二遍更新
                dp[1][i] = Math.min(Math.min(dp[2][i - 1], dp[3][i - 1]) + 1, dp[1][i]);
                dp[2][i] = Math.min(Math.min(dp[1][i - 1], dp[3][i - 1]) + 1, dp[2][i]);
                dp[3][i] = Math.min(Math.min(dp[2][i - 1], dp[1][i - 1]) + 1, dp[3][i]);
            } else if (obstacles[i] == 1) {
                dp[1][i] = obstacles.length * 3;
                dp[2][i] = dp[2][i - 1];
                dp[3][i] = dp[3][i - 1];
                // 第二遍更新，看看是否存在测跳的问题
                dp[2][i] = Math.min(dp[3][i - 1] + 1, dp[2][i]);
                dp[3][i] = Math.min(dp[3][i], dp[2][i] + 1);
            } else if (obstacles[i] == 2) {
                dp[2][i] = obstacles.length * 3;
                dp[1][i] = dp[1][i - 1];
                dp[3][i] = dp[3][i - 1];
                // 第二遍更新，看看是否存在测跳的问题
                dp[1][i] = Math.min(dp[3][i - 1] + 1, dp[1][i]);
                dp[3][i] = Math.min(dp[3][i], dp[1][i] + 1);
            } else {
                dp[3][i] = obstacles.length * 3;
                dp[2][i] = dp[2][i - 1];
                dp[1][i] = dp[1][i - 1];
                // 第二遍更新，看看是否存在测跳的问题
                dp[2][i] = Math.min(dp[1][i - 1] + 1, dp[2][i]);
                dp[1][i] = Math.min(dp[1][i], dp[2][i] + 1);
            }
        }

        return Math.min(dp[1][obstacles.length - 1], Math.min(dp[2][obstacles.length - 1], dp[3][obstacles.length - 1]));
    }

    // 1817. 查找用户活跃分钟数
    public int[] findingUsersActiveMinutes(int[][] logs, int k) {
        Arrays.sort(logs, (a, b) -> {
            if (a[0] == b[0]) {
                return a[1] - b[1];
            } else {
                return a[0] - b[0];
            }
        });

        // 这个数组，下标从1开始意味着res[0]代表着活跃度为1的用户数量
        int[] res = new int[k];
        Map<Integer, Integer> map = new HashMap<>();

//        Map<Integer, ArrayList<Integer>> map1 = new HashMap<>();
//        for (int[] log : logs) {
//            map1.put(log[0], new ArrayList<>());
//        }

        for (int i = 0; i < logs.length; i++) {
            if (map.get(logs[i][0]) == null) {
                map.put(logs[i][0], 1);
//                map1.get(logs[i][0]).add(logs[i][1]);
            } else {
                if (i != 1 && logs[i - 1][0] != logs[i][0]) {
                    map.put(logs[i][0], map.get(logs[i][0]) + 1);
                }
//                if (!map1.get(logs[i][0]).contains(logs[i][1])) {
//                    map.put(logs[i][0], map.get(logs[i][0]) + 1);
//                    map1.get(logs[i][0]).add(logs[i][1]);
//                }
            }
        }

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            res[entry.getValue() - 1]++;
        }

        return res;
    }

    // 2299. 强密码检验器 II
    public boolean strongPasswordCheckerII(String password) {
        if (password.length() < 8) {
            return false;
        }
        String str = "!@#$%^&*()-+";

        int mark1 = 0, mark2 = 0, mark3 = 0, mark4 = 0;
        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);

            if (i > 0 && c == password.charAt(i - 1)) {
                return false;
            }

            if (c <= '9' && c >= '0') {
                mark1 = 1;
            } else if (c <= 'z' && c >= 'a') {
                mark2 = 1;
            } else if (c <= 'Z' && c >= 'A') {
                mark3 = 1;
            } else if (str.indexOf(c) != -1) {
                mark4 = 1;
            }
        }

        return (mark1 + mark2 + mark3 + mark4) == 4;
    }

    // 1814. 统计一个数组中好对子的数目
    public int countNicePairs(int[] nums) {
        int res = 0;
        final int MOD = 1000000007;

        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            int val = (int) (num - rev(num));
            if (map.get(val) == null) {
                map.put(val, 0);
            } else {
                map.put(val, map.get(val) + 1);
            }
            res = (res + map.get(val)) % MOD;
        }

        return res;
    }

    long rev(long num) {
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(num));
        stringBuilder.reverse();

        return Long.parseLong(stringBuilder.toString());
    }

    // 1813. 句子相似性 III
    public boolean areSentencesSimilar(String sentence1, String sentence2) {

        if (sentence1.length() == 0 || sentence2.length() == 0 || sentence2.equals(" ") || sentence1.equals(" ")) {
            return true;
        }
        String[] firstArr = sentence1.split(" ");
        String[] secondArr = sentence2.split(" ");

        if (firstArr.length == secondArr.length) {
            for (int i = 0; i < firstArr.length; i++) {
                if (!firstArr[i].equals(secondArr[i])) {
                    return false;
                }
            }
        }
        return true;
    }

    // 2293. 极大极小游戏
    public int minMaxGame(int[] nums) {
        while (nums.length != 1) {
            int[] arr = new int[nums.length / 2];
            for (int i = 0; i < nums.length / 2; i++) {
                if (i % 2 == 0) {
                    arr[i] = Math.min(nums[2 * i], nums[2 * i + 1]);
                } else {
                    arr[i] = Math.max(nums[2 * i], nums[2 * i + 1]);
                }
            }

            nums = arr;
        }

        return nums[0];
    }

    // 2240. 买钢笔和铅笔的方案数
    public long waysToBuyPensPencils(int total, int cost1, int cost2) {
        long res = 0;
        long first = 0, second;
        while (first * cost1 <= total) {
            second = (total - first * cost1) / cost2;
            res += second + 1;
            first++;
        }

        return res;
    }

    // 2287. 重排字符形成目标字符串
    public int rearrangeCharacters(String s, String target) {
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < target.length(); i++) {
            map.merge(target.charAt(i), 0, Integer::sum);
        }

        int[] arr = new int[26];
        for (int i = 0; i < s.length(); i++) {
            if (map.containsKey(s.charAt(i))) {
                arr[s.charAt(i) - 'a']++;
            }
        }

        int res = 100000000;
        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            if (entry.getValue() == 0) {

            }
            res = Math.min(res, arr[entry.getKey() - 'a'] / entry.getValue());
        }

        return res;
    }

    // 1807. 替换字符串中的括号内容
    public String evaluate(String s, List<List<String>> knowledge) {
        Map<String, String> map = new HashMap<>();
        for (List<String> list : knowledge) {
            map.put(list.get(0), list.get(1));
        }

        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder before = new StringBuilder();
        char c;
        for (int i = 0; i < s.length(); i++) {
            c = s.charAt(i);
            if (c != '(') {
                stringBuilder.append(c);
            } else {
                i++;
                while (i < s.length() && s.charAt(i) != ')') {
                    before.append(s.charAt(i));
                    i++;
                }
                if (map.get(before.toString()) == null) {
                    stringBuilder.append("?");
                } else {
                    stringBuilder.append(map.get(before.toString()));
                }
                before = new StringBuilder();
            }
        }

        return stringBuilder.toString();
    }

    // 2283. 判断一个数的数字计数是否等于数位的值
    public boolean digitCount(String num) {
        Map<Integer, Integer> map = new HashMap<>();
        char c;
        for (int i = 0; i < num.length(); i++) {
            c = num.charAt(i);
            map.merge(c - '0', 1, Integer::sum);
        }

        for (int i = 0; i < num.length(); i++) {
            c = num.charAt(i);
            if (map.get(i) != null) {
                if (map.get(i) != (c - '0')) {
                    return false;
                }
            } else {
                if ((c - '0') != 0) {
                    return false;
                }
            }
        }

        return true;
    }

    // 2348. 全 0 子数组的数目
    public long zeroFilledSubarray(int[] nums) {
        long res = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) {
                int count = 0;
                int part = 0;
                while (nums[i] == 0) {
                    count++;
                    part += count;
                    i++;
                    if (i == nums.length) {
                        break;
                    }
                }
                System.out.println(count);
                res += part;
                if (i >= nums.length) {
                    break;
                }
                i--;
            }
        }

        return res;
    }

}
