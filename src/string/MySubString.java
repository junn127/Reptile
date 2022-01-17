package string;

public class MySubString {

    private String src;
    private String startsWith;
    private String endsWith;

    private int begin;
    private int end;

    public MySubString(String src, String startsWith, String endsWith) {
        // 一般截取，endsWith 为前段截取后部分搜索第一个
        this.src = src;
        this.startsWith = startsWith;
        this.endsWith = endsWith;
        this.begin = src.indexOf(startsWith);
        begin += startsWith.length();
        this.end = src.indexOf(endsWith, begin);
    }

    public MySubString(String src, String startsWith, String endsWith, int type) {
        this.src = src;
        this.startsWith = startsWith;
        this.endsWith = endsWith;
        if (type == 1) {
            // 截取文件后缀 “hello.exe.txt” ".", "" 得 "txt"
            this.begin = src.lastIndexOf(startsWith);
            begin += startsWith.length();
            this.end = src.lastIndexOf(endsWith);
        }
        if (type == 2) {
            // 截取文件名字 “hello.exe.txt” "", "." 得 "hello.exe"
            this.begin = src.indexOf(startsWith);
            if(begin!=-1) {
                begin += startsWith.length();
            }
            this.end = src.lastIndexOf(endsWith);
        }
    }

    @Override
    public String toString() {
        String result;
        if (!isContain()) {
            result = "";
        } else if (begin == end) {
            result = "";
        } else {
            result = src.substring(begin, end);
        }
        return result;
    }

    private boolean isContain() {
        return begin != -1 && end != -1;
    }

}

