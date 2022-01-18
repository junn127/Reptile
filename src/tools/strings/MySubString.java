package tools.strings;

public class MySubString {

    public static String cut(String src, String startsWith, String endsWith) {
        // 一般截取，endsWith 为前段截取后部分搜索第一个
        int begin = 0;
        int end = 0;

        begin = src.indexOf(startsWith);
        begin += startsWith.length();
        end = src.indexOf(endsWith, begin);

        String result;
        if (begin == -1 || end == -1 || begin == 0) {
            result = "";
        } else {
            result = src.substring(begin, end);
        }

        return result;
    }

    public static String cut(String src, String startsWith, String endsWith, int type) {
        int begin = 0;
        int end = 0;

        if (type == 1) {
            // 截取文件后缀 “hello.exe.txt” ".", "" 得 "txt"
            begin = src.lastIndexOf(startsWith);
            begin += startsWith.length();
            end = src.lastIndexOf(endsWith);
        }
        if (type == 2) {
            // 截取文件名字 “hello.exe.txt” "", "." 得 "hello.exe"
            begin = src.indexOf(startsWith);
            if (begin != -1) {
                begin += startsWith.length();
            }
            end = src.lastIndexOf(endsWith);
        }

        String result;
        if (begin == -1 || end == -1) {
            result = "";
        } else if (begin == end) {
            result = "";
        } else {
            result = src.substring(begin, end);
        }

        return result;
    }

}

