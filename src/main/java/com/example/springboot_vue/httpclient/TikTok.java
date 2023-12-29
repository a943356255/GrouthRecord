package com.example.springboot_vue.httpclient;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
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
import java.util.Arrays;

public class TikTok {

    String url = "http://v26-web.douyinvod.com/0794b7ed796a8c92cad0e05dee2639e7/658c39db/video/tos/cn/tos-cn-ve-15/oUqKUgDnlBEDupYeDDpBXAQRAASAbgDgV9I5ey/?a=6383";
    public static void main(String[] args) throws IOException {
        String baseUrl = "https://www.douyin.com/discover?modal_id=7313867104770379017";
        TikTok tikTok = new TikTok();
        tikTok.getTargetUrl(baseUrl);
//        tikTok.getVideo();
    }

    public String getTargetUrl(String baseUrl) throws IOException {
        final WebClient webClient = new WebClient(BrowserVersion.CHROME);

        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setActiveXNative(false);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(true);

        HtmlPage page = webClient.getPage(baseUrl);
        webClient.waitForBackgroundJavaScript(5000);

        String pageXml = page.asXml();
        Document document = Jsoup.parse(pageXml);
        Elements elements = document.getElementsByTag("script");
        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i).toString().contains("http://") && elements.get(i).toString().contains("video")) {
                if (elements.get(i).data().contains("")) {

                }
                System.out.println(elements.get(i).data());
//                System.out.println("urlList = " + elements.get(i).getElementsByTag("urlList"));
            }
        }
