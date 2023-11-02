package mybatis;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestClassPath {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:motan_server.xml");
        String url = "jdbc:mysql://localhost:3306/db";
        String username = "root";
        String password = "root";
        String driverClassName = "com.mysql.cj.jdbc.Driver";
        // 注册驱动
        Class.forName(driverClassName);
        // 获取连接
        Connection connection = DriverManager.getConnection(url, username, password);
    }

}
