package com.example.springboot_vue.lanqiao;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BFS_Forwarding {

    static int[] redCount, blueCount;
    static List<Integer>[] edges;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        String colors = scanner.next();
        edges = new List[n + 1];
        for (int i = 1; i <= n; i++) {
            edges[i] = new ArrayList<>();
        }
        for (int i = 1; i < n; i++) {
            int u = scanner.nextInt();
            int v = scanner.nextInt();
            edges[u].add(v);
            edges[v].add(u);
        }
        redCount = new int[n + 1];
        blueCount = new int[n + 1];
        dfs(1, -1, colors);
        System.out.println(getResult(n));
    }

    static void dfs(int cur, int pre, String colors) {
        if (colors.charAt(cur - 1) == 'R') {
            redCount[cur]++;
        } else {
            blueCount[cur]++;
        }
        for (int next : edges[cur]) {
            if (next == pre) {
                continue;
            }
            dfs(next, cur, colors);
            redCount[cur] += redCount[next];
            blueCount[cur] += blueCount[next];
        }
    }

    static int getResult(int n) {
        int res = 0;
        for (int i = 1; i <= n; i++) {
            for (int j : edges[i]) {
                if (j < i) {
                    continue;
                }
                int redDiff = Math.abs(redCount[j] - redCount[i]);
                int blueDiff = Math.abs(blueCount[j] - blueCount[i]);
                res += Math.max(redDiff - blueDiff, blueDiff - redDiff);
            }
        }
        return res;
    }

}