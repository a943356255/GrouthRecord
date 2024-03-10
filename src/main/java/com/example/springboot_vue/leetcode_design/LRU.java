package com.example.springboot_vue.leetcode_design;

import java.util.*;

// LCR 031. LRU 缓存
public class LRU {

    static class Node {
        Node prev;
        Node next;
        int val;
        int key;
    }

    int size = 0;
    int capacity;
    Map<Integer, Node> map = new HashMap<>();

    Node head = new Node();
    Node tail = new Node();

    public LRU(int capacity) {
        this.capacity = capacity;
        head.next = tail;
        head.val = -1;
        tail.prev = head;
        map.put(-1, head);
    }

    public int get(int key) {
        if (map.get(key) != null) {
            int val;
            Node temp = map.get(key);
            val = temp.val;
            // 将前后连接
            temp.prev.next = temp.next;
            temp.next.prev = temp.prev;

            // 移到队头
            temp.next = head.next;
            head.next.prev = temp;
            head.next = temp;
            temp.prev = head;
            return val;
        }
        return -1;
    }

    public void put(int key, int value) {
        Node node;
        int mark = -1;
        if (map.get(key) != null) {
            node = map.get(key);
            mark = 1;
        } else {
            node = new Node();
            size++;
        }
        node.val = value;
        node.key = key;
        map.put(key, node);
        if (mark == 1) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        // 插入队头
        node.next = head.next;
        head.next.prev = node;
        head.next = node;
        node.prev = head;
        if (size > capacity) {
            Node temp = tail.prev;
            tail.prev = temp.prev;
            tail.prev.next = tail;
            map.remove(temp.key);
            size--;
        }
    }

}

