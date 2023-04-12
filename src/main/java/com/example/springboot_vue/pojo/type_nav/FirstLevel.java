package com.example.springboot_vue.pojo.type_nav;

import java.util.ArrayList;

public class FirstLevel {
    private int id;
    private String categoryId;
    private ArrayList<SecondLevel> firstLevelList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public ArrayList<SecondLevel> getFirstLevelList() {
        return firstLevelList;
    }

    public void setFirstLevelList(ArrayList<SecondLevel> firstLevelList) {
        this.firstLevelList = firstLevelList;
    }
}
