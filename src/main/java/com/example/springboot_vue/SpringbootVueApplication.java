package com.example.springboot_vue;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.example.springboot_vue.pojo")
@MapperScan("com.example.springboot_vue.mapper")
@MapperScan("com.baomidou.mybatisplus.samples.quickstart.mapper")
@MapperScan("com.example.springboot_vue.file_operate.mapper")
@SpringBootApplication
public class SpringbootVueApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootVueApplication.class, args);
    }

}
