package com.example.springboot_vue.leetcode;

import java.util.Arrays;

public class LeetCodeMain10 {

    public static void main(String[] args) {

    }

    // 394. 字符串解码
    public String decodeString(String s) {
        return null;
    }

    // 2136. 全部开花的最早一天
    public int earliestFullBloom(int[] plantTime, int[] growTime) {
        int[][] arr = new int[plantTime.length][2];
        for (int i = 0; i < plantTime.length; i++) {
            arr[i][0] = plantTime[i];
            arr[i][1] = growTime[i];
        }
        Arrays.sort(arr, (a, b) -> (b[1] - a[1]));

        int[] res = new int[plantTime.length];
        int max = -1, plant = 0;
        for (int i = 0; i < arr.length; i++) {
            res[i] = plant + arr[i][0] + arr[i][1];
            plant = plant + arr[i][0];
            max = Math.max(res[i], max);
        }

        return max;
    }

    // 605. 种花问题
    public boolean canPlaceFlowers(int[] flowerbed, int n) {

        if (flowerbed.length == 1) {
            if (flowerbed[0] == 0) {
                return true;
            } else {
                return flowerbed[0] != n;
            }
        }

        int count = 0;
        if (flowerbed.length > 1) {
            if (flowerbed[0] == 0 && flowerbed[1] == 0) {
                count = 1;
                flowerbed[0] = 1;
            }
        }
        for (int i = 1; i < flowerbed.length; i++) {
            if (flowerbed[i] == 1) {
                continue;
            }
            if (i != flowerbed.length - 1) {
                if (flowerbed[i + 1] == 0 && flowerbed[i - 1] == 0) {
                    flowerbed[i] = 1;
                    count++;
                }
            } else {
                if (flowerbed[i - 1] == 0) {
                    flowerbed[i] = 1;
                    count++;
                }
            }
        }

        return count >= n;
    }

}
