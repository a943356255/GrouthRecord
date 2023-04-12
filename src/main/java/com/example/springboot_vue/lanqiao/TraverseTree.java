package com.example.springboot_vue.lanqiao;

import java.util.*;

public class TraverseTree {
    public static int n;
    public static int[] postOrderTraversal = new int[n];
    public static int[] inorderTraversal = new int[n];
    public static Map<Integer, Integer> map = new HashMap<>();
    public static Map<Integer, Integer> lChildren = new HashMap<>();
    public static Map<Integer, Integer> rChildren = new HashMap<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        n = scanner.nextInt();
        // 后续遍历
        for (int i = 0; i < n; i++) {
            postOrderTraversal[i] = scanner.nextInt();
        }
        // 中序遍
        for (int i = 0; i < n; i++) {
            inorderTraversal[i] = scanner.nextInt();
            // map记录中序遍历每一个节点以及对应的下标
            map.put(inorderTraversal[i], i);
        }

        int root = build(0, inorderTraversal.length - 1, 0, postOrderTraversal.length - 1);
        bfs(root);
    }

    // il表示中序左端点，ir中序右端点，pl后序左端点，pr后序右端点
    public static int build(int il, int ir, int pl, int pr) {
        // 获取跟节点的值
        int rootVal = postOrderTraversal[postOrderTraversal.length - 1];
        // 获取节点对应的下标
        int index = map.get(rootVal);
        //
        if (il < index) {
            // index - 1是根节点左边一个节点的位置，即确定他左子树的区间il ~ index - 1
            // rootVal是根节点，然后在中序遍历数组中来进行划分，
            // 这个build返回的是rootVal这个节点的左子树的根节点
            // 这里后两个参数代表后续遍历中，左子树区间的左端点和右端点
            lChildren.put(rootVal, build(il, index - 1, pl, pl + index - 1 - il));
        }
        if (ir > index) {
            // 这里返回的是rootVal这个节点右子树根节点
            rChildren.put(rootVal, build(index + 1, ir, pl + index - 1 - il, pr - 1));
        }

        return rootVal;
    }

    public static void bfs(int root) {
        Queue<Integer> queue = new ArrayDeque<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int t = queue.poll();
            System.out.print(t + " ");
            if (lChildren.containsKey(t)) queue.offer(lChildren.get(t));
            if (rChildren.containsKey(t)) queue.offer(rChildren.get(t));
        }
    }
}
