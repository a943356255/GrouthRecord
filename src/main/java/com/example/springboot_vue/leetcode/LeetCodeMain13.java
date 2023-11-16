package com.example.springboot_vue.leetcode;

import java.util.*;

public class LeetCodeMain13 {

    // 2656. K 个元素的最大和
    public int maximizeSum(int[] nums, int k) {

//        PriorityQueue<Integer> queue = new PriorityQueue<>((a, b) -> b - a);
//        for (int num : nums) {
//            queue.add(num);
//        }
//
//        for (int i = 0; i < k; i++) {
//            int temp = queue.poll();
//            sum += temp;
//            queue.add(temp + 1);
//        }
        int sum = 0, max = 0;
        for (int num : nums) {
            if (max < num) {
                max = num;
            }
        }
        sum += max * k + (k * (k - 1)) / 2;
        return sum;
    }

    // 2760. 最长奇偶子数组
    public int longestAlternatingSubarray(int[] nums, int threshold) {
        int left = 0, max = 0;
        while (left != nums.length) {
            if (nums[left] % 2 == 0 && nums[left] <= threshold) {
                int right = left + 1;
                int temp = 0;
                while (right != nums.length && nums[right] % 2 != temp && nums[right] <= threshold) {
                    temp = nums[right] % 2;
                    right++;
                }
                max = Math.max(max, right - left);
            }
            left ++;
        }

        return max;
    }

    // 1334. 阈值距离内邻居最少的城市
    public int findTheCity(int n, int[][] edges, int distanceThreshold) {
        int[] ans = {Integer.MAX_VALUE / 2, -1};
        int[][] dis = new int[n][n];
        boolean[][] vis = new boolean[n][n];
        int[][] mp = new int[n][n];
        for (int i = 0; i < n; ++i) {
            Arrays.fill(dis[i], Integer.MAX_VALUE / 2);
            Arrays.fill(mp[i], Integer.MAX_VALUE / 2);
        }

        for (int[] eg : edges) {
            int from = eg[0], to = eg[1], weight = eg[2];
            mp[from][to] = mp[to][from] = weight;
        }

        for (int i = 0; i < n; ++i) {
            dis[i][i] = 0;
            for (int j = 0; j < n; ++j) {
                int t = -1;
                for (int k = 0; k < n; ++k) {
                    if (!vis[i][k] && (t == -1 || dis[i][k] < dis[i][t])) {
                        t = k;
                    }
                }
                for (int k = 0; k < n; ++k) {
                    dis[i][k] = Math.min(dis[i][k], dis[i][t] + mp[t][k]);
                }
                vis[i][t] = true;
            }
        }

        for (int i = 0; i < n; ++i) {
            int cnt = 0;
            for (int j = 0; j < n; ++j) {
                if (dis[i][j] <= distanceThreshold) {
                    cnt++;
                }
            }
            if (cnt <= ans[0]) {
                ans[0] = cnt;
                ans[1] = i;
            }
        }

        return ans[1];
//        Map<Integer, List<Map<Integer, Integer>>> map = new HashMap<>();
//        for (int i = 0; i < edges.length; i++) {
//            Map<Integer, Integer> partMap = new HashMap<>();
//            partMap.put(edges[i][1], edges[i][2]);
//            if (map.get(edges[i][0]) == null) {
//                List<Map<Integer, Integer>> list = new ArrayList<>();
//                list.add(partMap);
//                map.put(edges[i][0], list);
//            } else {
//                map.get(edges[i][0]).add(partMap);
//            }
//        }
//
//        int res = 0;
//        Queue<Integer> queue = new ArrayDeque<>();
//        for (int i = 0; i < n; i++) {
//            queue.add(i);
//            int[] arr = new int[n];
//            Arrays.fill(arr, 0);
//            while (!queue.isEmpty()) {
//                int size = queue.size();
//                for (int j = 0; j < size; j++) {
//                    int node = queue.poll();
//                    if (arr[node] == 1) {
//                        continue;
//                    }
//                    arr[node] = 1;
//                    List<Map<Integer, Integer>> list = map.get(node);
//                }
//            }
//        }
//
//        return res;
    }

}
