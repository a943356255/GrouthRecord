package mybatis;

import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.transaction.support.TransactionTemplate;

import java.io.File;
import java.nio.charset.Charset;

//@Configuration
public class DataSourceConfiguration implements BeanPostProcessor, EnvironmentAware {

    private ConfigurableEnvironment configurableEnvironment;

    @Autowired
    TransactionTemplate transactionTemplate;

    @SneakyThrows
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        // 这里就是在Mybatis的Bean初始化之前，我们将读取给定目录下的文件，然后将其塞到配置文件当中，然后用这个值去创建bean
        if (bean instanceof MybatisAutoConfiguration) {
            // 这里是读取到的额外的配置文件
            String config = FileUtils.readFileToString(new File("path"), Charset.defaultCharset());

        }

        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.configurableEnvironment = (ConfigurableEnvironment) environment;
    }
}
