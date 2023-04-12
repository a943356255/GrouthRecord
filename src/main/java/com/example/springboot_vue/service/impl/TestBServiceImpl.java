package com.example.springboot_vue.service.impl;

import com.example.springboot_vue.service.TestService;
import org.springframework.stereotype.Service;

@Service
public class TestBServiceImpl implements TestService {

    @Override
    public void printf() {
        System.out.println("此处打印B的内容");
    }

}
