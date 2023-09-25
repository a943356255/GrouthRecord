package com.example.springboot_vue.mini_spring.main;

import com.example.springboot_vue.mini_spring.service.AService;
import com.example.springboot_vue.mini_spring.utils.ClassPathXmlApplicationContext;

public class Main {

    public static void main(String[] args) {
        // 这里默认读取resources下的文件，因为内部实现使用了classloader，只能寻找编译过后的文件
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("file/test.xml");
        AService aService = (AService) classPathXmlApplicationContext.getBean("aService");
        aService.sayHello();
    }

}
