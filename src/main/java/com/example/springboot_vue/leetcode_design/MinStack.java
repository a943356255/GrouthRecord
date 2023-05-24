package com.example.springboot_vue.leetcode_design;

import java.util.*;

public class MinStack {

    PriorityQueue<Integer> queue;
    Stack<Integer> stack;
    Map<Integer, Integer> map;

    public MinStack() {
        queue = new PriorityQueue<>(Comparator.comparingInt(a -> a));
        stack = new Stack<>();
        map = new HashMap<>();
    }

    public void push(int val) {
        stack.push(val);
        queue.offer(val);
    }

    public void pop() {
        int val = stack.peek();
        map.merge(val, 1, Integer::sum);
        if (!queue.isEmpty()) {
            if (val == queue.peek()) {
                queue.poll();
            }
        }

        stack.pop();
    }

    public int top() {
        if (!stack.isEmpty()) {
            return stack.peek();
        }
        return 0;
    }

    public int getMin() {
        if (!queue.isEmpty()) {
            return queue.peek();
        }
        return 0;
    }

}
