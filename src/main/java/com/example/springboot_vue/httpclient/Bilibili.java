package com.example.springboot_vue.httpclient;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Bilibili {

//    String url = "https://xy61x147x214x3xy.mcdn.bilivideo.cn:4483/upgcxcode/10/28/1382242810/1382242810-1-100113.m4s?e=ig8euxZM2rNcNbdlhoNvNC8BqJIzNbfqXBvEqxTEto8BTrNvN0GvT90W5JZMkX_YN0MvXg8gNEV4NC8xNEV4N03eN0B5tZlqNxTEto8BTrNvNeZVuJ10Kj_g2UB02J0mN0B5tZlqNCNEto8BTrNvNC7MTX502C8f2jmMQJ6mqF2fka1mqx6gqj0eN0B599M=&uipk=5&nbs=1&deadline=1703674069&gen=playurlv2&os=mcdn&oi=2102830557&trid=00004cb2d0f39f104edba6730c3966de171bu&mid=354054152&platform=pc&upsig=cd9545d6fb3e84bf1f98591b373f32d4&uparams=e,uipk,nbs,deadline,gen,os,oi,trid,mid,platform&mcdnid=2003583&bvc=vod&nettype=0&orderid=0,3&buvid=271AE0B2-C6BE-1964-F58A-C355CAA2007549009infoc&build=0&f=u_0_0&agrr=1&bw=120320&logo=A0000002";
    String url = "https://upos-sz-mirror08h.bilivideo.com/upgcxcode/10/28/1382242810/1382242810-1-100113.m4s?e=ig8euxZM2rNcNbdlhoNvNC8BqJIzNbfqXBvEqxTEto8BTrNvN0GvT90W5JZMkX_YN0MvXg8gNEV4NC8xNEV4N03eN0B5tZlqNxTEto8BTrNvNeZVuJ10Kj_g2UB02J0mN0B5tZlqNCNEto8BTrNvNC7MTX502C8f2jmMQJ6mqF2fka1mqx6gqj0eN0B599M=&uipk=5&nbs=1&deadline=1703674235&gen=playurlv2&os=08hbv&oi=2102830557&trid=fa723ed588774adbb21f5b6a97e965aau&mid=354054152&platform=pc&upsig=13d4f5bb9d977bc90984dd70e92de1b6&uparams=e,uipk,nbs,deadline,gen,os,oi,trid,mid,platform&bvc=vod&nettype=0&orderid=1,3&buvid=271AE0B2-C6BE-1964-F58A-C355CAA2007549009infoc&build=0&f=u_0_0&agrr=1&bw=120320&logo=40000000";
    public static void main(String[] args) throws IOException {
        String url = "https://www.bilibili.com/video/BV1Re411B7xc/?spm_id_from=333.880.my_history.page.click&vd_source=47073b9a4320333c2e8d3544a7fc9448";

        Bilibili bilibili = new Bilibili();
        String html = bilibili.test(url);
//        System.out.println(html);
//        bilibili.getVideo();
    }

    public String test(String url) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        HttpGet optionRequest = new HttpGet(url);
        optionRequest.setHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.0.0 Safari/537.36");
        optionRequest.setHeader("Cookie", "buvid_fp_plain=undefined; DedeUserID=354054152; DedeUserID__ckMd5=ab3f955a69f0c254; i-wanna-go-back=-1; CURRENT_FNVAL=4048; CURRENT_QUALITY=80; LIVE_BUVID=AUTO7116779145436655; header_theme_version=CLOSE; CURRENT_PID=e01f1960-cd47-11ed-bc0b-dba20274edf5; hit-new-style-dyn=0; hit-dyn-v2=1; FEED_LIVE_VERSION=V8; home_feed_column=5; enable_web_push=DISABLE; browser_resolution=1707-841; _uuid=BDF35129-48C8-1552-AC16-FD2106512C88159435infoc; PVID=1; buvid3=271AE0B2-C6BE-1964-F58A-C355CAA2007549009infoc; b_nut=1701410949; buvid4=2CA479B0-4EC2-9EE4-6636-B3D8FCDF42E831859-022102709-Ik93gWcclu8VL3w58Fu%2BNT7HGmP0YmRnw1Zr4hGkskg%3D; fingerprint=cf4f14ba92ed829c712c0ec979b09b1b; SESSDATA=76f44461%2C1719201364%2C74bed%2Ac2CjCBv28vfJQt-irslVWimxO7bGWnx_NCtefS57ImlZwtOJj8aRnm52TH-rLoJBSEn-ASVnNXTjltWXVOX3lZZThBa0RLdUNLTm1FWTlWcEJXSnF5QVVGemFsN0U3WHVFX2NOV3lPQTZJclhsc0dLMVVJbTRnWktqLTdvaURLMTN3ODJhN01FRHdBIIEC; bili_jct=40f7a5563fb5f9dcb36e53afade08988; bili_ticket=eyJhbGciOiJIUzI1NiIsImtpZCI6InMwMyIsInR5cCI6IkpXVCJ9.eyJleHAiOjE3MDM5MDg1NzEsImlhdCI6MTcwMzY0OTMxMSwicGx0IjotMX0.Ko_ApANK46PCz_8sINsOILeXKvaVsXFI8sYeAiJAQgo; bili_ticket_expires=1703908511; rpdid=|(ku|k)J|ll~0J'u~|JYRmJ~J; sid=7yp24md3; b_lsid=A696BDA5_18CAA25FD40; bp_video_offset_354054152=879698263917199363; buvid_fp=271AE0B2-C6BE-1964-F58A-C355CAA2007549009infoc");

        response = httpClient.execute(optionRequest);

        HttpEntity httpEntity = response.getEntity();
        String html = EntityUtils.toString(httpEntity, "utf-8");
        Document document = Jsoup.parse(html);
        Elements script = document.getElementsByTag("script");
        for (int i = 0; i < script.size(); i++) {
            if (i == 3) {
                System.out.println(script.get(i).toString());
            }
        }

        return html;
    }

    public void getVideo() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpGet optionRequest = new HttpGet(url);

        optionRequest.setHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.0.0 Safari/537.36");
        optionRequest.setHeader("accept", "*/*");
        optionRequest.setHeader("origin", "https://www.bilibili.com");
        optionRequest.setHeader("referer", "https://www.bilibili.com/video/BV1Re411B7xc/?spm_id_from=333.880.my_history.page.click&vd_source=47073b9a4320333c2e8d3544a7fc9448");
        optionRequest.setHeader("accept-language", "zh-CN,zh;q=0.9");
        optionRequest.setHeader("accept-encoding", "identity");
        optionRequest.setHeader("Cookie", "buvid_fp_plain=undefined; DedeUserID=354054152; DedeUserID__ckMd5=ab3f955a69f0c254; i-wanna-go-back=-1; CURRENT_FNVAL=4048; CURRENT_QUALITY=80; LIVE_BUVID=AUTO7116779145436655; header_theme_version=CLOSE; CURRENT_PID=e01f1960-cd47-11ed-bc0b-dba20274edf5; hit-new-style-dyn=0; hit-dyn-v2=1; FEED_LIVE_VERSION=V8; home_feed_column=5; enable_web_push=DISABLE; browser_resolution=1707-841; _uuid=BDF35129-48C8-1552-AC16-FD2106512C88159435infoc; PVID=1; buvid3=271AE0B2-C6BE-1964-F58A-C355CAA2007549009infoc; b_nut=1701410949; buvid4=2CA479B0-4EC2-9EE4-6636-B3D8FCDF42E831859-022102709-Ik93gWcclu8VL3w58Fu%2BNT7HGmP0YmRnw1Zr4hGkskg%3D; fingerprint=cf4f14ba92ed829c712c0ec979b09b1b; SESSDATA=76f44461%2C1719201364%2C74bed%2Ac2CjCBv28vfJQt-irslVWimxO7bGWnx_NCtefS57ImlZwtOJj8aRnm52TH-rLoJBSEn-ASVnNXTjltWXVOX3lZZThBa0RLdUNLTm1FWTlWcEJXSnF5QVVGemFsN0U3WHVFX2NOV3lPQTZJclhsc0dLMVVJbTRnWktqLTdvaURLMTN3ODJhN01FRHdBIIEC; bili_jct=40f7a5563fb5f9dcb36e53afade08988; bili_ticket=eyJhbGciOiJIUzI1NiIsImtpZCI6InMwMyIsInR5cCI6IkpXVCJ9.eyJleHAiOjE3MDM5MDg1NzEsImlhdCI6MTcwMzY0OTMxMSwicGx0IjotMX0.Ko_ApANK46PCz_8sINsOILeXKvaVsXFI8sYeAiJAQgo; bili_ticket_expires=1703908511; rpdid=|(ku|k)J|ll~0J'u~|JYRmJ~J; sid=7yp24md3; b_lsid=A696BDA5_18CAA25FD40; bp_video_offset_354054152=879698263917199363; buvid_fp=271AE0B2-C6BE-1964-F58A-C355CAA2007549009infoc");
        optionRequest.setHeader("sec-ch-ua", "Chromium\";v=\"104\", \" Not A;Brand\";v=\"99\", \"Google Chrome\";v=\"104");

        CloseableHttpResponse response = httpClient.execute(optionRequest);

        HttpEntity httpEntity = response.getEntity();
        byte[] bytes = EntityUtils.toByteArray(httpEntity);
        DataOutputStream out = new DataOutputStream(new FileOutputStream("D:\\bilibili_video\\test.mp4"));

        out.write(bytes);
        out.close();

        HttpClientUtils.closeQuietly(response);
        HttpClientUtils.closeQuietly(httpClient);
    }
}
