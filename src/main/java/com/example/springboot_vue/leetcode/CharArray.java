package com.example.springboot_vue.leetcode;

public class CharArray {

    public static void main(String[] args) throws InterruptedException {
        while (true) {

            new Thread(() -> {
                int index = 10;
                while (index-- > 0) {
                    System.out.println("第二个线程");
                }

            }).start();

            new Thread(() -> {
                int a = 100;
                int b = 10;
                int index = 10;
                while (index > 0) {
                    a /= (b - index);
                    index--;
                }
            }).start();

            Thread.sleep(1000);
            System.out.println("我是主线程");
        }
    }

    char[] element = new char[50];

    public CharArray() {

    }

    public CharArray(char[] element) {
        this.element = element;
    }

    public CharArray(CharArray old) {
        this.element = old.element;
    }
}
