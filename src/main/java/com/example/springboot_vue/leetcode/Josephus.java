package com.example.springboot_vue.leetcode;

class Children {
    int number;
    Children next;
    public Children(int number) {
        this.number = number;
    }
}

public class Josephus {
    public static int lastRemaining(int m) {
        Children first = new Children(0);
        Children current = first;
        for (int i = 1; i < 5; i++) {
            current.next = new Children(i);
            current = current.next;
        }
        current.next = first;
        Children previous = current;
        current = first;
        while (current.next != current) {
            for (int i = 1; i < m; i++) {
                previous = current;
                current = current.next;
            }
            previous.next = current.next;
            current = current.next;
        }
        return current.number;
    }

    public static void main(String[] args) {
        int m = 3;
        System.out.println("The last remaining child is at position: " + lastRemaining(m));
    }
}
