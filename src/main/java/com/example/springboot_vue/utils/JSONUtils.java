package com.example.springboot_vue.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.springboot_vue.mapper.CityMapper;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Random;

@Service
public class JSONUtils {

    @Autowired
    CityMapper cityMapper;

    public static void add() {
        Random random = new Random();
        int value = random.nextInt(10000);
        JSONObject[] jsonObject = new JSONObject[10];

        for (int i = 0; i < 10; i++) {
            JSONObject partValue = new JSONObject();
            partValue.put("name", i);
            partValue.put("value", value);
            jsonObject[i] = partValue;
//            write(jsonObject[i].toJSONString(), "D:\\vue_组件\\test.json");
        }
    }

//    public static void write(String str, String path) {
//        try {
//            File file = new File(path);        //文件路径（路径+文件名）
//            if (!file.exists()) {    //文件不存在则创建文件，先创建目录
//                File dir = new File(file.getParent());
//                dir.mkdirs();
//                file.createNewFile();
//            }
//
//            FileOutputStream outStream = new FileOutputStream(file, true);    //文件输出流用于将数据写入文件
//            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outStream, StandardCharsets.UTF_8);
//            outputStreamWriter.write(str + ",");
//            outputStreamWriter.close();    //关闭文件输出流
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public void testAll() throws IOException {
        File file = new File("C:\\Users\\郭俊豪\\Desktop\\test.json");

        String content= FileUtils.readFileToString(file,"UTF-8");

        JSONArray jsonArray = JSONArray.parseArray(content);
//        File file2 = new File("C:\\Users\\郭俊豪\\Desktop\\chongqing.json");
//
//        String content2 = FileUtils.readFileToString(file2,"UTF-8");
//
//        // 整个省份的
//        JSONObject one = JSONObject.parseObject(content2);

        for (int i = 0; i < jsonArray.size(); i++) {
            // 每一个省份的数据
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            testJson(jsonObject);
        }
    }

    public void testJson(JSONObject jsonObject) {

//        File file = new File("C:\\Users\\郭俊豪\\Desktop\\chongqing.json");
//
//        String content= FileUtils.readFileToString(file,"UTF-8");
//
//        // 整个省份的
//        JSONObject jsonObject = JSONObject.parseObject(content);

        // 城市
        String province = (String) jsonObject.get("provinceName");
        //
        JSONArray partJson = (JSONArray) jsonObject.get("mallCityList");

        System.out.println("partJson = " + partJson.toString());

        int size = partJson.size();
        System.out.println("size = " + size);
        // 存放city名称
        ArrayList<String> cityNames = new ArrayList<>();
        // 存放area名称
        ArrayList[] areasList = new ArrayList[size];

        for (int i = 0; i < size; i++) {

            JSONObject part = (JSONObject) partJson.get(i);
            System.out.println("part = " + part.toJSONString());
            System.out.println("part.get(cityName) = " + part.get("cityName"));


            cityNames.add((String) part.get("cityName"));
            JSONArray jsonArray = (JSONArray) part.get("mallAreaList");

            System.out.println("jsonArray = " + jsonArray);

            ArrayList<String> list = new ArrayList<>();
            for (int j = 0; j < jsonArray.size(); j++) {
                JSONObject obj = (JSONObject) jsonArray.get(j);
                list.add((String) obj.get("areaName"));
            }

            System.out.println(list.toString());
            System.out.println(cityNames.get(i));

            cityMapper.insertCity(list, cityNames.get(i));
        }

        cityMapper.insertCity(cityNames, province);
    }

}

