package com.example.springboot_vue.service;

import com.alibaba.fastjson.JSONObject;
import com.example.springboot_vue.pojo.city.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import java.util.List;
import java.util.Map;

public interface CRUDService {

    JSONObject getCrudValue(Map<String, Object> map);

    void test(Map<String, Object> map);

    void insertCity(String filepath);

    void exportCity(String name, String pageRange, int pageSize, String path);

    void insertCityByOneThread(String filepath);

    void insertCityByOneThreadUID(String filepath);

    void insertAutoIdCity(String filepath);

    JSONObject submitTest(Map<String, Object> map, DataSourceTransactionManager dataSourceTransactionManager);

}