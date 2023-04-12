package com.example.springboot_vue.acwing;

import java.util.*;

public class MyTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] start = new int[n];
        int[] end = new int[n];
        for (int i = 0; i < n; i++) {
            start[i] = scanner.nextInt();
        }
        for (int i = 0; i < n; i++) {
            end[i] = scanner.nextInt();
        }
        scanner.close();

        int[] time = new int[2000001]; // 建立一个时间轴，记录每个时刻的流星数
        for (int i = 0; i < n; i++) {
            time[start[i]]++; // 开始时刻加1
            time[end[i]+1]--; // 结束时刻后一秒减1
        }

        int maxCount = 0; // 最多观测到的流星数
        int bestTimeCount = 0; // 最佳观测时间段的个数
        int count = 0; // 当前观测时间段的流星数
        for (int i = 1; i < time.length; i++) {
            count += time[i];
            if (count > maxCount) { // 如果当前观测时间段的流星数大于最大值
                maxCount = count; // 更新最大值
                bestTimeCount = 1; // 当前时间为最佳观测时间段
            } else if (count == maxCount) { // 如果当前观测时间段的流星数等于最大值
                bestTimeCount++; // 最佳观测时间段的个数加1
            }
        }

        System.out.println(maxCount + " " + bestTimeCount);
    }
}

