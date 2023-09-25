package com.example.springboot_vue.mini_spring.utils;

import com.example.springboot_vue.mini_spring.beans.BeanDefinition;
import com.example.springboot_vue.mini_spring.beans.BeanFactory;
import org.dom4j.Element;

// 将解析好的xml转换为BeanDefinition
public class XmlBeanDefinitionReader {
    // 工厂
    BeanFactory beanFactory;

    public XmlBeanDefinitionReader(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public void loadBeanDefinitions(Resource resource) {
        // resource里存储了所有从xml中解析出来的bean
        while (resource.hasNext()) {
            Element element = (Element) resource.next();
            // 构造BeanDefinition实例
            String beanID = element.attributeValue("id");
            String beanClassName = element.attributeValue("class");
            BeanDefinition beanDefinition = new BeanDefinition(beanID, beanClassName);
            // 存储进工厂的list
            this.beanFactory.registerBeanDefinition(beanDefinition);
        }
    }
}