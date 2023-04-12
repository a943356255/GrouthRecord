package com.example.springboot_vue.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springboot_vue.pojo.verify.ResultVO;

import java.util.List;
import java.util.Map;

public interface UserService {

    String getCode(String phone);

    String register(String phone, String password);

    JSONObject login(Map<String, String> map);

    JSONObject getInfo(String token);

    ResultVO<List<String>> getRoles();

    ResultVO<String> insertController(Map<String, Object> map);
}
