package com.example.springboot_vue.pojo.mybatis_plus_pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("mybatis_plus_table")
public class MybatisPlusPojo {
    private int id;
    @TableField(value = "nickname")
    private String name;
    private Integer age;
    private String email;

    public MybatisPlusPojo() {

    }

    public MybatisPlusPojo(int id, String name, Integer age, String email) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
    }

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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
