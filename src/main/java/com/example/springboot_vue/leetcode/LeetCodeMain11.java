package com.example.springboot_vue.leetcode;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class LeetCodeMain11 {

    public static void main(String[] args) {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1, 1);
        map.put(2, 1);
        map.put(3, 1);
        map.put(4, 1);

        int val = map.get(1);
        System.out.println(val);
        ConcurrentHashMap<Integer, Integer> testMap = new ConcurrentHashMap<>();
        Map<Integer, Integer> hashTable = new Hashtable<>();
        testMap.size();
    }


    // 78. 子集
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        res.add(new ArrayList<>());
        LinkedList<Integer> list = new LinkedList<>();
        traceBack(nums, 0, res, list);
        return res;
    }

    public void traceBack(int[] nums, int index, List<List<Integer>> res, LinkedList<Integer> temp) {
        for (int i = index; i < nums.length; i++) {
            if (!temp.contains(nums[i])) {
                temp.add(nums[i]);
                res.add(new ArrayList<>(temp));
                traceBack(nums, index + 1, res, temp);
                temp.removeLast();
            }
        }
    }

    // 437. 路径总和 III
    public int pathSum(TreeNode root, int targetSum) {
        if (root == null) {
            return 0;
        }
        int res;

        res = dfsPathSum(root, targetSum);
        res += pathSum(root.left, targetSum);
        res += pathSum(root.right, targetSum);

        return res;
    }

    public int dfsPathSum(TreeNode root, long targetSum) {
        if (root == null) {
            return 0;
        }

        int res = 0;

        int val = root.val;
        if (val == targetSum) {
            res++;
        }

        res += dfsPathSum(root.left, targetSum - val);
        res += dfsPathSum(root.right, targetSum - val);
        return res;
    }

    // 1004. 最大连续1的个数 III
    public int longestOnes(int[] nums, int k) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) {
                list.add(i);
            }
        }
        if (list.size() == 0) {
            return nums.length;
        }

        for (int i = list.get(0); i < list.size() - k; i++) {
            for (int j = i; j < i + k; j++) {

            }
        }

        return 0;
    }

    // 1726. 同积元组
    public int tupleSameProduct(int[] nums) {
        int res = 0;
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (map.get(nums[i] * nums[j]) == null) {
                    map.put(nums[i] * nums[j], 1);
                } else {
                    map.put(nums[i] * nums[j], map.get(nums[i] * nums[j]) + 1);
                }
            }
        }

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            res += entry.getValue() * (entry.getValue() - 1) * 4;
        }

        return res;
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
