package com.example.springboot_vue.pojo.goods;

import java.util.ArrayList;

public class AttrsList {
    private int id;
    private String title;
    private ArrayList<String> attrsList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<String> getAttrsList() {
        return attrsList;
    }

    public void setAttrsList(ArrayList<String> attrsList) {
        this.attrsList = attrsList;
    }
}
