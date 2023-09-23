package com.example.springboot_vue.leetcode_design;

import java.util.*;

// 1993. 树上的操作
public class LockingTree {

    private final int[] parent;
    private int[][] lockArr;
    Map<Integer, List<Integer>> map;

    public LockingTree(int[] parent) {
        this.parent = parent;

        // 初始全为0，表示都没上锁，[0,0]第一位表示是否上锁，第二位表示上锁的用户
        lockArr = new int[parent.length][2];
        for (int i = 0; i < parent.length; i++) {
            Arrays.fill(lockArr[i], 0);
        }

        // 记录每个父节点有哪些子节点
        map = new HashMap<>();
        for (int i = 0; i < parent.length; i++) {
            if (map.get(parent[i]) == null) {
                List<Integer> list = new ArrayList<>();
                list.add(i);
                map.put(parent[i], list);
            } else {
                map.get(parent[i]).add(i);
            }
        }
    }

    public boolean lock(int num, int user) {
        if (lockArr[num][0] == 0) {
            lockArr[num][0] = 1;
            lockArr[num][1] = user;
            return true;
        }

        return false;
    }

    public boolean unlock(int num, int user) {
        if (lockArr[num][0] == 1 && lockArr[num][1] == user) {
            lockArr[num][0] = 0;
            return true;
        }

        return false;
    }

    public boolean upgrade(int num, int user) {
        // 未上锁,同时祖先未上锁
        if (lockArr[num][0] == 0 && dfs(parent, num)) {
            // 获取所有子节点
            List<Integer> list = map.get(num);
            int mark = 0;
            // 遍历节点的所有子节点，并且解锁
            Queue<List<Integer>> queue = new LinkedList<>();
            queue.add(list);
            while (!queue.isEmpty()) {
                List<Integer> temp = queue.poll();
                if (temp != null) {
                    for (int i = 0; i < temp.size(); i++) {
                        if (lockArr[temp.get(i)][0] == 1) {
                            // 解锁
                            lockArr[temp.get(i)][0] = 0;
                            mark = 1;
                        }
                        queue.add(map.get(temp.get(i)));
                    }
                }
            }

            if (mark == 1) {
                lockArr[num][0] = 1;
                lockArr[num][1] = user;
                return true;
            }
        }

        return false;
    }

    public boolean dfs(int[] parent, int num) {
        while (parent[num] != -1) {
            if (lockArr[parent[num]][0] == 1) {
                return false;
            }

            num = parent[num];
        }

        return true;
    }
}
