package test;


import annotation.MyDefaultValue;
import cn.dev33.satoken.stp.StpUtil;

public class Test {

    public static void main(String[] args) {
//        StpUtil.login("testId");
//        StpUtil.createLoginSession("testId");
        Test test = new Test();
//        test.testExe();
    }

    public void testExe(@MyDefaultValue(value = "册数数据") String str) {
        System.out.println(str);
//        Father father = new Child();
//        father.fatherMethod();
    }

    private static <T extends Number> double add(T a, T b) {
        System.out.println(a + "+" + b + "=" + (a.doubleValue() + b.doubleValue()));
        return a.doubleValue() + b.doubleValue();
    }
}

class Father {
    int test;
    int name;

    public Father(int test, int name) {
        this.test = test;
        this.name = name;
    }

    public Father() {

    }

    public void fatherMethod() {
        System.out.println("456");
    }
}

class Child extends Father {

    public Child() {

    }

    // 由于父类只有有参的构造器，所以子类必须有构造器，且必须显示的调用父类的构造器
    public Child(int test, int name) {
        // 当父类添加了无参的构造器后，这句话可以注释调，但是它会隐式的调用父类的无参构造
        super(test, name);
    }

    public void childMethod() {

    }

    public void fatherMethod() {
        System.out.println("123");
    }
}