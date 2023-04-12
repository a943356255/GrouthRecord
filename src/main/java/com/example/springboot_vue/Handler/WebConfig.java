package com.example.springboot_vue.Handler;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginHandler()).addPathPatterns("/**");
    }

    /**
     * /**表示本应用的所有方法都会去处理跨域请求，
     * allowedMethods表示允许通过的请求数，
     * allowedHeaders则表示允许的请求头。
     * 经过这样的配置之后，就不必在每个方法上单独配置跨域了。
     */

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8081")
                .allowedMethods("*")
                .allowedHeaders("*");
    }
}
