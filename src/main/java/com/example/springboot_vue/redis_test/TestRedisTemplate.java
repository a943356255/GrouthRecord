package com.example.springboot_vue.redis_test;

import com.example.springboot_vue.pojo.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

public class TestRedisTemplate {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    void testString() {
        // 设置一个String类型的值
        redisTemplate.opsForValue().set("name", "张三");
        // 获取一个String类型的值
        redisTemplate.opsForValue().get("name");

        // 添加一个对象类型  由于存储对象需要存储对象的字节码，会导致字节码本身比数据要大，浪费存储空间
        // 所以一般采用private RedisTemplate<String, String> redisTemplate;
        redisTemplate.opsForValue().set("user:1", new User());
        // 获取
        User user = (User) redisTemplate.opsForValue().get("user:1");

    }
}
