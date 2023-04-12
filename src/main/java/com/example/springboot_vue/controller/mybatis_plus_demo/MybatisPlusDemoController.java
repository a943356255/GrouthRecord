package com.example.springboot_vue.controller.mybatis_plus_demo;

import com.alibaba.fastjson.JSONObject;
import com.example.springboot_vue.service.MybatisPlusService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/plus")
public class MybatisPlusDemoController {

    @Resource
    MybatisPlusService mybatisPlusServiceImpl;

    @RequestMapping("/firstTest")
    public JSONObject testCRUD() {
        return mybatisPlusServiceImpl.testMybatisPlus();
    }

}
