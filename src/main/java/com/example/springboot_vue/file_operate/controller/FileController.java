package com.example.springboot_vue.file_operate.controller;

import com.example.springboot_vue.file_operate.pojo.Chunk;
import com.example.springboot_vue.file_operate.service.ChunkService;
import com.example.springboot_vue.httpclient.TestTaoBao;
import com.example.springboot_vue.pojo.verify.ResultVO;
import org.apache.commons.io.FileUtils;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@RestController
@RequestMapping("/file")
public class FileController {

    private final String filePath = "D:\\test_file_upload";

    private final String filePathTemp = "D:\\test_file_upload";

    @Autowired
    ChunkService chunkServiceImpl;

    @RequestMapping("/download")
    public ResultVO<String> download(@RequestBody Map<String, Object> map) throws IOException {

        String url = (String) map.get("url");
        String filename = (String) map.get("filename");

        TestTaoBao testTaoBao = new TestTaoBao();
        testTaoBao.test();

        return new ResultVO<>("下载视频成功");
    }

    @RequestMapping("/uploadFile")
    public ResultVO<String> upload(HttpServletRequest request, Chunk chunk) throws Exception {
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if (isMultipart) {

            String filename = chunk.getFilename();

            MultipartFile file = chunk.getFile();
            if (file == null) {
                throw new Exception("参数验证失败！");
            }

            Integer chunkNumber = chunk.getChunkNumber();
            if (chunkNumber == null) {
                chunkNumber = 0;
            }

            File outFile = new File(filePathTemp + File.separator + chunk.getIdentifier(), chunkNumber + ".part");

            InputStream inputStream = file.getInputStream();
            FileUtils.copyInputStreamToFile(inputStream, outFile);
        }

        return new ResultVO<>("上传成功");
    }

    @RequestMapping("/mergeFile")
    public ResultVO<String> mergeFile(String filename, String guid) throws Exception {

        File file = new File(filePathTemp + File.separator + guid);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null && files.length > 0) {
                File partFile = new File(filePath + File.separator + filename);
                for (int i = 1; i <= files.length; i++) {
                    File s = new File(filePathTemp + File.separator + guid, i + ".part");
                    FileOutputStream destTempfos = new FileOutputStream(partFile, true);
                    FileUtils.copyFile(s, destTempfos);
                    destTempfos.close();
                }
                FileUtils.deleteDirectory(file);
            }
        }

        // 添加到数据库
        chunkServiceImpl.insert(filename, filePath);
        return new ResultVO<>("合并成功");
    }
//    public static void merge(String targetFile, String folder) {
//        try {
//            Files.createFile(Paths.get(targetFile));
//            Files.list(Paths.get(folder))
//                    .filter(path -> path.getFileName().toString().contains("-"))
//                    .sorted((o1, o2) -> {
//                        String p1 = o1.getFileName().toString();
//                        String p2 = o2.getFileName().toString();
//                        int i1 = p1.lastIndexOf("-");
//                        int i2 = p2.lastIndexOf("-");
//                        return Integer.valueOf(p2.substring(i2)).compareTo(Integer.valueOf(p1.substring(i1)));
//                    })
//                    .forEach(path -> {
//                        try {
//                            //以追加的形式写入文件
//                            Files.write(Paths.get(targetFile), Files.readAllBytes(path), StandardOpenOption.APPEND);
//                            //合并后删除该块
//                            Files.delete(path);
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    });
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
