package com.example.springboot_vue.utils;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.plugin.table.LoopRowTableRenderPolicy;
import com.example.springboot_vue.pojo.Demo;
import com.spire.doc.FileFormat;
import com.spire.doc.documents.TextSelection;
import org.apache.commons.io.FileUtils;

import com.spire.doc.Document;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.*;
import java.util.*;
import java.util.List;

public class WordExport {

    public static void main(String[] args) throws IOException {
        WordExport wordExport = new WordExport();
        wordExport.testLoop();
        String path = "D:\\git\\result.docx";
        replaceTextFont(path);
        copyFile("result.docx", "D:\\git\\new.docx");
    }

    public static String outPath = "D:\\git\\result.docx";

    public static void replaceTextFont(String filePath) throws IOException {
        //创建一个Document实例
        Document document = new Document();
        //加载Word文档
        document.loadFromFile(filePath);

        String font2 = "方正楷体_GBK";
        String str2 = "GDP增速,税收收入增速,全体居民人均可支配收入增速,规上工业增加值增速,规上工业企业利润增速,固定资产投资增速,制造业投资增速," +
                "社会消费品零售总额增速,招商签约项目数（20亿元以上）,政府性债务化解成效,地方财政收入增速,税收收入占比,第一产业增加值增速,规上工业亩均税收," +
                "第三产业增加值增速,数字经济核心产业增加值增速,房地产开发投资增速,民间投资（不含房地产）增速,高技术产业投资占比,进出口总额增速,市场主体新发展率," +
                "民营经济增加值增速,民营经济增加值占比,招商项目资金到位额增速,全年目标为增长,以上";
        String[] strings2 = str2.split(",");
        setData(document, strings2, font2);

        String str = "A档区县有,B档区县有,C档区县有,D档区县有,E档区县有";
        String font = "方正黑体_GBK";
        String[] strings1 = str.split(",");
        setData(document, strings1, font);

        String font3 = "Times New Roman";
        String str3 = "%,-,GDP,.";
        String[] strings3 = str3.split(",");
        setData(document, strings3, font3);

        // 保存结果文档
        document.saveToFile("result.docx", FileFormat.Docx);
    }

    public static void setData(Document document, String[] strings, String font) {
        for (String string : strings) {
            TextSelection[] text = document.findAllString(string, false, true);
            if (text != null) {
                for (TextSelection selection : text) {
                    System.out.println("string = " + string + "  选中的字符为：" + selection.getSelectedText() + "  font = " + font);
                    selection.getAsOneRange(false).getCharacterFormat().setFontName(font);
                }
            }
        }
    }

    public static void copyFile(String sourcePath, String destinationPath) {
        try {
            File sourceFile = new File(sourcePath);
            File destinationFile = new File(destinationPath);

            // 创建输入流和输出流
            FileInputStream inputStream = new FileInputStream(sourceFile);
            FileOutputStream outputStream = new FileOutputStream(destinationFile);

            // 缓冲区大小
            byte[] buffer = new byte[4096];
            int bytesRead;

            // 逐个读取字节，并写入目标文件
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            // 关闭流
            inputStream.close();
            outputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
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
