package generics;

import java.lang.reflect.InvocationTargetException;

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

    }

}
