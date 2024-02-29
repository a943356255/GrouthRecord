package com.example.springboot_vue.leetcode;

import java.util.*;

public class LeetCodeMain16 {

    public static void main(String[] args) {
        String str = "411032199811252314 + 199929992992";
    }

//    public List<String> wordBreak(String s, List<String> wordDict) {
//        Map<String, String> map = new HashMap<>();
//        for (String value : wordDict) {
//            map.put(value, "1");
//        }
//
//        StringBuilder first = new StringBuilder();
//        for (int i = 0; i < s.length(); i++) {
//            char c = s.charAt(i);
//            first.append(c);
//            if (map.get(first.toString()) != null) {
//                StringBuilder temp = new StringBuilder(first);
//                dfs(temp, i + 1, s, map);
//            }
//        }
//
//        return wordBreak;
//    }
//
//    public void dfs(StringBuilder str, int index, String s, Map<String, String> map) {
//        str.append(" ");
//        for (int i = index; i < s.length(); i++) {
//            char c = s.charAt(i);
//
//        }
//    }

    // 140. 单词拆分 II
    public List<String> wordBreak(String s, List<String> wordDict) {
        Map<Integer, List<List<String>>> map = new HashMap<>();
        List<List<String>> wordBreaks = backtrack(s, s.length(), new HashSet<>(wordDict), 0, map);
        List<String> breakList = new LinkedList<>();
        for (List<String> wordBreak : wordBreaks) {
            breakList.add(String.join(" ", wordBreak));
        }
        return breakList;
    }

    public List<List<String>> backtrack(String s, int length, Set<String> wordSet, int index, Map<Integer, List<List<String>>> map) {
        if (!map.containsKey(index)) {
            List<List<String>> wordBreaks = new LinkedList<>();
            if (index == length) {
                wordBreaks.add(new LinkedList<>());
            }
            for (int i = index + 1; i <= length; i++) {
                String word = s.substring(index, i);
                if (wordSet.contains(word)) {
                    List<List<String>> nextWordBreaks = backtrack(s, length, wordSet, i, map);
                    for (List<String> nextWordBreak : nextWordBreaks) {
                        LinkedList<String> wordBreak = new LinkedList<>(nextWordBreak);
                        wordBreak.offerFirst(word);
                        wordBreaks.add(wordBreak);
                    }
                }
            }
            map.put(index, wordBreaks);
        }
        return map.get(index);
    }
}
