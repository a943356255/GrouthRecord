package com.example.springboot_vue.leetcode_design;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class StackOfPlates {

    public int cap;
    int index = 0;
    List<Stack<Integer>> list = new ArrayList<>();

    public StackOfPlates(int cap) {
        this.cap = cap;
        Stack<Integer> stack = new Stack<>();
        list.add(stack);
    }

    public void push(int val) {
        if (cap == 0) {
            return;
        }

        if (list.size() == 0) {
            Stack<Integer> stack = new Stack<>();
            stack.push(val);
            list.add(stack);
        } else {
            if (list.get(index).size() < cap) {
                list.get(index).push(val);
            } else {
                Stack<Integer> stack = new Stack<>();
                stack.push(val);
                list.add(stack);
                index++;
            }
        }
    }

    public int pop() {
        int val;
        if (index > 0) {
            if (list.get(index).isEmpty()) {
                val = list.get(--index).pop();
            } else {
                val = list.get(index).pop();
            }
        } else {
            if (list.get(index).isEmpty()) {
                return -1;
            } else {
                val = list.get(index).pop();
            }
        }

        return val;
    }

    public int popAt(int index) {
        if (index >= list.size()) {
            return -1;
        }
        int val;
        if (list.get(index).isEmpty()) {
            return -1;
        } else {
            val = list.get(index).pop();
            if (list.get(index).isEmpty()) {
                list.remove(index);
                this.index = Math.max(this.index - 1, 0);
            }
        }

        return val;
    }

}
