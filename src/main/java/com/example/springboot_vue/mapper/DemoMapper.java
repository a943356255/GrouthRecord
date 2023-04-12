package com.example.springboot_vue.mapper;

import com.example.springboot_vue.pojo.Category;
import com.example.springboot_vue.pojo.Demo;
import com.example.springboot_vue.pojo.Test;
import com.example.springboot_vue.pojo.test.Teacher;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface DemoMapper {
    Demo selDemoByAccount(String account);

    int inertDemo(Demo demo);

    Category getCategory(String categoryId, String categoryLevel);

    ArrayList<Category> getAllCategory();

    int insertCate();

    int insertJson(Test test);

    ArrayList<Test> selJsonResult();

    ArrayList<Teacher> selTeacher();
}
