package com.example.springboot_vue.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.springboot_vue.mapper.SearchMapper;
import com.example.springboot_vue.pojo.goods.Goods;
import com.example.springboot_vue.pojo.goods.ReturnType;
import com.example.springboot_vue.pojo.goods.Type;
import com.example.springboot_vue.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    SearchService searchServiceImpl;

    @Autowired
    SearchMapper searchMapper;

    @RequestMapping("/getInfo")
    public JSONObject getSearch(@RequestBody Map<String, String> map) {

        JSONObject jsonObject = new JSONObject();
        ReturnType returnType = searchServiceImpl.getReturnType(map);
        jsonObject.put("searchList", returnType);

        return jsonObject;
    }

    @RequestMapping("/getDetail")
    public JSONObject getDetail(@RequestParam String id) {
        return searchServiceImpl.getDetail(id);
    }

    @RequestMapping("/addToCar")
    public JSONObject addToCar(@RequestBody Map<String, String> map) {
        System.out.println(123);
        String id = map.get("id");
        String num = map.get("num");
        System.out.println(id + "  " + num);

        return new JSONObject();
    }

    @RequestMapping("/insertValue")
    public int insertValue(@RequestBody Map<String, String> map) {
        Type type = new Type();
        type.setName(map.get("typeName"));
        type.setCategoryId(Integer.parseInt(map.get("typeId")));

        for (int i = 0; i < 15; i++) {
            searchMapper.insertType(type);
        }

        Goods goods = new Goods();
        goods.setCategoryId(map.get("categoryId"));
        goods.setCamera(map.get("camera"));
        goods.setName("Apple苹果iPhone 13pro");
        goods.setImage("/images/search/mobile06.png");
        goods.setPrice(6999);
        goods.setTrademark("苹果");
        goods.setCategoryName("苹果");
        goods.setSize("4.0-4.9英寸");
        goods.setNet("联通4G");

        for (int i = 0; i < 15; i++) {
            searchMapper.insertGoods(goods);
        }

        return 1;
    }

    @RequestMapping("/allSearch")
    public JSONObject goSearch(@RequestBody Map<String, String> map) {
        return searchServiceImpl.getSearchResult(map);
    }

    @RequestMapping("/deepSearch")
    public JSONObject deepSearch(@RequestBody Map<String, Object> map) {
        return searchServiceImpl.deepSearch(map);
    }

    @RequestMapping("/getTypeNav")
    public JSONObject getData() {
        return searchServiceImpl.getTypeNav();
    }

    @RequestMapping("/addGoods")
    public JSONObject addGoods(@RequestBody Map<String, Object> map) {
        return searchServiceImpl.addGoods(map);
    }

    @RequestMapping("/getNewDetail")
    public JSONObject getNewDetail(@RequestBody Map<String, Object> map) {
        return searchServiceImpl.getNewDetail(map);
    }

    @RequestMapping("/delGoods")
    public JSONObject delGoods(@RequestBody Map<String, Object> map) {
        return searchServiceImpl.delGoods(map);
    }

    @RequestMapping("/changeGoods")
    public JSONObject changeGoods(@RequestBody Map<String, Object> map) {
        return searchServiceImpl.changeGoods(map);
    }
}
