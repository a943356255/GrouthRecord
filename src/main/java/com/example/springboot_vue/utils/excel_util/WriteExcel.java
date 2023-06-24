package com.example.springboot_vue.utils.excel_util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.handler.WriteHandler;
import com.alibaba.excel.write.metadata.WriteSheet;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

public class WriteExcel {

    public static void main(String[] args) throws IOException {
        String[] headArr = {"first", "second", "third"};
        String[] fieldArr = {"first", "second", "third"};
        Map<String, String> map = new HashMap<>();
        map.put("first", "123");
        map.put("second", "1234");
        map.put("third", "1235");
        List<Map<String, String>> list = new ArrayList<>();
        list.add(map);

        Map<String, String> map1 = new HashMap<>();
        map1.put("first", "12223");
        map1.put("second", "123433");
        map1.put("third", "123544");
        list.add(map1);

        OutputStream outputStream = new FileOutputStream("D:\\git\\test_t.xlsx");
        new WriteExcel().write(outputStream, headArr, "测试名称", list, fieldArr);
    }

    //不创建对象的导出
    public void write(OutputStream out, String[] headArr, String sheetName, List<Map<String, String>> dataList, String[] fieldArr) throws IOException {
        EasyExcel.write(out).head(head(headArr)).sheet(sheetName).doWrite(dataList(dataList, fieldArr));
    }

    private List<List<String>> head(String[] headMap) {
        List<List<String>> list = new ArrayList<>();
        for (String head : headMap) {
            List<String> headList = new ArrayList<>();
            headList.add(head);
            list.add(headList);
        }
        return list;
    }

    // 设置导出的数据内容
    private List<List<Object>> dataList(List<Map<String, String>> dataList, String[] dataStrMap) {
        List<List<Object>> list = new ArrayList<>();
        dataList.forEach(map->{
            List<Object> data = new ArrayList<>();
            for (String s : dataStrMap) {
                data.add(map.get(s));
            }
            list.add(data);
        });
        return list;
    }
}