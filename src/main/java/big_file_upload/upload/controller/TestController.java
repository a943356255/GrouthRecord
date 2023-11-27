package big_file_upload.upload.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @Repository
    public static class InnerController implements TestInterface {

        @Override
        public void printf() {
            System.out.println("test");
        }
    }

    @Autowired
    @Qualifier("testController.InnerController")
    TestInterface innerController;

    @RequestMapping("/testMethod")
    public void testMethod() {
        innerController.printf();
    }
}
