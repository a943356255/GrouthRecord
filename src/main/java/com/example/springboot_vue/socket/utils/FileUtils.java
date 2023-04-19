package com.example.springboot_vue.socket.utils;

import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileUtils {

    public static String path = "D:\\mysql\\";

    public boolean isDir(String name) {
        return new File(path + name).isDirectory();
    }

    public boolean isExists(String name, String DBName) {
        return new File(path + "\\" + DBName + "\\" + name + ".txt").exists();
    }

    public String getPath(JSONObject jsonObject) {

        String tableName = (String) jsonObject.get("tableName");
        String DBName = (String) jsonObject.get("DBName");

        return path + "\\" + DBName + "\\" + tableName + ".txt";
    }

    public String readLine(int line, String path) {

        String str = "";
        try {
            File file = new File(path);
            FileReader fileReader = new FileReader(file);
            // 可以执行读取某一行
            LineNumberReader lineNumberReader = new LineNumberReader(fileReader);
            // 设置读取哪一行
            lineNumberReader.setLineNumber(line);
            // 执行读取
            str = lineNumberReader.readLine();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static void writeFile(String filepath, String str) {

        File file = new File(filepath);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file, true);
            fileOutputStream.write((str+"\t\n").getBytes());
            fileOutputStream.close();

//            FileWriter fileWriter = new FileWriter(file);
//            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
//
//            bufferedWriter.close();
//            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static JSONObject readFile(String filepath, List<String> keyList, List<String> valueList,
                                      List<String> limitList) {
        // 用户返回的结果集
        JSONObject returnJSON = new JSONObject();
        // 用于中间判断的结果集
        JSONObject jsonObject;

        List<JSONObject> list = new ArrayList<>();
        List<String> returnList = new ArrayList<>();

        File file = new File(filepath);
        String str;
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            // 提前读一行，跳过第一行
            bufferedReader.readLine();
            while ((str = bufferedReader.readLine()) != null) {

                System.out.println("读取的结果为：" + str);

                jsonObject = JSONObject.parseObject(str);
                int index = 0;
                // 说明有where
                if (keyList.size() > 0) {
                    for (int i = 0; i < keyList.size(); i++) {
                        if (jsonObject.get(keyList.get(i)).equals(valueList.get(i))) {
                            index++;
                        }
                    }
                    // 满足所有的检索条件
                    if (index == keyList.size()) {
                        list.add(jsonObject);
                    }
                } else {
                    list.add(jsonObject);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // limitList不为空，说明有具体的限制，不是查询全部
        if (limitList != null) {
            for (JSONObject object : list) {
                for (String s : limitList) {
                    returnList.add((String) object.get(s));
                }
            }
            returnJSON.put("data", returnList);
        } else {
            returnJSON.put("data", list);
        }

        for (String s : returnList) {
            System.out.println(s);
        }

        return returnJSON;
    }

    public List<String> getField(JSONObject jsonObject) {
        // 要插入文件的路径
        String filepath = getPath(jsonObject);
        String result = readLine(0, filepath);

        // 返回分割后的结果
        return Arrays.asList(result.split(" "));
    }

    public boolean insert(JSONObject jsonObject) {

        JSONObject valueJSON = new JSONObject();

        // 获取到文件中的字段名
        List<String> list = getField(jsonObject);

        // 要写入的路径
        String filepath = getPath(jsonObject);

        // 获取到要写入的内容
        ArrayList<String> keyList = (ArrayList<String>) jsonObject.get("keyArray");
        ArrayList<String> valueList = (ArrayList<String>) jsonObject.get("valueArray");

        if (!list.containsAll(keyList)) {
            return false;
        }

        int index = 0;
        // 循环赋值，如果keyList里有，就赋值，否则置空
        for (String s : list) {
            if (list.contains(keyList.get(index))) {
                valueJSON.put(keyList.get(index), valueList.get(index));
                index++;
                if (index == valueList.size()) {
                    keyList.add("null");
                }
            } else {
                valueJSON.put(s, "null");
            }
        }

        // 转换成string返回
        String str = valueJSON.toString();

        writeFile(filepath, str);

        return true;
    }

    public JSONObject select(JSONObject jsonObject) {
        // 要返回给用户的结果集
        JSONObject returnJSON = new JSONObject();

        // 获取到文件中的字段名
        List<String> fieldList = getField(jsonObject);

        // 要写入文件的路径
        String filepath = getPath(jsonObject);

        // 要进行筛选的字段
        List<String> keyList = (List<String>) jsonObject.get("keyList");
        List<String> valueList = (List<String>) jsonObject.get("valueList");
        List<String> limitList = (List<String>) jsonObject.get("limitList");

        // 说明条件中的字段不存在
        if (!fieldList.containsAll(keyList)) {
            returnJSON.put("code", "-1");
        }

        // 获取到从文件中读取的结果
        returnJSON = readFile(filepath, keyList, valueList, limitList);

        return returnJSON;
    }

}
