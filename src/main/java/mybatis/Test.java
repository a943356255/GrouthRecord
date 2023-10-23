package mybatis;

import com.example.springboot_vue.mapper.crud.CRUDMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class Test {

    public static void main(String[] args) throws IOException {
        // 读取mybatis-config.xml
        InputStream inputStream = Resources.getResourceAsStream("");
        // 解析配置文件，并创建工厂类
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        // 创建SqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();
        // 创建对应的mapper
        CRUDMapper crudMapper = sqlSession.getMapper(CRUDMapper.class);
        // 执行
        crudMapper.insert(null, null);
    }

}
