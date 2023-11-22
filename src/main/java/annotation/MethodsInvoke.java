package annotation;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class MethodsInvoke {

    public void invokeMethods(Object o, String methodName, Class<?>[] args) throws Exception {
        Class<?> clazz = o.getClass();
        Method method = clazz.getMethod(methodName, args);
        Parameter[] parameters = method.getParameters();
        int count = 0;
        for (Parameter parameter : parameters) {
            if (parameter.isAnnotationPresent(MyDefaultValue.class)) {
                count++;
            }
        }

        Object[] res = new String[count];
        int index = 0;
        for (Parameter parameter : parameters) {
            if (parameter.isAnnotationPresent(MyDefaultValue.class)) {
                res[index++] = parameter.getAnnotation(MyDefaultValue.class).value();
            }
        }

        method.invoke(o, res);
    }
}