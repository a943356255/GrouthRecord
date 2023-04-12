package com.example.springboot_vue.pojo;

public class TypeNavTwo {
    private String categoryName;
    private String categoryId;
    private String category2Id;
    private TypeNav[] typeNavs;

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

    public TypeNav[] getTypeNavs() {
        return typeNavs;
    }

    public void setTypeNavs(TypeNav[] typeNavs) {
        this.typeNavs = typeNavs;
    }
}
