package com.example.springboot_vue.leetcode_design;

import java.util.*;

public class DinnerPlates {

    private final int capacity;

    List<Stack<Integer>> list;

    PriorityQueue<Integer> queue = new PriorityQueue<>();

    public static void main(String[] args) {
        String[] arr = {"push","push","push","push","push","push","push","push","popAtStack","popAtStack","popAtStack","popAtStack","push","push","push","push","push","push","push","push","pop","pop","pop","pop"};
        int[] val = {471,177,1,29,333,154,130,333,1,0,2,0,165,383,267,367,53,373,388,249,0,0,0,0};

        DinnerPlates dinnerPlates = new DinnerPlates(2);
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].equals("push")) {
                dinnerPlates.push(val[i]);
            } else if (arr[i].equals("popAtStack")) {
                dinnerPlates.popAtStack(val[i]);
            } else {
                dinnerPlates.pop();
            }
        }
    }

    public DinnerPlates(int capacity) {
        this.capacity = capacity;
        list = new ArrayList<>();
        list.add(new Stack<>());
    }

    public void push(int val) {
        int index = list.size() - 1;

        int mark = 0;
        if (!queue.isEmpty()) {
            int queueIndex = queue.peek();
            while (!queue.isEmpty() && queueIndex > list.size() - 1) {
                queue.poll();
                if (!queue.isEmpty()) {
                    queueIndex = queue.peek();
                }
            }

            if (queueIndex <= list.size() - 1) {
                list.get(queueIndex).add(val);
                mark = 1;
                if (list.get(queueIndex).size() == capacity) {
                    queue.poll();
                }
            }
        }

        if (mark == 1) {
            return;
        }

        if (list.get(index).size() == capacity) {
            Stack<Integer> stack = new Stack<>();
            stack.push(val);
            list.add(stack);
        } else {
            list.get(index).push(val);
        }
    }

    public int pop() {
        while (!list.isEmpty() && list.get(list.size() - 1).isEmpty()) {
            list.remove(list.size() - 1);
        }

        if (list.isEmpty()) {
            return -1;
        }

        return list.get(list.size() - 1).pop();
    }

    public int popAtStack(int index) {
        if (index > list.size() || list.get(index).isEmpty()) {
            return -1;
        }

        if (!queue.contains(index)) {
            queue.offer(index);
        }
        return list.get(index).pop();
    }

}
