package com.example.springboot_vue.leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LeetCodeMain17 {

    public static void main(String[] args) {
        var list = new ArrayList<Integer>();
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
