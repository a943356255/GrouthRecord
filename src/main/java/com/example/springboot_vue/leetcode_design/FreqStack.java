package com.example.springboot_vue.leetcode_design;

import java.util.*;

public class FreqStack {

    // 用该map来统计每一个val出现的次数
    Map<Integer, Integer> map = new HashMap<>();
    // 用上边出现的次数，作为key，key为出现的频率，value为同一频率的栈
    Map<Integer, Stack<Integer>> stackMap = new HashMap<>();

    Stack<Integer> stack = new Stack<>();


    List<Integer> list = new ArrayList<>();

    public FreqStack() {

    }

    public void push(int val) {
        stack.push(val);

        map.merge(val, 1, Integer::sum);
    }

    public int pop() {
        System.out.println(stack.toString());
        int max = -1;
        int key = 0;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (max < entry.getValue()) {
                max = entry.getValue();
                key = entry.getKey();
            }
        }

        int returnVal = -1;
        Stack<Integer> stack1 = new Stack<>();
        while (!stack.isEmpty()) {
            if (map.get(stack.peek()) == max || stack.peek() == key) {
                map.put(stack.peek(), max - 1);
                break;
            }
            stack1.add(stack.pop());
        }
        System.out.println(stack1.toString());
        returnVal = stack.pop();

        while (!stack1.isEmpty()) {
            stack.push(stack1.pop());
        }

        return returnVal;
    }

}
