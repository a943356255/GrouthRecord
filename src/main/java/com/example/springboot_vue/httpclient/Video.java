package com.example.springboot_vue.httpclient;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.CookieManager;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.Cookie;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;

import javax.net.ssl.SSLException;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.Set;

public class Video {

    public static void main(String[] args) throws IOException {
        Video video = new Video();
        String url = "https://www.xvideos.com/video57683933/she_loves_when_i_fuck_her_tight_ass_-_amateur_creampie#";
        video.test(url);
    }

    public String test(String url) throws IOException {
        final WebClient webClient = new WebClient(BrowserVersion.CHROME, "127.0.0.1", 7890);

        webClient.getCookieManager().setCookiesEnabled(true);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setActiveXNative(false);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(true);

        webClient.addCookie("82f4e7005fab1c29vY4zpKel2xLe4V4oddunHvNUihnRiDK1KuUOBwbje-E-cwKoeXIsZ2GkY7bO4Em9RLcfMSj6TF95yuQfRPd1ZH733BSnHdUIuSDPjTBqUDwd3XXPO7LWGCkuEemyjtRf_6FWEXnvKtMyJTTofACtV7kvgKw_6c7iBbcInFZUpTRxh1MZnnpi8RWsBya7UvQtoesu_gJYbQdDw5wjWKqOh9eQ0DZfcR__y274NWALcBsP6tXFgla0H8Yr8b9Mx_nziwtZhBc-bhwCJ5u7XSnkR9BKCYZWnDQ4F9zfTpAQhS5k_-YPzUxHq1P8drqMR8Ie6TKytEvsGJ1ueJkRn3kSz3xqt5e68OMtUyiDJ9KO9V16lcm_lVk-iXQceRipQFxi4GGNJVxBtp-m9JYI2ric2tmT7zXyFzSUG4UbaxmUnTUapyQ8pbUxgtfoxCu5GzSM0hHpewJ3_uZjwg2KcXOD9YjA9bidORfSHfGYm0G3MGw%3D", new URL("https://www.xvideos.com/video57683933/she_loves_when_i_fuck_her_tight_ass_-_amateur_creampie#"), "www.xvideos.com");
        HtmlPage page = webClient.getPage(url);
        webClient.waitForBackgroundJavaScript(5000);

        String pageXml = page.asXml();
        Document document = Jsoup.parse(pageXml);
        Elements elements = document.getElementsByTag("script");
        for (int i = 0; i < elements.size(); i++) {
            System.out.println(elements.get(i).text());
            System.out.println();
            System.out.println();
        }
        System.out.println(elements.toString());
        return pageXml;
    }
}
