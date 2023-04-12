package com.example.springboot_vue.pojo.user;

import java.util.ArrayList;

public class RouteAndRole {
    private int id;
    private int parentId;
    private String authName;
    private String path;
    private ArrayList<RouteAndRole> children;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthName() {
        return authName;
    }

    public void setAuthName(String authName) {
        this.authName = authName;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public ArrayList<RouteAndRole> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<RouteAndRole> children) {
        this.children = children;
    }
}
