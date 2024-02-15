package static_demo;

public class TestStatic {

    static String account = "9433562553";
    static String password = "123";
    private String name;
    private String email;
    public String test;

    public TestStatic() {

    }

    public void justPrint() {
        System.out.println("这是非静态方法");
    }

    public static void staticPrintf() {
        System.out.println("这是静态方法");
    }

    public TestStatic(String name, String email, String test) {
        this.name = name;
        this.email = email;
        this.test = test;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
