package com.example.springboot_vue.mini_spring.beans;

import com.example.springboot_vue.mini_spring.exception.BeansException;

public interface BeanFactory {

    Object getBean(String beanName) throws BeansException;

    void registerBeanDefinition(BeanDefinition beanDefinition);

}
