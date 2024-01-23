package com.example.springboot_vue.leetcode;

import java.util.*;

public class LeetCodeMain15 {

    public static void main(String[] args) {
        LeetCodeMain15 leetCodeMain15 = new LeetCodeMain15();
        leetCodeMain15.maximumSwap(4567);

    }

    // 2765. 最长交替子数组
    public int alternatingSubarray(int[] nums) {
        int res = -1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] - nums[i - 1] == 1) {
                System.out.println("nums[i] = " + nums[i] + " nums[i - 1] = " + nums[i - 1]);
                int temp = 2, mark = nums[i], tempIndex = i;
                for (int j = i + 2; j < nums.length; j += 2) {
                    if (nums[j] == mark && nums[j] - nums[j - 1] == 1) {
                        temp += 2;
                    } else {
                        break;
                    }
                    tempIndex = j;
                }
                System.out.println("tempIndex = " + tempIndex + " temp = " + temp);
                if (tempIndex + 1 < nums.length) {
                    if (nums[tempIndex + 1] == mark - 1) {
                        temp++;
                    }
                }
                System.out.println("temp = " + temp);
                res = Math.max(res, temp);
                i = tempIndex;
            }
        }

        return res;
    }

    // 670. 最大交换
    public int maximumSwap(int num) {
        List<Integer> list = new ArrayList<>();
        while (num > 0) {
            list.add(0, num % 10);
            num /= 10;
        }

        List<Integer> temp = new ArrayList<>(list);
        temp.sort((a, b) -> b - a);
        for (int i = 0; i < list.size(); i++) {
            if (!list.get(i).equals(temp.get(i))) {
                int big = temp.get(i);
                int min = list.get(i);
                for (int j = list.size() - 1; j >= i + 1; j--) {
                    if (list.get(j) == big) {
                        list.set(j, min);
                        list.set(i, big);
                        break;
                    }
                }
                break;
            }
        }

        int res = list.get(0);
        for (int i = 1; i < list.size(); i++) {
            res = res * 10 + list.get(i);
        }
        return res;
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
