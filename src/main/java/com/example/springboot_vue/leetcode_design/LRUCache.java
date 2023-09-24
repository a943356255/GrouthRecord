package com.example.springboot_vue.leetcode_design;

import java.util.HashMap;
import java.util.Map;

// 146. LRU 缓存
public class LRUCache {
    static class DLinkedNode {
        int key;
        int value;
        DLinkedNode prev;
        DLinkedNode next;
        public DLinkedNode() {}
        public DLinkedNode(int _key, int _value) {
            key = _key; value = _value;
        }
    }

    private final Map<Integer, DLinkedNode> cache = new HashMap<>();
    private int size;
    private final int capacity;
    private final DLinkedNode head, tail;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        head = new DLinkedNode();
        tail = new DLinkedNode();
        head.next = tail;
        tail.prev = head;
    }

    public int get(int key) {
        if (cache.get(key) == null) {
            return -1;
        }

        // 移动到尾节点
        moveToTail(key);
        return cache.get(key).value;
    }

    public void put(int key, int value) {
        // 判断之前是否存在
        DLinkedNode node = cache.get(key);
        if (node == null) {
            node = new DLinkedNode(key, value);
            size++;
            // 尾插法
            addToTail(node);
            cache.put(key, node);
            if (size > capacity) {
                removeFromHead();
                size--;
            }
        } else {
            node.value = value;
            moveToTail(key);
        }
    }

    public void addToTail(DLinkedNode node) {
        node.prev = tail.prev;
        tail.prev.next = node;
        node.next = tail;
        tail.prev = node;
    }

    public void moveToTail(int key) {
        DLinkedNode node = cache.get(key);
        // 从链表中间移除
        delNode(node);

        // 插入尾节点
        addToTail(node);
    }

    public void delNode(DLinkedNode node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    public void removeFromHead() {
        cache.put(head.next.key, null);
        // 删除头节点后的一个节点
        head.next = head.next.next;
        head.next.prev = head;
    }
}
