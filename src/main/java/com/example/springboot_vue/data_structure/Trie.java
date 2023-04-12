package com.example.springboot_vue.data_structure;

public class Trie {

    public static void main(String[] args) {
        Trie trie = new Trie(10, 26);
    }

    // N代表这个字典树要存储组合的数量，比如要存储10个单词，那么N就是10
    // M代表字典树每一个单词种类的个数，比如可能是字母组合，那就是26，可能是数字组合，那就是10
    private int N, M;

    // idx仅仅用来左一个标记
    private int idx = 0;

    // 这里是用一个二维数组来模拟一个树
    // 其中N代表的是第N个单词，然后对应的M代表的是单词每一个字符
    private final int[][] tree;

    // cnt是一个标记数组，标记单词的末尾。
    private final int[] cnt;

    public Trie(int n, int m) {
        this.N = n;
        this.M = m;
        tree = new int[N][M];
        cnt = new int[N];
    }

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

    int query(String str) {
        int high = 0;
        for (int i = 0; i < str.length(); i++) {
            int u = str.charAt(i) - 'a';
            // 中间有一个单词没有出现过，说明肯定不存在, 直接返回0
            if (tree[high][u] == 0) {
                return 0;
            }

            // 向下走一层
            high = tree[high][u];
        }

        // cnt[high]的值代表了以当前字符结尾的单词有几个
        return cnt[high];
    }
}
