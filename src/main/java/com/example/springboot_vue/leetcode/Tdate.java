package com.example.springboot_vue.leetcode;

public class Tdate {

    private int day;
    private int month;
    private int year;

    public Tdate() {

    }

    public Tdate(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    boolean getDate(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
        return true;
    }

    public Tdate(Tdate old) {
        this.day = old.day;
        this.month = old.month;
        this.year = old.year;
    }

    void print() {
        System.out.println(day + "/" + month + "/" + year);
    }

    Tdate add(int ad) {
        day += ad;
        return this;
    }
}
