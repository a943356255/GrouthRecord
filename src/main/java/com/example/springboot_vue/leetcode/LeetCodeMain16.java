package com.example.springboot_vue.leetcode;

import java.util.*;

public class LeetCodeMain16 {

    public static void main(String[] args) {
        String str = "411032199811252314 + 199929992992";
    }

//    public List<String> wordBreak(String s, List<String> wordDict) {
//        Map<String, String> map = new HashMap<>();
//        for (String value : wordDict) {
//            map.put(value, "1");
//        }
//
//        StringBuilder first = new StringBuilder();
//        for (int i = 0; i < s.length(); i++) {
//            char c = s.charAt(i);
//            first.append(c);
//            if (map.get(first.toString()) != null) {
//                StringBuilder temp = new StringBuilder(first);
//                dfs(temp, i + 1, s, map);
//            }
//        }
//
//        return wordBreak;
//    }
//
//    public void dfs(StringBuilder str, int index, String s, Map<String, String> map) {
//        str.append(" ");
//        for (int i = index; i < s.length(); i++) {
//            char c = s.charAt(i);
//
//        }
//    }

    // 2368. 受限条件下可到达节点的数目
    public int reachableNodes(int n, int[][] edges, int[] restricted) {
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

    // 825. 适龄的朋友
    public int numFriendRequests(int[] ages) {
        return 0;
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
