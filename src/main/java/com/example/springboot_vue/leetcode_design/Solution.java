package com.example.springboot_vue.leetcode_design;

import java.util.Arrays;
import java.util.Random;

public class Solution {

    int[] w;
    int[] arr = new int[100];

    public Solution(int[] w) {
        this.w = w;
        int sum = Arrays.stream(w).sum();

        for (int i = 0; i < w.length; i++) {
            double part = (w[i] / (float) sum);
            int count = (int) (part * 100);
            for (int j = 0; j < count; j++) {
                arr[j] = i;
            }
        }
    }

    public int pickIndex() {
        Random random = new Random();
        return arr[random.nextInt(100)];
    }

}
