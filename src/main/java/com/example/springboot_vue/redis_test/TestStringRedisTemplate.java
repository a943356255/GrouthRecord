package com.example.springboot_vue.redis_test;

import com.example.springboot_vue.pojo.user.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.HashMap;
import java.util.Map;

public class TestStringRedisTemplate {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private static final ObjectMapper mapper = new ObjectMapper();

    void test() {
        stringRedisTemplate.opsForValue().get("name");
        stringRedisTemplate.opsForValue().set("name", "张三");
    }

    void testObject() throws JsonProcessingException {
        User user = new User();
        // 手动序列化为
        String json = mapper.writeValueAsString(user);
        // 插入数据
        stringRedisTemplate.opsForValue().set("user:1", json);

        // 取值
        String jsonUser = stringRedisTemplate.opsForValue().get("user:1");
        // 手动反序列化
        User readUser = mapper.readValue(jsonUser, User.class);
    }

    // 操作hash
    void testHash() {
        // 添加数据
        stringRedisTemplate.opsForHash().put("user", "name", "张三");
        stringRedisTemplate.opsForHash().put("user", "age", "24");

        // 添加多个
        stringRedisTemplate.opsForHash().putAll("user", new HashMap<>());

        // 获取数据
        stringRedisTemplate.opsForHash().get("user", "name");

        // 获取多个
        Map<Object, Object> map =  stringRedisTemplate.opsForHash().entries("user");
    }
}
