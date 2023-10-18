package com.example.springboot_vue.leetcode;

import java.util.*;

public class LeetCodeMain11 {

    public static void main(String[] args) {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1, 1);
        map.put(2, 1);
        map.put(3, 1);
        map.put(4, 1);

        int val = map.get(1);
        System.out.println(val);
    }

    // 61. 旋转链表
    public ListNode rotateRight(ListNode head, int k) {
        if (head == null) {
            return null;
        }

        int count = 0;
        ListNode tempNode = head;
        while (tempNode != null) {
            count++;
            tempNode = tempNode.next;
        }

        k = k % count;

        ListNode fast = head;
        ListNode slow = head;
        int temp = 0;
        while (temp < k) {
            fast = fast.next;
            temp++;
            if (fast == null) {
                fast = head;
            }
        }

        if (fast == slow) {
            return head;
        }

        while (fast.next != null) {
            fast = fast.next;
            slow = slow.next;
        }

        fast.next = head;
        head = slow.next;
        slow.next = null;
        return head;
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
