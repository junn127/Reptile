package com.novel;

import string.MySubString;
import utils.SslUtils;

import java.io.*;
import java.net.URL;
import java.util.Scanner;

public class DownNovel implements Runnable {
    private String pathOfDown;
    private String nameOfDown;
    private String linkOfDown;

    public DownNovel(String pathOfDown, String nameOfDown, String linkOfDown) {
        this.pathOfDown = pathOfDown + "/" + nameOfDown;
        this.nameOfDown = nameOfDown;
        this.linkOfDown = linkOfDown;
    }

    @Override
    public void run() {
        System.out.println("开始下载: " + nameOfDown);
        BufferedReader br = null;
        StringBuffer codeOfWeb = new StringBuffer();
        File file = null;
        BufferedWriter bw = null;
        Scanner sc = new Scanner(System.in);

        try {
            URL url = new URL(linkOfDown);
            SslUtils.ignoreSsl();
//            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("118.113.204.28", 63342)); 动态代理类
            br = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream(), "gb2312"));
            file = new File(pathOfDown + ".txt");

            if (!file.exists()) {
                //先得到文件的上级目录，并创建上级目录，在创建文件
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

            bw = new BufferedWriter(new FileWriter(file));

            String lineOfCode;
            while ((lineOfCode = br.readLine()) != null) {
                codeOfWeb.append(lineOfCode + "\n");
            }

            String textOfChapter = new MySubString(codeOfWeb.toString(),
                    "<div id=\"content\">", "<div class=\"clear\"></div>").toString();
            textOfChapter = "  " + new MySubString(textOfChapter, "</div>", "<div style").toString().trim();
            textOfChapter = textOfChapter.replaceAll("<br/><br/>", "\n  ")
                    .replaceAll("<br/>", "\n  ")
                    .replaceAll("a", "");

            bw.write(textOfChapter);
            System.out.println(String.format("%-40s", nameOfDown) + "  下载完成！");

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
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    System.out.println(String.format("%-40s", nameOfDown) + "  下载失败！");
                }
            }
        }

    }
}
