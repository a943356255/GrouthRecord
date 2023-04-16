package com.example.springboot_vue.leetcode_design;

import java.util.HashMap;
import java.util.Map;

/**
 * 1157. 子数组中占绝大多数的元素
 */
public class MajorityChecker {

    public int[] arr;

    public MajorityChecker(int[] arr) {
        this.arr = arr;
    }

    // 暴力写法，会超时
    public int query(int left, int right, int threshold) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = left; i <= right; i++) {
            map.merge(arr[i], 1, Integer::sum);
        }

        int max = 0, res = -1;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getValue() >= threshold && entry.getValue() > max) {
                max = entry.getValue();
                res = entry.getKey();
            }
        }

        return res;
    }

}
