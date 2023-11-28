package com.example.springboot_vue.leetcode_design;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FrontMiddleBackQueue {

    List<Integer> list;
    Map<Integer, Integer> map;
    int index = 0;

    public FrontMiddleBackQueue() {
        list = new ArrayList<>();
    }

    public void pushFront(int val) {
        list.add(0, val);
        System.out.println(list.toString());
    }

    public void pushMiddle(int val) {
        int index = getIndex();
        list.add(index, val);
        System.out.println(list.toString());
    }

    public void pushBack(int val) {
        list.add(val);
        System.out.println(list.toString());
    }

    public int popFront() {
        System.out.println(list.toString());
        if (list.size() == 0) {
            return -1;
        }
        int temp = list.get(0);
        for (int i = 1; i < list.size(); i++) {
            list.set(i - 1, list.get(i));
        }
        list.remove(list.size() - 1);
        return temp;
    }

    public int popMiddle() {
        if (list.size() == 0) {
            return -1;
        }
        int index;
        if (list.size() % 2 == 0) {
            index = Math.max(list.size() / 2 - 1, 0);
        } else {
            index = list.size() / 2;
        }
        int temp = list.get(index);
        for (int i = index + 1; i < list.size(); i++) {
            list.set(i - 1, list.get(i));
        }
        list.remove(list.size() - 1);
        return temp;
    }

    public int popBack() {
        if (list.size() == 0) {
            return -1;
        }
        int temp = list.get(list.size() - 1);
        list.remove(list.size() - 1);
        return temp;
    }

    public int getIndex() {
        //        if (list.size() % 2 == 0) {
//            temp = list.size() / 2 - 1;
//        } else {
//            temp = list.size() / 2;
//        }

        return list.size() / 2;
    }
}
