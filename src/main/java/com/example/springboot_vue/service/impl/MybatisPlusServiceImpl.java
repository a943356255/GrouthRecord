package com.example.springboot_vue.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot_vue.mapper.mybatis_plus_demo.MybatisPlusMapper;
import com.example.springboot_vue.pojo.mybatis_plus_pojo.MybatisPlusPojo;
import com.example.springboot_vue.service.MybatisPlusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MybatisPlusServiceImpl extends ServiceImpl<MybatisPlusMapper, MybatisPlusPojo> implements MybatisPlusService {

    @Autowired
    private MybatisPlusMapper mybatisPlusMapper;

    @Override
    public JSONObject testMybatisPlus() {

        JSONObject jsonObject = new JSONObject();

        // count函数会直接获取当前表下的数据量
        int id = count() + 1;
        MybatisPlusPojo mybatisPlusPojo = new MybatisPlusPojo(id, "Billie", 24, "943356255@qq.com");

        QueryWrapper<MybatisPlusPojo> countWrapper = new QueryWrapper<>();
        countWrapper.eq("email", "943356255@qq.com");
        int count = count(countWrapper);
        System.out.println(count);

        // 利用queryWrapper拼接sql
        QueryWrapper<MybatisPlusPojo> queryWrapper = new QueryWrapper<>();
        // queryWrapper拼接的条件
        queryWrapper.eq("id", 5)
                .eq("nickname", mybatisPlusPojo.getName());
        List<MybatisPlusPojo> whereList = mybatisPlusMapper.selectList(queryWrapper);
        jsonObject.put("whereList", whereList);

        // 无条件查找，会查询所有字段
        List<MybatisPlusPojo> list = mybatisPlusMapper.selectList(null);
        jsonObject.put("list", list);

        // 添加语句
        save(mybatisPlusPojo);
        // mybatisPlusMapper.insert(mybatisPlusPojo);

        // 删除
        Map<String, Object> map = new HashMap<>();
        map.put("nickname", "Sandy");
        map.put("email", "943356255@qq.com");
        removeByMap(map);

        return jsonObject;
    }
}
