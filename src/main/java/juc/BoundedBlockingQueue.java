package juc;

public class BoundedBlockingQueue implements Queue {

    // 当前大小
    protected int size;

    // 容量
    protected final int capacity;

    // 头指针，empty: head.next == tail == null
    protected Node head;

    // 尾指针
    protected Node tail;

    public BoundedBlockingQueue(int capacity) {
        this.capacity = capacity;
        this.head = new Node(null);
        this.tail = head;
        this.size = 0;
    }

    // 如果队列已满，通过返回值标识
    public synchronized boolean offer(Object obj) {
        if (size < capacity) {
            Node node = new Node(obj);
            tail.next = node;
            tail = node;
            ++size;
            return true;
        }
        return false;
    }

    // 如果队列为空，head.next == null；返回空元素
    public synchronized Object poll() {
        if (head.next != null) {
            Object result = head.next.value;
            head.next.value = null;
            head = head.next; // 丢弃头结点
            --size;
            return result;
        }
        return null;
    }

    // 省略 Node 的定义
    class Node {
        Object value;
        BoundedBlockingQueue.Node next;
        Node(Object obj) {
            this.value = obj;
            next = null;
        }
    }
}
