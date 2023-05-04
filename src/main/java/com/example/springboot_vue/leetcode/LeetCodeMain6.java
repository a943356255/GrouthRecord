package com.example.springboot_vue.leetcode;

import java.util.Arrays;
import java.util.Stack;

public class LeetCodeMain6 {

    public static void main(String[] args) {

    }

    // 2106. 摘水果
    public int maxTotalFruits(int[][] fruits, int startPos, int k) {
        int[] arr = new int[fruits[fruits.length - 1][0] + 1];
        int index;
        if (fruits[0][0] == 0) {
            arr[0] = fruits[0][1];
            index = 1;
        } else {
            arr[0] = 0;
            index = 0;
        }
        // 求前缀和
        for (int i = 1; i < arr.length; i++) {
            if (i == fruits[index][0]) {
                arr[i] = arr[i - 1] + fruits[index][1];
                index++;
            } else {
                arr[i] = arr[i - 1];
            }
        }
        int maxLeft = Math.max(0, startPos - k);
        int maxRight = Math.min(arr.length - 1, startPos + k);

        int legalStartPos;
        if (startPos > arr.length) {
            legalStartPos = Math.max(startPos - k, arr.length - 1);
            k = k - (startPos - legalStartPos);
            if (legalStartPos > arr.length) {
                return 0;
            }
        } else {
            legalStartPos = startPos;
        }

        int max, val = arr[maxRight] - arr[Math.max(Math.min(startPos - 1, maxRight), 0)];
        if (maxLeft == 0) {
            max = Math.max(arr[legalStartPos], val);
        } else {
            max = Math.max(arr[legalStartPos] - arr[maxLeft - 1], val);
        }
        // 优先往左走，然后右拐
        for (int i = maxLeft + 1; i < legalStartPos; i++) {
            max = Math.max(max, arr[Math.max(legalStartPos, maxRight - 2 * (i - maxLeft))] - arr[i - 1]);
        }

        // 优先往右走，然后左拐
        for (int i = legalStartPos + 1; i <= maxRight; i++) {
            max = Math.max(max, arr[i] - arr[Math.max(Math.min(startPos - k + (i - legalStartPos) * 2, legalStartPos) - 1, 0)]);
        }

        return max;
    }

    // 1003. 检查替换后的词是否有效
    public boolean isValid(String s) {
        Stack<String> stack = new Stack<>();
        stack.add(s);

        while (!stack.isEmpty()) {
            StringBuilder str = new StringBuilder(stack.pop());
            int mark = 0;
            for (int i = 0; i < str.length() - 2; i++) {
                if (str.charAt(i) == 'a' && str.charAt(i + 1) == 'b' && str.charAt(i + 2) == 'c') {
                    stack.push(str.substring(0, i) + str.substring(i + 3, str.length()));
                    mark = 1;
                    break;
                }
            }
            if (mark == 0) {
                return false;
            }

            if (stack.peek().equals("")) {
                return true;
            }
        }

        return true;
    }
}
