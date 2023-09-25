package com.example.springboot_vue.mini_spring.beans;

import com.example.springboot_vue.mini_spring.exception.BeansException;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

// 这里是用工厂方法，来替换ClassPathXmlApplicationContext类
public class SimpleBeanFactory implements BeanFactory {

    // 存储所有从xml中解析的bean的id以及name,组装成BeanDefinition
    private final List<BeanDefinition> beanDefinitions = new ArrayList<>();
    // 仅仅存储beanNames，用于快速判断xml中是否有该元素
    private final List<String> beanNames = new ArrayList<>();
    // 这里存储已经创建了的实例
    private final Map<String, Object> singletons = new HashMap<>();

    public SimpleBeanFactory() {

    }

    // getBean，容器的核心方法
    public Object getBean(String beanName) throws BeansException {
        // 先尝试直接拿Bean实例
        Object singleton = singletons.get(beanName);
        // 如果此时还没有这个Bean的实例，则获取它的定义来创建实例
        if (singleton == null) {
            int i = beanNames.indexOf(beanName);
            // i不存在，说明从xml中加载的内容没有该beanName，直接返回
            if (i == -1) {
                throw new BeansException();
            } else {
                // 获取Bean的定义
                BeanDefinition beanDefinition = beanDefinitions.get(i);
                try {
                    singleton = Class.forName(beanDefinition.getClassName()).getDeclaredConstructor().newInstance();
                } catch (IllegalAccessException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException e) {
                    e.printStackTrace();
                }
                // 注册Bean实例
                singletons.put(beanDefinition.getId(), singleton);
            }
        }

        return singleton;
    }

    public void registerBeanDefinition(BeanDefinition beanDefinition) {
        this.beanDefinitions.add(beanDefinition);
        this.beanNames.add(beanDefinition.getId());
    }
}
