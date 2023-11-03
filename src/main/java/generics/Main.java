package generics;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        // 泛型类的测试代码
        SingleGenerics<Integer> testClass = new SingleGenerics<>();
        testClass.setName(1);

        SingleGenerics<String> secondClass = new SingleGenerics<>();
        secondClass.setName("testName");

        DoubleGenerics<String, String> firstTest = new DoubleGenerics<>();
        firstTest.setKey("");
        firstTest.setVal("");

        DoubleGenerics<Integer, Integer> secondTest = new DoubleGenerics<>();
        secondTest.setVal(1);
        secondTest.setKey(2);

        Object result =  secondTest.getObject(Class.forName("com.example.springboot_vue.pojo.Demo"));

        List<B> list = new ArrayList<>();
        funD(list);
    }

    // 如下两个方法不会报错
    public static void funA(A a) {
        // ...
    }
    public static void funB(B b) {
        funA(b);
        // ...
    }

    public static void funC(List<? extends A> listA) {
        // ...
    }

    public static void funD(List<B> listB) {
        funC(listB); // OK
        // ...
    }

}

class A {

}

class B extends A {

}

