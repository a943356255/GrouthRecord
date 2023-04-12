package com.example.springboot_vue.lanqiao;

import java.util.Scanner;

public class Bricks {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int t = scanner.nextInt();
        while (t > 0) {
            t--;
            int n = scanner.nextInt();
            String str = scanner.next();
            StringBuilder stringBuilder = new StringBuilder(str);

            int count1 = 0, count2 = 0, max;
            if (stringBuilder.charAt(0) != 'B') {
                count1++;
                stringBuilder.replace(0, 1, "B");
            }
            // 第一种情况，首字母B
            int index = 1;
            for (int i = 1; i < stringBuilder.length(); i++) {
                if (index == 1) {
                    if (stringBuilder.charAt(i) != 'W') {
                        count1++;
                        stringBuilder.replace(i, i + 1, "W");
                    }
                } else {
                    if (stringBuilder.charAt(i) != 'B') {
                        count1++;
                        stringBuilder.replace(i, i + 1, "B");
                    }
                }
                index = 1 - index;
            }

            // 第二种情况，首字母W
            stringBuilder = new StringBuilder(str);
            if (stringBuilder.charAt(0) != 'W') {
                count2++;
                stringBuilder.replace(0, 1, "W");
            }
            index = 0;
            for (int i = 1; i < stringBuilder.length(); i++) {
                if (index == 0) {
                    if (stringBuilder.charAt(i) != 'B') {
                        count1++;
                        stringBuilder.replace(i, i + 1, "B");
                    }
                } else {
                    if (stringBuilder.charAt(i) != 'W') {
                        count1++;
                        stringBuilder.replace(i, i + 1, "W");
                    }
                }
                index = 1 - index;
            }

            max = Math.min(count1, count2);

            System.out.println(max);
        }
    }

}