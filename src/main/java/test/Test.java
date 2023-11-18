package test;

import cn.dev33.satoken.stp.StpUtil;

public class Test {

    public static void main(String[] args) {
//        StpUtil.login("testId");
//        StpUtil.createLoginSession("testId");
        Test test = new Test();
        test.testExe();
    }

    public void testExe() {
        Father father = new Child();
        father.fatherMethod();
    }

    private static <T extends Number> double add(T a, T b) {
        System.out.println(a + "+" + b + "=" + (a.doubleValue() + b.doubleValue()));
        return a.doubleValue() + b.doubleValue();
    }
}

class Father {
    int test;
    int name;
    public void fatherMethod() {
        System.out.println("456");
    }
}

class Child extends Father {

    public void childMethod() {

    }

    public void fatherMethod() {
        System.out.println("123");
    }
}