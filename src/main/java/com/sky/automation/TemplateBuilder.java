package com.sky.automation;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TemplateBuilder {

    public static Map<String, Object> attributeMap;

    public static void init() {
        String entityName = FileUtil.getProps().getProperty("template.entity.classname");
        String lowName = FileUtil.getProps().getProperty("template.entity.lowName");
        attributeMap = new HashMap<>();
        attributeMap.put("entity", entityName);
        attributeMap.put("lowentity", lowName);
        attributeMap.put("modelname", "");
        attributeMap.put("requestpath", "/test");
    }

    public static void main(String[] args) throws IOException{
        init();
        List<String> allTemplate = FileUtil.listAllTemplate();
        RegularExpressionUtil expressionUtil = new RegularExpressionUtil(attributeMap);
        for (String templateFilePath : allTemplate) {
            String[] pathArray = templateFilePath.split("\\\\");
            String fileName = attributeMap.get("entity") + pathArray[pathArray.length - 1];
            String modelName = pathArray[pathArray.length - 1].replace(".txt","");
            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader reader = FileUtil.readTemplate(templateFilePath);
            String line = null;
            boolean first = true;
            while ((line = reader.readLine()) != null) {
                String formatLine = expressionUtil.match(line);
                if(first){
                    String packageName = formatLine.replace("package ","").replace(";","");
                    PackageUtil.add(modelName,packageName);
                    first = false;
                }
                stringBuilder.append(formatLine + "\r\n");
            }
            String parentDir = PackageUtil.get(modelName).replace(".","/");
            String targetFile = parentDir  + "/" + fileName;
            FileUtil.outputFile(parentDir, targetFile, stringBuilder.toString());
        }

    }
}
