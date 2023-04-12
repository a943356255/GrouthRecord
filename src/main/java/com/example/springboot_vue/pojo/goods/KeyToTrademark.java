package com.example.springboot_vue.pojo.goods;

import com.alibaba.fastjson.JSONObject;

public class KeyToTrademark {
    private int id;
    private String keyWords;
    private JSONObject trademark;
    private JSONObject detail;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

    public JSONObject getTrademark() {
        return trademark;
    }

    public void setTrademark(JSONObject trademark) {
        this.trademark = trademark;
    }

    public JSONObject getDetail() {
        return detail;
    }

    public void setDetail(JSONObject detail) {
        this.detail = detail;
    }
}
