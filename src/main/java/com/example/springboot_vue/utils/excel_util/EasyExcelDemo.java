package com.example.springboot_vue.utils.excel_util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.example.springboot_vue.mapper.CityMapper;
import com.example.springboot_vue.pojo.city.City;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.*;

public class EasyExcelDemo {

    public static void main(String[] args) {
        EasyExcelDemo easyExcelDemo = new EasyExcelDemo();
//        easyExcelDemo.write();
//        easyExcelDemo.readExcel();
    }

    public void readExcel(CityMapper cityMapper, CountDownLatch latch) {
        int total = cityMapper.getTotalData();
        String fileName = "D:\\bilibili_video\\test.xlsx";
        EasyExcel.read(fileName, City.class, new CityDataListener(cityMapper, total)).doReadAll();
    }

    public void threadReadExcel(CityMapper cityMapper, String sheetName) {
        String fileName = "D:\\bilibili_video\\test.xlsx";
        EasyExcel.read(fileName, City.class, new OtherCityListener(cityMapper)).sheet(sheetName).doRead();
    }

    public void write(List<City> cities, int i, String filename) {
        String fileName = "D:\\bilibili_video\\test1.xlsx";
        ExcelWriter excelWriter = EasyExcel.write(filename).build();
        WriteSheet writeSheet = EasyExcel.writerSheet(i, "sheet_" + i).head(City.class).build();
        excelWriter.write(cities, writeSheet);
        excelWriter.finish();
    }

    public List<City> getCity(int index) {
        List<City> list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            City city = new City(UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString());
            list.add(city);
        }
        return list;
    }

}
