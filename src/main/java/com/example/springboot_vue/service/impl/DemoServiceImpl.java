package com.example.springboot_vue.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.springboot_vue.mapper.DemoMapper;
import com.example.springboot_vue.pojo.*;
import com.example.springboot_vue.pojo.test.Teacher;
import com.example.springboot_vue.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Map;

@Service
public class DemoServiceImpl implements DemoService {

    @Autowired
    DemoMapper demoMapper;

    @Override
    public void test() {
        ArrayList<Teacher> list = demoMapper.selTeacher();
        for (Teacher teacher : list) {
            System.out.println(teacher.getAccount() + " " + teacher.getCollege());
        }
    }

    @Override
    public void testJson(Test test) {
        demoMapper.insertJson(test);
    }

    @Override
    public ArrayList<Test> selJsonResult() {
        return demoMapper.selJsonResult();
    }

    @Override
    public Demo getDemo(String account) {
        if (account == null) {
            return null;
        }
        return demoMapper.selDemoByAccount(account);
    }

    @Override
    public int addDemo(Demo demo) {
        if (demo == null) {
            return 0;
        }
        return demoMapper.inertDemo(demo);
    }

    @Override
    public Category getCategory(String categoryId, String categoryLevel) {
        return demoMapper.getCategory(categoryId, categoryLevel);
    }

    @Override
    public JSONObject getAllCategory() {
        JSONObject jsonObject = new JSONObject();
        ArrayList<Category> list = demoMapper.getAllCategory();

        int count1 = 0, count2 = 0, count3 = 0, index = 0;
        for (Category category : list) {
            if (category.getCategoryLevel().equals("1")) {
                count1++;
            }
        }
        TypeNavOne[] typeNavOnes = new TypeNavOne[count1];

        // 分类1的遍历
        for (Category category : list) {
            if (category.getCategoryLevel().equals("1")) {
                // 封装一个一级分类
                TypeNavOne typeNav1 = new TypeNavOne();
                typeNav1.setCategoryId(category.getCategoryId());
                typeNav1.setCategoryName(category.getCategoryName());
                typeNavOnes[index] = typeNav1;
                index++;
            }
        }

        // 设置二级
        for (int i = 0; i < count1; i++) {
            String fatherId = typeNavOnes[i].getCategoryId();
            // 获取每一个二级的大小
            count2 = 0;
            for (int k = 0; k < list.size(); k++) {
                if (list.get(k).getCategoryId().equals(fatherId) && list.get(k).getCategoryLevel().equals("2")) {
                    count2++;
                }
            }
            if (count2 == 0) {
                continue;
            }

            // 获取父级属性
            TypeNavTwo[] typeNavTwos = new TypeNavTwo[count2];

            index = 0;
            for (int j = 0; j < list.size(); j++) {
                // 获取到本次遍历的层级以及id
                String level = list.get(j).getCategoryLevel();
                String childrenId = list.get(j).getCategoryId();
                // 对比，如果是2级同时从属于父级
                if (childrenId.equals(fatherId) && level.equals("2")) {
                    TypeNavTwo typeNavTwo = new TypeNavTwo();
                    typeNavTwo.setCategoryId(fatherId);
                    typeNavTwo.setCategoryName(list.get(j).getCategoryName());
                    typeNavTwo.setCategory2Id(list.get(j).getCategory2Id());
                    typeNavTwos[index] = typeNavTwo;
                    index++;
                }
            }
            typeNavOnes[i].setTypeNavTwos(typeNavTwos);

            // 开始添加第三层
            for (int j = 0; j < typeNavOnes[i].getTypeNavTwos().length; j++) {
                // 不为空再进入
                if (typeNavOnes[i].getTypeNavTwos()[j] != null) {
                    String levelTwoId = typeNavOnes[i].getTypeNavTwos()[j].getCategory2Id();
                    count3 = 0;
                    for (int k = 0; k < list.size(); k++) {
                        if (list.get(k).getCategory3Id() != null) {
                            if (list.get(k).getCategory3Id().equals(levelTwoId) && list.get(k).getCategoryLevel().equals("3")) {
                                count3++;
                            }
                        }
                    }

                    TypeNav[] typeNavs = new TypeNav[count3];
                    index = 0;
                    for (int k = 0; k < list.size(); k++) {
                        if (list.get(k).getCategory3Id() != null) {
                            if (list.get(k).getCategory3Id().equals(levelTwoId) && list.get(k).getCategoryLevel().equals("3")) {
                                TypeNav typeNav = new TypeNav();
                                typeNav.setCategoryName(list.get(k).getCategoryName());
                                // 这个id也设置为最外层的id
                                typeNav.setCategoryId(fatherId);
                                typeNavs[index] = typeNav;
                                index++;
                            }
                        }
                    }
                    typeNavOnes[i].getTypeNavTwos()[j].setTypeNavs(typeNavs);
                }
            }
        }

        jsonObject.put("categoryList", typeNavOnes);

        return jsonObject;
    }

    @RequestMapping("/getDetail")
    public JSONObject getDetail(@RequestBody Map<String, String> map) {
        JSONObject jsonObject = new JSONObject();

        return jsonObject;
    }
}
