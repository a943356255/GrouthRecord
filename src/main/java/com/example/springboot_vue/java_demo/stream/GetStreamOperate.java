package com.example.springboot_vue.java_demo.stream;

import com.example.springboot_vue.java_demo.stream.pojo.Employee;

import java.util.*;
import java.util.stream.Stream;

public class GetStreamOperate {

    public static void main(String[] args) {
        List<Employee> employees = getEmployeeDataList();
        Employee[] employeesArr = getEmployeeDataArray();
        GetStreamOperate streamOperate = new GetStreamOperate();

        // 通过list获取两个数据流集合
        streamOperate.getStreamByList(employees);

        // 通过数组获取
        streamOperate.getStreamByArray(employeesArr);
    }

    public void getStreamByStream() {
        Stream<Integer> stream = Stream.iterate(1, t -> t * 2).limit(20);
        stream.forEach(System.out::println);

        Stream.generate(Math::random).limit(10).filter(a -> a > 10).forEach(System.out::println);
    }

    public void getStreamByOf() {
        Stream<Integer> integerStream = Stream.of(1, 2, 3, 4, 5, 6);
    }

    public void getStreamByArray(Employee[] employees) {
        Stream<Employee> stream = Arrays.stream(employees);
        stream.forEach(a -> System.out.println(a.getDepartmentId()));
    }

    public void getStreamByList(List<Employee> employees) {
        // 返回一个顺序流 （按照集合顺序获取）
        Stream<Employee> stream = employees.stream();
        stream.forEach(a -> System.out.println(a.getId()));

        // 返回一个并行流 （类似于线程去获取数据，无序）
        Stream<Employee> parallelStream = employees.parallelStream();
        parallelStream.forEach(a -> System.out.println(a.getId()));
    }

    public static List<Employee> getEmployeeDataList(){
        List<Employee> list = new ArrayList<>();
        list.add(new Employee(1, "张三", 20, 8500D, 1));
        list.add(new Employee(2, "李四", 18, 600D, 1));
        list.add(new Employee(3, "王五", 21, 5500D, 3));
        list.add(new Employee(4, "小白", 30, 8500D, 2));
        return list;
    }

    public static Employee[] getEmployeeDataArray() {
        Employee e1 = new Employee(1, "张三", 20, 8500D, 1);
        Employee e2 = new Employee(2, "李四", 18, 600D, 1);
        Employee e3 = new Employee(3, "王五", 22, 5000D, 1);

        return new Employee[]{e1, e2, e3};
    }
}
