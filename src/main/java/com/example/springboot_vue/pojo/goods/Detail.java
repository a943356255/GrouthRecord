package com.example.springboot_vue.pojo.goods;

import com.alibaba.fastjson.JSONObject;

public class Detail {
    private int id;
    private String name;
    private String price;
//    private String key;
    private JSONObject attrJson;
//    private Goods goods;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public JSONObject getAttrJson() {
        return attrJson;
    }

    public void setAttrJson(JSONObject attrJson) {
        this.attrJson = attrJson;
    }

//    public Goods getGoods() {
//        return goods;
//    }
//
//    public void setGoods(Goods goods) {
//        this.goods = goods;
//    }
}
