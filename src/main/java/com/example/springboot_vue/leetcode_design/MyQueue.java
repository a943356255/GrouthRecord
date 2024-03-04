package com.example.springboot_vue.leetcode_design;

import java.util.Stack;

// 232. 用栈实现队列
public class MyQueue {

    Stack<Integer> first;
    Stack<Integer> second;

    public MyQueue() {
        first = new Stack<>();
        second = new Stack<>();
    }

    public void push(int x) {
        while (!second.isEmpty()) {
            first.push(second.pop());
        }
        first.push(x);
    }

    public int pop() {
        while (!first.isEmpty()) {
            second.push(first.pop());
        }
        return second.pop();
    }

    public int peek() {
        while (!first.isEmpty()) {
            second.push(first.pop());
        }

        return second.peek();
    }

    public boolean empty() {
        return second.isEmpty() && first.isEmpty();
    }

}
