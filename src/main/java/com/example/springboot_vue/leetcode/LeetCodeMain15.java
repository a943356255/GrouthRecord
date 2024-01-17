package com.example.springboot_vue.leetcode;

import java.util.HashMap;
import java.util.Map;

public class LeetCodeMain15 {

    public static void main(String[] args) {

    }

    // 2744. 最大字符串配对数目
    public int maximumNumberOfStringPairs(String[] words) {
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < words.length; i++) {
            if (words[i].charAt(0) != words[i].charAt(words[i].length() - 1))
                map.put(words[i], "1");
        }

        int res = 0;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            StringBuilder temp = new StringBuilder(entry.getKey());
            if (map.get(temp.reverse().toString()) != null) {
                res++;
            }
        }

        return res / 2;
    }

}
