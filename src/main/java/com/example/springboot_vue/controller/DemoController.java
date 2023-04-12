package com.example.springboot_vue.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.springboot_vue.pojo.Demo;
import com.example.springboot_vue.pojo.Test;
import com.example.springboot_vue.service.DemoService;
import com.example.springboot_vue.httpclient.GetData;
import com.example.springboot_vue.utils.JSONUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    DemoService demoServiceImpl;

    @Autowired
    JSONUtils jsonUtils;

    @Autowired
    GetData getData;

    @RequestMapping("/testDemo")
    public Demo testDemo() {
        demoServiceImpl.test();
        return new Demo();
    }

    @RequestMapping("/getCategory")
    public JSONObject getCategory() {
        JSONObject jsonObject = demoServiceImpl.getAllCategory();
        return jsonObject;
    }

    @RequestMapping("/getSearch")
    public JSONObject getSearch(@RequestBody Map<String, String> map) {
        String categoryId = map.get("categoryId");
        String categoryName = map.get("categoryName");
        String keyWords = map.get("keyWords");

        System.out.println(categoryId + "-" + categoryName + "-" + keyWords);
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("keyWords", keyWords);

        return jsonObject;
    }

    @RequestMapping("/testJson")
    public int testJson(@RequestBody Map<String, Object> map) {
        Test test = new Test();
        String account = (String) map.get("account");
        String name = (String) map.get("name");
        LinkedHashMap linkedHashMap = (LinkedHashMap) map.get("skill");

        JSONObject skill = new JSONObject();
        skill.put("A", linkedHashMap.get("A"));
        skill.put("B", linkedHashMap.get("B"));
        skill.put("C", linkedHashMap.get("C"));

        test.setAccount(account);
        test.setName(name);
        test.setSkill(skill);
        demoServiceImpl.testJson(test);

        return 1;
    }

    @RequestMapping("/getJson")
    public JSONObject getJson() {
        JSONObject jsonObject = new JSONObject();

        ArrayList<Test> list = demoServiceImpl.selJsonResult();
        jsonObject.put("jsonList", list);
        return jsonObject;
    }

    @RequestMapping("/addDemo")
    public int addDemo(@RequestBody Demo demo) {
        demoServiceImpl.test();
//        demoServiceImpl.addDemo(demo)
        return  1;
    }

    @RequestMapping("/addCity")
    public void test() throws IOException {

        getData.getCity();
    }

}
