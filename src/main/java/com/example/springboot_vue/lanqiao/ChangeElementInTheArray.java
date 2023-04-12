package com.example.springboot_vue.lanqiao;

import java.util.Scanner;

public class ChangeElementInTheArray {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int t = scanner.nextInt();
        while (t != 0) {
            t--;
            int n = scanner.nextInt();
            int[] v = new int[n];
            for (int i = 1; i <= n; i++) {
                int a = scanner.nextInt();
                // 这里的i - a + 1代表当前a可以包含的区间，比如说i = 4， a = 3，
                // i - a + 1 = 2,意思是从下标2到下标4（2，3，4）这三个区间都被操作
                // 只记录最左区间的原因是后续要求前缀和，最左端被修改，他就一定
                int left = Math.max(1, i - a + 1);
                v[left] ++;
                v[i + 1]--;
            }
            for (int i = 1; i <= n; i++) {
                v[i] += v[i - 1];
                System.out.println(v[i] + " ");
            }
            System.out.println();
        }
    }
}
