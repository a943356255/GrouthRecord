package com.example.springboot_vue.pojo;

import com.alibaba.fastjson.JSONObject;

public class Test {
    private int id;
    private String name;
    private String account;
    private JSONObject skill;

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

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public JSONObject getSkill() {
        return skill;
    }

    public void setSkill(JSONObject skill) {
        this.skill = skill;
    }
}
