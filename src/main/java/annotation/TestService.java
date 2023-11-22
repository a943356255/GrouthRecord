package annotation;

public class TestService {

    public void testMethods(@MyDefaultValue("默认传递的值") String str, String second) {
        System.out.println("参数1 = " + str + " 参数2 = " + second);
    }

}
