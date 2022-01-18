package com.novel;

import tools.strings.MySubString;
import tools.utils.Html;

import java.io.*;
import java.util.Scanner;

public class DownNovel implements Runnable {
    private String path;
    private String pathOfDown;
    private String nameOfDown;
    private String linkOfDown;
    private int orderOfDown;
    private final int timeOfWait = 300;
    // 间隔越短，下载越快，但访问失败的概率越高

    public DownNovel(String pathOfDown, String nameOfDown, String linkOfDown, int i) {
        this.path = pathOfDown;
        this.pathOfDown = this.path + "/" + nameOfDown;
        this.nameOfDown = nameOfDown;
        this.linkOfDown = linkOfDown;
        this.orderOfDown = i + 1;
    }

    @Override
    public void run() {
        File file = null;
        BufferedWriter bw = null;
        Scanner sc = new Scanner(System.in);

        try {
            Thread.currentThread().sleep(orderOfDown * timeOfWait);
            System.out.println("开始下载: " + nameOfDown);
            file = new File(pathOfDown + ".txt");
            //先判断文件夹是否存在
            isExists(file);
            bw = new BufferedWriter(new FileWriter(file));

            String codeOfWeb = Html.htmlGet(linkOfDown, "gb2312");

            // 内容截取
            String textOfChapter = MySubString.cut(codeOfWeb, "<div id=\"content\">", "<div class=\"clear\"></div>");
            textOfChapter = "  " + MySubString.cut(textOfChapter, "</div>", "<div style").trim();
            textOfChapter = textOfChapter.replaceAll("<br/><br/>", "\n  ")
                    .replaceAll("<br/>", "\n  ")
                    .replaceAll("a", "");

            if (textOfChapter.trim().equals("")) {
                System.out.println(String.format("%-40s", nameOfDown) + "  失败！为你重新下载");
                new Thread(new DownNovel(path, nameOfDown, linkOfDown, 14)).start();
            } else {
                bw.write(textOfChapter);
                System.out.println(String.format("%-40s", nameOfDown) + "  下载完成");
            }

        } catch (Exception e) {
            System.out.println(String.format("%-40s", nameOfDown) + "  下载失败！");
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    System.out.println(String.format("%-40s", nameOfDown) + "  下载失败！");
                }
            }
        }

    }

    private void isExists(File file) {
        if (!file.exists()) {
            //先得到文件的上级目录，并创建上级目录，再创建文件
            if (!file.getParentFile().exists()) {
                if (!file.getParentFile().getParentFile().exists()) {
                    if (!file.getParentFile().getParentFile().getParentFile().exists()) {
                        file.getParentFile().getParentFile().getParentFile().getParentFile().mkdir();
                    }
                    file.getParentFile().getParentFile().getParentFile().mkdir();
                }
                file.getParentFile().getParentFile().mkdir();
            }
            file.getParentFile().mkdir();
        }
    }

}
