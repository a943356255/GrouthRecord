package com.example.springboot_vue.leetcode;

import java.util.*;

public class LeetCodeMain15 {

    public static void main(String[] args) {

    }



    // 2788. 按分隔符拆分字符串
    public List<String> splitWordsBySeparator(List<String> words, char separator) {
        List<String> res = new ArrayList<>();
        for (String word : words) {
            StringBuilder sb = new StringBuilder();
            int length = word.length();
            for (int i = 0; i < length; i++) {
                char c = word.charAt(i);
                if (c == separator) {
                    if (sb.length() > 0) {
                        res.add(sb.toString());
                        sb.setLength(0);
                    }
                } else {
                    sb.append(c);
                }
            }
            if (sb.length() > 0) {
                res.add(sb.toString());
            }
        }
        return res;
    }

    // 2171. 拿出最少数目的魔法豆
    public long minimumRemoval(int[] beans) {
//        Arrays.sort(beans);
//        System.out.println(Arrays.toString(beans));
//        long temp = 0;
//        // 按照第一个位置拿
//        for (int i = 1; i < beans.length; i++) {
//            temp += beans[i] - beans[0];
//        }
//        System.out.println("temp = " + temp);
//        long min = temp;
//        int add = beans[0];
//        // 按照第i个位置拿
//        for (int i = 1; i < beans.length; i++) {
//            if (beans[i] == beans[i - 1]) {
//                add += beans[i];
//                continue;
//            }
//            // 以i为基准，后续每一个都会少拿掉(beans[i] - beans[index])个豆子，后续一共有(beans.length - i)
//            // 所以会少拿differ个豆子，但是会多拿掉前i个豆子，不包括i
//            int differ = (beans[i] - beans[i - 1]) * (beans.length - i);
//            temp = temp - differ + beans[i - 1];
//            System.out.println("differ = " + differ + " temp = " + temp + " add = " + add);
//            if (temp < min) {
//                min = temp;
//            }
//            add += beans[i];
//        }
//
//        return min;
        int n = beans.length;
        Arrays.sort(beans);
        long total = 0; // 豆子总数
        for (int bean : beans) {
            total += bean;
        }

        long res = total; // 最少需要移除的豆子数
        for (int i = 0; i < n; i++) {
            res = Math.min(res, total - (long) beans[i] * (n - i));
        }

        return res;
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
