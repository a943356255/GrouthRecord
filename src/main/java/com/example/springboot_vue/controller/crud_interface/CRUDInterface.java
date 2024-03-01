package com.example.springboot_vue.controller.crud_interface;

import com.alibaba.fastjson.JSONObject;
import com.example.springboot_vue.mapper.UserMapper;
import com.example.springboot_vue.service.CRUDService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.stream.*;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
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

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    DataSourceTransactionManager dataSourceTransactionManager;

    @RequestMapping("/testSubmit")
    public JSONObject submitTest(@RequestBody Map<String, Object> map) {
        return crudServiceImpl.submitTest(map, dataSourceTransactionManager);
    }

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

    @RequestMapping("/testParamAuto")
    public String testRequestParamAuto(@RequestParam("filepath") String filepath) {
        crudServiceImpl.insertAutoIdCity(filepath);
        return "上传成功";
    }

    @RequestMapping("/testParam")
    public String testRequestParam(@RequestParam("filepath") String filepath) {
        crudServiceImpl.insertCity(filepath);
        return "上传成功";
    }

    @RequestMapping("/testParamOne")
    public String testRequestParamByOne(@RequestParam("filepath") String filepath) {
        crudServiceImpl.insertCityByOneThread(filepath);
        return "上传成功";
    }

    @RequestMapping("/testParamOneUID")
    public String testRequestParamByUID(@RequestParam("filepath") String filepath) {
        crudServiceImpl.insertCityByOneThreadUID(filepath);
        return "上传成功";
    }

    @RequestMapping("/exportData")
    public String export(@RequestBody Map<String, Object> map, HttpServletResponse response) {
//        String pageRange = "1-2100001";
//        int pageSize = 1000;
//        String filepath = "D:\\bilibili_video\\export.xlsx";

        String name = (String) map.get("name");
        String pageRange = (String) map.get("pageRange");
        // 批量查询的条数，目前每个sheet固定3w条数据
        int pageSize = (int) map.get("pageSize");
        String filepath = (String) map.get("filepath");
        crudServiceImpl.exportCity(name, pageRange, pageSize, filepath);
        // 写回请求
        writeBackFile(response, filepath, name);
        return "导出成功";
    }

    public void writeBackFile(HttpServletResponse response, String filepath, String filename) {
        File file = new File(filepath);
        ServletOutputStream out = null;
        BufferedInputStream inputStream = null;
        try {
            inputStream = new BufferedInputStream(new FileInputStream(file));
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment;filename=" + filename + ";"+"filename*=utf-8''" + filename);
            out = response.getOutputStream();
            byte[] buffer = new byte[1024];
            int n;
            // while循环一直读取缓冲流中的内容到输出的对象中
            while ((n = inputStream.read(buffer)) != -1) {
                out.write(buffer, 0, n);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.flush();
                    out.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
