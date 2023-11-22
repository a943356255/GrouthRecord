package annotation;

public class Main {

    public static void main(String[] args) throws Exception {
        MethodsInvoke methodsInvoke = new MethodsInvoke();
        Class<?>[] clazz = {String.class};
        String[] parameters = new String[1];
        methodsInvoke.invokeMethods(new TestService(), "testMethods", null);
    }

}
