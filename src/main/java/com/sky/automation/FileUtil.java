package com.sky.automation;

import java.io.*;
import java.net.URL;
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
    private static String enableDefault = (String) props.get("template.enable.default");


    public static void readTemplateConfig() throws IOException {
        InputStream in = FileUtil.class.getClassLoader().getResourceAsStream("template.properties");
        props = new Properties();
        props.load(in);
    }

    public static Properties getProps() {
        return props;
    }

    public static List<String> listAllTemplate() throws IOException {
        File file = null;
        if (enableDefault.equals("true")) {
            URL resource = FileUtil.class.getClassLoader().getResource("demo");
            file = new File(resource.getPath());
        } else {
            file = new File(templateFilePath);
        }
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
    public static BufferedReader readTemplate(String filePath) throws FileNotFoundException {
        return new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
    }

    // 将文件写出到目标路径
    public static void outputFile(String parentDir, String fileName, String content) throws IOException {
        String dirPath = outputFilePath + "/" + parentDir;
        String filePath = outputFilePath + "/" + fileName;
        File targetParentDir = new File(dirPath);
        if (!targetParentDir.exists()) {
            targetParentDir.mkdirs();
        }
        FileOutputStream outputStream = new FileOutputStream(filePath);
        outputStream.write(content.getBytes());
        outputStream.flush();
        outputStream.close();
    }
}
