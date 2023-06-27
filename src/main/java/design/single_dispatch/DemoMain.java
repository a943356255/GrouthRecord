package design.single_dispatch;

public class DemoMain {

    public static void main(String[] args) {
        SingleDispatchClass demo = new SingleDispatchClass();
        ParentClass p = new ChildClass();
        // 执行哪个对象的方法，由对象的实际类型决定。
        // 这里对象p的实例化是ChildClass，所以调用了child的f方法。
        demo.polymorphismFunction(p);
        // 执行对象的哪个方法，由参数对象的声明类型决定
        // 这里指的是执行SingleDispatchClass的哪一个overloadFunction方法
        // 他取决于参数p声明的类型，即ParentClass
        demo.overloadFunction(p);
    }

}
