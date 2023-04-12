package com.example.springboot_vue.pojo;

public class NewTypeNav {
    private int id;
    private String category1Id;
    private String category2Id;
    private String category3Id;
    private int parentId;
    private int parent2Id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory1Id() {
        return category1Id;
    }

    public void setCategory1Id(String category1Id) {
        this.category1Id = category1Id;
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

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getParent2Id() {
        return parent2Id;
    }

    public void setParent2Id(int parent2Id) {
        this.parent2Id = parent2Id;
    }
}
