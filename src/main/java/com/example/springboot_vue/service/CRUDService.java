package com.example.springboot_vue.service;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

public interface CRUDService {

    JSONObject getCrudValue(Map<String, Object> map);
}
