package com.example.springboot_vue.httpclient;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.springboot_vue.mapper.CityMapper;
import com.example.springboot_vue.pojo.city.City;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

@Service
public class GetData {

    @Autowired
    CityMapper cityMapper;

    public static void main(String[] args) {

//        new GetData().myMain();

    }

    // 封装json格式的数据
    public void getCity() {

        ArrayList<City> list = cityMapper.selCity();
        System.out.println(list.size());

        JSONArray jsonArray = new JSONArray();

        String provinceName = list.get(0).getName();

        for (int i = 1; i < list.size(); i++) {
            if (list.get(i).getParentName().equals("1")) {
                provinceName = list.get(i).getName();
                continue;
            }
            String test = list.get(i).getNumber();

            String[] values = test.split("万");

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", list.get(i).getName());
            jsonObject.put("value", Double.parseDouble(values[0]) * 10000);

            jsonArray.add(jsonObject);

            if (i + 1 < list.size() && list.get(i + 1).getParentName().equals("1")) {
                System.out.println("changeName");
                write(jsonArray.toJSONString(), "D:\\map_data\\" + provinceName + ".json");
                jsonArray = new JSONArray();
            }
        }

        write(jsonArray.toJSONString(), "D:\\map_data\\" + provinceName + ".json");
    }

    // 生成json文件
    public void write(String str, String path) {
        try {
            File file = new File(path);        //文件路径（路径+文件名）
            if (!file.exists()) {    //文件不存在则创建文件，先创建目录
                File dir = new File(file.getParent());
                dir.mkdirs();
                file.createNewFile();
            }

            FileOutputStream outStream = new FileOutputStream(file, true);    //文件输出流用于将数据写入文件
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outStream, StandardCharsets.UTF_8);
            outputStreamWriter.write(str);
            outputStreamWriter.close();    //关闭文件输出流
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void myMain() {
        String[] provinceName = {"广东", "山东", "河南", "江苏", "四川", "河北", "湖南", "浙江", "安徽", "湖北", "广西",
                "云南", "江西", "辽宁", "福建", "陕西", "贵州", "山西", "重庆", "黑龙江", "新疆", "甘肃", "上海", "吉林",
                "内蒙古", "北京", "天津", "海南", "宁夏", "青海", "西藏"};

        for (int i = 0; i < provinceName.length; i++) {
            String url = "https://www.hongheiku.com/tag/" + provinceName[i];
            test(url);
        }

//        test("https://www.hongheiku.com/tag/广东");
    }

    // 发请求，拿到html
    public String test(String url) {

        // 生成httpClient，相当于打开浏览器
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;

        // 创建get请求，相当于在浏览器地址栏输入 网址
        HttpGet request = new HttpGet(url);
        request.setHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.0.0 Safari/537.36");

        try {
            // 发去http请求
            response = httpClient.execute(request);
            // 判断相应状态
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                // 获取响应内容
                HttpEntity httpEntity = response.getEntity();

                String html = EntityUtils.toString(httpEntity, "utf-8");

//                System.out.println("在response header中获取的内容" + Arrays.toString(response.getHeaders("Content-Range")));
//                analysisHtml(html);
                return html;
            } else {
                // 请求不成功
                System.out.println(EntityUtils.toString(response.getEntity(), "utf-8"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            HttpClientUtils.closeQuietly(response);
            HttpClientUtils.closeQuietly(httpClient);
        }

        return null;
    }

    // 解析html
    public void analysisHtml(String html) {
        Document document = Jsoup.parse(html);
        Elements element = document.getElementsByTag("tr");

        // 用于存储名称以及对应地区人数
        ArrayList<String> name = new ArrayList<>();
        ArrayList<String> number = new ArrayList<>();

        // 省的数据
        JSONArray provinceJson = new JSONArray();

        for (int i = 2; i < element.size(); i++) {

            String[] arr = element.get(i).getElementsByTag("td").text().split(" ");
            String str = element.get(i).getElementsByTag("a").attr("href");

            // 处理子链接相关的内容
            if (!str.equals("") && i != 2) {
                // 得到子html
                String childrenHtml = test(str);
                // 解析子html
                Document childrenDocument = Jsoup.parse(childrenHtml);
                // 获取对应table
                Elements elements = childrenDocument.getElementsByTag("table");
                int size = elements.size();

                int index = 0;
                if (size == 4) {
                    index = 3;
                } else {
                    index = 2;
                }
                if (size == 5) {
                    index = 4;
                }

                // 阳泉市特殊处理
                if (str.equals("https://www.hongheiku.com/shanxi/295.html")) {
                    index = 2;
                }

                System.out.println(elements.size());
                if (elements.size() > 0) {
                    // table具体值
                    String[] value = elements.get(index).getElementsByTag("td").text().split(" ");
                    System.out.println(elements.get(index));

                    if (value.length > 5) {
                        String fileName = value[5];
                        JSONArray jsonArray = new JSONArray();

                        try {
                            // 特殊城市
                            Integer.valueOf(fileName);
                            fileName = value[4];
                            for (int j = 8; j < value.length; j += 4) {
                                JSONObject jsonObject = new JSONObject();
                                jsonObject.put("name", value[j]);

                                String doubleValue = value[j + 1];
                                double trueValue = Double.parseDouble(doubleValue);
                                jsonObject.put("value", trueValue / 10000.00);

                                jsonArray.add(jsonObject);
                            }
                        } catch (Exception e) {
                            // 通用城市
                            for (int j = 10; j < value.length; j += 5) {
                                JSONObject jsonObject = new JSONObject();
                                jsonObject.put("name", value[j]);

                                String doubleValue = value[j + 1];
                                double trueValue = Double.parseDouble(doubleValue);
                                jsonObject.put("value", trueValue / 10000.00);
                                jsonArray.add(jsonObject);
                            }
                        }

                        write(jsonArray.toJSONString(), "D:\\map_data\\" + fileName + ".json");
                    }
                }
            }
        }
//        cityMapper.insertCityAndNumber(name, number);
    }
}
