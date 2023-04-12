package com.example.springboot_vue.service.impl;

import com.example.springboot_vue.service.TestService;
import org.springframework.stereotype.Service;

@Service
public class TestAServiceImpl implements TestService {

    @Override
    public void printf() {
        System.out.println("在此处打印A的内容");
    }

}
