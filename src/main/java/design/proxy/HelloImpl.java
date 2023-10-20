package design.proxy;

public class HelloImpl implements IHello {

    @Override
    public int sayHello() {
        System.out.println("Hello world!");
        return 1;
    }
}
