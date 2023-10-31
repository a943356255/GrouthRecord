package com.example.springboot_vue.leetcode;

import java.util.*;

public class LeetCodeMain12 {

    public static void main(String[] args) {
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        for (int i = 0; i < 5; i++) {
            queue.offer(i);
        }
        Map<Integer, PriorityQueue<Integer>> map = new HashMap<>();
        map.put(1, queue);

        PriorityQueue<Integer> temp = new PriorityQueue<>();
        temp.addAll(map.get(1));
        while (!temp.isEmpty()) {
            temp.poll();
        }

        PriorityQueue<Integer> temp1 = map.get(1);
        System.out.println(temp1.size());
    }

    // LCR 077. 排序链表(采用归并排序)
    public ListNode sortListByMerge(ListNode head) {
        return null;
    }

    // 2003. 每棵子树内缺失的最小基因值
    public int[] smallestMissingValueSubtree(int[] parents, int[] nums) {
        int n = parents.length;
        List<Integer>[] children = new List[n];
        for (int i = 0; i < n; i++) {
            children[i] = new ArrayList<>();
        }
        for (int i = 1; i < n; i++) {
            children[parents[i]].add(i);
        }

        int[] res = new int[n];
        Arrays.fill(res, 1);
        Set<Integer>[] geneSet = new Set[n];
        for (int i = 0; i < n; i++) {
            geneSet[i] = new HashSet<>();
        }
        dfs(0, res, nums, children, geneSet);
        return res;
    }

    public int dfs(int node, int[] res, int[] nums, List<Integer>[] children, Set<Integer>[] geneSet) {
        geneSet[node].add(nums[node]);
        for (int child : children[node]) {
            res[node] = Math.max(res[node], dfs(child, res, nums, children, geneSet));
            if (geneSet[node].size() < geneSet[child].size()) {
                Set<Integer> temp = geneSet[node];
                geneSet[node] = geneSet[child];
                geneSet[child] = temp;
            }
            geneSet[node].addAll(geneSet[child]);
        }
        while (geneSet[node].contains(res[node])) {
            res[node]++;
        }
        return res[node];
    }

    // 2003. 每棵子树内缺失的最小基因值
    public int[] smallestMissingValueSubtreeWrong(int[] parents, int[] nums) {
        int[] res = new int[parents.length];
        Map<Integer, PriorityQueue<Integer>> map = new HashMap<>();
        for (int i = 1; i < parents.length; i++) {
            if (map.get(parents[i]) == null) {
                PriorityQueue<Integer> queue = new PriorityQueue<>();
                // parent[i]是该队列中所有元素共同的父节点
                map.put(parents[i], queue);
            }
            // i是对应的节点
            map.get(parents[i]).offer(i);
        }

        for (int i = 1; i < parents.length; i++) {
            PriorityQueue<Integer> queue = new PriorityQueue<>(map.get(i));

        }
        for (Map.Entry<Integer, PriorityQueue<Integer>> entry : map.entrySet()) {
            PriorityQueue<Integer> queue = new PriorityQueue<>(map.get(entry.getKey()));

        }

        int[] arr = new int[100001];
        for (int num : nums) {
            arr[num]++;
        }

        for (int i = 1; i < parents.length; i++) {
            // 这里就是获取到第i个元素所有的子节点
            map.get(i);
        }
        return res;
    }

    // 31. 下一个排列
    public void nextPermutation(int[] nums) {
        int first = nums.length - 2, second = nums.length - 1;
        int mark = 0, minIndex = nums.length - 1;
        // 这一步结束后，从second ~ num.length - 1必定是降序的
        while (first >= 0) {
            if (nums[first] < nums[second]) {
                mark = 1;
                break;
            }
            first--;
            second--;
        }

        if (mark == 0) {
            Arrays.sort(nums);
        } else {
            int tempLeft = second, tempRight = nums.length - 1;
            while (tempLeft < tempRight) {
                swap(nums, tempLeft, tempRight);
                tempLeft++;
                tempRight--;
            }

            for (int i = second; i < nums.length; i++) {
                if (nums[i] > nums[first]) {
                    swap(nums, i, first);
                    break;
                }
            }
        }
    }

    public void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
