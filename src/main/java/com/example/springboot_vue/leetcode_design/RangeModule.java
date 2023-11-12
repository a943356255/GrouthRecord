package com.example.springboot_vue.leetcode_design;

import java.util.Map;
import java.util.TreeMap;

// 715. Range 模块
public class RangeModule {

    TreeMap<Integer, Integer> intervals;

    public RangeModule() {
        intervals = new TreeMap<>();
    }

    /**
     * 添加范围时，先获取到大于当前left的最小值，然后判断它是否为空，如果为空，则说明当前left已经是区间的最大值
     * 那么直接添加就可以了
     * 另一种情况，返回的entry是当前集合中的最小值，
     */
    public void addRange(int left, int right) {
        // 这里返回的是key大于 left 的最小的value
        Map.Entry<Integer, Integer> entry = intervals.higherEntry(left);
        // firstEntry，返回该集合中最小的key以及它对应的value
        // 这里，判断了返回的元素是否是当前集合的最小元素，如果不是，那么意味着此次插入的是在区间内
        if (entry != intervals.firstEntry()) {
            // lowerEntry返回key小于给定值的最大值
            // 这里，给start赋值，如果为空，则意味着没有更大的，取当前集合的最大元素，如果不为空，则取比获取到的left小一个的元素
            Map.Entry<Integer, Integer> start = entry != null ? intervals.lowerEntry(entry.getKey()) : intervals.lastEntry();
            // 这种情况，说明新增区间在集合中最后一个元素范围内
            if (start != null && start.getValue() >= right) {
                return;
            }
            //
            if (start != null && start.getValue() >= left) {
                left = start.getKey();
                intervals.remove(start.getKey());
            }
        }
        while (entry != null && entry.getKey() <= right) {
            right = Math.max(right, entry.getValue());
            intervals.remove(entry.getKey());
            entry = intervals.higherEntry(entry.getKey());
        }
        intervals.put(left, right);
    }

    public void myAddRange(int left, int right) {
        Map.Entry<Integer, Integer> entry = intervals.higherEntry(left);
        // entry为null，说明集合中没有比left更大的元素，但仍然有可能包裹此次插入的数据
        if (entry == null) {
            entry = intervals.lastEntry();
            if (right >= entry.getValue()) {
                intervals.put(entry.getKey(), right);
            }
            return;
        }

        // 此时说明left是该集合中key最小的
        if (entry == intervals.firstEntry()) {

        }
        System.out.println();
    }

    public boolean queryRange(int left, int right) {
        Map.Entry<Integer, Integer> entry = intervals.higherEntry(left);
        if (entry == intervals.firstEntry()) {
            return false;
        }
        entry = entry != null ? intervals.lowerEntry(entry.getKey()) : intervals.lastEntry();
        return entry != null && right <= entry.getValue();
    }

    public void removeRange(int left, int right) {
        Map.Entry<Integer, Integer> entry = intervals.higherEntry(left);
        if (entry != intervals.firstEntry()) {
            Map.Entry<Integer, Integer> start = entry != null ? intervals.lowerEntry(entry.getKey()) : intervals.lastEntry();
            if (start != null && start.getValue() >= right) {
                int ri = start.getValue();
                if (start.getKey() == left) {
                    intervals.remove(start.getKey());
                } else {
                    intervals.put(start.getKey(), left);
                }
                if (right != ri) {
                    intervals.put(right, ri);
                }
                return;
            } else if (start != null && start.getValue() > left) {
                if (start.getKey() == left) {
                    intervals.remove(start.getKey());
                } else {
                    intervals.put(start.getKey(), left);
                }
            }
        }

        while (entry != null && entry.getKey() < right) {
            if (entry.getValue() <= right) {
                intervals.remove(entry.getKey());
                entry = intervals.higherEntry(entry.getKey());
            } else {
                intervals.put(right, entry.getValue());
                intervals.remove(entry.getKey());
                break;
            }
        }
    }

}
