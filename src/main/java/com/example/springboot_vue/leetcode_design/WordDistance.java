package com.example.springboot_vue.leetcode_design;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordDistance {

    Map<String, List<Integer>> map = new HashMap<>();

    public WordDistance(String[] wordsDict) {
        for (int i = 0; i < wordsDict.length; i++) {
            if (map.containsKey(wordsDict[i])) {
                map.get(wordsDict[i]).add(i);
            } else {
                List<Integer> list = new ArrayList<>();
                list.add(i);
                map.put(wordsDict[i], list);
            }
        }
        System.out.println(map.toString());
    }

    public int shortest(String word1, String word2) {
        List<Integer> word1List = map.get(word1);
        List<Integer> word2List = map.get(word2);
        System.out.println(word1List.toString());
        System.out.println(word2List.toString());
        int res = Integer.MAX_VALUE;
        for (Integer value : word1List) {
            for (Integer integer : word2List) {
                res = Math.min(res, Math.abs(value - integer));
            }
        }

        return res;
    }

}
