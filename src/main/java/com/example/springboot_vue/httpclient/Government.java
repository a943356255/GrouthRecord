package com.example.springboot_vue.httpclient;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.net.ssl.SSLContext;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Government {

    CloseableHttpClient httpClient = createClient();

    public static void main(String[] args) throws IOException {
        Government government = new Government();
        String temp = "https://www.tanghe.gov.cn/portal/search/index/keyword/%E6%8B%9B%E8%81%98/p/";
        String url = "https://www.tanghe.gov.cn";
        int size = 2;
        for (int i = 1; i <= size; i++) {
            String tempUrl = temp + i + ".html";
            List<String> res = government.getUrl(tempUrl);
            for (int j = 0; j < res.size(); j++) {
                String html = government.sendRequest(url + res.get(j));
                government.handleData(html, "D:\\bilibili_video");
            }
        }
    }

    public List<String> getUrl(String url) {
        String html = sendRequest(url);
        Document document = Jsoup.parse(html);
        Elements elementsByClass = document.getElementsByClass("czzxx-right-min");
        List<String> list = new ArrayList<>();
        for (org.jsoup.nodes.Element byClass : elementsByClass) {
            Elements elements = byClass.getElementsByTag("a");
            for (org.jsoup.nodes.Element element : elements) {
                list.add(element.getElementsByTag("a").attr("href"));
            }
        }
        return list;
    }

    // 解析处理
    public void handleData(String html, String filepath) throws IOException {
        Document document = Jsoup.parse(html);
        Elements elementsByClass = document.getElementsByClass("web-tynr-box");
        String h1 = elementsByClass.get(0).getElementsByTag("h1").text();
        CloseableHttpClient httpClient = createClient();
        for (org.jsoup.nodes.Element byClass : elementsByClass) {
            Elements elements = byClass.getElementsByTag("a");
            for (org.jsoup.nodes.Element element : elements) {
                // 获取路径和文件名
                String href = element.attr("href");
                String filename = element.attr("title");
                DataOutputStream out;
                if (href.contains("xlsx")) {
                    HttpGet optionRequest = new HttpGet(href);

                    optionRequest.setHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");
                    optionRequest.setHeader("accept", "*/*");
                    optionRequest.setHeader("accept-language", "zh-CN,zh;q=0.9");
                    optionRequest.setHeader("accept-encoding", "identity");
                    optionRequest.setHeader("referer", "https://www.tanghe.gov.cn/zcwjjtz/70686.html");
                    optionRequest.setHeader("sec-ch-ua", "Not_A Brand\";v=\"8\", \"Chromium\";v=\"120\", \"Google Chrome\";v=\"120");

                    CloseableHttpResponse response = httpClient.execute(optionRequest);
                    HttpEntity entity = response.getEntity();
                    byte[] bytes = EntityUtils.toByteArray(entity);
                    out = new DataOutputStream(new FileOutputStream(filepath + "\\" + h1 + filename + ".xlsx"));
                    out.write(bytes);
                }
            }
        }

        httpClient.close();
    }

    // 发起请求，拿到对应的html元素
    public String sendRequest(String url) {
        // 生成httpClient，相当于打开浏览器
        CloseableHttpResponse response = null;
        // 创建get请求，相当于在浏览器地址栏输入 网址
        HttpGet optionRequest = new HttpGet(url);
        optionRequest.setHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");
        CloseableHttpClient httpClient = createClient();
        try {
            // 发去http请求
            response = httpClient.execute(optionRequest);
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

    // 创建请求，忽略证书
    public CloseableHttpClient createClient() {
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, (x509Certificates, s) -> true).build();
            SSLConnectionSocketFactory sslFactory = new SSLConnectionSocketFactory(sslContext);
            return HttpClients.custom().setSSLSocketFactory(sslFactory).build();
        } catch (Exception ignored) {
        }
        return HttpClients.createDefault();
    }

}
