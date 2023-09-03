package com.example.springboot_vue.api_code;

import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        int n = scanner.nextInt();
//        int m = scanner.nextInt();
//
//        int[] aArr = new int[n];
//        for (int i = 0; i < n; i++) {
//            aArr[i] = scanner.nextInt();
//        }
//
//        int[][] bcArr = new int[m][2];
//        for (int i = 0; i < m; i++) {
//            bcArr[i][0] = scanner.nextInt();
//            bcArr[i][1] = scanner.nextInt();
//        }
//        Arrays.sort(bcArr, (ints, t1) -> t1[1] - ints[1]);
//
//        int sum = 0;
//        for (int i = 0; i < aArr.length; i++) {
//            int mark = 1;
//            for (int j = 0; j < m; j++) {
//                if (aArr[i] >= bcArr[j][0]) {
//                    sum += (aArr[i] - bcArr[j][1]);
//                    mark = 0;
//                    break;
//                }
//            }
//            if (mark == 1) {
//                sum += aArr[i];
//            }
//        }
//
//        System.out.println(sum);
        Main main = new Main();
        main.test();
    }

    public void test() {
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();

        String[] spiltResult = str.split(",");
        int[][] arr = new int[spiltResult.length][2];
        for (int i = 0; i < spiltResult.length; i++) {
            String[] temp = spiltResult[i].split(":");
            arr[i][0] = Integer.parseInt(temp[0]);
            arr[i][1] = Integer.parseInt(temp[1]);
        }
        Arrays.sort(arr, (a, b) -> b[1] - a[1]);

        int sum = arr[0][1];
        int temp = arr[0][1];
        for (int i = 0; i < arr.length; i++) {
            if (temp >= arr[i][1]) {
                temp -= arr[i][0];
            } else {
                sum += (arr[i][1] - temp);
                temp = arr[i][1] - arr[i][0];
            }
        }

        if (sum > 4800) {
            System.out.println(-1);
            return;
        }
        System.out.println(sum);
    }
}
