package com.example.springboot_vue.leetcode;

import java.util.*;

public class LeetCodeMain18 {

    public static void main(String[] args) {

    }

    // 25. K 个一组翻转链表
    public ListNode reverseKGroup(ListNode head, int k) {
        if (k == 1) {
            return head;
        }
        int count = 0;
        ListNode first, kTail;
        first = head;
        List<ListNode[]> list = new ArrayList<>();
        while (head != null) {
            count++;
            if (count == k) {
                kTail = head;
                ListNode tempHead = first;
                ListNode tempTail = kTail;
                list.add(new ListNode[]{tempHead, tempTail});
                reserveList(tempHead, tempTail);
            }
            head = head.next;
        }

        for (int i = 1; i < list.size(); i++) {
            list.get(i - 1)[0].next = list.get(i)[1];
        }

        return first;
    }

    // 92. 反转链表 II
    public ListNode reverseBetween(ListNode head, int left, int right) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode temp = head;
        ListNode tempFirst = new ListNode(), tempSecond = new ListNode();
        while (temp != null) {
            if (temp.val == left) {
                tempFirst = temp;
            }

            if (temp.val == right) {
                tempSecond = temp;
            }
            temp = temp.next;
        }
        ListNode node = tempSecond.next.next;
        reserveList(tempFirst, tempSecond);
        tempFirst.next.next = node;
        return head;
    }

    // 反转链表
    public void reserveList(ListNode head, ListNode tail) {
        ListNode pre = head, next;
        head = head.next;
        next = head.next;
        while (next != null && next != tail.next) {
            pre.next = null;
            head.next = pre;
            pre = head;
            head = next;
            next = next.next;
        }
    }

    // 114. 二叉树展开为链表
    public void flatten(TreeNode root) {
        if (root == null) {
            return;
        }
        List<TreeNode> list = new ArrayList<>();
        flattenDfs(root, list);
        for (int i = 1; i < list.size(); i++) {
            root.left = null;
            root.right = list.get(i);
            root = root.right;
        }
    }

    public void flattenDfs(TreeNode root, List<TreeNode> list) {
        if (root == null) {
            return;
        }
        list.add(root);
        flattenDfs(root.left, list);
        flattenDfs(root.right, list);
    }

    // 49. 字母异位词分组
    public List<List<String>> groupAnagrams(String[] strs) {
        List<List<String>> res = new ArrayList<>();
        Map<String, List<String>> map = new HashMap<>();
        for (String str : strs) {
            int[] arr = new int[26];
            for (int j = 0; j < str.length(); j++) {
                char c = str.charAt(j);
                arr[c - 'a']++;
            }
            String temp = Arrays.toString(arr);
            if (map.get(temp) == null) {
                List<String> list = new ArrayList<>();
                map.put(temp, list);
            }
            map.get(temp).add(str);
        }

        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            res.add(entry.getValue());
        }

        return res;
    }

    // 1. 两数之和
    public int[] twoSumEasy(int[] nums, int target) {
        int[] res = new int[2];
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(target - nums[i])) {
                res[0] = i;
                res[1] = map.get(target - nums[i]);
            } else {
                map.put(nums[i], i);
            }
        }

        return res;
    }

    // 167. 两数之和 II - 输入有序数组
    public int[] twoSum(int[] numbers, int target) {
        int[] res = new int[2];
        int left = 0, right = numbers.length - 1;
        while (left < right) {
            if (numbers[left] + numbers[right] == target) {
                res[0] = left + 1;
                res[1] = right + 1;
                break;
            } else if (numbers[left] + numbers[right] > target) {
                right--;
            } else {
                left++;
            }
        }

        return res;
    }

    // 151. 反转字符串中的单词
    public String reverseWords(String s) {
        Stack<String> stack = new Stack<>();
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == ' ') {
                while (i < s.length() && s.charAt(i) == ' ') {
                    i++;
                }
                if (str.length() > 0) {
                    stack.push(str.toString());
                }
                str = new StringBuilder();
                if (i < s.length()) {
                    str.append(s.charAt(i));
                }
            } else {
                str.append(c);
            }
        }
        if (str.length() > 0) {
            stack.push(str.toString());
        }
        str = new StringBuilder();
        while (!stack.isEmpty()) {
            str.append(stack.pop()).append(" ");
        }
        str.delete(str.length() - 1, str.length());
        return str.toString();
    }

    // 238. 除自身以外数组的乘积
    public int[] productExceptSelf(int[] nums) {
        int[] res = new int[nums.length];
        long temp = 1, countZero = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) {
                countZero++;
            } else {
                temp *= nums[i];
            }
        }

        if (countZero > 1) {
            Arrays.fill(res, 0);
            return res;
        }

        for (int i = 0; i < nums.length; i++) {
            if (countZero > 0) {
                if (nums[i] != 0) {
                    res[i] = 0;
                } else {
                    res[i] = (int) temp;
                }
            } else {
                res[i] = (int) (temp / nums[i]);
            }
        }

        return res;
    }

    // 2684. 矩阵中移动的最大次数
    // 这个题，最多从左移动到右这么多次
    public int maxMoves(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        Set<Integer> q = new HashSet<>();

        for (int i = 0; i < m; i++) {
            q.add(i);
        }

        for (int j = 1; j < n; j++) {
            Set<Integer> q2 = new HashSet<>();
            for (int i : q) {
                for (int i2 = i - 1; i2 <= i + 1; i2++) {
                    if (0 <= i2 && i2 < m && grid[i][j - 1] < grid[i2][j]) {
                        q2.add(i2);
                    }
                }
            }
            q = q2;
            if (q.isEmpty()) {
                return j - 1;
            }
        }

        return n - 1;
//        int[][] dp = new int[grid.length][grid[0].length];
//        for (int[] ints : dp) {
//            Arrays.fill(ints, -1);
//        }
//        int max = 0;
//        Queue<int[]> queue = new ArrayDeque<>();
//        for (int i = 0; i < grid.length; i++) {
//            queue.add(new int[]{i, 0});
//            dp[i][0] = 0;
//        }
//
//        while (!queue.isEmpty()) {
//            int size = queue.size();
//            for (int k = 0; k < size; k++) {
//                int[] arr = queue.poll();
//                int i = arr[0];
//                int j = arr[1];
//                if (i - 1 >= 0 && j + 1 < grid[0].length) {
//                    if (grid[i][j] < grid[i - 1][j + 1]) {
//                        dp[i - 1][j + 1] = Math.max(dp[i - 1][j + 1], dp[i][j] + 1);
//                        max = Math.max(dp[i - 1][j + 1], max);
//                        queue.offer(new int[]{i - 1, j + 1});
//                    }
//                }
//
//                if (j + 1 < grid[0].length) {
//                    if (grid[i][j] < grid[i][j + 1]) {
//                        dp[i][j + 1] = Math.max(dp[i][j + 1], dp[i][j] + 1);
//                        max = Math.max(dp[i][j + 1], max);
//                        queue.offer(new int[]{i, j + 1});
//                    }
//                }
//
//                if (i + 1 < grid.length && j + 1 < grid[0].length) {
//                    if (grid[i][j] < grid[i + 1][j + 1]) {
//                        dp[i + 1][j + 1] = Math.max(dp[i + 1][j + 1], dp[i][j] + 1);
//                        max = Math.max(dp[i + 1][j + 1], max);
//                        queue.offer(new int[]{i + 1, j + 1});
//                    }
//                }
//            }
//        }
//        System.out.println(Arrays.deepToString(dp));
//        return max;
    }

    // 209. 长度最小的子数组
    public int minSubArrayLen(int target, int[] nums) {
        int left = 0, right = 0, sum = 0;
        int min = Integer.MAX_VALUE;
        while (right < nums.length) {
            sum += nums[right++];
            if (sum >= target) {
                min = Math.min(min, right - left);
                while (sum >= target) {
                    sum -= nums[left++];
                    if (sum >= target) {
                        min = Math.min(min, right - left);
                    }
                }
            }
        }

        if (min == Integer.MAX_VALUE) {
            min = 0;
        }
        return min;
    }

    // 55. 跳跃游戏，判断当前能到达的最远距离是否在大于等于下标i即可
    public boolean canJump(int[] nums) {
        boolean[] dp = new boolean[nums.length];
        Arrays.fill(dp, false);
        dp[0] = true;
        int maxIndex = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (i <= maxIndex) {
                dp[i] = true;
                maxIndex = Math.max(maxIndex, i + nums[i]);
            }
        }

        return dp[nums.length - 1];
    }

    // 122. 买卖股票的最佳时机 II
    public int maxProfitTwo(int[] prices) {
        int maxRes = 0, minIn = prices[0];
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] >= minIn) {
                maxRes += prices[i] - minIn;
            }
            minIn = prices[i];
        }

        return maxRes;
    }

    // 121. 买卖股票的最佳时机
    public int maxProfit(int[] prices) {
        int minIn = prices[0];
        int maxRes = 0;
        for (int i = 1; i < prices.length; i++) {
            // 之前的最小价格，今天卖出
            maxRes = Math.max(maxRes, prices[i] - minIn);
            minIn = Math.min(prices[i], minIn);
        }
        return maxRes;
    }

    // 88. 合并两个有序数组
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        if (n == 0) {
            return;
        }
        int left = m - 1, right = nums2.length - 1, index = nums1.length - 1;
        while (left >= 0 && right >= 0) {
            if (nums1[left] > nums2[right]) {
                nums1[index--] = nums1[left--];
            } else {
                nums1[index--] = nums2[right--];
            }
        }

        while (right >= 0) {
            nums1[index--] = nums2[right--];
        }
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

    // 27. 移除元素
    public int removeElement(int[] nums, int val) {
        int fast = 0, slow = 0;
        while (fast < nums.length) {
            if (nums[fast] != val) {
                nums[slow++] = nums[fast++];
            } else {
                while (fast < nums.length && nums[fast] == val) {
                    fast++;
                }
                if (fast < nums.length) {
                    nums[slow++] = nums[fast++];
                }
            }
        }

        return slow;
        // 这种写法，是不考虑顺序的写法
//        int ans=nums.length;
//        for(int i=0;i<ans;){
//            if(nums[i]==val){
//                nums[i]=nums[ans-1];
//                ans--;
//            }else{
//                i++;
//            }
//        }
//        return ans;
    }

    // 26. 删除有序数组中的重复项
    public int removeDuplicatesEasy(int[] nums) {
        if (nums.length == 1) {
            return 1;
        }
        int slow = 1, fast = 1;
        while (fast < nums.length) {
            if (nums[fast] == nums[slow - 1]) {
                fast++;
            } else {
                nums[slow] = nums[fast];
                fast++;
                slow++;
            }
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
