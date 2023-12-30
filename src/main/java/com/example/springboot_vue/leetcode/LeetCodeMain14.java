package com.example.springboot_vue.leetcode;

import java.util.*;

public class LeetCodeMain14 {

    public static void main(String[] args) {
//        String str = "123";
//        String temp = "123";
//        StringBuilder stringBuilder = new StringBuilder(str);
//        int k = 12345;
//        StringBuilder strK = new StringBuilder();
//        while (k > 0) {
//            strK.insert(0, k % 10);
//            k /= 10;
//        }
//        System.out.println(strK.compareTo(stringBuilder));

        int sum = 10000;
        for (int i = 0; i < 10000; i++) {
            sum = (int) Math.floor(sum / 2);
            System.out.println(sum);
        }
        System.out.println(sum);
    }

    // 1185. 一周中的第几天
    public String dayOfTheWeek(int day, int month, int year) {
        String[] week = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        int[] monthDays = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30};
        /* 输入年份之前的年份的天数贡献 */
        int days = 365 * (year - 1971) + (year - 1969) / 4;
        /* 输入年份中，输入月份之前的月份的天数贡献 */
        for (int i = 0; i < month - 1; ++i) {
            days += monthDays[i];
        }
        if ((year % 400 == 0 || (year % 4 == 0 && year % 100 != 0)) && month >= 3) {
            days += 1;
        }
        /* 输入月份中的天数贡献 */
        days += day;
        return week[(days + 3) % 7];
    }

    // 2706. 购买两块巧克力
    public int buyChoco(int[] prices, int money) {
        Arrays.sort(prices);
        if (prices[0] + prices[1] > money) {
            return money;
        } else {
            return money - (prices[0] + prices[1]);
        }
    }

    // 1839. 所有元音按顺序排布的最长子字符串
    public int longestBeautifulSubstring(String word) {
        int max = 0, temp = 0;
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == 'a') {
                int index = i + 1, count = 1;
                while (index < word.length() && word.charAt(index) >= word.charAt(index - 1)) {
                    char tempChar = word.charAt(index);
                    if (tempChar != word.charAt(index - 1)) {
                        count++;
                    }
                    index++;
                }

                if (count == 5) {
                    max = Math.max(max, index - i);
                }
                i = index;
                i--;
            }
        }

        return max;
    }

    // 2660. 保龄球游戏的获胜者
    public int isWinner(int[] player1, int[] player2) {
        int score1 = 0, score2 = 0, mark1 = 0, mark2 = 0;
        for (int i = 0; i < player1.length; i++) {
            if (mark1 != 0) {
                score1 += player1[i] * 2;
                mark1--;
            } else {
                score1 += player1[i];
            }

            if (mark2 != 0) {
                score2 += player2[i] * 2;
                mark2--;
            } else {
                score2 += player2[i];
            }

            if (player1[i] == 10) {
                mark1 = 2;
            }

            if (player2[i] == 10) {
                mark2 = 2;
            }
        }

        System.out.println("score1 = " + score1 + " score2 = " + score2);
        if (score1 > score2) {
            return 1;
        } else if (score1 < score2) {
            return 2;
        } else {
            return 0;
        }
    }

    // 1276. 不浪费原料的汉堡制作方案
    public List<Integer> numOfBurgers(int tomatoSlices, int cheeseSlices) {
        List<Integer> res = new ArrayList<>();
        if (tomatoSlices % 2 != 0 || cheeseSlices > tomatoSlices) {
            return res;
        }

        int count = cheeseSlices * 2;
        int mix = tomatoSlices - count;
        if (mix < 0) {
            return res;
        }
        count = cheeseSlices;
        int another = 0;
        while (mix > 0) {
            another += 1;
            count -= 1;
            mix -= 2;
            if (count < 0) {
                break;
            }
        }

        if (mix != 0 || count < 0) {
            return res;
        }
        res.add(another);
        res.add(count);

        return res;
    }

    // 1954. 收集足够苹果的最小花园周长
    public long minimumPerimeter(long neededApples) {
        long n = 1;
        while (2 * n * (n + 1) * (2 * n + 1) < neededApples) {
            n++;
        }
        return n * 8;
    }

    // 1962. 移除石子使总数最小
    public int minStoneSum(int[] piles, int k) {
        PriorityQueue<Integer> queue = new PriorityQueue<>((a, b) -> b - a);
        int sum = 0;
        for (int a : piles) {
            queue.offer(a);
            sum += a;
        }
        while (!queue.isEmpty() && k > 0) {
            int val = queue.poll();
            int temp = (int) Math.floor(val / 2);
            sum -= temp;
            queue.offer(val - temp);
            k--;
        }
        System.out.println(queue.toString());

        return sum;
    }

    // 300. 最长递增子序列
    public int lengthOfLIS(int[] nums) {
        int[] dp = new int[nums.length];
        Arrays.fill(dp, 1);

        int res = 1;
        for (int i = 1; i < nums.length; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                    res = Math.max(dp[i], res);
                }
            }
        }

        return res;
    }

    // 1671. 得到山形数组的最少删除次数
    // 这个题的难点在于，没有考虑好该怎么转化问题，其实就是找到每一个最高点
    // 然后找他们两端呈山形的最大长度
    // 相当于计算从左往右和从右往左递增的最大子序列
    public int minimumMountainRemovals(int[] nums) {
        int n = nums.length;
        // 这里，得到了每个下标位置，最长的递增子序列
        int[] pre = getLISArray(nums);
        // 这三步，是为了得到从右往左递增的序列
        int[] reversed = reverse(nums);
        int[] suf = getLISArray(reversed);
        suf = reverse(suf);

        int ans = 0;
        for (int i = 0; i < n; ++i) {
            if (pre[i] > 1 && suf[i] > 1) {
                ans = Math.max(ans, pre[i] + suf[i] - 1);
            }
        }

        return n - ans;
    }

    public int[] getLISArray(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        Arrays.fill(dp, 1);
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < i; ++j) {
                if (nums[j] < nums[i]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }
        return dp;
    }

    public int[] reverse(int[] nums) {
        int n = nums.length;
        int[] reversed = new int[n];
        for (int i = 0; i < n; i++) {
            reversed[i] = nums[n - 1 - i];
        }
        return reversed;
    }

    // 2866. 美丽塔 II
    public long maximumSumOfHeights(List<Integer> maxHeights) {
        int n = maxHeights.size();
        long res = 0;
        long[] prefix = new long[n];
        long[] suffix = new long[n];
        Deque<Integer> stack1 = new ArrayDeque<>();
        Deque<Integer> stack2 = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            while (!stack1.isEmpty() && maxHeights.get(i) < maxHeights.get(stack1.peek())) {
                stack1.pop();
            }
            if (stack1.isEmpty()) {
                prefix[i] = (long) (i + 1) * maxHeights.get(i);
            } else {
                prefix[i] = prefix[stack1.peek()] + (long) (i - stack1.peek()) * maxHeights.get(i);
            }
            stack1.push(i);
        }

        for (int i = n - 1; i >= 0; i--) {
            while (!stack2.isEmpty() && maxHeights.get(i) < maxHeights.get(stack2.peek())) {
                stack2.pop();
            }
            if (stack2.isEmpty()) {
                suffix[i] = (long) (n - i) * maxHeights.get(i);
            } else {
                suffix[i] = suffix[stack2.peek()] + (long) (stack2.peek() - i) * maxHeights.get(i);
            }
            stack2.push(i);
            res = Math.max(res, prefix[i] + suffix[i] - maxHeights.get(i));
        }

        return res;
    }

    // 2522. 将字符串分割成值不超过 K 的子字符串
    public int minimumPartition(String s, int k) {
        StringBuilder strK = new StringBuilder();
        while (k > 0) {
            strK.insert(0, k % 10);
            k /= 10;
        }
        System.out.println(strK.toString());
        int res = 0;
        StringBuilder str = new StringBuilder();
        str.append(s.charAt(0));
        for (int i = 1; i < s.length(); i++) {
            char c = s.charAt(i);
            if (str.compareTo(strK) < 0) {
                if (str.append(c).compareTo(strK) > 0) {
                    res++;
                    str = new StringBuilder();
                    str.append(c);
                }
            } else {
                System.out.println(str.toString());
                return -1;
            }
        }

        if (str.compareTo(strK) < 0) {
            res++;
            return res;
        } else {
            return -1;
        }
    }

    // 2828. 判别首字母缩略词
    public boolean isAcronym(List<String> words, String s) {
        if (s.length() != words.size()) {
            return false;
        }

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c != words.get(i).charAt(0)) {
                return false;
            }
        }
        return true;
    }

    // 1901. 寻找峰值 II
    public int[] findPeakGrid(int[][] mat) {
        int m = mat.length, n = mat[0].length;
        int low = 0, high = m - 1;
        // 题解也是竖着二分，然后横着找这一排的最大值
        while (low <= high) {
            int i = (low + high) / 2;
            int j = -1, maxElement = -1;
            for (int k = 0; k < n; k++) {
                if (mat[i][k] > maxElement) {
                    j = k;
                    maxElement = mat[i][k];
                }
            }
            if (i - 1 >= 0 && mat[i][j] < mat[i - 1][j]) {
                high = i - 1;
                continue;
            }
            if (i + 1 < m && mat[i][j] < mat[i + 1][j]) {
                low = i + 1;
                continue;
            }
            return new int[]{i, j};
        }
        return new int[0]; // impossible
//        int[] res = new int[2];
//        for (int i = 0; i < mat[0].length; i++) {
//            int left = 0, right = mat.length - 1;
//            int resMid = 0;
//            while (left < right) {
//                int mid = (left + right) / 2;
//                int leftVal, rightVal, midVal = mat[mid][i];
//                if (mid - 1 >= 0) {
//                    leftVal = mat[mid - 1][i];
//                } else {
//                    leftVal = -1;
//                }
//
//                if (mid + 1 <= mat[0].length - 1) {
//                    rightVal = mat[mid + 1][i];
//                } else {
//                    rightVal = -1;
//                }
//
//                if (midVal > leftVal && midVal > rightVal) {
//                    resMid = mid;
//                    break;
//                } else if (midVal < leftVal) {
//                    right = mid - 1;
//                } else {
//                    left = mid + 1;
//                }
//            }
//
//            int upVal, downVal;
//            if (i - 1 >= 0) {
//                upVal = mat[resMid][i - 1];
//            } else {
//                upVal = -1;
//            }
//
//            if (i + 1 <= mat[0].length - 1) {
//                downVal = mat[resMid][i + 1];
//            } else {
//                downVal = -1;
//            }
//
//            if (mat[resMid][i] > downVal && mat[resMid][i] > upVal) {
//                return new int[]{resMid, i};
//            }
//        }
//
//        return new int[]{1, 1};
    }

    // 162. 寻找峰值
    public int findPeakElement(int[] nums) {
        int left = 0, right = nums.length - 1;
        while (left < right) {
            int mid = (left + right) / 2;
            int midLeft = Math.max(0, mid - 1), midRight = Math.min(nums.length - 1, mid + 1);
            if (nums[mid] > nums[midLeft] && nums[mid] > nums[midRight]) {
                return mid;
            } else if (nums[mid] > nums[midLeft] && nums[mid] < nums[midRight]) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        if (nums.length == 2) {
            if (nums[0] > nums[1]) {
                return 0;
            } else {
                return 1;
            }
        }

        return left;
    }

    // 746. 使用最小花费爬楼梯
    public int minCostClimbingStairs(int[] cost) {
        int[] dp = new int[cost.length + 1];
        dp[0] = cost[0];
        dp[1] = cost[1];
        for (int i = 2; i < cost.length; i++) {
            dp[i] = Math.min(dp[i - 1], dp[i - 2]) + cost[i];
        }
        dp[cost.length] = Math.min(dp[cost.length - 1], dp[cost.length - 2]);
        return dp[cost.length];
    }

    // 2415. 反转二叉树的奇数层
    public TreeNode reverseOddLevels(TreeNode root) {
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        boolean isOdd = false;
        while (!queue.isEmpty()) {
            int sz = queue.size();
            List<TreeNode> arr = new ArrayList<>();
            for (int i = 0; i < sz; i++) {
                TreeNode node = queue.poll();
                if (isOdd) {
                    arr.add(node);
                }
                if (node.left != null) {
                    queue.offer(node.left);
                    queue.offer(node.right);
                }
            }
            if (isOdd) {
                for (int l = 0, r = sz - 1; l < r; l++, r--) {
                    int temp = arr.get(l).val;
                    arr.get(l).val = arr.get(r).val;
                    arr.get(r).val = temp;
                }
            }
            isOdd ^= true;
        }
        return root;

//        if (root == null) {
//            return null;
//        }
//        Queue<TreeNode> queue = new ArrayDeque<>();
//        queue.add(root);
//        int height = 0;
//        while (!queue.isEmpty()) {
//            int size = queue.size();
//            List<TreeNode> list = new ArrayList<>();
//            for (int i = 0; i < size; i++) {
//                TreeNode node = queue.poll();
//                if (height % 2 != 0) {
//                    list.add(node);
//                }
//                list.add(node);
//                if (node.left != null) {
//                    queue.add(node.left);
//                }
//                if (node.right != null) {
//                    queue.add(node.right);
//                }
//            }
//            if (height % 2 != 0) {
//                int temp = size - 1;
//                int tempSize = (int) Math.ceil(size / 2);
//                for (int i = 0; i < tempSize; i++) {
//                    int tempVal = list.get(i).val;
//                    list.get(i).val = list.get(temp).val;
//                    list.get(temp).val = tempVal;
//                    temp--;
//                }
//            }
//
//            height++;
//        }
//
//        return root;
    }

    // 2415. 反转二叉树的奇数层,深度优先遍历的解
    public void dfs(TreeNode left, TreeNode right, boolean isOdd) {
        // 完美二叉树，左子树可能为空，left为空，right一定为空
        if (left == null) {
            return;
        }
        if (isOdd) {
            int tmp = left.val;
            left.val = right.val;
            right.val = tmp;
        }
        dfs(left.left, right.right, !isOdd);
        dfs(left.right, right.left, !isOdd);
    }

    // 2132. 用邮票贴满网格图
    public boolean possibleToStamp(int[][] grid, int stampHeight, int stampWidth) {
        int m = grid.length, n = grid[0].length;
        int[][] sum = new int[m + 2][n + 2];
        int[][] diff = new int[m + 2][n + 2];
        // 这里计算了一个前缀和数组
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                sum[i][j] = sum[i - 1][j] + sum[i][j - 1] - sum[i - 1][j - 1] + grid[i - 1][j - 1];
            }
        }

        // 这里计算差分数组
        for (int i = 1; i + stampHeight - 1 <= m; i++) {
            for (int j = 1; j + stampWidth - 1 <= n; j++) {
                int x = i + stampHeight - 1;
                int y = j + stampWidth - 1;
                if (sum[x][y] - sum[x][j - 1] - sum[i - 1][y] + sum[i - 1][j - 1] == 0) {
                    diff[i][j]++;
                    diff[i][y + 1]--;
                    diff[x + 1][j]--;
                    diff[x + 1][y + 1]++;
                }
            }
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                diff[i][j] += diff[i - 1][j] + diff[i][j - 1] - diff[i - 1][j - 1];
                if (diff[i][j] == 0 && grid[i - 1][j - 1] == 0) {
                    return false;
                }
            }
        }
        return true;

    }

    // 构建差分数组以及通过差分数复原数组
    public void diff(int[] arr) {
        // 构建差分数组
        int[] diff = new int[arr.length];
        diff[0] = arr[0];
        for (int i = 1; i < arr.length; i++) {
            diff[i] = arr[i] - arr[i - 1];
        }

        // 通过diff构建原数组
        int[] res = new int[arr.length];
        res[0] = diff[0];
        // 这里，res中存储的是原数组的值，diff[i] = res[i] - res[i - 1]
        // 那么res[i] = res[i - 1] + diff[i] = res[i -1] + res[i] - res[i - 1]
        for (int i = 1; i < res.length; i++) {
            res[i] = res[i - 1] + diff[i];
        }

        System.out.println(Arrays.toString(res));
    }

}
