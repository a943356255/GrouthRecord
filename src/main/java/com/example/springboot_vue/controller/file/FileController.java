package com.example.springboot_vue.controller.file;

import com.example.springboot_vue.pojo.verify.ResultVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

//@RequestMapping("/file")
public class FileController {

//    @RequestMapping("/uploadFile")
    public ResultVO<String> upload(MultipartFile multipartFile) {

        return null;
    }
}
