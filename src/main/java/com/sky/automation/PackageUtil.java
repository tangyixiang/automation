package com.sky.automation;

import java.util.HashMap;
import java.util.Map;

public class PackageUtil {

    public static Map<String,String> packageMap = new HashMap<>();

    public static void add(String modelName,String packageName){
        if(packageMap.get(modelName) == null){
            packageMap.put(modelName,packageName);
        }
    }

    public static String get(String modelName){
        return packageMap.get(modelName);
    }

}
