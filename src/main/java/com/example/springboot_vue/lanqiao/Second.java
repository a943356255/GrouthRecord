package com.example.springboot_vue.lanqiao;

import java.util.Scanner;

public class Second {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        int a = scanner.nextInt();
        int b = scanner.nextInt();

        int[][] arr = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                arr[i][j] = scanner.nextInt();
            }
        }

        int part = 998244353;
        long res = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (i + a <= n && j + b <= m) {
                    int max = arr[i][j];
                    int min = arr[i][j];
                    for (int k = i; k < i + a; k++) {
                        for (int l = j; l < j + b; l++) {
                            if (arr[k][l] > max) {
                                max = arr[k][l];
                            }
                            if (arr[k][l] < min) {
                                min = arr[k][l];
                            }
                        }
                    }
                    res += (long) max * min;
                }
            }
        }

        System.out.println(res % part);
    }

}
