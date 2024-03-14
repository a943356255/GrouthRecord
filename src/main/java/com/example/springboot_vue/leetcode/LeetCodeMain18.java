package com.example.springboot_vue.leetcode;

import java.util.*;

public class LeetCodeMain18 {

    public static void main(String[] args) {

    }

    // 80. 删除有序数组中的重复项 II
    public int removeDuplicates(int[] nums) {
        if (nums.length <= 2) {
            return nums.length;
        }
        // slow 和 fast并不是一直相差二
        // 这里跟我的思路有点相似，就是前边一个指针，指向当前需要被覆盖的下标
        // 但是我的问题在于试图通过第一次遍历去找前一个指针的位置
        // 事实是，只需要将第一个指针设置到2即可。因为前边有两个数字，而比较时是那slow - 2 和fast进行比较
        // 这样还解决了我之前需要额外判断覆盖写是否超过2个
        // 当fast 和 slow - 2不相等的时候，slow指向的就是要被覆盖掉的
        int slow = 2, fast = 2;
        while (fast < nums.length) {
            if (nums[slow - 2] != nums[fast]) {
                nums[slow] = nums[fast];
                slow++;
            }
            fast++;
        }
        return slow;
    }

    // 2789. 合并后数组中的最大元素
    public long maxArrayValue(int[] nums) {
        long lastNum = nums[nums.length - 1];
        long max = lastNum;
        for (int i = nums.length - 2; i >= 0; i--) {
            if (nums[i] <= lastNum) {
                lastNum += nums[i];
            } else {
                lastNum = nums[i];
            }
            max = Math.max(max, lastNum);
        }

        return max;
    }

    int maxPathSumRes = Integer.MIN_VALUE;
    // 124. 二叉树中的最大路径和
    public int maxPathSum(TreeNode root) {
        maxPathSumDfs(root);
        return maxPathSumRes;
    }

    public int maxPathSumDfs(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int left = Math.max(maxPathSumDfs(root.left), 0);
        int right = Math.max(maxPathSumDfs(root.right), 0);

        maxPathSumRes = Math.max(maxPathSumRes, left + right + root.val);

        // 这里，往父节点返回时，并不能将子节点的左右子树都走完，只能走一个，所以不是全部相加
        // return Math.max(0, left + right + root.val);
        // 这两个值只能取较大的
        // 如果是负数，这里为什么不返回0？其实不影响，为什么呢？看上面的left 和right，他在那里取了0，所以这里不用取
        return root.val + Math.max(left, right);
    }

    // LCR 024. 反转链表
    public ListNode reverseList(ListNode head) {
        if (head == null) {
            return null;
        }

        ListNode prev = null;
        ListNode current = head;
        while (current != null) {
            ListNode next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }

        return prev;
    }

    // 73. 矩阵置零
    public void setZeroes(int[][] matrix) {
        boolean column = false, line = false;
        for (int[] ints : matrix) {
            if (ints[0] == 0) {
                line = true;
                break;
            }
        }
        for (int i = 0; i < matrix[0].length; i++) {
            if (matrix[0][i] == 0) {
                column = true;
                break;
            }
        }

        for (int i = 1; i < matrix.length; i++) {
            for (int j = 1; j < matrix[0].length; j++) {
                if (matrix[i][j] == 0) {
                    matrix[i][0] = 0;
                    matrix[0][j] = 0;
                }
            }
        }

        for (int i = 1; i < matrix.length; i++) {
            for (int j = 1; j < matrix[0].length; j++) {
                if (matrix[i][0] == 0 || matrix[0][j] == 0) {
                    matrix[i][j] = 0;
                }
            }
        }

        if (column) {
            Arrays.fill(matrix[0], 0);
        }

        if (line) {
            for (int i = 0; i < matrix.length; i++) {
                matrix[i][0] = 0;
            }
        }
    }

    // 56. 合并区间
    public int[][] merge(int[][] intervals) {
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));

        List<int[]> list = new ArrayList<>();
        int lastStart = intervals[0][0];
        int lastEnd = intervals[0][1];
        list.add(new int[]{lastStart, lastEnd});

        for (int i = 1; i < intervals.length; i++) {
            int start = intervals[i][0];
            int end = intervals[i][1];
            if (start > lastEnd) {
                list.add(new int[]{start, end});
                lastEnd = end;
                lastStart = start;
            } else {
                list.remove(list.size() - 1);
                list.add(new int[]{lastStart, Math.max(lastEnd, end)});
                lastEnd = Math.max(end, lastEnd);
            }
        }

        int[][] arr = new int[list.size()][2];
        for (int i = 0; i < list.size(); i++) {
            arr[i][0] = list.get(i)[0];
            arr[i][1] = list.get(i)[1];
        }

        return arr;
    }

}
