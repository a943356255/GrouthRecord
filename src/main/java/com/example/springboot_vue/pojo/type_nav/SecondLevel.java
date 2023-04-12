package com.example.springboot_vue.pojo.type_nav;

import java.util.ArrayList;

public class SecondLevel {
    private int id;
    private String category2Id;
    private ArrayList<String> levelThreeList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory2Id() {
        return category2Id;
    }

    public void setCategory2Id(String category2Id) {
        this.category2Id = category2Id;
    }

    public ArrayList<String> getLevelThreeList() {
        return levelThreeList;
    }

    public void setLevelThreeList(ArrayList<String> levelThreeList) {
        this.levelThreeList = levelThreeList;
    }
}
