package com.example.springboot_vue.httpclient;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpMessage;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.http.util.TextUtils;
import org.bytedeco.javacv.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class TestTaoBao {

    public static void main(String[] args) throws IOException, InterruptedException {
        TestTaoBao testTaoBao = new TestTaoBao();
////        String url = "https://cn-cq-cm-01-02.bilivideo.com/upgcxcode/59/16/778531659/778531659-1-100024.m4s?e=ig8euxZM2rNcNbdlhoNvNC8BqJIzNbfqXBvEqxTEto8BTrNvN0GvT90W5JZMkX_YN0MvXg8gNEV4NC8xNEV4N03eN0B5tZlqNxTEto8BTrNvNeZVuJ10Kj_g2UB02J0mN0B5tZlqNCNEto8BTrNvNC7MTX502C8f2jmMQJ6mqF2fka1mqx6gqj0eN0B599M=&uipk=5&nbs=1&deadline=1660188577&gen=playurlv2&os=bcache&oi=0&trid=0000487f3b6be900491fbb8a207b799e6634u&mid=354054152&platform=pc&upsig=bfc3a4cc0dc590393192d8933e4477a7&uparams=e,uipk,nbs,deadline,gen,os,oi,trid,mid,platform&cdnid=6073&bvc=vod&nettype=0&orderid=0,3&agrr=0&bw=103024&logo=80000000";

//        // 0为视频文件，1为音频文件
//        String[] baseUrl = testTaoBao.getBaseUrl("");

//        testTaoBao.test(baseUrl[0], "video");
//        testTaoBao.test(baseUrl[1], "audio");
        System.out.println(getVideoImage("https://www.bilibili.com/video/BV1D8411a72M/?is_story_h5=false&p=1&share_from=ugc&share_medium=android&share_plat=android&share_session_id=dd22cdb1-a2ee-45c1-a132-70b9206c6cbe&share_source=COPY&share_tag=s_i&timestamp=1667347247&unique_k=Ze7bk6f"));
//        testTaoBao.getBaseUrl("https://www.bilibili.com/video/BV1ym4y1F7Fa/?is_story_h5=false&p=1&share_from=ugc&share_medium=android&share_plat=android&share_session_id=b6f4a5ec-00ac-4c71-bf32-48753ab0b207&share_source=COPY&share_tag=s_i&timestamp=1667059697&unique_k=IaBqv7u&vd_source=47073b9a4320333c2e8d3544a7fc9448");
//        String url = "https://www.bilibili.com/video/BV1Dd4y1U7AE?p=";
//        for (int i = 4; i <= 100; i++) {
//            url = url + i + "&vd_source=47073b9a4320333c2e8d3544a7fc9448";
//            String[] baseUrl = testTaoBao.getBaseUrl(url);
//            String filename = String.valueOf(i);
//            testTaoBao.test(baseUrl[1], filename);
//        }
//        testTaoBao.merge("测试合并");
    }

    public void test() throws IOException {

        TestTaoBao testTaoBao = new TestTaoBao();

        for (int i = 4; i <= 100; i++) {
            String url = "https://www.bilibili.com/video/BV1Dd4y1U7AE?p=" + i + "&vd_source=47073b9a4320333c2e8d3544a7fc9448";
//            System.out.println("url = " + url);
            String[] baseUrl = testTaoBao.getBaseUrl(url);
//            System.out.println("baseUrl = " + baseUrl[1]);

            String filename = String.valueOf(i);
            testTaoBao.test(baseUrl[1], filename);
        }
    }

    public String[] getBaseUrl(String url) throws IOException {
//        String url = "https://www.bilibili.com/video/BV1vt4y157rJ";

        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;

        HttpGet optionRequest = new HttpGet(url);
        optionRequest.setHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.0.0 Safari/537.36");

        response = httpClient.execute(optionRequest);

        HttpEntity httpEntity = response.getEntity();

        String html = EntityUtils.toString(httpEntity, "utf-8");
        Document document = Jsoup.parse(html);

        // 获取script标签
        Elements elements = document.getElementsByTag("script");
        // 取第三个，有baseUrl 2或者3 改一下就可以了
        Element element = elements.get(2);
        String jsonStr = element.data().substring(20);

        System.out.println(jsonStr);

        // 获取json中的baseUrl
        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
        JSONObject data = (JSONObject) jsonObject.get("data");
        JSONObject dash = (JSONObject) data.get("dash");
        JSONArray video = (JSONArray) dash.get("video");
        JSONArray audio = (JSONArray) dash.get("audio");
        JSONObject audioZero = (JSONObject) audio.get(0);
        JSONObject videoZero = (JSONObject) video.get(0);

        String[] str = new String[2];
        str[0] = (String) videoZero.get("baseUrl");
        str[1] = (String) audioZero.get("baseUrl");
        return str;
    }

    public static String getVideoImage(String url) {
        String html = new GetData().test(url);
        Document document = Jsoup.parse(html);
        Elements elements = document.select("meta[itemprop=image]");

        String imageUrl = null;
        if (elements != null) {
            Element element = elements.get(0);
            imageUrl = element.attr("content");
        }
        return imageUrl;
    }

    public void merge(String filename) {

        String outPut = "D:\\video\\" + filename + ".mp4";
        String video = "D:\\video\\video.mp4";
        String audio = "D:\\video\\audio.mp3";
        System.out.println("进入 合并");

        FrameRecorder recorder = null;
        FrameGrabber grabber1 = null;
        FrameGrabber grabber2 = null;
        try {
            //抓取视频帧
            grabber1 = new FFmpegFrameGrabber(video);
            System.out.println(grabber1);
            //抓取音频帧
            grabber2 = new FFmpegFrameGrabber(audio);

            grabber1.start();
            grabber2.start();
            //创建录制
            recorder = new FFmpegFrameRecorder(outPut,
                    grabber1.getImageWidth(),
                    grabber1.getImageHeight(),
                    grabber2.getAudioChannels());
            recorder.setFormat("mp4");
            recorder.setFrameRate(grabber1.getFrameRate());
            recorder.setSampleRate(grabber2.getSampleRate());
            recorder.setVideoBitrate(8000000);
            recorder.setAudioQuality(0);

            recorder.start();
            Frame frame1;
            Frame frame2;
            //先录入视频
            while ((frame1 = grabber1.grabFrame()) != null) {
                recorder.record(frame1);
            }
            //然后录入音频
            while ((frame2 = grabber2.grabFrame()) != null) {
                recorder.record(frame2);
            }
            grabber1.stop();
            grabber2.stop();
            recorder.stop();
        } catch (Exception e) {
            System.out.println("出现错误");
            e.printStackTrace();
        } finally {
            try {
                if (recorder != null) {
                    recorder.release();
                }
                if (grabber1 != null) {
                    grabber1.release();
                }
                if (grabber2 != null) {
                    grabber2.release();
                }
            } catch (FrameRecorder.Exception | FrameGrabber.Exception e) {
                e.printStackTrace();
            }
        }

        System.out.println("结束合并");
    }

    public void test(String url, String fileName) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;

        HttpGet optionRequest = new HttpGet(url);
//        String optionUrl = "cn-cq-cm-01-02.bilivideo.com";
//        optionRequest.setHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.0.0 Safari/537.36");
//        optionRequest.setHeader("host", optionUrl);
//        optionRequest.setHeader("Access-Control-Request-Method", "GET");
//        optionRequest.setHeader("Access-Control-Request-Headers", "range");
//        optionRequest.setHeader("connection", "keep-alive");
//        optionRequest.setHeader("origin", "https://www.bilibili.com");
//        optionRequest.setHeader("referer", "https://www.bilibili.com/video/BV1kW4y1m7mN?spm_id_from=333.851.b_7265636f6d6d656e64.5&vd_source=47073b9a4320333c2e8d3544a7fc9448");
//        optionRequest.setHeader("accept-language", "zh-CN,zh;q=0.9");
//        optionRequest.setHeader("accept-encoding", "identity");
//        optionRequest.setHeader("cache-control", "max-age=0");
//        optionRequest.setHeader("DNT", "1");
//        setRequestCookies(optionRequest);
//
//        response = httpClient.execute(optionRequest);
//
//        appendCookies(response);
//        HttpGet request = new HttpGet(url);
        optionRequest.setHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.0.0 Safari/537.36");
        optionRequest.setHeader("accept", "*/*");
        optionRequest.setHeader("origin", "https://www.bilibili.com");
        optionRequest.setHeader("referer", "https://www.bilibili.com/video/BV1kW4y1m7mN?spm_id_from=333.851.b_7265636f6d6d656e64.5&vd_source=47073b9a4320333c2e8d3544a7fc9448");
//        request.setHeader("range", "bytes=0-10672575");
        optionRequest.setHeader("range", "bytes=1065-1744");
        optionRequest.setHeader("accept-language", "zh-CN,zh;q=0.9");
        optionRequest.setHeader("accept-encoding", "identity");
        optionRequest.setHeader("sec-ch-ua", "Chromium\";v=\"104\", \" Not A;Brand\";v=\"99\", \"Google Chrome\";v=\"104");

        response = httpClient.execute(optionRequest);
        // 判断相应状态
        String size = response.getHeaders("Content-Range")[0].getValue().split("/")[1];

        System.out.println("size = " + size);
        optionRequest.setHeader("range", "bytes=0-" + size);
        // 再次发起请求，拿全部数据
        response = httpClient.execute(optionRequest);
        HttpEntity httpEntity = response.getEntity();

        byte[] bytes = EntityUtils.toByteArray(httpEntity);
        DataOutputStream out = null;
        if (fileName.equals("video")) {
            out = new DataOutputStream(new FileOutputStream("D:\\video\\" + fileName + ".mp4"));
        } else {
            out = new DataOutputStream(new FileOutputStream("D:\\video\\" + fileName + ".mp3"));
        }

        out.write(bytes);
        out.close();

        HttpClientUtils.closeQuietly(response);
        HttpClientUtils.closeQuietly(httpClient);
    }

    private String cookies;

    private void setRequestCookies(HttpMessage reqMsg) {
        if (!TextUtils.isEmpty(cookies)) {
            reqMsg.setHeader("cookie", cookies);
        }
    }

    private void appendCookies(HttpMessage resMsg) {
        System.out.println();
        System.out.println("resMsg" + resMsg);
        System.out.println();
        Header setCookieHeader = resMsg.getFirstHeader("Set-Cookie");

        if (setCookieHeader != null
                && TextUtils.isEmpty(setCookieHeader.getValue())) {
            String setCookie = setCookieHeader.getValue();
            if (TextUtils.isEmpty(cookies)) {
                cookies = setCookie;
            } else {
                cookies = cookies + "; " + setCookie;
            }
        }
    }
}
