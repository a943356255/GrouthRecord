package big_file_upload.upload.controller;

import big_file_upload.upload.Chunk;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/filepath")
public class FileUploadController {

//    String path = "D:\\bilibili_video\\test\\test";
//
//    String outPath = "D:\\bilibili_video\\test";

    ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();

    SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");

    String path = "/usr/app/server/test/test";

    String outPath = "/usr/app/server/test";

    @RequestMapping("/upload")
    public String upload(MultipartFile file, Chunk chunk, String time) throws IOException {
        if (file == null) {
            System.out.println("文件为空");
            return "error";
        }

        Date arriveDate = new Date(System.currentTimeMillis());
        System.out.println("请求" + chunk.getChunk() + "到达时间为：" + arriveDate  + ", 开始上传文件, 请求发送时间为time = " + time);

        File outFile = new File(path + File.separator, String.valueOf((chunk.getChunk() + 1)));
        InputStream inputStream = file.getInputStream();
        FileUtils.copyInputStreamToFile(inputStream, outFile);
        inputStream.close();

        // 打印上传完成时间
        Date date = new Date(System.currentTimeMillis());
        System.out.println("文件上传完成时间" + formatter.format(date));

//        for (int i = 0; i < file.length; i++) {
//            File outFile = new File(path, fileName + "-" + (i + 1) + "-" + file.length);
//
//        }

        return "";
    }

    @RequestMapping("/verifyFileExist")
    public JSONObject verifyFileExist(@RequestBody Map<String, Object> map) throws NoSuchAlgorithmException {
        String md5 = (String) map.get("md5");
        String filename = (String) map.get("filename");
        String type = (String) map.get("type");
        JSONObject jsonObject = new JSONObject();

        File file = new File(outPath + File.separator + md5 + File.separator + filename + "." + type);
        if (!file.exists()) {
            System.out.println("文件不存在");
            // 1代表文件不存在，直接传
            jsonObject.put("code", "1");
            return jsonObject;
        }
        System.out.println("文件存在");

        // 找到对应文件，做一个MD5，然后根据传过来的MD5进行比对
        MessageDigest md = MessageDigest.getInstance("MD5");
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
            byte[] buffer = new byte[1024];
            int read;
            while ((read = bis.read(buffer)) != -1) {
                md.update(buffer, 0, read);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] digest = md.digest();
        String tempMD5 = String.format("%032x", new BigInteger(1, digest));
        if (tempMD5.equals(md5)) {
            jsonObject.put("code", "1");
        } else {
            jsonObject.put("code", "0");
        }

        return jsonObject;
    }

    @RequestMapping("/mergeFile")
    public String mergeFile(@RequestBody Map<String, Object> map) throws IOException {
        String filename = (String) map.get("fileName");
        String type = (String) map.get("type");
        int chunks = (int) map.get("chunks");
        String md5 = (String) map.get("md5");
        Date date = new Date(System.currentTimeMillis());
        System.out.println("开始执行文件合并,当前时间为 ：" + date);
        File makeDir = new File(outPath + File.separator + md5);
        if (makeDir.mkdir()) {
            // 最终汇总的文件
            String finalPath = outPath + File.separator + md5 + File.separator + filename + "." + type;
            File outFile = new File(finalPath);
            for (int i = 1; i <= chunks; i++) {
                String findName = String.valueOf(i);
                File partFile = new File(path + File.separator + findName);
                FileOutputStream fileOutputStream = new FileOutputStream(outFile, true);
                FileUtils.copyFile(partFile, fileOutputStream);
                fileOutputStream.close();
            }
            File file = new File(path);
            FileUtils.deleteDirectory(file);
        }
        date = new Date(System.currentTimeMillis());
        System.out.println("文件合并完成, 当前时间为：" + date);
        return "";
    }

    @RequestMapping("/uploadSuccess")
    public JSONObject uploadSuccess(@RequestBody Map<String, Object> map) {
        // 路径名
        String md5 = (String) map.get("md5");
        String filename = (String) map.get("name");
        int chunks = (int) map.get("chunks");

        JSONObject jsonObject = new JSONObject();
        File file = new File(path);
        File[] array = file.listFiles();
        List<String> list = new ArrayList<>();
        if (array != null) {
            for (File value : array) {
                if (value.isFile()) {
                    list.add(value.getName());
                }
            }
        }
        System.out.println("验证文件完整性完成");
        if (list.size() == chunks) {
            jsonObject.put("code", 0);
        } else {
            jsonObject.put("code", 1);
        }
        jsonObject.put("data", list);
        return jsonObject;
    }
}