package com.example.springboot_vue.concurrency;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    static int[] arr = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    static int[] arr2 = {0, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    public static void main(String[] args) throws IOException {
//        System.out.println((long) Math.pow(12, 7));
        first();
    }

    // 棋盘
    public static void first() {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();

        int[][] arr = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                arr[i][j] = 0;
            }
        }

        int[][] arr2 = new int[m][4];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < 4; j++) {
                arr2[i][j] = scanner.nextInt();
            }
        }

        for (int i = 0; i < m; i++) {
            for (int j = arr2[i][0]; j <= arr2[i][2]; j++) {
                for (int k = arr2[i][1]; k <= arr2[i][3]; k++) {
                    arr[j - 1][k - 1] = 1 - arr[j - 1][k - 1];
                }
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(arr[i][j]);
            }
            System.out.println();
        }
    }
}
