package com.example.springboot_vue.java_demo.stream;

import com.example.springboot_vue.java_demo.stream.pojo.Employee;

import java.util.*;
import java.util.stream.Stream;

import static com.example.springboot_vue.java_demo.stream.GetStreamOperate.getEmployeeDataList;

public class StreamOperate {

    public static void main(String[] args) {
        List<Employee> employees = getEmployeeDataList();
        StreamOperate streamOperate = new StreamOperate();

//        streamOperate.testSplitOperate(employees);
        streamOperate.testLimitOperate(employees);
    }

    public void testSplitOperate(List<Employee> employees) {
        printf(employees);
        Stream<Employee> stream = employees.stream();
        // 只保留薪资大于5000的
        stream.filter(a -> a.getSalary() > 5000).forEach(a -> System.out.println(a.toString()));
    }

    public void testLimitOperate(List<Employee> employees) {
        printf(employees);

        employees.stream().limit(3).forEach(a -> System.out.println(a.toString()));
    }

    public void printf(List<Employee> employees) {
        employees.forEach(a -> System.out.println(a.toString()));
        System.out.println();
    }
}
