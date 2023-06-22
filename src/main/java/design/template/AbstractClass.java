package design.template;

public abstract class AbstractClass {

    public final void templateMethod() {
        //
        System.out.println("调用method1");
        method1();

        System.out.println("调用method2");
        method2();
    }

    protected abstract void method1();
    protected abstract void method2();

}
