package com.example.springboot_vue.leetcode_design;

public class StreamChecker {

    int[][] tree = new int[100010][26];
    int[] cnt = new int[100010];
    int idx = 0;

    StringBuilder stringBuilder = new StringBuilder();

    void insert(String str) {
        // 树的高度
        int high = 0;
        // 遍历每一个字符，加入树中
        for (int i = 0; i < str.length(); i++) {
            // 判断u在高度为high的这一层中，是第几个字符，去对应的下标
            int u = str.charAt(i) - 'a';
            // 如果下标已经存过，说明该位置之前有字符，则不管，如果没有，则存当前idx
            // 对于每一个单词，每一个N只会存储该单词的一个字母，然后就去到下一层。通过tree[][]的值来判断他属于哪一个单词
            if (tree[high][u] == 0) {
                tree[high][u] = ++idx;
            }
            // 因为idx的值是在不断增大，意味着tree的值也在增大，保证了high会一直增大，而且每次增加一，相当于树往下走一层
            high = tree[high][u];
        }

        // 当一个字符遍历完成后，在该层的cnt下标处加一，说明有一个单词在这里结尾
        cnt[high]++;
    }

    boolean query(StringBuilder str) {
        int high = 0;
        for (int i = 0; i < str.length(); i++) {
            int u = str.charAt(i) - 'a';
            // 中间有一个单词没有出现过，说明肯定不存在, 直接返回0
            if (tree[high][u] == 0) {
                return false;
            }

            // 向下走一层
            high = tree[high][u];
        }

        // cnt[high]的值代表了以当前字符结尾的单词有几个
        return true;
    }

    public StreamChecker(String[] words) {
        for (String word : words) {
            insert(word);
        }
    }

    public boolean query(char letter) {
        stringBuilder.append(letter);
        return query(stringBuilder);
    }

}
