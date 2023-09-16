package com.example.springboot_vue.leetcode_design;

/*
他构造的树，第一层是一个26个节点的数组，而每一个数组是一个Trie节点，他们下面又有一个26个字母的数组
 */
// 208. 实现 Trie (前缀树)
public class Trie {

    private Trie[] children;
    private boolean isEnd;

    public Trie() {
        children = new Trie[26];
        isEnd = false;
    }

    public void insert(String word) {
        // 这里是创建了一个引用，因为后续要改变node的指向，用于添加节点
        // 这里，每次调用insert的时候，都创建了一个引用,但是该引用和之前的引用都指向了同一个对象
        // 也就是调用构造函数时创建的对象
        Trie node = this;
        // 字典树是记录每一个单词的字母，这里需要遍历word
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            // 算出该字母的下标
            int index = c - 'a';
            // 此时一定有一个字母c被添加到字典树中，所以说如果原来的地方不存在，就新创建一个节点
            if (node.children[index] == null) {
                node.children[index] = new Trie();
            }
            // 将当前节点指向子节点
            node = node.children[index];
        }

        node.isEnd = true;
    }

    public boolean search(String word) {
        Trie node = searchPrefix(word);
        return node != null && node.isEnd;
    }

    public boolean startsWith(String prefix) {
        return searchPrefix(prefix) != null;
    }

    public Trie searchPrefix(String prefix) {
        Trie node = this;
        for (int i = 0; i < prefix.length(); i++) {
            char c = prefix.charAt(i);
            int index = c - 'a';
            // 如果遍历该单词的过程中，有一个字符不存在，那就返回null
            if (node.children[index] == null) {
                return null;
            }
            node = node.children[index];
        }

        return node;
    }
}
