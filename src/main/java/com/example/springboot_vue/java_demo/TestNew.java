package com.example.springboot_vue.java_demo;

import com.example.springboot_vue.pojo.city.City;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class TestNew {

    public static void main(String[] args) {
        new TestNew().testStream();
    }

    public void test() {
        // 自己定义一个函数式接口
        int value = trueAdd(2, 3, (x, y) -> x * y);
        System.out.println(value);

        // 利用内置的consumer（消费型接口）
        String str = "123";
        testConsumer(str, name -> {
            name += "这里是另外对name字符串的处理";
            System.out.println(name);
        });
    }

    public int trueAdd(int x, int y, TestInterface testInterface) {
        return testInterface.add(x, y);
    }

    private void testConsumer(String str, Consumer<String> consumer) {
        consumer.accept(str);
    }

    public void testObject() {
        City city = new City();
        city.setName("中国");
        city.setNumber("14亿");

        Supplier<String> supplier = city::getName;
        String str = supplier.get();
        System.out.println(str);
    }

    public void testStream() {
        ArrayList<String> list = new ArrayList<>();
        list.add("123");
        list.add("123");
        list.add("123");
        list.add("456");

       list.stream()
               .distinct()
               .forEach(System.out::println);

    }
}
