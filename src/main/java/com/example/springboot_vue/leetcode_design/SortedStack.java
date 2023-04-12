package com.example.springboot_vue.leetcode_design;

import java.util.Deque;
import java.util.LinkedList;

public class SortedStack {

    //原始栈
    Deque<Integer> stack = new LinkedList<>();
    //辅助栈
    Deque<Integer> tmp = new LinkedList<>();

    public SortedStack() {

    }

    public void push(int val) {
        if (stack.isEmpty() && tmp.isEmpty()) {
            stack.push(val);
        } else {
            if (tmp.isEmpty() || val > tmp.peek()) {
                while (!stack.isEmpty() && val > stack.peek()) {
                    tmp.push(stack.pop());
                }
                stack.push(val);
            } else {
                while (!tmp.isEmpty() && val < tmp.peek()) {
                    stack.push(tmp.pop());
                }
                tmp.push(val);
            }
        }
    }

    public void pop() {
        if (stack.isEmpty() && tmp.isEmpty()) {
            return;
        }
        while (!tmp.isEmpty()) {
            stack.push(tmp.pop());
        }
        stack.pop();
    }

    public int peek() {
        if (isEmpty()) {
            return -1;
        } else {
            while (!tmp.isEmpty()) {
                stack.push(tmp.pop());
            }
            return stack.peek();
        }
    }

    public boolean isEmpty() {
        return stack.isEmpty() && tmp.isEmpty();
    }

}
