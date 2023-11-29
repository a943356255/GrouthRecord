package spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import spring.service.TestService;

@Component
public class NewDemoController {

    @Autowired
    TestService testService;

    public NewDemoController() {
        testService.printf();
    }

}
