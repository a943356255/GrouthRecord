package big_file_upload.upload.controller;

import big_file_upload.upload.Chunk;
import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Map;

@RestController
@RequestMapping("/filepath")
public class FileUploadController {

    String path = "D:\\bilibili_video\\test\\test";

    String outPath = "D:\\bilibili_video\\test";

    @RequestMapping("/upload")
    public String upload(MultipartFile[] file, String fileName) throws IOException {
        if (file == null) {
            System.out.println("文件为空");
            return "error";
        }
        for (int i = 0; i < file.length; i++) {
            File outFile = new File(path, fileName + "-" + (i + 1) + "-" + file.length + ".part");
            InputStream inputStream = file[i].getInputStream();
            FileUtils.copyInputStreamToFile(inputStream, outFile);
            inputStream.close();
        }

        return "";
    }

    @RequestMapping("/mergeFile")
    public String mergeFile(@RequestBody Map<String, Object> map) throws IOException {
        String filename = (String) map.get("fileName");
        String type = (String) map.get("type");
        int chunks = (int) map.get("chunks");

        // 最终汇总的文件
        File outFile = new File(outPath + File.separator + filename + "." + type);
        for (int i = 1; i <= chunks; i++) {
            String findName = filename + "-" + i + "-" + chunks + ".part";
            File partFile = new File(path + File.separator + findName);
            FileOutputStream fileOutputStream = new FileOutputStream(outFile, true);
            FileUtils.copyFile(partFile, fileOutputStream);
            fileOutputStream.close();
        }
        File file = new File(path);
        FileUtils.deleteDirectory(file);

        return "";
    }

    @RequestMapping("/uploadSuccess")
    public String uploadSuccess() {
        return "";
    }
}