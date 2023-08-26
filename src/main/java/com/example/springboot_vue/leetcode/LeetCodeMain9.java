package com.example.springboot_vue.leetcode;

import java.util.ArrayList;
import java.util.List;

public class LeetCodeMain9 {

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
