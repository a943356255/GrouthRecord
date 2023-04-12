package com.example.springboot_vue.pojo.login;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AsyncRouterMap {

    private int id;

    @NotNull(message = "路径不能为空")
    private String path;

    @NotNull(message = "名称不能为空")
    private String name;

    @NotNull(message = "组件不能为空")
    private String component;

    @NotNull(message = "meta不能为空")
    private JSONObject meta;

}
