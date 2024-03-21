package com.example.springboot_vue.leetcode;

import java.util.*;

public class LeetCodeMain19 {

    public static void main(String[] args) {

    }

    // 14. 最长公共前缀
    public String longestCommonPrefix(String[] strs) {
        StringBuilder res = new StringBuilder();

        int min = Integer.MAX_VALUE;
        for (int i = 0; i < strs.length; i++) {
            min = Math.min(min, strs[i].length());
        }

        int right = 0;
        while (right < min) {
            char c = strs[0].charAt(right);
            int same = 1;
            for (int i = 1; i < strs.length; i++) {
                if (strs[i].charAt(right) != c) {
                    same = 0;
                    break;
                }
            }
            if (same == 1) {
                res.append(c);
            } else {
                break;
            }
            right++;
        }

        return res.toString();
    }

    // 433. 最小基因变化
    public int minMutation(String startGene, String endGene, String[] bank) {
        int res = Integer.MAX_VALUE;
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < startGene.length(); i++) {
            if (startGene.charAt(i) != endGene.charAt(i)) {
                list.add(i);
            }
        }

        return res;
    }

    // 30. 串联所有单词的子串
    public List<Integer> findSubstring(String s, String[] words) {
        Map<String, Integer> map = new HashMap<>();
        for (String word : words) {
            map.merge(word, 1, Integer::sum);
        }

        int length = words[0].length();
        StringBuilder first = new StringBuilder();
        List<String> list = new ArrayList<>();
        int left = 0, right = 0;
        while (right < s.length()) {
            char c = s.charAt(right);
            first.append(c);
            if (first.length() == length) {
                list.add(new String(first));
                first.delete(left, left + 1);
                left++;
            }
            right++;
        }

        List<Integer> res = new ArrayList<>();
        left = 0;
        right = 0;
        while (right < list.size()) {

        }

        return res;
    }

    // 58. 最后一个单词的长度
    public int lengthOfLastWord(String s) {
        int res = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            char c = s.charAt(i);
            if (c == ' ') {
            } else {
                while (i >= 0 && s.charAt(i--) != ' ') {
                    res++;
                }
                break;
            }
        }

        return res;
    }
}
