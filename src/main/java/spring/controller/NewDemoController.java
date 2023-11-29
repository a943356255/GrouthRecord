package spring.controller;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import spring.service.TestService;

import javax.annotation.PostConstruct;

@Component
public class NewDemoController {

//    @Autowired
//    TestService testService;

//    @PostConstruct
//    public void init() {
//        testService.printf();
//    }

    TestService testService;

    public NewDemoController(TestService testService) {
        this.testService = testService;
        System.out.println("执行代码");
        testService.printf();
    }

//    @Override
//    public void afterPropertiesSet() throws Exception {
//        testService.printf();
//    }

}
