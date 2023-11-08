package big_file_upload.upload.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/file")
public class FileUploadController {

    @RequestMapping("/upload")
    public String upload(MultipartFile multipartFile, @RequestBody Map<String, Object> map) {
        System.out.println(map.toString());
        return "";
    }


}
