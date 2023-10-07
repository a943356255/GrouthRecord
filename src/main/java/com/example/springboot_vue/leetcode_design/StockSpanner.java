package com.example.springboot_vue.leetcode_design;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 901. 股票价格跨度
 * 自己做的时候，看了提示用了单调栈，但是没法解决新进入的元素比之前所有元素都大的情况
 * 题解使用了下标来解决这个问题，记录每个元素进入栈的下标，可以利用下标知道上一个比它大的元素是谁
 * 然后通过下标计算个数就可以了
 */

public class StockSpanner {

    Deque<int[]> stack;
    int idx;

    public StockSpanner() {
        stack = new ArrayDeque<int[]>();
        stack.push(new int[]{-1, Integer.MAX_VALUE});
        idx = -1;
    }

    public int next(int price) {
        idx++;
        while (price >= stack.peek()[1]) {
            stack.pop();
        }
        int ret = idx - stack.peek()[0];
        stack.push(new int[]{idx, price});
        return ret;
    }
}
