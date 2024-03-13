package com.example.springboot_vue.leetcode;

import java.util.*;

public class LeetCodeMain18 {

    public static void main(String[] args) {

    }

    // 56. 合并区间
    public int[][] merge(int[][] intervals) {
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));

        List<int[]> list = new ArrayList<>();
        int lastStart = intervals[0][0];
        int lastEnd = intervals[0][1];
        list.add(new int[]{lastStart, lastEnd});

        for (int i = 1; i < intervals.length; i++) {
            int start = intervals[i][0];
            int end = intervals[i][1];
            if (start > lastEnd) {
                list.add(new int[]{start, end});
                lastEnd = end;
                lastStart = start;
            } else {
                list.remove(list.size() - 1);
                list.add(new int[]{lastStart, Math.max(lastEnd, end)});
                lastEnd = Math.max(end, lastEnd);
            }
        }

        int[][] arr = new int[list.size()][2];
        for (int i = 0; i < list.size(); i++) {
            arr[i][0] = list.get(i)[0];
            arr[i][1] = list.get(i)[1];
        }

        return arr;

//        TreeMap<Integer, Integer> treeMap = new TreeMap<>();
//        for (int i = 0; i < intervals.length; i++) {
//            int start = intervals[i][0];
//            int end = intervals[i][1];
//
//            Map.Entry<Integer, Integer> higherEntry = treeMap.higherEntry(start);
//            Map.Entry<Integer, Integer> lowerEntry = treeMap.lowerEntry(start);
//            if (higherEntry == null && lowerEntry == null) {
//                treeMap.put(start, end);
//            }
//
//            // 有大的，没有小的
//            if (higherEntry == null && lowerEntry != null) {
//                int val = lowerEntry.getValue();
//                if (start > val) {
//                    treeMap.put(start, end);
//                } else {
//                    int tempEnd = lowerEntry.getValue();
//                    treeMap.remove(val);
//                    treeMap.put(val, Math.max(tempEnd, end));
//                }
//            }
//
//            if (higherEntry != null && lowerEntry == null) {
//                int key = higherEntry.getKey();
//                int val = higherEntry.getValue();
//                if (key > end) {
//                    treeMap.put(start, end);
//                } else {
//                    treeMap.remove(key);
//                    treeMap.put(start, Math.max(end, val));
//                }
//            }
//
//
//        }
//
//        int[][] res = new int[treeMap.size()][2];
//        for (Map.Entry<Integer, Integer> entry : treeMap.entrySet()) {
//
//        }
//
//        return res;
    }

}
