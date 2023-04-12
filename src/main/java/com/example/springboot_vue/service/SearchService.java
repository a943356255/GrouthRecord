package com.example.springboot_vue.service;

import com.alibaba.fastjson.JSONObject;
import com.example.springboot_vue.pojo.goods.Goods;
import com.example.springboot_vue.pojo.goods.ReturnType;
import com.example.springboot_vue.pojo.goods.Type;

import java.util.ArrayList;
import java.util.Map;

public interface SearchService {

    ReturnType getReturnType(Map<String, String> map);

    ArrayList<Goods> selAllGoods();

    ArrayList<Type> selAllType();

    JSONObject getDetail(String id);

    JSONObject getSearchResult(Map<String, String> map);

    JSONObject deepSearch(Map<String, Object> map);

    JSONObject getTypeNav();

    JSONObject addGoods(Map<String, Object> map);

    JSONObject getNewDetail(Map<String, Object> map);

    JSONObject delGoods(Map<String, Object> map);

    JSONObject changeGoods(Map<String, Object> map);
}
