package com.example.springboot_vue.lanqiao;

import java.util.Scanner;

public class TheTargetSumOfArr {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        int m = scanner.nextInt();
        int x = scanner.nextInt();

        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = scanner.nextInt();
        }

        int[] arr2 = new int[m];
        for (int i = 0; i < m; i++) {
            arr2[i] = scanner.nextInt();
        }

        int resI = 0, resJ = m - 1;
        while (resI < resJ) {
            if (arr[resI] + arr2[resJ] == x) {
                break;
            } else if (arr[resI] + arr2[resJ] > x) {
                resJ--;
            } else {
                resI++;
            }
        }

        System.out.println(resI + " " + resJ);
    }

}
