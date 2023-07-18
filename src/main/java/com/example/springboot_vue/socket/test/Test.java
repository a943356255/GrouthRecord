package com.example.springboot_vue.socket.test;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Test {

    public static void main(String[] args) throws IOException {


//          new FileUtils().writeFile("D:\\mysql\\mysql\\first.txt", "name age time now");
//        MyThread myThread = new MyThread("线程1");
//        MyThread myThread2 = new MyThread("线程2");
//        MyThread myThread3 = new MyThread("线程3");
//
//        for (int i = 0; i < 50; i++) {
//            myThread.run();
//            myThread2.run();
//            myThread3.run();
//        }

    }

    public void test() throws IOException {

        String url = "https://www.meijuba.net/html/24370.html";
        // 创建httpclient，相当于浏览器
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        // 创建一个get请求
        HttpPost httpPost = new HttpPost(url);

        // 相应模式
        CloseableHttpResponse response = null;
        try {
            // 由客户端执行请求
            response = httpClient.execute(httpPost);

            HttpEntity httpEntity = response.getEntity();

            if (httpEntity != null) {
                System.out.println(httpEntity.getContentLength());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (httpClient != null) {
                httpClient.close();
            }
            if (response != null) {
                response.close();
            }
        }
    }

}

class MyThread implements Runnable {

    public static int num = 0;
    private String name;

    public MyThread(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        change();
    }

    public void change() {
        Lock lock = new ReentrantLock();
        lock.lock();
        num ++;
        System.out.println(name + "num = " + num);
        lock.unlock();
    }
}
