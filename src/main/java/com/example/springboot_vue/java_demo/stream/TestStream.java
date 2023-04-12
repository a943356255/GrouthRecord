package com.example.springboot_vue.java_demo.stream;

import com.example.springboot_vue.pojo.user.User;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class TestStream {
    public static void main(String[] args) throws IOException {
        int[] arr = {9, 8, 7, 6, 5, 4, 3};
        TestStream testStream = new TestStream();
        testStream.testStreamFilter();
    }

    public void testIntStream() throws IOException {
        // 包含左，不包含右
        IntStream intStream = IntStream.range(0, 10);
        // 转成array
        int[] arr = intStream.toArray();
        // 转换成array
        Integer[] test = intStream.boxed().toArray(Integer[]::new);

        List<User> list = new ArrayList<>();
        list.add(new User());
        int sum = list.stream()
                // 过滤掉id = 1的元素
                .filter(value -> value.getId() == 1)
                .mapToInt(User::getId)
                .sum();

        Stream<String> stringStream = Files.lines(Paths.get("C:\\Users\\郭俊豪\\Desktop\\redis.txt"));
        // 可以直接打印文件内容
        stringStream.forEach(System.out::println);
    }

    public void testStreamAnyMatch() {
        List<String> list = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            list.add(String.valueOf(i));
        }
        list.add("test");
        System.out.println(list.stream().anyMatch(s -> s.equals("test")));
    }

    // 通过使用filter方法进行条件筛选，filter的方法参数为一个条件（过滤保留函数返回值为 true 的元素）
    public void testStreamFilter() {
        List<Integer> integerList = Arrays.asList(1, 2, 3, 4, 5, 6);
        // 过滤掉所有i > 3的
        Stream<Integer> stream = integerList.stream().filter(i -> i > 3);
        stream.forEach(System.out::println);
    }

    public void testArraysSort(int[] arr) {
        Integer[] idx = IntStream.range(0, arr.length).boxed().toArray(Integer[]::new);
        Arrays.sort(idx, Comparator.comparingInt(i -> arr[i]));

        for (Integer integer : idx) {
            System.out.println(integer);
        }
    }
}
