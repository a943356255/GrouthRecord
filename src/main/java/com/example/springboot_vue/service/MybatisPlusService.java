package com.example.springboot_vue.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springboot_vue.pojo.mybatis_plus_pojo.MybatisPlusPojo;

public interface MybatisPlusService extends IService<MybatisPlusPojo> {

    JSONObject testMybatisPlus();
}
