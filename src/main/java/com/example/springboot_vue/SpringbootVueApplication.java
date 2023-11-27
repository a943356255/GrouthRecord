package com.example.springboot_vue;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@MapperScan("com.example.springboot_vue.pojo")
@MapperScan("com.example.springboot_vue.mapper")
@MapperScan("com.baomidou.mybatisplus.samples.quickstart.mapper")
@MapperScan("com.example.springboot_vue.file_operate.mapper")
@ComponentScans(value = {@ComponentScan(value = "big_file_upload")})
@SpringBootApplication
public class SpringbootVueApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootVueApplication.class, args);
    }

}
