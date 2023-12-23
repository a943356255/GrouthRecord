package annotation;


import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class MethodsInvoke {

    public void invokeMethods(Object o, String methodName, Class<?>[] args, Object[] parameterType) throws Exception {
//        Class<?> clazz = o.getClass();
//        Method method = clazz.getMethod(methodName, args);
//
//        Parameter[] parameters = method.getParameters();
//        Object[] res = new String[parameterType.length];
//        for (int i = 0; i < parameters.length; i++) {
//            if (parameters[i].isAnnotationPresent(MyDefaultValue.class)) {
//                if (parameterType[i] == null) {
//                    res[i] = parameters[i].getAnnotation(MyDefaultValue.class).value();
//                }
//            } else {
//                res[i] = parameterType[i];
//            }
//        }
//
//        method.invoke(o, res);
    }
}