package com.example.springboot_vue.httpclient;

import cn.hutool.core.collection.CollUtil;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class Main {

    List<List<String>> rows =  CollUtil.newArrayList();

    public static void main(String[] args) throws IOException {
        String url = "http://www.12316cq.com/pages/agricultura.html?agriType=3";
        new Main().waitJS(url);
    }

    public void media(String html, int count) throws IOException {
        Document document = Jsoup.parse(html);

        Elements element = document.getElementsByClass("ncp-cont ng-scope");
        for (int i = 0; i < element.size(); i++) {
            List<String> list = new ArrayList<>();
            list.add(element.get(i).getElementsByTag("img").attr("src"));
            list.add(element.get(i).getElementsByClass("p-title ng-binding").text());
            list.add(element.get(i).getElementsByClass("p-location").text());
            list.add(element.get(i).getElementsByClass("p-info ng-binding ng-scope").text());
            rows.add(list);
        }
        System.out.println("---------------------------------" + count);
        if (count == 6) {
            System.out.println("---------------------------------开始写入");
            new TestCQUPT().write(rows, "E:\\excel.xlsx");
        }

    }

    public String waitJS(String url) throws IOException {
        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);
        java.util.logging.Logger.getLogger("org.apache.http.client").setLevel(Level.OFF);

        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setJavaScriptEnabled(true);              // 启用JS解释器，默认为true
        webClient.getOptions().setCssEnabled(false);                    // 禁用css支持
        webClient.getOptions().setThrowExceptionOnScriptError(false);   // js运行错误时，是否抛出异常
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setTimeout(10 * 1000);                   // 设置连接超时时间
        HtmlPage page = webClient.getPage(url);
        webClient.waitForBackgroundJavaScript(30 * 1000);               // 等待js后台执行30秒

        String pageAsXml = page.asXml();
        HtmlDivision pageDiv = (HtmlDivision)page.getElementById("page");
        DomNodeList<HtmlElement> elements = pageDiv.getElementsByTagName("a");

        int count = 2;
        // 先调用，写第一页
        media(pageAsXml, count);
        while (count != 7) {
            for (HtmlElement a : elements) {
                if (a.asNormalizedText().equals("下一页")) {
                    page = a.click();
                    webClient.waitForBackgroundJavaScript(30 * 1000);
                    pageAsXml = page.asXml();
                    media(pageAsXml, count);
                    count++;
                }
            }
        }

        return pageAsXml;
    }
}
