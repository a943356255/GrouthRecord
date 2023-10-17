package com.example.springboot_vue.leetcode_design;

import com.example.springboot_vue.leetcode.TreeNode;

// 173. 二叉搜索树迭代器
public class BSTIterator {

    TreeNode root;
    int index = 0, count;
    int[] arr = new int[100001];

    // 2652. 倍数求和
    public int sumOfMultiples(int n) {
        int res = 0;
        for (int i = 1; i <= n; i++) {
            if (i % 3 == 0 || i % 5 == 0 || i % 7 == 0) {
                res += i;
            }
        }
        return res;
    }

    public BSTIterator(TreeNode root) {
        this.root = root;
        dfs(root);
        count = index;
        index = 0;
    }

    public int next() {
        return arr[index++];
    }

    public void dfs(TreeNode node) {
        if (node == null) {
            return;
        }

        dfs(node.left);
        arr[index++] = node.val;
        dfs(node.right);
    }

    public boolean hasNext() {
        return index != count;
    }

}
