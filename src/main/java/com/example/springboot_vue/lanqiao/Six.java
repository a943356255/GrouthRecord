package com.example.springboot_vue.lanqiao;

import java.util.Scanner;

public class Six {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        int x = scanner.nextInt();
        int y = scanner.nextInt();

        int[][] arr = new int[n][3];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 3; j++) {
                arr[i][j] = scanner.nextInt();
            }
        }

        int res = 0;
        int left = arr[0][0], right = arr[0][0] + arr[0][1];
        for (int i = 0; i < n; i++) {

        }
    }

}
