package com.example.springboot_vue.leetcode;

public class LeetCodeMain10 {

    public static void main(String[] args) {

    }

    // 394. 字符串解码
    public String decodeString(String s) {
        return null;
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
