package com.example.springboot_vue.leetcode;

import java.util.*;

public class LeetCodeMain9 {

    // 2594. 修车的最少时间
    public long repairCars(int[] ranks, int cars) {
        // 这里时直接计算任意一个人修全部车的时间，那么总的修车时间一定从1到该时间之内
        // 然后利用二分即可
        long l = 1, r = (long) ranks[0] * cars * cars;
        while (l < r) {
            // m是二分后的总时间
            long m = l + r >> 1;
            if (check(ranks, cars, m)) {
                r = m;
            } else {
                l = m + 1;
            }
        }
        return l;
    }

    public boolean check(int[] ranks, int cars, long m) {
        long cnt = 0;
        for (int x : ranks) {
            // 因为计算修车时间是x * n * n，所以这里m / x再开根号就是i员工在改时间内修车的数量
            // cnt计算出了所有员工在改时间内修车的总数量
            cnt += (long) Math.sqrt(m / x);
        }
        return cnt >= cars;
    }

    // 912. 排序数组
    public int[] sortArray(int[] nums) {
        Arrays.sort(nums);
        return nums;
    }

    private TreeNode ans;
    private int maxDepth = -1; // 全局最大深度
    // 1123.最深叶节点的最近公共祖先
    public TreeNode lcaDeepestLeaves(TreeNode root) {
        dfs(root, 0);
        return ans;
    }

    private int dfs(TreeNode node, int depth) {
        if (node == null) {
            maxDepth = Math.max(maxDepth, depth); // 维护全局最大深度
            return depth;
        }

        // 获取左子树最深叶节点的深度
        int leftMaxDepth = dfs(node.left, depth + 1);
        // 获取右子树最深叶节点的深度
        int rightMaxDepth = dfs(node.right, depth + 1);
        // 这一步，深度遍历完又回到了根节点，如果他的左子树深度和右子树深度一样，返回改节点即可。
        // 它会一步一步的回退到最开始的根节点，每次回退都会判断一次
        if (leftMaxDepth == rightMaxDepth && leftMaxDepth == maxDepth) {
            ans = node;
        }

        // 当前子树最深叶节点的深度
        return Math.max(leftMaxDepth, rightMaxDepth);
    }

    // 2605. 从两个数字数组里生成最小数字
    public int minNumber(int[] nums1, int[] nums2) {
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        Set<Integer> set = new HashSet<>();
        for (int j : nums2) {
            set.add(j);
        }
        int temp = Math.min(nums1[0], nums2[0]) * 10 + Math.max(nums1[0], nums2[0]);
        for (int j : nums1) {
            if (j <= temp && set.contains(j)) {
                return j;
            } else if (j > temp) {
                System.out.println(j + " " + temp);
                break;
            }
        }

        return temp;
    }

    // 1921. 消灭怪物的最大数量
    public int eliminateMaximum(int[] dist, int[] speed) {
        double[] arr = new double[dist.length];
        for (int i = 0; i < dist.length; i++) {
            arr[i] = (double) dist[i] / speed[i];
        }
        Arrays.sort(arr);

        int res = 1, count = 1;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] - count > 0) {
                res++;
                count++;
            } else {
                break;
            }
        }

        return res;
    }

    // 116. 填充每个节点的下一个右侧节点指针
    public Node connect(Node root) {
        if (root == null) {
            return null;
        }

        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                Node temp = queue.poll();
                if (temp != null) {
                    if (i < size - 1) {
                        temp.next = queue.peek();
                    }

                    if (temp.left != null) {
                        queue.add(temp.left);
                    }

                    if (temp.right != null) {
                        queue.add(temp.right);
                    }
                }
            }
        }

        return root;
    }

    // 823. 带因子的二叉树
    public int numFactoredBinaryTrees(int[] arr) {
        Arrays.sort(arr);
        int n = arr.length;
        long[] dp = new long[n];
        long res = 0, mod = 1000000007;
        for (int i = 0; i < n; i++) {
            dp[i] = 1;
            // 内层循环每次都是从0到i - 1
            for (int left = 0, right = i - 1; left <= right; left++) {
                // 这里是找到第一个满足arr[left] * arr[right]小于当前arr[i]的元素
                while (right >= left && (long) arr[left] * arr[right] > arr[i]) {
                    right--;
                }
                if (right >= left && (long) arr[left] * arr[right] == arr[i]) {
                    // 这里，是以di[i]为根节点的二叉树的个数
                    // 如果左子树不等于右子树，那么两者交换就多了一种情况，如果相等则无法交换，个数就是左子树的种类*右子树种类
                    if (right != left) {
                        dp[i] = (dp[i] + dp[left] * dp[right] * 2) % mod;
                    } else {
                        dp[i] = (dp[i] + dp[left] * dp[right]) % mod;
                    }
                }
            }
            res = (res + dp[i]) % mod;
        }

        return (int) res;
    }

    // 228. 汇总区间
    public List<String> summaryRanges(int[] nums) {
        List<String> res = new ArrayList<>();
        if (nums.length == 0) {
            return res;
        }

        StringBuilder str = new StringBuilder(String.valueOf(nums[0]));
        int length = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == nums[i - 1] + 1) {
                length++;
            } else {
                if (length > 1) {
                    str.append("->").append(nums[i - 1]);
                }
                res.add(str.toString());
                length = 1;
                str = new StringBuilder(nums[i]);
            }
        }
        if (length > 1) {
            res.add(str.append("->").append(nums[nums.length - 1]).toString());
        } else {
            res.add(str.toString());
        }

        return res;
    }

}
