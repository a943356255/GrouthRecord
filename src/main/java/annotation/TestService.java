package annotation;

public class TestService {

    public void testMethods(@MyDefaultValue("默认传递的值") String str) {
        System.out.println("nothing" + " 参数 = " + str);
    }

}
