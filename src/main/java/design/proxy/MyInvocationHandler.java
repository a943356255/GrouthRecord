package design.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 这里是一个动态的代理类，只需要传入对应的类，就会执行对应类的对应方法
 * 也就是说我们只用实现这一个代理类就行了，我们所有相同的代理实现都可以用该类
 */
public class MyInvocationHandler implements InvocationHandler {

    // 目标对象
    private Object target;

    public MyInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] args) throws Throwable {
        System.out.println("------插入前置通知代码-------------");
        // 执行相应的目标方法，rs是方法的返回值
        Object rs = method.invoke(target, args);
        System.out.println("------插入后置处理代码-------------");

        return rs;
    }
}
