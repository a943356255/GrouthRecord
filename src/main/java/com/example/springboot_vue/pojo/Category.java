package com.example.springboot_vue.pojo;

import java.util.List;

public class Category {

    private int id;
    private String categoryName;
    // 每一种分类都是同一个categoryId，区分的时候用level来区分
    private String categoryId;
    private String category2Id;
    private String category3Id;
    // 用于标记是几级分类
    private String categoryLevel;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategory2Id() {
        return category2Id;
    }

    public void setCategory2Id(String category2Id) {
        this.category2Id = category2Id;
    }

    public String getCategory3Id() {
        return category3Id;
    }

    public void setCategory3Id(String category3Id) {
        this.category3Id = category3Id;
    }

    public String getCategoryLevel() {
        return categoryLevel;
    }

    public void setCategoryLevel(String categoryLevel) {
        this.categoryLevel = categoryLevel;
    }

}
