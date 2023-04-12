package com.example.springboot_vue.reflection;

public class TestReflection {

    private int age;

    private String name;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void printf(String str) {
        System.out.println(str);
    }

}
