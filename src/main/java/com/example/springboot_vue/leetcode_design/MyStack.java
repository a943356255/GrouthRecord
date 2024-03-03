package com.example.springboot_vue.leetcode_design;

import java.util.*;

// 225. 用队列实现栈
public class MyStack {

    Queue<Integer> first;
    Queue<Integer> second;

    /*
      这里是一直维持栈的特性，通过不断交换两个引用类型的指向达到的
     */
    public MyStack() {
        first = new ArrayDeque<>();
        second = new ArrayDeque<>();
    }

    public void push(int x) {
        first.offer(x);
        // 队列2元素全部入队1
        while (!second.isEmpty()) {
            first.offer(second.poll());
        }

        Queue<Integer> temp = first;
        first = second;
        second = temp;
    }

    public int pop() {
        return second.poll();
    }

    public int top() {
        return second.peek();
    }

    public boolean empty() {
        return second.isEmpty();
    }

}
