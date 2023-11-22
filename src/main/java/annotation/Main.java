package annotation;

public class Main {

    public static void main(String[] args) throws Exception {
        MethodsInvoke methodsInvoke = new MethodsInvoke();
        String[] arr = new String[]{"test"};
        methodsInvoke.invokeMethods(new TestService(), "testMethods", arr);
    }

}
