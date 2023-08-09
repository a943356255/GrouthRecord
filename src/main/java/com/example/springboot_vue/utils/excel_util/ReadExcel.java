package com.example.springboot_vue.utils.excel_util;

import com.example.springboot_vue.pojo.TestUser;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReadExcel {

    public static void main(String[] args) {
        String path = "D:\\git\\单位信息.xlsx";
        List<String> list = new ArrayList<>();
        int size = 16;
        for (int i = 0; i < size; i++) {
            list.add(String.valueOf(i));
        }

        var res = readExcelAndInsertIntoDB(path, list);
        for (Map<String, String> re : res) {
            System.out.println(re.toString());
        }
    }

    public static void disposeXlsx(String path, ArrayList<TestUser> list) {
        try {
            // 创建工作簿对象
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(new FileInputStream(path));
            // 获取工作簿下sheet的个数
            int sheetNum = xssfWorkbook.getNumberOfSheets();
            System.out.println("该excel文件中总共有：" + sheetNum + "个sheet");

            // 遍历工作簿中的所有数据
            for (int i = 0; i < sheetNum; i++) {
                //读取第i个工作表
                System.out.println("读取第" + (i + 1) + "个sheet");
                XSSFSheet sheet = xssfWorkbook.getSheetAt(i);
                // 获取最后一行的num，即总行数。此处从0开始
                int maxRow = sheet.getLastRowNum();
                // 这里是读取excel的每一行
                for (int row = 0; row <= maxRow; row++) {
                    if (row == 0) {
                        continue;
                    }
                    // 获取最后单元格num，即总单元格数 ***注意：此处从1开始计数***
                    int maxRol = sheet.getRow(row).getLastCellNum();
                    // 这里是读取每一行的每一列的数据
                    for (int rol = 0; rol < maxRol; rol++) {
                        System.out.println(sheet.getRow(row).getCell(rol));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Map<String, String>> readExcelAndInsertIntoDB(String path, List<String> list) {
        ArrayList<Map<String, String>> res = new ArrayList<>();
        FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(path);
            // 创建工作簿对象
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(fileInputStream);
            // 获取工作簿下sheet的个数
            int sheetNum = xssfWorkbook.getNumberOfSheets();

            // 遍历工作簿中的所有数据
            for (int i = 0; i < sheetNum; i++) {
                // 读取第i个工作表
                XSSFSheet sheet = xssfWorkbook.getSheetAt(i);
                // 获取最后一行的num，即总行数。此处从0开始
                int maxRow = sheet.getLastRowNum();
                // 这里是读取excel的每一行
                for (int row = 0; row <= maxRow; row++) {
                    // 每一行需要用一个map来存储，
                    Map<String, String> map = new HashMap<>();
                    if (row == 0) {
                        continue;
                    }

                    // 获取每一行有多少列
                    int maxRol = sheet.getRow(row).getLastCellNum();
                    for (int rol = 0; rol < maxRol; rol++) {
                        map.put(list.get(rol), String.valueOf(sheet.getRow(row).getCell(rol)));
                    }
                    res.add(map);
                }
            }
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return res;
    }

    public static void disposeXls(String path) {
        try {
            //创建工作簿
            HSSFWorkbook hssfWorkbook = new HSSFWorkbook(new FileInputStream(path));
            //获取工作簿下sheet的个数
            int sheetNum = hssfWorkbook.getNumberOfSheets();
            System.out.println("该excel文件中总共有：" + sheetNum + "个sheet");

            //遍历工作簿中的所有数据
            for (int i = 0; i < sheetNum; i++) {
                //读取第i个工作表
                System.out.println("读取第" + (i + 1) + "个sheet");
                HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(i);
                //获取最后一行的num，即总行数。此处从0开始
                int maxRow = hssfSheet.getLastRowNum();
                for (int row = 0; row <= maxRow; row++) {
                    //获取最后单元格num，即总单元格数 ***注意：此处从1开始计数***
                    int maxRol = hssfSheet.getRow(row).getLastCellNum();
                    System.out.println("--------第" + row + "行的数据如下--------");
                    for (int rol = 0; rol < maxRol; rol++) {
                        System.out.print(hssfSheet.getRow(row).getCell(rol));
                    }
                    System.out.println();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
