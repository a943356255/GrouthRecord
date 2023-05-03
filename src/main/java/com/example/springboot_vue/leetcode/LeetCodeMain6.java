package com.example.springboot_vue.leetcode;

import java.util.Stack;

public class LeetCodeMain6 {

    public static void main(String[] args) {
        StringBuilder stringBuilder = new StringBuilder("abc");
        Stack<String> stack = new Stack<>();
        stack.push(stringBuilder.substring(0, 0) + stringBuilder.substring(3, 3));
        System.out.println(stack.peek().equals(""));
//        System.out.println(stringBuilder.substring(0, 0) + stringBuilder.substring(3, 3));
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