//        CloseableHttpClient httpClient = HttpClients.createDefault();
//        HttpGet optionRequest = new HttpGet(baseUrl);
//        optionRequest.setHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.0.0 Safari/537.36");
//        optionRequest.setHeader("accept", "*/*");
//        optionRequest.setHeader("Content-Type", "application/json");
//        optionRequest.setHeader("origin", "https://www.douyin.com");
//        optionRequest.setHeader("referer", "https://www.douyin.com/user/self?modal_id=7299051699279252746&showTab=favorite_collection");
//        optionRequest.setHeader("accept-language", "zh-CN,zh;q=0.9");
//        optionRequest.setHeader("accept-encoding", "identity");
//        optionRequest.setHeader("Cookie", "douyin.com; passport_assist_user=CkGFBQ71pWFO-6kOEJhhENh4dc4Brdy3j_Be9PhjMfR9KkntGrSPdxjMNocCqgXy2VgJG3I23-Loqz68X0lfncYvFBpKCjzotR1IG9VE3dL6DPKRK2GOd7eHWyVl_n2UzPfXtJEFSsDoEGpu7nzz53kIFPWKYjKoU6H5id3ffWPSU2oQ78-7DRiJr9ZUIAEiAQPla9Pp; n_mh=38-OrRuF_VTqSauspL7XGGP2i5fLfCJg_J1U6-viU5Y; sso_uid_tt=39987b338207af85161a5bf44eea5728; sso_uid_tt_ss=39987b338207af85161a5bf44eea5728; toutiao_sso_user=df73426c9169f3c41a86d7b1624fd1d0; toutiao_sso_user_ss=df73426c9169f3c41a86d7b1624fd1d0; LOGIN_STATUS=1; store-region=cn-cq; store-region-src=uid; d_ticket=9b2882ed05cb4c170a8af7e1574c7d582eaa1; my_rd=2; ttwid=1%7CLXEzTN4Y6PtvvSCs1Vu7vjwjPCKDK5m6sjGK35iyFa4%7C1697706049%7C4b38197ee8c64c26c465c55174328ffbf16c07f64c36b65127c2b95084b32f69; uid_tt=5754f74e54a174136967d9fc90431bf0; uid_tt_ss=5754f74e54a174136967d9fc90431bf0; sid_tt=723356447044566aa88ae35027577972; sessionid=723356447044566aa88ae35027577972; sessionid_ss=723356447044566aa88ae35027577972; s_v_web_id=verify_loyeavnc_uwHf5L7F_uztS_4JuM_AjXS_N1O3xE0YjQU8; passport_csrf_token=4bab4fd2b70292b21b3d5645ad17703f; passport_csrf_token_default=4bab4fd2b70292b21b3d5645ad17703f; __live_version__=%221.1.1.5417%22; live_use_vvc=%22false%22; douyin.com; device_web_cpu_core=16; device_web_memory_size=8; architecture=amd64; publish_badge_show_info=%220%2C0%2C0%2C1703668683418%22; dy_swidth=1707; dy_sheight=960; csrf_session_id=f8e0f02cc8cabb5413034ef7955d7d0c; strategyABtestKey=%221703668684.722%22; volume_info=%7B%22isUserMute%22%3Afalse%2C%22isMute%22%3Atrue%2C%22volume%22%3A0.982%7D; stream_player_status_params=%22%7B%5C%22is_auto_play%5C%22%3A0%2C%5C%22is_full_screen%5C%22%3A0%2C%5C%22is_full_webscreen%5C%22%3A0%2C%5C%22is_mute%5C%22%3A1%2C%5C%22is_speed%5C%22%3A1%2C%5C%22is_visible%5C%22%3A1%7D%22; sid_ucp_sso_v1=1.0.0-KGFmMGZlZDMyNzdmYzEzNjgxYzVhZDgzYjJlYjU1YTVjN2E2M2E3ZWYKHwjIlZCn1fSVBRDM16-sBhjvMSAMMIOg1fIFOAZA9AcaAmxmIiBkZjczNDI2YzkxNjlmM2M0MWE4NmQ3YjE2MjRmZDFkMA; ssid_ucp_sso_v1=1.0.0-KGFmMGZlZDMyNzdmYzEzNjgxYzVhZDgzYjJlYjU1YTVjN2E2M2E3ZWYKHwjIlZCn1fSVBRDM16-sBhjvMSAMMIOg1fIFOAZA9AcaAmxmIiBkZjczNDI2YzkxNjlmM2M0MWE4NmQ3YjE2MjRmZDFkMA; bd_ticket_guard_client_web_domain=2; carnival_live_pull=1703668685387; sid_guard=723356447044566aa88ae35027577972%7C1703668684%7C5184000%7CSun%2C+25-Feb-2024+09%3A18%3A04+GMT; sid_ucp_v1=1.0.0-KDY4YmJjODJiMTY0NmQ4NjdjMjRiNWVjNDU0MmVjZGQ3ZTYzYjY3ZWMKGwjIlZCn1fSVBRDM16-sBhjvMSAMOAZA9AdIBBoCbHEiIDcyMzM1NjQ0NzA0NDU2NmFhODhhZTM1MDI3NTc3OTcy; ssid_ucp_v1=1.0.0-KDY4YmJjODJiMTY0NmQ4NjdjMjRiNWVjNDU0MmVjZGQ3ZTYzYjY3ZWMKGwjIlZCn1fSVBRDM16-sBhjvMSAMOAZA9AdIBBoCbHEiIDcyMzM1NjQ0NzA0NDU2NmFhODhhZTM1MDI3NTc3OTcy; xg_device_score=7.63586757002114; __ac_nonce=0658bfec400dbb20bc7fc; __ac_signature=_02B4Z6wo00f01IrAthgAAIDBgOZtsnzY9pSK4LKAAEdAvGamFyc1uZK.s1BpKbAXEVdIFmR3jZJynYrkSaJ0jLj4gsGOK-kXNP5j5JYlFkFbglSBSiWVcictq2DvAIjxkJ9mzq9nhR0o.CBCf1; stream_recommend_feed_params=%22%7B%5C%22cookie_enabled%5C%22%3Atrue%2C%5C%22screen_width%5C%22%3A1707%2C%5C%22screen_height%5C%22%3A960%2C%5C%22browser_online%5C%22%3Atrue%2C%5C%22cpu_core_num%5C%22%3A16%2C%5C%22device_memory%5C%22%3A8%2C%5C%22downlink%5C%22%3A1.5%2C%5C%22effective_type%5C%22%3A%5C%223g%5C%22%2C%5C%22round_trip_time%5C%22%3A350%7D%22; msToken=YM16EnFMouwSN6nU3uN8ZG8ezpNHMYpDsIVm4GPem_-62aaZqTwt8UBGJoYqao4gvqz8cDOCIrziNl2MBZAYTnJADGOxB3n1VuU1NdjyMugbEIdfUMJ8g2Aa6UzEpR4=; home_can_add_dy_2_desktop=%221%22; bd_ticket_guard_client_data=eyJiZC10aWNrZXQtZ3VhcmQtdmVyc2lvbiI6MiwiYmQtdGlja2V0LWd1YXJkLWl0ZXJhdGlvbi12ZXJzaW9uIjoxLCJiZC10aWNrZXQtZ3VhcmQtcmVlLXB1YmxpYy1rZXkiOiJCQTVnT2NUL0RGZ2I3eS9UKzZyVmNyQlZNNkhPSlpwL2lCOFdYSHlFKzN6ZGlTWGRmUWdWclg0cEJRK20yT3FHQTFNcUpBQVRScDhxN3Q1QlRMZ09PSTg9IiwiYmQtdGlja2V0LWd1YXJkLXdlYi12ZXJzaW9uIjoxfQ%3D%3D; FOLLOW_LIVE_POINT_INFO=%22MS4wLjABAAAArVj_fpCBTdA4phkk8sHF4hGCeBWFhcSRTbv_7p1P6dyh1zxF6GuLtyW8WuH8fsM2%2F1703692800000%2F0%2F1703673593438%2F0%22; tt_scid=wkOsF8j0T1nkCXMtJOjnhZZI7f3Jt8hZ8mxJalaA9SIW81y9M.H5OBl7i-k7JyRn8f4f; odin_tt=871dbda93314bca7bf92e35469450a8f1945e4124a5f7114a4480fa1ed2f7af93cfb9bb17a0d34382ef00a58e55a67d0; msToken=FrD_m-1sRKYDokNgKPMWuqV8x_9oFQ-Fi8MFmgsobzAWkWwpbfcMwLoZ83Np8azZU875QKgshJn5y4JE-l13MKl67NphOQnrBbeQvG3MJ4oisoWrfvHL6BAJitbHOHk=; download_guide=%223%2F20231227%2F0%22; pwa2=%220%7C0%7C3%7C0%22; IsDouyinActive=false; passport_fe_beating_status=false");
//
//        CloseableHttpResponse response = httpClient.execute(optionRequest);
//        response.setHeader("Content-Type", "application/json; charset=UTF-8");
//        HttpEntity httpEntity = response.getEntity();
//        String html = EntityUtils.toString(httpEntity, "utf-8");
//        Document document = Jsoup.parse(html);
//        System.out.println(document.toString());
//        System.out.println("class = " + document.getElementsByClass("object-fit-container"));
//        System.out.println("tag = " + document.getElementsByTag("video").toString());

        return "";
    }

    public void getVideo() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet optionRequest = new HttpGet(url);

        optionRequest.setHeader("Content-Type", "application/json");
        optionRequest.setHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.0.0 Safari/537.36");
        optionRequest.setHeader("accept", "*/*");
        optionRequest.setHeader("origin", "https://www.douyin.com");
        optionRequest.setHeader("referer", "https://www.douyin.com/user/self?modal_id=7299051699279252746&showTab=favorite_collection");
        optionRequest.setHeader("accept-language", "zh-CN,zh;q=0.9");
        optionRequest.setHeader("accept-encoding", "identity");
        optionRequest.setHeader("sec-ch-ua", "Chromium\";v=\"104\", \" Not A;Brand\";v=\"99\", \"Google Chrome\";v=\"104");

        CloseableHttpResponse response = httpClient.execute(optionRequest);
        response.setHeader("Content-Type", "application/json; charset=UTF-8");
        HttpEntity httpEntity = response.getEntity();
        byte[] bytes = EntityUtils.toByteArray(httpEntity);
        DataOutputStream out = new DataOutputStream(new FileOutputStream("D:\\bilibili_video\\tiktok.mp4"));

        out.write(bytes);
        out.close();

        HttpClientUtils.closeQuietly(response);
        HttpClientUtils.closeQuietly(httpClient);
    }
}
