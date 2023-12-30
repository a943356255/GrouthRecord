package com.example.springboot_vue.utils.excel_util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.example.springboot_vue.pojo.city.City;

import java.util.ArrayList;
import java.util.List;

public class EasyExcelDemo {

    public static void main(String[] args) {
        EasyExcelDemo easyExcelDemo = new EasyExcelDemo();
        easyExcelDemo.write();
    }

    public void readExcel() {
        String fileName = "D:\\bilibili_video\\test.xlsx";
    }

    public void write() {
        String fileName = "D:\\bilibili_video\\test.xlsx";
        ExcelWriter excelWriter = EasyExcel.write(fileName).build();

        for (int i = 0; i < 70; i++) {
            WriteSheet writeSheet = EasyExcel.writerSheet(i, "sheet_" + i).head(City.class).build();
            List<City> cities = getCity(i);
            excelWriter.write(cities, writeSheet);
        }

        excelWriter.finish();
    }

    public List<City> getCity(int index) {
        List<City> list = new ArrayList<>();
        for (int i = 0; i < 30000; i++) {
            City city = new City("1" + i + index, "2" + i + index, "3" + i + index);
            list.add(city);
        }
        return list;
    }

}
