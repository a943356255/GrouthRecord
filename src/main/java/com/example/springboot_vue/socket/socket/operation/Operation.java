package com.example.springboot_vue.socket.socket.operation;

import com.alibaba.fastjson.JSONObject;
import com.example.springboot_vue.socket.utils.FileUtils;
import com.example.springboot_vue.socket.utils.StringUtils;

public class Operation {

    FileUtils fileUtils = new FileUtils();

    StringUtils stringUtils = new StringUtils();

    // insert into table
    public boolean operation(String sql, String DBName) {

        String firstWord = sql.substring(0, sql.indexOf(" "));

        JSONObject jsonObject;

        // 通过第一个单词判断进行的什么操作，针对不同操作做不同处理
        if ("insert".equals(firstWord)) {
            // 将sql分割，取到对应的数据
            jsonObject = stringUtils.insert(sql, DBName);
            // -1表示sql有问题
            if (jsonObject.get("code").equals("-1")) {
                return false;
            }
            // 执行具体的处理
            insert(jsonObject);
        } else if ("select".equals(firstWord)) {
            jsonObject = stringUtils.select(sql, DBName);
            // -1表示sql有问题
            if (jsonObject.get("code").equals("-1")) {
                return false;
            }
            select(jsonObject);
        } else if ("update".equals(firstWord)) {
            jsonObject = stringUtils.update(sql, DBName);
            // -1表示sql有问题
            if (jsonObject.get("code").equals("-1")) {
                return false;
            }
            update(jsonObject);
        } else if ("delete".equals(firstWord)) {
            jsonObject = stringUtils.delete(sql, DBName);
            // -1表示sql有问题
            if (jsonObject.get("code").equals("-1")) {
                return false;
            }
            delete(jsonObject);
        } else {
            // 如果不是上述四种，则表示有问题
            return false;
        }

        return true;
    }

    public void insert(JSONObject value) {
        fileUtils.insert(value);
    }

    public void select(JSONObject value) {
        fileUtils.select(value);
    }

    public void update(JSONObject value) {

    }

    public void delete(JSONObject value) {

    }


}
