package annotation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodsInvoke {

//    public void invokeMethods(Class<?> clazz, String methods, Object[] args) throws Exception {
////        Class<?> c = Class.forName(methods);
//        Method method = clazz.getMethod(methods);
//        // 判断对应方法是否有注解
//        if (!method.isAnnotationPresent(DefaultValue.class)) {
//            System.out.println("方法没有添加注解");
//        }
//
//        method.invoke(clazz, args);
//    }

    public void invokeMethods(Object o, String methodName, Object[] args) throws Exception {
        Class<?> clazz = o.getClass();

        Class<?>[] argsClass;
        if (args != null) {
            argsClass = new Class[args.length];
            for (int i = 0; i < args.length; i++) {
                argsClass[i] = args[i].getClass();
            }
        } else {
            argsClass = new Class[0];
        }

        Method method = clazz.getMethod(methodName, argsClass);

        // 判断对应方法是否有注解
        if (!method.isAnnotationPresent(DefaultValue.class)) {
            System.out.println("方法没有添加注解");
        }

        method.invoke(o, args);
    }

}
