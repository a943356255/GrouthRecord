package com.example.springboot_vue.pojo.city;

public class UCity {

    private String name;
    private String number;
    private String parentName;
    private String test;
    private String test2;
    private String markId;

    public UCity() {

    }

    public UCity(String name, String number, String parentName, String test, String test2, String markId) {
        this.name = name;
        this.number = number;
        this.parentName = parentName;
        this.test = test;
        this.test2 = test2;
        this.markId = markId;
    }

    public UCity(String name, String number, String parentName, String test, String test2) {
        this.name = name;
        this.number = number;
        this.parentName = parentName;
        this.test = test;
        this.test2 = test2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public String getTest2() {
        return test2;
    }

    public void setTest2(String test2) {
        this.test2 = test2;
    }

    public String getMarkId() {
        return markId;
    }

    public void setMarkId(String markId) {
        this.markId = markId;
    }
}
