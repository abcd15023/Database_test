package com.zun.database_test.hua;

/**
 * 字符串操作工具包
 */
public class StringUtils {

    private StringUtils() {}

    /**
     * 判断给定字符串是否空白串。 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
     */
    public static boolean isEmpty(String input) {
        if (input == null || "".equals(input))
            return true;

        for (int index = 0; index < input.length(); index++) {
            char c = input.charAt(index);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    /**
     * 字符串转整数
     */
    public static int toInt(String str, int defValue) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            L.e(L.LEVEL_TEST,"string to int error is " + e);
        }
        return defValue;
    }

    /**
     * 对象转整数
     */
    public static int toInt(Object obj) {
        if (obj == null){
            return 0;
        }
        return toInt(obj.toString(), 0);
    }

    /**
     * 对象转整数
     */
    public static long toLong(String obj) {
        try {
            return Long.parseLong(obj);
        } catch (Exception e) {
            L.e(L.LEVEL_TEST,"string to long error is " + e);
        }
        return 0;
    }

    /**
     * 字符串转布尔值
     */
    public static boolean toBool(String b) {
        try {
            return Boolean.parseBoolean(b);
        } catch (Exception e) {
            L.e(L.LEVEL_TEST,"string to boolean error is " + e);
        }
        return false;
    }

    /**
     * 转码处理
     */
    public static String stringEncodeUTF8(String str) {
        String dstStr = "";
        try {
            dstStr = java.net.URLEncoder.encode(str, "utf-8");
        } catch (Exception e) {
            L.e(L.LEVEL_TEST,"string encode to utf8 error is " + e);
        }

        return dstStr;
    }

    /**
     * 判断是否全为空格
     */
    public static boolean isAllSpace(String str) {

        for (int index = 0; index < str.length(); index++) {
            char tempChar = str.charAt(index);
            if (tempChar != 32) {
                return false;//有一个不为空格则显示
            }
        }
        return true;
    }

    public static double toDouble(String score) {
        try {
            return Double.parseDouble(score);
        } catch (Exception e) {
            L.e(L.LEVEL_TEST,"string to double error is " + e);
        }
        return -1;
    }
}
