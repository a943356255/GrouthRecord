package com.example.springboot_vue.httpclient;

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

import java.io.*;

public class GetImage {

    public static void main(String[] args) throws IOException, InterruptedException {
        String url = "https://kanmeitu1.cc/p/55306.html";
        GetImage getImage = new GetImage();
        getImage.getImageUrl(url);
    }

    public void getImageUrl(String url) throws InterruptedException {
        String html = sendRequest(url);
        Document document = Jsoup.parse(html);
        Elements element = document.getElementsByTag("img");
        String[] file = document.getElementsByClass("detail_title").text().split("/");
        for (org.jsoup.nodes.Element value : element) {
            String imgUrl = value.attr("src");
            String[] filename = imgUrl.split("/");
            if (!imgUrl.startsWith("https")) {
                continue;
            }
            download(imgUrl, filename[filename.length - 1], file[0]);
            Thread.sleep(300);
        }
    }

    public static void download(String url, String filename, String file) {
        // 存储路径
        String pathCreate = "D:\\迅雷下载\\" + file;

        // 创建文件夹
        File make = new File(pathCreate);
        if (!make.exists()) {
            make.mkdirs();
        }

        HttpGet get = new HttpGet(url);
        // 设置请求头
        get.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.0.0 Safari/537.36");
        HttpEntity entity = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try (CloseableHttpResponse response = httpClient.execute(get)) {
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                entity = response.getEntity();
                if (entity != null) {
                    File picFile = new File(pathCreate  + "\\" + filename);
                    try (OutputStream out = new BufferedOutputStream(new FileOutputStream(picFile))) {
                        entity.writeTo(out);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭实体，关于 httpClient 的关闭资源，有点不太了解。
                EntityUtils.consume(entity);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String sendRequest(String url) {

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
                return EntityUtils.toString(httpEntity, "utf-8");
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
}
