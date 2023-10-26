package mybatis;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class TestClassPath {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:motan_server.xml");
        Class.forName("com.mysql.jdbc.Driver");
        String url = "";
        Connection connection = DriverManager.getConnection(url);
        Statement statement = connection.createStatement();
    }

}
