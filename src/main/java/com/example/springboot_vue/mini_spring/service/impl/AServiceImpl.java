package com.example.springboot_vue.mini_spring.service.impl;

import com.example.springboot_vue.mini_spring.service.AService;

public class AServiceImpl implements AService {

    @Override
    public void sayHello() {
        System.out.println("test say hello");
    }
}
