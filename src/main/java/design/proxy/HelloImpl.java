package design.proxy;

public class HelloImpl implements IHello {

    @Override
    public int sayHello() {
        System.out.println("Hello world!");
        return 1;
    }

    @Override
    public int testFuncTwo() {
        System.out.println("测试第二个方法");
        return 333;
    }


}
