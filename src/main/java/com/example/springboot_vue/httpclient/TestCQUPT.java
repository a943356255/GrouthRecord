package com.example.springboot_vue.httpclient;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;


public class TestCQUPT {

    List<List<String>> rows =  CollUtil.newArrayList();

    public static void main(String[] args) throws IOException {
        String path = "E:\\wx.xlsx";
        disposeXlsx(path);
//        String url = "https://mp.weixin.qq.com/s/U9gIDWymFNyIGaU9qB68pQ";
//        new TestCQUPT().testWX(url);
    }

    public String testWX(String url) throws IOException {
//        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);
//        java.util.logging.Logger.getLogger("org.apache.http.client").setLevel(Level.OFF);
//
//        WebClient webClient = new WebClient(BrowserVersion.CHROME);
//        webClient.getOptions().setJavaScriptEnabled(true);              // 启用JS解释器，默认为true
//        webClient.getOptions().setCssEnabled(false);                    // 禁用css支持
//        webClient.getOptions().setThrowExceptionOnScriptError(false);   // js运行错误时，是否抛出异常
//        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
//        webClient.getOptions().setTimeout(10 * 1000);                   // 设置连接超时时间
//        HtmlPage page = webClient.getPage(url);
//        webClient.waitForBackgroundJavaScript(30 * 1000);               // 等待js后台执行30秒
//
//        String pageAsXml = page.asXml();

//        Document document = Jsoup.parse(pageAsXml);
//        return document.getElementById("publish_time").text();
//        System.out.println(document.getElementsById("publish_time").text());
        return "";
    }

    public String getDate(String url) {
        String html = new GetData().test(url);
        Document document = Jsoup.parse(html);

        Elements element = document.getElementsByTag("table");
        Element element1 = element.get(0).getElementById("clickcount").parent();
        String[] arr = element1.text().split("：");
        return arr[1].split(" ")[0];
    }

    public static void disposeXlsx(String path) {
        TestCQUPT testCQUPT = new TestCQUPT();
        try {
            // 创建工作簿对象
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(new FileInputStream(path));
            // 获取工作簿下sheet的个数
            int sheetNum = xssfWorkbook.getNumberOfSheets();

            // 遍历工作簿中的所有数据
            for (int i = 0; i < sheetNum; i++) {
                XSSFSheet sheet = xssfWorkbook.getSheetAt(i);
                // 获取最后一行的num，即总行数。此处从0开始
                int maxRow = sheet.getLastRowNum();
                for (int row = 0; row <= maxRow; row++) {
                    // 获取最后单元格num，即总单元格数 ***注意：此处从1开始计数***
                    int maxRol = sheet.getRow(row).getLastCellNum();
                    List<String> list = CollUtil.newArrayList();
                    String time = null;
                    for (int rol = 0; rol < maxRol; rol++) {
                        String url = sheet.getRow(row).getCell(rol).toString();
                        list.add(testCQUPT.testWX(url));
                    }
                    testCQUPT.rows.add(list);
                }
            }
//            testCQUPT.write();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(List<List<String>> list, String path) {
        //通过工具类创建writer
        ExcelWriter writer = ExcelUtil.getWriter(path);
        // 通过构造方法创建writer
        // ExcelWriter writer = new ExcelWriter("d:/writeTest.xls");

        // 跳过当前行，既第一行，非必须，在此演示用
        writer.passCurrentRow();

        // 合并单元格后的标题行，使用默认标题样式
//        writer.merge(rows.get(0).size() - 1, "测试标题");
        // 一次性写出内容，强制输出标题
        writer.write(list, true);
        // 关闭writer，释放内存
        writer.close();
    }
}
