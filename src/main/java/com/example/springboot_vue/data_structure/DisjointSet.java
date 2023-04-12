package com.example.springboot_vue.data_structure;

public class DisjointSet {

    public static void main(String[] args) {
        Trie trie = new Trie(200, 26);
    }

    // 在并查集当中，uf[x] = y,意味着y是x的父节点
    // 如果uf[x] = x，意味着x就是根节点，这是一种设定。
    public int find(int[] uf, int x) {
        if (uf[x] == x) {
            return x;
        }
        return uf[x] = find(uf, uf[x]);
    }

    // 并查集合并的过程就是在集合uf中，先找到x元素的根节点，再找到y元素的根节点
    // 之后，将一个节点 根节点 的 父节点，设置为另一个元素的根节点
    public void merge(int[] uf, int x, int y) {
        x = find(uf, x);
        y = find(uf, y);
        uf[y] = x;
    }
}
