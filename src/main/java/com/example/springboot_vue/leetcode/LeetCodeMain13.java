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

    // 2736. 最大和查询
    public int[] maximumSumQueries(int[] nums1, int[] nums2, int[][] queries) {
        int n = nums1.length;

        // 这一段代码，是按照nums1的值降序排列
        int[][] sortedNums = new int[n][2];
        for (int i = 0; i < n; i++) {
            sortedNums[i][0] = nums1[i];
            sortedNums[i][1] = nums2[i];
        }
        Arrays.sort(sortedNums, (a, b) -> b[0] - a[0]);

        // 这一段，是按照查询的范围的第一个值降序排列
        // 降序排列可以确保后续查询的第一个坐标都在前一个查询的范围内，这样也可以确保不回退sortedNums
        int q = queries.length;
        int[][] sortedQueries = new int[q][3];
        for (int i = 0; i < q; i++) {
            // 这里记录i，是因为排过序后，答案需要记录原先的顺序
            sortedQueries[i][0] = i;
            sortedQueries[i][1] = queries[i][0];
            sortedQueries[i][2] = queries[i][1];
        }
        Arrays.sort(sortedQueries, (a, b) -> b[1] - a[1]);


        List<int[]> stack = new ArrayList<>();
        int[] answer = new int[q];
        Arrays.fill(answer, -1);

        int j = 0;
        for (int[] query : sortedQueries) {
            int i = query[0], x = query[1], y = query[2];
            // 这里是遍历sortedNums，对于所有满足nums[i] > query[i][0]的，进行处理
            while (j < n && sortedNums[j][0] >= x) {
                // 取出满足条件的数组
                int[] pair = sortedNums[j];
                int num1 = pair[0], num2 = pair[1];

                // 这里，如果栈顶的末位元素的 nums1 + nums2 < 此次计算的num1 + nums2，将栈顶元素移除
                // 这个栈是按照nums1 + nums2 的大小由大到小的
                // 这个栈，是按照nums[2]的值单调递增的
                // 这里需要注意，因为nums1的值是变得越来越小的，当nums1 + nums2 > 之前的和，只可能是nums2变得更大
                // 所以栈中的nums2一定是单调递增的。
                while (!stack.isEmpty() && stack.get(stack.size() - 1)[1] <= num1 + num2) {
                    stack.remove(stack.size() - 1);
                }

                // 这里是先不管nums2是否满足条件，先放入栈中，然后在二分查找的时候去判断是否满足
                if (stack.isEmpty() || stack.get(stack.size() - 1)[0] < num2) {
                    stack.add(new int[]{num2, num1 + num2});
                }
                j++;
            }

            // 真正看y也就是nums2是否满足条件是在二分查找这里判断的，上述出栈和进栈的过程只是保证nums1 + num2的值
            // 二分查找是根据y来查找的，
            int k = binarySearch(stack, y);
            if (k < stack.size()) {
                answer[i] = stack.get(k)[1];
            }
        }
        return answer;
    }

    public int binarySearch(List<int[]> list, int target) {
        int low = 0, high = list.size();
        while (low < high) {
            int mid = low + (high - low) / 2;
            if (list.get(mid)[0] >= target) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }
        return low;
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
