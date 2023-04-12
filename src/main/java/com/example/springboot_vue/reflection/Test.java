package com.example.springboot_vue.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class Test {

    public static void main(String[] args) throws Exception {
        // 指定要创建的对象，根据对象名
        Class clz = Class.forName("com.example.springboot_vue.reflection.TestReflection");

        // 通过clz来创建构造器
        Constructor appleConstructor = clz.getConstructor();

        // 获取一个具体的对象
        TestReflection test = (TestReflection) appleConstructor.newInstance();

        test.printf("123");

        // 获取改类的一个方法
        Method printf = clz.getMethod("printf", String.class);

        printf.invoke(test, "这是将要打印的内容");
    }

}


