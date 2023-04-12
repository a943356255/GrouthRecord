package com.example.springboot_vue.pojo;

public class TypeNavOne {
    private String categoryName;
    private String categoryId;
    private TypeNavTwo[] typeNavTwos;

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

    public TypeNavTwo[] getTypeNavTwos() {
        return typeNavTwos;
    }

    public void setTypeNavTwos(TypeNavTwo[] typeNavTwos) {
        this.typeNavTwos = typeNavTwos;
    }
}
