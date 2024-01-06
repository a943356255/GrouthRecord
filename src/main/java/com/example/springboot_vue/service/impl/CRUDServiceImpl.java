package com.example.springboot_vue.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.springboot_vue.mapper.CityMapper;
import com.example.springboot_vue.mapper.crud.CRUDMapper;
import com.example.springboot_vue.pojo.city.City;
import com.example.springboot_vue.service.CRUDService;
import com.example.springboot_vue.utils.excel_util.EasyExcelDemo;
import com.example.springboot_vue.utils.excel_util.ReadExcel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@Service
@Transactional
public class CRUDServiceImpl implements CRUDService {

    @Autowired
    CRUDMapper crudMapper;

    @Autowired
    CityMapper cityMapper;

//    @Resource
//    MongoTemplate mongoTemplate;

    @Override
    public JSONObject getCrudValue(Map<String, Object> map) {

        JSONObject jsonObject = new JSONObject();
        // 获取要操作的表
        String table = (String) map.get("table");
        // 要执行的操作
        String operate = (String) map.get("operate");
        System.out.println("本次操作是：" + operate);

        // 需要操作的字段
        LinkedHashMap<String, Object> columns = (LinkedHashMap) map.get("column");
        System.out.println("columns = " + columns);

        // 需要排序的字段
        // select * from table_name order by column_1, column_2 desc

        // key为要限制的字段，value为具体的限制结果
        LinkedHashMap<String, Object> condition = (LinkedHashMap) map.get("condition");
//        LinkedHashMap<String, Object> columnsArr;
//        if (condition != null) {
//            for (Map.Entry<String, Object> entry : condition.entrySet()) {
//                System.out.println(entry.getKey());
//                columnsArr = (LinkedHashMap<String, Object>) entry.getValue();
//                // 这种写法不行，这样会导致解析玩的map把符号扔掉
//            }
//        }

        // 如果不存在字段，直接返回
        if (columns == null) {
            jsonObject.put("code", -1);
            return jsonObject;
        }

        switch (operate) {
            case "add":
                // 添加数据
                crudMapper.insert(columns, table);
                break;
            case "select":

                int pageNo = (int) map.get("pageNo");
                int pageSize = (int) map.get("pageSize");
                int excelPageSize = 0;
                if (map.get("excelPageSize") != null) {
                    excelPageSize = (int) map.get("excelPageSize");
                }

                LinkedHashMap<String, Object> like = (LinkedHashMap<String, Object>) map.get("like");
                System.out.println("like = " + like);

                String searchValue = (String) map.get("searchValue");

                ArrayList<Map<String, Object>> list;

                // 判断是否是因为导出excel需要查询数据
                if (excelPageSize != 0) {
                    list = crudMapper.select(columns, condition, table, (pageNo - 1) * pageSize, excelPageSize, like, searchValue);
                } else {
                    list = crudMapper.select(columns, condition, table, (pageNo - 1) * pageSize, pageSize, like, searchValue);
                }

                System.out.println("查询结果大小为:" + list.size());
                jsonObject.put("valueList", list);

                int size = crudMapper.selectCount(columns, condition, table, like);
                System.out.println("size大小为" + size);
                jsonObject.put("size", size);

                break;
            case "delete":
                // 删除操作,如果不传条件，默认删除失败
                if (condition == null) {
                    jsonObject.put("code", -1);
                    return jsonObject;
                }
                crudMapper.delete(table, condition);
                break;
            case "update":
                // 修改操作
                crudMapper.update(columns, table, condition);
                break;
            default:
                // 不是上述四种操作，直接返回结果
                jsonObject.put("code", -1);
                return jsonObject;
        }

        return jsonObject;
    }

    @Override
    public void test(Map<String, Object> map) {
        String table = (String) map.get("table");
        List<String> list = (List<String>) map.get("column");

        List<Map<String, String>> res = ReadExcel.readExcelAndInsertIntoDB("D:\\git\\test.xlsx", list);
        crudMapper.insertExcelData(table, res, list);
    }

    ExecutorService executorService = new ThreadPoolExecutor(20, 20, 10, TimeUnit.MINUTES, new LinkedBlockingDeque<>());
    List<CompletableFuture<Integer>> allFutures = new ArrayList<>();

    @Override
    public void insertCity(List<City> list) {
        EasyExcelDemo easyExcelDemo = new EasyExcelDemo();
        CountDownLatch latch = new CountDownLatch(1);

//        easyExcelDemo.readExcel(cityMapper, latch);
        InputStream inputStream = null;
        try {
             inputStream = new BufferedInputStream(new FileInputStream("D:\\bilibili_video\\test.xlsx"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ExcelReader build = EasyExcel.read(inputStream).build();
        List<ReadSheet> readSheets = build.excelExecutor().sheetList();
        System.out.println("一共有" + readSheets.size());
        for (int i = 0; i < readSheets.size(); i++) {
            int finalI = i;
            CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
                easyExcelDemo.threadReadExcel(cityMapper, readSheets.get(finalI).getSheetName());
                return null;
            }, executorService);
            allFutures.add(future);
        }

        CompletableFuture<Void> allCompleted = CompletableFuture.allOf(allFutures.toArray(new CompletableFuture[0]));
        allCompleted.join();
    }

}
