package com.example.springboot_vue.utils;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.plugin.table.LoopRowTableRenderPolicy;
import com.example.springboot_vue.pojo.Demo;
import org.apache.commons.io.FileUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordExport {

    public static void main(String[] args) throws IOException {
        WordExport wordExport = new WordExport();
        wordExport.testLoop();
    }

    public void testLoop() {
        // 模板文件路径
        String templatePath = "D:\\0_代码\\test.docx";
        // 输出文件路径
        String outputPath = "D:\\0_代码\\result.docx";

        File file = new File(templatePath);
        try (XWPFDocument document = new XWPFDocument()) {
            // Create a list of data
            List<String> items = new ArrayList<>();
            items.add("Item 1");
            items.add("Item 2");
            items.add("Item 3");

            // Load the template Word document
            try (XWPFDocument template = new XWPFDocument(FileUtils.openInputStream(file))) {
                // Get the template paragraphs
                List<XWPFParagraph> paragraphs = template.getParagraphs();

                // Loop through the paragraphs and replace placeholders
                for (XWPFParagraph paragraph : paragraphs) {
                    String text = paragraph.getText();

                    // Check if the paragraph contains a placeholder
                    if (text.contains("XX") && text.contains("*")) {
                        // Replace the placeholders with data
                        for (String item : items) {
                            String replacedText = text.replace("XX", item).replace("*", "your_value");
                            XWPFParagraph newParagraph = document.createParagraph();
                            XWPFRun run = newParagraph.createRun();
                            run.setText(replacedText);
                        }
                    } else {
                        if (text.contains("{{first}}")) {
                            String replacedText = text.replace("{{first}}", "join");
                            XWPFParagraph newParagraph = document.createParagraph();
                            XWPFRun run = newParagraph.createRun();
                            run.setText(replacedText);
                        } else {
                            // Copy the paragraph as it is
                            XWPFParagraph newParagraph = document.createParagraph();
                            newParagraph.getCTP().setPPr(paragraph.getCTP().getPPr());
                            for (XWPFRun run : paragraph.getRuns()) {
                                XWPFRun newRun = newParagraph.createRun();
                                newRun.getCTR().setRPr(run.getCTR().getRPr());
                                newRun.setText(run.getText(0));
                            }
                        }

                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Save the filled Word document
            try (FileOutputStream fileOut = new FileOutputStream(outputPath)) {
                document.write(fileOut);
                System.out.println("Word document has been created successfully!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void test() throws IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("first","1");
        params.put("second","2");
        params.put("third","3");
//        ClassPathResource resource = new ClassPathResource("file/test.doc");
        File file = new File("D:\\0_代码\\test.docx");

        int size = 3;
        List<Demo> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Demo demo = new Demo(i, "名字" + i, "account" + i, "password" + i);
            list.add(demo);
        }

        params.put("demo", list);

        // 创建行循环策略
        LoopRowTableRenderPolicy rowTableRenderPolicy = new LoopRowTableRenderPolicy();
        // 告诉模板引擎，要在employees做行循环,绑定行循环策略
        Configure configure = Configure.builder().bind("demo", rowTableRenderPolicy).build();

        // 数据填充
        XWPFTemplate template = XWPFTemplate.compile(file, configure).render(params);
        String docOutPath = "D:\\0_代码\\result.docx";
        OutputStream outputStream = new FileOutputStream(docOutPath);

        template.write(outputStream);
        template.close();
    }

    public void test1() {
        try {
            FileInputStream templateFile = new FileInputStream("D:\\0_代码\\test.doc");
            XWPFDocument document = new XWPFDocument(templateFile);

            Map<String, String> placeholderMap = new HashMap<>();
            placeholderMap.put("first", "John");
            placeholderMap.put("second", "25");
            placeholderMap.put("foreach", "3");

            List<XWPFParagraph> paragraphs = document.getParagraphs();

            for (XWPFParagraph paragraph : paragraphs) {
                List<XWPFRun> runs = paragraph.getRuns();

                for (XWPFRun run : runs) {
                    String text = run.getText(0);

                    for (Map.Entry<String, String> entry : placeholderMap.entrySet()) {
                        String placeholder = entry.getKey();
                        String replacement = entry.getValue();

                        if (text != null && text.contains(placeholder)) {
                            text = text.replace(placeholder, replacement);
                            run.setText(text, 0);
                        }
                    }
                }
            }

            FileOutputStream outputFile = new FileOutputStream("D:\\0_代码\\result.doc");
            document.write(outputFile);

            outputFile.close();
            templateFile.close();
            document.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
