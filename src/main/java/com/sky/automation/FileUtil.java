package com.sky.automation;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class FileUtil {

    static {
        try {
            readTemplateConfig();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Properties props;
    private static String templateFilePath = (String) props.get("template.filepath");
    private static String outputFilePath = (String) props.get("template.out.filepath");
    private static String function = (String) props.get("template.entity.classname");
    private static String basePackage = (String) props.get("template.project.package");


    public static void readTemplateConfig() throws IOException {
        InputStream in = FileUtil.class.getClassLoader().getResourceAsStream("template.properties");
        props = new Properties();
        props.load(in);
    }

    public static List<String> listAllTemplate() throws IOException {
        File file = new File(templateFilePath);
        if (file.exists()) {
            List<String> fileList = new ArrayList<>();
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (File templateFile : files) {
                    fileList.add(templateFile.getAbsolutePath());
                }
                return fileList;
            }
        } else {
            file.createNewFile();
        }
        return null;
    }

    // 读取模板文件
    public BufferedReader readTemplate(String filePath) throws FileNotFoundException {
        return new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
    }

    // 将文件写出到目标路径
    public static void outputFile(String targetPath, String content) throws IOException {
        FileOutputStream outputStream = new FileOutputStream(targetPath);
        outputStream.write(content.getBytes());
        outputStream.flush();
        outputStream.close();

    }

}
