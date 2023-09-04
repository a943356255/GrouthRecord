package com.example.springboot_vue.leetcode_design;

import com.example.springboot_vue.leetcode.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

public class Codec {

    StringBuilder str = new StringBuilder();

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        if (root == null) {
            return null;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                if (node != null) {
                    str.append(node.val);
                    if (node.left != null) {
                        queue.add(node.left);
                    }
                    if (node.right != null) {
                        queue.add(node.right);
                    }
                } else {
                    str.append("null");
                }
                if (queue.size() > 0) {
                    str.append(",");
                }
            }
        }

        System.out.println(str.toString());

        return str.toString();
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        if (data == null) {
            return null;
        }

        String[] spilt = data.split(",");
        if (spilt.length == 0) {
            TreeNode node = new TreeNode();
            node.val = Integer.parseInt(data);
            return node;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        int level = 2, index = 1;
        TreeNode node = new TreeNode();
        node.val = Integer.parseInt(spilt[0]);
        queue.add(node);
        int count = (int) Math.pow(2, level - 1);
        while (!queue.isEmpty()) {
            // 这个循环，一次添加一层
            int mark = 0;
            for (int i = index; (i < index + count) && (i < spilt.length); i += 2) {
                mark = 1;
                TreeNode father = queue.poll();
                TreeNode left = new TreeNode();
                if (spilt[i].equals("null")) {
                    father.left = null;
                } else {
                    left.val = Integer.parseInt(spilt[i]);
                    father.left = left;
                    queue.add(left);
                }

                if (i + 1 < spilt.length) {
                    TreeNode right = new TreeNode();
                    if (spilt[i + 1] != null) {
                        right.val = Integer.parseInt(spilt[i + 1]);
                        father.right = right;
                        queue.add(right);
                    } else {
                        father.right = null;
                    }
                }
            }
            index += count;
            level++;
            if (mark == 0) {
                break;
            }
        }

        return node;
    }

}
