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

    // 409. 最长回文串
    public int longestPalindrome(String s) {
        int res = 0;
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            map.merge(c, 1, Integer::sum);
        }

        int odd = 0;
        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            if (entry.getValue() % 2 != 0) {
                odd++;
            }
            res += entry.getValue();
        }

        if (odd >= 2) {
            res -= (odd - 1);
        }

        return res;
    }

    // 841. 钥匙和房间
    public boolean canVisitAllRooms(List<List<Integer>> rooms) {
        Set<Integer> set = new HashSet<>();
        set.add(0);
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(0);
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                // 这里是所有已经获得钥匙的房间
                int index = queue.poll();
                List<Integer> temp = rooms.get(index);
                for (int j = 0; j < rooms.get(index).size(); j++) {
                    if (!set.contains(rooms.get(index).get(j))) {
                        queue.offer(rooms.get(index).get(j));
                    }
                }
                set.addAll(temp);
            }
        }

        System.out.println(set.toString());
        return set.size() == rooms.size();
    }

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
