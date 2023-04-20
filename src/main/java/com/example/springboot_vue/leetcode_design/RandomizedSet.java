package com.example.springboot_vue.leetcode_design;

import java.util.*;

public class RandomizedSet {

    Set<Integer> set;
    List<String> list = new ArrayList<>();
    Random random = new Random();

    public RandomizedSet() {
        set = new HashSet<>();
    }

    public boolean insert(int val) {
        if (set.contains(val)) {
            return false;
        } else {
            set.add(val);
            list.add(String.valueOf(val));
            return true;
        }
    }

    public boolean remove(int val) {
        if (set.contains(val)) {
            set.remove(val);
            list.remove(String.valueOf(val));
            return true;
        } else {
            return false;
        }
    }

    public int getRandom() {
        return Integer.parseInt(list.get(random.nextInt(set.size())));
    }

}
