package com.example.springboot_vue.leetcode_design;

import java.util.HashMap;
import java.util.Map;

// 460. LFU 缓存
class Node {
    int key;
    int value;
    // 这个应该是该节点存储于哪一个双向链表的下标
    int freq = 1;
    Node pre;
    Node next;

    public Node() {}

    public Node(int key, int value) {
        this.key = key;
        this.value = value;
    }
}

class DoublyLinkedList {
    Node head;
    Node tail;

    public DoublyLinkedList() {
        head = new Node();
        tail = new Node();
        head.next = tail;
        tail.pre = head;
    }

    void removeNode(Node node) {
        node.pre.next = node.next;
        node.next.pre = node.pre;
    }

    void addNode(Node node) {
        node.next = head.next;
        head.next.pre = node;
        head.next = node;
        node.pre = head;
    }
}

public class LFUCache {

    // 存储具体的缓存内容
    public Map<Integer, Node> cache;
    // 存储每一个频次对应的双向链表
    public Map<Integer, DoublyLinkedList> queue;
    int capacity, size, min;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        cache = new HashMap<>();
        queue = new HashMap<>();
    }

    public int get(int key) {
        Node node = cache.get(key);
        if (node == null) {
            return -1;
        }
        moveNode(node);
        return node.value;
    }

    public void put(int key, int value) {
        if (capacity == 0) {
            return;
        }
        Node node = cache.get(key);
        // 说明之前不存在
        if (node == null) {
            node = new Node(key, value);
            size++;
            // 超过最大限制
            if (size > capacity) {
                // 获取到当前最小使用频次的链表（这里获取到的链表是否会为空呢）
                DoublyLinkedList list = queue.get(min);
                // 删除具体的缓存
                cache.remove(list.tail.pre.key);
                // 删除节点,插入使用的头插法，则删除时删除队尾节点的前一个节点即可
                list.removeNode(list.tail.pre);
                size--;
            }

            DoublyLinkedList tempList = queue.get(node.freq);
            // 将其加入到对应的链表当中
            if (tempList == null) {
                tempList = new DoublyLinkedList();
                queue.put(node.freq, tempList);
            }
            tempList.addNode(node);
            // 新添加节点后，最小使用频次即为1
            min = 1;
            // 添加到具体的缓存
            cache.put(key, node);
        } else {
            // 之前存在，则修改值，然后移动节点
            node.value = value;
            moveNode(node);
        }
    }

    // 将节点从一个链表中移动到另一个链表当中
    public void moveNode(Node node) {
        DoublyLinkedList list = queue.get(node.freq);
        // 先在原来的链表中删除该节点
        list.removeNode(node);
        // 说明此时该节点处于最小使用次数的链表当中，并且链表已经为空了额
        if (node.freq == min && list.head.next == list.tail) {
            min = node.freq + 1;
        }
        // 多使用了一次
        node.freq++;
        // 获取到对应的双向链表
        DoublyLinkedList temp = queue.get(node.freq);
        // 如果为空，则初始化链表
        if (temp == null) {
            temp = new DoublyLinkedList();
            queue.put(node.freq, temp);
        }
        // 添加节点
        temp.addNode(node);
    }
}
