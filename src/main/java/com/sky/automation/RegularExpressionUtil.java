package com.sky.automation;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegularExpressionUtil {

    final String matchFormat = "\\$\\{(.*?)\\}";
    Map<String, String> replaceTextMap;

    String last = null;

    private static RegularExpressionUtil util = new RegularExpressionUtil();

    public RegularExpressionUtil(){}

    public RegularExpressionUtil(Map replaceTextMap) {
        this.replaceTextMap = replaceTextMap;
    }

    public String match(String text) {
        this.last = text;
        Pattern pattern = Pattern.compile(matchFormat);
        Matcher matcher = pattern.matcher(last);
        if (matcher.find()) {
            last = matcher.replaceFirst(replaceTextMap.get(matcher.group(1)));
            match(last);
        }
        return last;
    }

    public Map<String, String> getReplaceTextMap() {
        return replaceTextMap;
    }

    /*public static void main(String[] args) {
        RegularExpressionUtil util = new RegularExpressionUtil();
        Map<String, String> textMap = util.getReplaceTextMap();
        textMap.put("a", "zhangsan");
        textMap.put("bizDate", "天气");
        String text = "update demo1 set ptime= a${a} dsd，update demo1 set ptime=${bizDate} sd";
        System.out.println(util.match(text));
    }*/

}
