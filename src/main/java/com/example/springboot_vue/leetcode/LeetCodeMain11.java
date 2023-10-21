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

    int[][] direction = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    boolean result = false;
    // 79. 单词搜索
    public boolean exist(char[][] board, String word) {
        int h = board.length, w = board[0].length;
        boolean[][] visited = new boolean[h][w];
        // 这一块思路和我一样，遍历board，以每个点为起点进行一次深度遍历，看是否可以组合成对应单词
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                boolean flag = check(board, visited, i, j, word, 0);
                if (flag) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean check(char[][] board, boolean[][] visited, int i, int j, String s, int k) {
        // 这个k记录str对应的下标，如果s的对应下标k与当前需要遍历的字符i,j不对应，则直接返回
        if (board[i][j] != s.charAt(k)) {
            return false;
        } else if (k == s.length() - 1) {
            return true;
        }
        visited[i][j] = true;
        int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        boolean result = false;
        for (int[] dir : directions) {
            int newi = i + dir[0], newj = j + dir[1];
            if (newi >= 0 && newi < board.length && newj >= 0 && newj < board[0].length) {
                // 这里，每次判断下一个位置是否被访问过
                // 我自己写的存在反复横跳导致无限递归的问题，主要是回溯写的有问题，所以还没出现，但是写对后会出现这个问题
                if (!visited[newi][newj]) {
                    // 没有访问过，则递归的进行访问
                    boolean flag = check(board, visited, newi, newj, s, k + 1);
                    if (flag) {
                        result = true;
                        break;
                    }
                }
            }
        }
        // 这里，每一次递归的访问结束后，对应的位置会被改为false
        visited[i][j] = false;
        return result;
    }

    /**
     * 79. 单词搜索 错误代码
     */
//    public boolean exist(char[][] board, String word) {
//        StringBuilder stringBuilder;
//        for (int i = 0; i < board.length; i++) {
//            for (int j = 0; j < board[0].length; j++) {
//                stringBuilder = new StringBuilder();
//                dfsExist(board, word, stringBuilder, i, j);
//            }
//        }
//
//        return result;
//    }
//
//    public void dfsExist(char[][] board, String word, StringBuilder res, int x, int y) {
//        for (int i = 0; i < direction.length; i++) {
//            if (legal(x, y, board)) {
//                res.append(board[x][y]);
//                System.out.println("res = " + res);
//                if (res.length() > word.length()) {
//                    return;
//                }
//                if (res.toString().equals(word)) {
//                    result = true;
//                    return;
//                }
//                dfsExist(board, word, res, x + direction[i][0], y + direction[i][1]);
//                res.deleteCharAt(res.length() - 1);
//            }
//        }
//    }

    public boolean legal(int x, int y, char[][] board) {
        return x >= 0 && y >= 0 && x < board.length && y < board[0].length;
    }

    // 2525. 根据规则将箱子分类
    public String categorizeBox(int length, int width, int height, int mass) {
        int Heavy = 0, Bulky = 0, temp = 10000;
        long res = (long) length * width * height;
        if (res >= Math.pow(10, 9) || length >= temp || width >= temp || height >= temp) {
            Bulky = 1;
        }

        if (mass >= 100) {
            Heavy = 1;
        }

        if (Heavy == 1 && Bulky == 1) {
            return "Both";
        } else if (Heavy == 0 && Bulky == 0) {
            return "Neither";
        } else {
            if (Heavy == 1) {
                return "Heavy";
            } else {
                return "Bulky";
            }
        }
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
                traceBack(nums, i, res, temp);
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
