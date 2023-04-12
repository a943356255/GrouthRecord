package com.example.springboot_vue.lanqiao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Five {

    static List<Integer> path;
    static long count = 0;
    static int M = 998244353;

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String[] s = bufferedReader.readLine().split(" ");
        int n = Integer.parseInt(s[0]);
        int m = Integer.parseInt(s[1]);
        path = new ArrayList<>();
        backTracking(n,0,m);
        System.out.println(count);
    }

    public static void backTracking(int n, int sum, int target) {
        if (path.size() == n) {
            if (isValid(path)) {
                count = (count + 1) % M;
            }
            return;
        }
        for (int i = 0; i < n; i++) {
            if (path.size() == 0 && i == 0) {
                continue;
            }
            if (sum + i > target) break;
            path.add(i);
            sum += i;
            backTracking(n,sum,target);
            sum -= i;
            path.remove(path.size() - 1);
        }
    }

    public static boolean isValid(List<Integer> path) {
        for (int i = 0; i < path.size(); i++) {
            // 奇数位
            if ((i + 1) % 2 != 0) {
                if (path.get(i) % 2 == 0) {
                    return false;
                }
            } else {
                if (path.get(i) % 2 != 0) {
                    return false;
                }
            }
        }
        return true;
    }
}