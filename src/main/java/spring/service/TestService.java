package spring.service;

import org.springframework.stereotype.Service;

@Service
public class TestService {

    public void printf() {
        System.out.println("这是一个测试类，在spring启动的时候调用");
    }
}
