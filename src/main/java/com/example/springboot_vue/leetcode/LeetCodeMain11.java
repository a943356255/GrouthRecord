package com.example.springboot_vue.leetcode;

import java.util.*;

public class LeetCodeMain11 {

    public static void main(String[] args) {

    }

    // 103. 二叉树的锯齿形层序遍历
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) {
            return res;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        int direction = 1;
        while (!queue.isEmpty()) {
            int size = queue.size();
            LinkedList<Integer> temp = new LinkedList<>();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }

                if (direction == 1) {
                    temp.add(node.val);
                } else {
                    temp.addFirst(node.val);
                }
            }

            res.add(temp);
            direction = 1 - direction;
        }

        return res;
    }

    // 2530. 执行 K 次操作后的最大分数
    public long maxKelements(int[] nums, int k) {
        PriorityQueue<Integer> queue = new PriorityQueue<>((a, b) -> b - a);
        for (int num : nums) {
            queue.offer(num);
        }

        long res = 0;
        for (int i = 0; i < k; i++) {
            int temp = queue.poll();
            res += temp;
            queue.offer((int) Math.ceil(temp / 3.0));
        }

        return res;
    }

}
