package com.example.springboot_vue.socket.utils;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringUtils {

    FileUtils fileUtils = new FileUtils();

    public List<ArrayList<String>> getResult(String sql, JSONObject jsonObject) {
        // 返回的结果集
        List<ArrayList<String>> list = new ArrayList<>();

        // 分割取到结果
        String str = sql.substring(sql.indexOf("where"));
        String[] result = str.split(" ");

        ArrayList<String> keyList = new ArrayList<>();
        ArrayList<String> valueList = new ArrayList<>();

        for (int i = 0; i < result.length; i++) {
            if (result[i].equals("=")) {
                // 如果超出或者不足，说明sql有问题
                if (i+1 >= result.length || i-1 < 0) {
                    return null;
                }
                keyList.add(result[i-1]);
                valueList.add(result[i+1]);
            }
        }

        list.add(keyList);
        list.add(valueList);

        return list;
    }

    public ArrayList<String> getLimit(String sql) {

        ArrayList<String> list = new ArrayList<>();
        // 分割
        String[] limit = sql.substring(6, sql.indexOf("from")).split(", ");

        for (String s : limit) {
            list.add(s.replace(" ", ""));
        }

        return list;
    }

    public JSONObject insert(String str, String DBName) {

        JSONObject jsonObject = new JSONObject();

        // 取出key和value以及table
        String key = str.substring(str.indexOf("(")+1, str.indexOf(")"));
        String value = str.substring(str.lastIndexOf("(")+1, str.lastIndexOf(")"));
        String table = str.substring(0, str.indexOf("("));

        // 进行分割
        String[] keyResult = key.split(", ");
        String[] valueResult = value.split(", ");
        String[] tableResult = table.split(" ");

        // 判断表名是否存在，数据字段是否对应
        if (fileUtils.isExists(tableResult[2], DBName) & keyResult.length == valueResult.length) {
            jsonObject.put("code", "1");
        } else {
            jsonObject.put("code", "-1");
            return jsonObject;
        }

        // 将结果转化为list
        List<String> keyList = new ArrayList<>(Arrays.asList(keyResult));
        List<String> valueList = new ArrayList<>(Arrays.asList(valueResult));

        jsonObject.put("tableName", tableResult[2]);
        jsonObject.put("operation", tableResult[0]);
        jsonObject.put("DBName", DBName);
        jsonObject.put("keyArray", keyList);
        jsonObject.put("valueArray", valueList);

        return jsonObject;
    }

    public JSONObject select(String sql, String DBName) {

        JSONObject jsonObject = new JSONObject();

        // key value 指where后的key和value
        ArrayList<String> keyList = new ArrayList<>();
        ArrayList<String> valueList = new ArrayList<>();
        // limit表示select后的限制
        ArrayList<String> limitList = new ArrayList<>();

        String tableName = "";
        String range = sql.substring(7, 8);
        // *代表查找全部
        if (range.equals("*")) {
            // 有where，说明按条件进行搜索
            if (sql.contains("where")) {
                tableName = sql.substring(0, sql.indexOf("where")).split(" ")[3];
                // select * from taleName where name = 郭俊豪 and age = 23
                List<ArrayList<String>> list = getResult(sql, jsonObject);
                if (list == null) {
                    jsonObject.put("code", -1);
                    return jsonObject;
                }
                keyList = list.get(0);
                valueList = list.get(1);
            } else {
                tableName = sql.substring(sql.lastIndexOf(" ") + 1);
            }
        } else {
            // select name, age from table where age = *;
            if (sql.contains("where")) {

                tableName = sql.substring(sql.indexOf("from"), sql.indexOf("where")).split(" ")[1];

                List<ArrayList<String>> list = getResult(sql, jsonObject);
                if (list == null) {
                    jsonObject.put("code", "-1");
                    return jsonObject;
                }
                keyList = list.get(0);
                valueList = list.get(1);
            } else {
                tableName = sql.substring(sql.lastIndexOf(" ")+1);
            }
            limitList = getLimit(sql);
        }

        jsonObject.put("tableName", tableName);
        jsonObject.put("keyList", keyList);
        jsonObject.put("valueList", valueList);
        jsonObject.put("limitList", limitList);
        jsonObject.put("DBName", DBName);
        jsonObject.put("code", "1");

        return jsonObject;
    }

    public JSONObject update(String sql, String DBName) {

        JSONObject jsonObject = new JSONObject();

        // update后的key
        ArrayList<String> limitKeyList = new ArrayList<>();
        // update后的value
        ArrayList<String> limitValueList = new ArrayList<>();
        // where中的key
        ArrayList<String> keyList = new ArrayList<>();
        // where中的value
        ArrayList<String> valueList = new ArrayList<>();
        // 查询结果 包含key，value list
        List<ArrayList<String>> list;

        String tableName = sql.split(" ")[1];

        String[] limit;

        // 包含where
        if (sql.contains("where")) {
            list = getResult(sql, jsonObject);
            keyList = list.get(0);
            valueList = list.get(1);
        } else {
            // update set name = ""
            limit = sql.substring(sql.indexOf("set")).split(" ");

        }

        jsonObject.put("tableName", tableName);
        jsonObject.put("DBName", DBName);
        jsonObject.put("keyList", keyList);
        jsonObject.put("valueList", valueList);
        jsonObject.put("limitKeyList", limitKeyList);
        jsonObject.put("limitValueList", limitValueList);

        return jsonObject;
    }

    public JSONObject delete(String sql, String DBName) {
        JSONObject jsonObject = new JSONObject();
        return jsonObject;
    }
}
