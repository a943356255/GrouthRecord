package com.example.springboot_vue.leetcode;

import java.util.*;

public class LeetCodeMain17 {

    public static void main(String[] args) {
        var list = new ArrayList<Integer>();
    }

    // BM53 缺失的第一个正整数
    public int minNumberDisappeared (int[] nums) {
        // write code here
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            set.add(nums[i]);
        }

        for (int i = 1; i <= nums.length; i++) {
            if (!set.contains(i)) {
                return i;
            }
        }

        return set.size() + 1;
    }

    // BM45 滑动窗口的最大值，纯手写板
    public ArrayList<Integer> maxInWindows (int[] num, int size) {
        if (size == 0 || size > num.length) {
            return new ArrayList<>();
        }
        if (size == num.length) {
            int max = Arrays.stream(num).max().getAsInt();
            ArrayList<Integer> list = new ArrayList<>();
            list.add(max);
            return list;
        }
        if (size == 1) {
            ArrayList<Integer> list = new ArrayList<>();
            for (int i : num) {
                list.add(i);
            }
            return list;
        }

        ArrayList<Integer> res = new ArrayList<>();
        PriorityQueue<int[]> queue = new PriorityQueue<>((a, b) -> (b[0] - a[0]));
        int left = 0, right = 0;
        for (int i = 0; i < size; i++) {
            queue.offer(new int[]{num[i], i});
            right = i;
        }

        res.add(queue.peek()[0]);
        while (right < num.length) {
            right++;
            if (right == num.length) {
                break;
            }
            left++;
            queue.offer(new int[]{num[right], right});
            while (!queue.isEmpty() && queue.size() > size && queue.peek()[1] < left) {
                queue.poll();
            }
            res.add(queue.peek()[0]);

        }

        return res;
    }

    ArrayList<Integer> getPath(TreeNode root, int target) {
        ArrayList<Integer> path = new ArrayList<>();
        while (root != null) {
            if (root.val != target) {
                path.add(root.val);
                if (root.val > target) {
                    root = root.left;
                } else {
                    root = root.right;
                }
            } else {
                path.add(root.val);
                break;
            }
        }

        return path;
    }

    // 二叉树的公共祖先
    public int lowestCommonAncestor (TreeNode root, int p, int q) {
        // write code here
        ArrayList<Integer> pList = getPath(root, p);
        ArrayList<Integer> qList = getPath(root, q);
        int res = 0;
        for (int i = 0; i < pList.size() && i < qList.size(); i++) {
            int x = pList.get(i);
            int y = qList.get(i);
            System.out.println("x = " + x + " y = " + y);
            System.out.println("pList.get(i) = " + pList.get(i) + " qList.get(i) = " + qList.get(i));
            System.out.println(pList.get(i) == qList.get(i));
            if (pList.get(i).equals(qList.get(i))) {
                res = pList.get(i);
            } else {
                break;
            }
        }

        return res;
    }

    // 299. 猜数字游戏
    public String getHint(String secret, String guess) {
        int count1 = 0;
        Map<Character, Integer> secretMap = new HashMap<>();
        Map<Character, Integer> guessMap = new HashMap<>();
        for (int i = 0; i < secret.length(); i++) {
            char c = secret.charAt(i);
            char b = guess.charAt(i);
            if (c == b) {
                count1++;
            } else {
                secretMap.merge(c, 1, Integer::sum);
                guessMap.merge(b, 1, Integer::sum);
            }
        }

        int count = 0;
        for (Map.Entry<Character, Integer> entry : secretMap.entrySet()) {
            int first = entry.getValue();
            int second = guessMap.get(entry.getKey()) == null ? 0 : guessMap.get(entry.getKey());
            count += Math.min(first, second);
        }

        return count1 + "A" + count + "B";
    }

    // BM63 跳台阶
    public int jumpFloor (int number) {
        // write code here
        if (number < 3) {
            return number;
        }

        int[] dp = new int[number + 1];
        dp[1] = 1;
        dp[2] = 2;

        for (int i = 3; i <= number; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[number];
    }
}
