package com.example.springboot_vue.leetcode;

import java.util.*;

public class LeetCodeMain10 {

    public static void main(String[] args) {

    }

    // 2578. 最小和分割
    public int splitNum(int num) {
        List<Integer> list = new ArrayList<>();
        while (num > 0) {
            list.add(num % 10);
            num /= 10;
        }
        list.sort(Comparator.comparingInt(a -> a));

        StringBuilder first = new StringBuilder();
        StringBuilder second = new StringBuilder();
        int mark = 0;
        for (int i = 0; i < list.size(); i++) {
            if (mark == 0) {
                first.append(list.get(i));
                mark = 1;
            } else {
                second.append(list.get(i));
                mark = 0;
            }
        }

        return Integer.parseInt(first.toString()) + Integer.parseInt(second.toString());
    }

    // 452. 用最少数量的箭引爆气球
    public int findMinArrowShots(int[][] points) {
        Arrays.sort(points, Comparator.comparingInt(a -> a[0]));
        int res = 1;
        int right = points[0][1];
        for (int i = 1; i < points.length; i++) {
            if (points[i][0] > right) {
                res++;
                right = points[i][1];
            } else {
                right = Math.min(right, points[i][1]);
            }
        }

        return res;
    }

    // 219. 存在重复元素 II
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (map.get(nums[i]) != null) {
                if (Math.abs(map.get(nums[i]) - i) <= k) {
                    return true;
                }
            }
            map.put(nums[i], i);
        }

        return false;
    }

    // 714. 买卖股票的最佳时机含手续费
    public int maxProfit(int[] prices, int fee) {
        int[][] dp = new int[prices.length][2];
        // 持有股票的最大收益
        dp[0][0] = -prices[0];
        // 持有现金的最大收益
        dp[0][1] = 0;
        for (int i = 1; i < prices.length; i++) {
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] - prices[i]);
            dp[i][1] = Math.max(dp[i - 1][0] + prices[i] - fee, dp[i - 1][1]);
        }

        return dp[prices.length - 1][1];
    }

    // 309. 买卖股票的最佳时机含冷冻期
    public int maxProfitCold(int[] prices) {
        if (prices.length < 2) {
            return 0;
        }

        int[][] dp = new int[prices.length][3];
        // 0代表持有现金，1代表持有股票，2代表在冷冻期
        dp[0][1] = -prices[0];
        for (int i = 1; i < prices.length; i++) {
            // 手上持有股票的最大利益
            // dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] - prices[i]);
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] - prices[i]);

            // 没有股票，处于冷冻期的最大利益
            // dp[i][2] = Math.max(dp[i][2], dp[i - 1][1]);
            dp[i][2] = dp[i - 1][1] + prices[i];

            // 没有股票，不处于冷冻期的最大利益
            // dp[i][0] = Math.max(dp[i - 1][0], dp[i - 2][1] + prices[i]);
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][2]);
        }

        return Math.max(dp[prices.length - 1][0], dp[prices.length - 1][2]);
    }

    int ans;
    // 543. 二叉树的直径
    public int diameterOfBinaryTree(TreeNode root) {
        ans = 1;
        getLength(root);
        return ans - 1;
    }

    public int getLength(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int left = getLength(root.left);
        int right = getLength(root.right);
        ans = Math.max(ans, left + right + 1);
        return Math.max(left, right) + 1;
    }

    // 188. 买卖股票的最佳时机 IV
    public int maxProfit(int k, int[] prices) {
        if (prices.length == 0) {
            return 0;
        }

        int n = prices.length;
        // 当k大于prices长度的一半时，多出去的部分是没有意义的
        k = Math.min(k, n / 2);
        // buy和sell 都表示进行j笔交易后的最大利润,不同的是buy此时还持有一只股票，而sell是不持有
        int[][] buy = new int[n][k + 1];
        int[][] sell = new int[n][k + 1];

        // 后续都是取较大值，所以这里先给第0天的每一笔交易都赋予最小值
        buy[0][0] = -prices[0];
        sell[0][0] = 0;
        for (int i = 1; i <= k; ++i) {
            buy[0][i] = sell[0][i] = Integer.MIN_VALUE / 2;
        }

        for (int i = 1; i < n; ++i) {
            // 这里是记录第i天不进行交易时，他们的最大值是多少
            // buy代表持有一只股票，那么它的取值就是i - 1天的值（i - 1天持有股票）
            // 或者i - 1天卖出股票后，再买入今天股票的值(因为buy要求必须持有一只股票)
            buy[i][0] = Math.max(buy[i - 1][0], sell[i - 1][0] - prices[i]);
            // 后续模拟在第i天进行k笔交易
            for (int j = 1; j <= k; ++j) {
                // 这里的buy和上面的一样
                buy[i][j] = Math.max(buy[i - 1][j], sell[i - 1][j] - prices[i]);
                // 这里的第一个值也很好理解，因为sell的i - 1肯定也是卖出股票后的价格
                // buy[i - 1][j - 1]是上一个持有股票时的最大值，加上今天卖出股票的收益
                // 两者取最大值
                // 这里为什么不是buy[i - 1][j]呢？因为buy[i - 1][j]是第j次交易的最大值
                sell[i][j] = Math.max(sell[i - 1][j], buy[i - 1][j - 1] + prices[i]);
            }
        }

        return Arrays.stream(sell[n - 1]).max().getAsInt();
    }

    // 123. 买卖股票的最佳时机 III
    public int maxProfit3(int[] prices) {
        return maxProfit(2, prices);
    }

    // 394. 字符串解码
    public String decodeString(String s) {
        return null;
    }

    // 122. 买卖股票的最佳时机 II,贪心写法
    public int maxProfit2(int[] prices) {
        int buy = prices[0], max = 0;

        for (int i = 1; i < prices.length; i++) {
            // 之前买入的价格大于当天的价格
            if (buy <= prices[i]) {
                max += prices[i] - buy;
            }
            buy = prices[i];
        }

        return max;
    }

    // 122. 买卖股票的最佳时机 II,Dp写法
    public int maxProfit2ByDp(int[] prices) {
        int len = prices.length;
        if (len < 2) {
            return 0;
        }

        // 0：持有现金
        // 1：持有股票
        // 状态转移：0 → 1 → 0 → 1 → 0 → 1 → 0
        int[][] dp = new int[len][2];
        dp[0][0] = 0;
        dp[0][1] = -prices[0];

        for (int i = 1; i < len; i++) {
            // 这两行调换顺序也是可以的
            // 0代表持有现金，那么当前时刻持有的现金就等于上一时刻持有现金和在当前时刻卖出上一时刻持有股票价格的较大值
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
            // 1代表持有股票，取上一时刻的较大值或者拿上一时刻持有的现金买入当前价格的股票
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] - prices[i]);
        }

        return dp[len - 1][0];
    }

    // 121. 买卖股票的最佳时机
    public int maxProfit(int[] prices) {
        int max = 0, count = prices[0];
        // 只能买卖一次，那么就每个价格都尝试买入和卖出，看看最多是多少
        for (int i = 1; i < prices.length; i++) {
            // 买入
            count = Math.min(count, prices[i]);
            // 卖出
            max = Math.max(max, prices[i] - count);
        }

        return max;
    }

    // 2136. 全部开花的最早一天
    public int earliestFullBloom(int[] plantTime, int[] growTime) {
        int[][] arr = new int[plantTime.length][2];
        for (int i = 0; i < plantTime.length; i++) {
            arr[i][0] = plantTime[i];
            arr[i][1] = growTime[i];
        }
        Arrays.sort(arr, (a, b) -> (b[1] - a[1]));

        int[] res = new int[plantTime.length];
        int max = -1, plant = 0;
        for (int i = 0; i < arr.length; i++) {
            res[i] = plant + arr[i][0] + arr[i][1];
            plant = plant + arr[i][0];
            max = Math.max(res[i], max);
        }

        return max;
    }

    // 605. 种花问题
    public boolean canPlaceFlowers(int[] flowerbed, int n) {

        if (flowerbed.length == 1) {
            if (flowerbed[0] == 0) {
                return true;
            } else {
                return flowerbed[0] != n;
            }
        }

        int count = 0;
        if (flowerbed.length > 1) {
            if (flowerbed[0] == 0 && flowerbed[1] == 0) {
                count = 1;
                flowerbed[0] = 1;
            }
        }
        for (int i = 1; i < flowerbed.length; i++) {
            if (flowerbed[i] == 1) {
                continue;
            }
            if (i != flowerbed.length - 1) {
                if (flowerbed[i + 1] == 0 && flowerbed[i - 1] == 0) {
                    flowerbed[i] = 1;
                    count++;
                }
            } else {
                if (flowerbed[i - 1] == 0) {
                    flowerbed[i] = 1;
                    count++;
                }
            }
        }

        return count >= n;
    }

}
