package annotation;

public class Main {

    public static void main(String[] args) throws Exception {
//        MethodsInvoke methodsInvoke = new MethodsInvoke();
//        Class<?>[] clazz = {String.class, String.class};
//        String[] parameters = {null, "我自己传递的参数"};
//        methodsInvoke.invokeMethods(new TestService(), "testMethods", clazz, parameters);
        new TestService().testMethods("first", "second");
    }

}
