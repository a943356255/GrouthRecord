package com.example.springboot_vue.lanqiao;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Four {

    // 小蓝的旅行计划
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();

        int[][] arr = new int[n][3];
        int total = 0, cost = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 3; j++) {
                arr[i][j] = scanner.nextInt();
            }
            // 总消费
            total += arr[i][0];
            // 总的加油数（最多）
            cost += arr[i][2];
        }
        if (total > cost + m) {
            System.out.println(-1);
            return;
        }

        m -= arr[0][0];
        int money = 0;
        PriorityQueue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        for (int i = 0; i < n - 1; i ++) {
            queue.offer(new int[]{arr[i][1], arr[i][2]});
            if (m > arr[i + 1][0]) {
                m -= arr[i + 1][0];
            } else {
                if (queue.isEmpty()) {
                    System.out.println(-1);
                    return;
                } else {
                    while (m < arr[i + 1][0] && !queue.isEmpty()) {
                        int[] temp = queue.poll();
                        if (m + temp[1] >= arr[i + 1][0]) {
                            money += temp[0] * (arr[i + 1][0] - m);
                            if (arr[i + 1][0] - m != temp[1]) {
                                queue.offer(new int[]{temp[0], temp[1] - (arr[i + 1][0] - m)});
                                m = arr[i + 1][0];
                                break;
                            }
                        } else {
                            m += temp[1];
                            money += temp[0] * temp[1];
                        }
                    }
                    if (m != arr[i + 1][0]) {
                        System.out.println(-1);
                        return;
                    }
                    m = 0;
                }
            }
        }

        System.out.println(money);
    }

}
