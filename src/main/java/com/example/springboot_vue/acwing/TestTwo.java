package com.example.springboot_vue.acwing;

import java.util.*;

public class TestTwo {
    static int ans;
    static int[] diff;
    static List<Integer>[] adj;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        String colors = scanner.next();
        int[] parent = new int[n];
        diff = new int[n];
        adj = new List[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<>();
        }
        for (int i = 1; i < n; i++) {
            parent[i] = scanner.nextInt() - 1;
            adj[parent[i]].add(i);
        }
        for (int i = 0; i < n; i++) {
            if (colors.charAt(i) == 'R') {
                diff[i] = 1;
            } else {
                diff[i] = -1;
            }
        }
        dfs(0);
        System.out.println(ans);
    }

    private static void dfs(int u) {
        int oldDiff = diff[u];
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, -1);
        if (map.containsKey(diff[u])) {
            int v = map.get(diff[u]);
            if (diff[u] == 0 || (diff[u] - diff[v] == (diff[0] - diff[u]) && (diff[0] - diff[u]) % 2 == 0)) {
                ans++;
            }
        }
        if (!map.containsKey(diff[u])) {
            map.put(diff[u], u);
        }
        for (int v : adj[u]) {
            diff[v] += oldDiff;
            dfs(v);
        }
        diff[u] = oldDiff;
    }


}


