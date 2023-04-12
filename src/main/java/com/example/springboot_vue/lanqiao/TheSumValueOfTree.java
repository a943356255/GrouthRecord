package com.example.springboot_vue.lanqiao;

import java.util.Scanner;

public class TheSumValueOfTree {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = scanner.nextInt();
        }

        int high = 0, res = 0, count = 0;
        long part = 0L, max = (long) -1e18;
        for (int i = 0; i < n; i++) {
            part += arr[i];
            count++;
            if (count == 1L << high) {
                high++;
                if (max < part) {
                    max = part;
                    res = high;
                }
                part = 0L;
                count = 0;
            }
        }

        System.out.println(res);
    }

}
