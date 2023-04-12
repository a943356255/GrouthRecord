package com.example.springboot_vue.lanqiao;

import java.util.Scanner;

public class HuiWen {

    public static void main(String[] args) {

        Object[] o = new Object[]{5};
        System.out.println(o[0].getClass());

        String str = new Scanner(System.in).next();

        int left = 0, right = str.length() - 1;
        StringBuilder stringBuilder = new StringBuilder(str);
        if (judge(str)) {
            int part = 0;
            while (left < right) {
                if (str.charAt(left) != 'a') {
                    stringBuilder.replace(left, left + 1, "a");
                    stringBuilder.replace(right, right + 1, "a");
                    part++;
                    break;
                }
                left++;
                right--;
            }
            if (str.length() % 2 != 0 && part == 0) {
                stringBuilder.replace(str.length() / 2, str.length() / 2 + 1, "a");
            }
            System.out.println(stringBuilder.toString());
            return;
        }

        int count = 0;
        int index1Left = -1, index1Right = -1, index2Left = -1, index2Right = -1;
        while (left < right) {
            if (stringBuilder.charAt(left) != stringBuilder.charAt(right)) {
                count++;
                if (count == 1) {
                    index1Left = left;
                    index1Right = right;
                } else {
                    index2Left = left;
                    index2Right = right;
                }
            }
            left++;
            right--;
        }

        if (count == 1) {
            if (str.charAt(index1Left) == 'a' || str.charAt(index1Right) == 'a') {
                if (str.length() % 2 != 0) {
                    stringBuilder.replace(str.length() / 2, str.length() / 2 + 1, "a");
                }
            }
            stringBuilder.replace(index1Left, index1Left + 1, "a");
            stringBuilder.replace(index1Right, index1Right + 1, "a");
        } else {
            char a = str.charAt(index1Left);
            char b = str.charAt(index1Right);
            if (a < b) {
                stringBuilder.replace(index1Right, index1Right + 1, String.valueOf(a));
            } else {
                stringBuilder.replace(index1Left, index1Left + 1, String.valueOf(b));
            }

            char c = str.charAt(index2Left);
            char d = str.charAt(index2Right);
            if (c < d) {
                stringBuilder.replace(index2Right, index2Right + 1, String.valueOf(c));
            } else {
                stringBuilder.replace(index2Left, index2Left + 1, String.valueOf(d));
            }
        }
        System.out.println(stringBuilder.toString());
    }

    public static boolean judge(String str) {
        int left = 0, right = str.length() - 1;
        while (left < right) {
            if (str.charAt(left) == str.charAt(right)) {
                left++;
                right--;
            } else {
                return false;
            }
        }
        return true;
    }
}
