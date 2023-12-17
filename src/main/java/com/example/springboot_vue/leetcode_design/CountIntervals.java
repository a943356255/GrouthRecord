package com.example.springboot_vue.leetcode_design;

import java.util.Map;
import java.util.TreeMap;

// 2276. 统计区间中的整数数目
public class CountIntervals {

    TreeMap<Integer, Integer> map = new TreeMap<>();
    int cnt = 0;

    public CountIntervals() {

    }

    public void add(int left, int right) {
        // 这里是获取现存元素中，left小于 当前传入right的最大值
        // 这里，如果left已经大于当前传入的right，则无需合并,找到left 小于right的，去判断是否交叉即可
        Map.Entry<Integer, Integer> interval = map.floorEntry(right);
        // 这里是判断是否需要合并
        while (interval != null && interval.getValue() >= left) {
            int l = interval.getKey(), r = interval.getValue();
            left = Math.min(left, l);
            right = Math.max(right, r);
            cnt -= r - l + 1;
            map.remove(l);
            interval = map.floorEntry(right);
        }
        cnt += (right - left + 1);
        map.put(left, right);
    }

    public int count() {
        return cnt;
    }

}
