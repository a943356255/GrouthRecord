package com.example.springboot_vue.service;

import com.alibaba.fastjson.JSONObject;
import com.example.springboot_vue.pojo.Category;
import com.example.springboot_vue.pojo.Demo;
import com.example.springboot_vue.pojo.Test;

import java.util.ArrayList;

public interface DemoService {

    Demo getDemo(String account);

    int addDemo(Demo demo);

    Category getCategory(String categoryId, String categoryLevel);

    JSONObject getAllCategory();

    void test();

    void testJson(Test test);

    ArrayList<Test> selJsonResult();

}
