package big_file_upload.test;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;

public class TestRandomAccessFile {

    private static final String path = "D:\\bilibili_video\\test.txt";

    public static void main(String[] args) throws IOException {
        // 这里是打开对应的文件
        Employee e1 = new Employee("zhangsan", 23);
        Employee e2 = new Employee("lisi", 24);
        Employee e3 = new Employee("wangwu", 25);

        RandomAccessFile ra = new RandomAccessFile(path, "rw");
        // 防止写入文件乱码
        ra.write(e1.name.getBytes(StandardCharsets.UTF_8));
        ra.writeInt(e1.age);
        ra.write(e2.name.getBytes(StandardCharsets.UTF_8));
        ra.writeInt(e2.age);
        ra.write(e3.name.getBytes(StandardCharsets.UTF_8));
        ra.writeInt(e3.age);
        ra.close();

        RandomAccessFile raf = new RandomAccessFile(path, "r");
        raf.skipBytes(12);
        int length = 8;
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < length; i++) {
            str.append(raf.readByte());
        }
        System.out.println("name = " + str);
        System.out.println("age = " + raf.readInt());
        raf.close();
    }

}

class Employee {
    String name;
    int age;
    final static int LEN = 8;

    public Employee(String name, int age) {
        if (name.length() > LEN) {
            name = name.substring(0, 8);
        } else {
            StringBuilder nameBuilder = new StringBuilder(name);
            while (nameBuilder.length() < LEN) {
                nameBuilder.append("\u0000");
            }
            name = nameBuilder.toString();
            this.name = name;
            this.age = age;
        }
    }
}