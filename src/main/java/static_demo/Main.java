package static_demo;

public class Main {

    public static void main(String[] args) {
        System.out.println(TestStatic.account);
//        // 这里是一个TestStatic的实例，它无法调用属于TestStatic的静态方法
        TestStatic testStatic = new TestStatic();
        testStatic.justPrint();
        new Main().change(testStatic);
        System.out.println(testStatic.test);
    }

    public void test() {
        System.out.println(TestStatic.account);
        TestStatic testStatic = new TestStatic();
    }

    public void change(TestStatic testStatic) {
        testStatic.test = "123";
    }
}
