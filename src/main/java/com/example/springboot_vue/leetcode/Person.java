package com.example.springboot_vue.leetcode;

import java.util.Arrays;

public class Person {

    Tdate t;
    CharArray charArray;

    public Person(Tdate t, CharArray charArray) {
        this.t = t;
        this.charArray = charArray;
    }

    public Person(Person p) {
        this.t = p.t;
        this.charArray = p.charArray;
    }

    // Person(char*pName)的实现
    public Person(char[] arr) {
        charArray = new CharArray(arr);
    }

    public Person() {

    }
}
