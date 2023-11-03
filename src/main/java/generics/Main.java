package generics;

public class Main {

    public static void main(String[] args) {
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
    }

}
