package com.example.springboot_vue.lanqiao;

import java.io.IOException;
import java.util.Scanner;

public class GetPartArr {

    public static void main(String[] args) throws IOException {

//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
//        String[] strings = bufferedReader.readLine().split(" ");
        Long res = 0L;

        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();

        int[] arr = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            arr[i] = scanner.nextInt();
            arr[i] += arr[i - 1];
        }

        if (arr[n] % 3 != 0) {
            System.out.println(res);
            return;
        }


        // 这里的i是第二刀，也就是结尾刀这个点的数据
        // i = 0是没有值的，默认从1开始有数据，从3开始，1 和 2 是第一个考虑的点
        for (int i = 3, cnt = 0; i <= n; i++) {
            // 这里arr[i - 1]是i-1的前缀和，如果等于n/3，说明这里可以切一刀
            // arr[i - 2]是第一个点，从前往后遍历，如果等于sum/3，那么就可以切一刀
            if (arr[i - 2] == arr[n] / 3) {
                cnt++;
            }
            // arr[n] - arr[i - 1]这里是一个区间，意思是最后到i-1这个点的和
            if (arr[n] - arr[i - 1] == arr[n] / 3) {
                res += cnt;
            }
        }

        System.out.println(res);
    }

}
