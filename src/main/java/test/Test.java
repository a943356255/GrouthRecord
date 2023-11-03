package test;

import cn.dev33.satoken.stp.StpUtil;

public class Test {

    public static void main(String[] args) {
//        StpUtil.login("testId");
//        StpUtil.createLoginSession("testId");
        int a = 1, b = 2;
        System.out.println(add(a, b));
    }

    public void testExe() {

    }

    private static <T extends Number> double add(T a, T b) {
        System.out.println(a + "+" + b + "=" + (a.doubleValue() + b.doubleValue()));
        return a.doubleValue() + b.doubleValue();
    }
}
