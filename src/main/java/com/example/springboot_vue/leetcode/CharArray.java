package com.example.springboot_vue.leetcode;

public class CharArray {

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
