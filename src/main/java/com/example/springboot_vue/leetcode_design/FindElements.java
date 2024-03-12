package com.example.springboot_vue.leetcode_design;

import com.example.springboot_vue.leetcode.TreeNode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

// 1261. 在受污染的二叉树中查找元素
public class FindElements {

    Set<Integer> set = new HashSet<>();
    public FindElements(TreeNode root) {
        if (root != null) {
            root.val = 0;
        }
        dfs(root);
    }

    public void dfs(TreeNode root) {
        if (root == null) {
            return;
        }
        int x = root.val;
        if (root.left != null) {
            root.left.val = x * 2 + 1;
            set.add(x * 2 + 1);
        }
        if (root.right != null) {
            root.right.val = 2 * x + 2;
            set.add(2 * x + 2);
        }
        System.out.println(root.val);
        dfs(root.left);
        dfs(root.right);
    }

    public boolean find(int target) {
        return set.contains(target);
    }

}
