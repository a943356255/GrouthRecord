package reflection;

import java.lang.reflect.Field;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException {
        Main main = new Main();
        main.getFiled();
    }

    public void getClassOne() {
        Class<User> userClass = User.class;
    }

    public void getClassTwo() {
        User user = new User();
        // 该class对象是在运行时从User实例获取的，而User实例是在运行时确定和创建，所以在编译阶段无法知道，只能用?
        Class<?> clazz = user.getClass();
    }

    public void getClassThree() throws ClassNotFoundException {
        // 该方法会立即触发类的初始化
        Class<?> clazz = Class.forName("reflection.User");
    }

    public void getFiled() throws ClassNotFoundException {
        Class<?> clazz = Class.forName("reflection.User");
        Field[] fields = clazz.getDeclaredFields();
//        for (Field field : fields) {
//            System.out.println(field.getName());
//        }
        // 该方法只返回public类型
        Field[] fields1 = clazz.getFields();
        for (Field field : fields1) {
            System.out.println(field.getName());
        }
    }
}
