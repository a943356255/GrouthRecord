package com.example.springboot_vue.controller.crud_interface;

import com.alibaba.fastjson.JSONObject;
import com.example.springboot_vue.mapper.UserMapper;
import com.example.springboot_vue.pojo.TestUser;
import com.example.springboot_vue.service.CRUDService;
import com.example.springboot_vue.utils.excel_util.ReadExcel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.stream.*;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/crudInterface")
public class CRUDInterface {

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    CRUDService crudServiceImpl;

    @Autowired
    UserMapper userMapper;

    @RequestMapping("/allCRUD")
    public JSONObject testInterface(@RequestBody Map<String, Object> map, HttpServletRequest request) throws IOException {
        return crudServiceImpl.getCrudValue(map);
    }

    @RequestMapping("/testUser")
    public void test(@RequestBody Map<String, Object> map) {
        crudServiceImpl.test(map);
    }

    public void testRedisTemplateStream() {
        List<MapRecord<String, Object, Object>> list = stringRedisTemplate.opsForStream().read(
                Consumer.from("g1", "c1"),
                StreamReadOptions.empty().count(1).block(Duration.ofSeconds(2)),
                StreamOffset.create("queueName", ReadOffset.lastConsumed())
        );
        // 这是创建一个线程池
//        ExecutorService executorService = Executors.newFixedThreadPool();
    }
}
