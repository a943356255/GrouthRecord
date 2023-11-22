package test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 该参数表示该注解用于方法参数
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface DefaultValue {

    String value();
}
