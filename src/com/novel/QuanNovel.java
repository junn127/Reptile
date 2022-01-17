package com.novel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Scanner;

import utils.SslUtils;
import string.MySubString;

public class QuanNovel {
    private String nameOfWeb = "全小说网";
    private final String homeLink = "https://quanxiaoshuo.com";
    private String currentLink;
    private String pathOfDown;

    public QuanNovel(String pathOfDown) {
        this.pathOfDown = pathOfDown;
    }

    public void menu() {
        currentLink = homeLink;
        System.out.println();
        System.out.println("你当前访问的是: " + nameOfWeb);
//        System.out.println("主页链接为: " + homeLink);
        System.out.println("下载地址: " + pathOfDown);
        System.out.println("请选择目录对应序号: ");
        System.out.println("1.搜索小说");
        System.out.println("2.玄幻小说");
        System.out.println("3.言情小说");
        System.out.println("4.网游小说\n");
        Scanner sc = new Scanner(System.in);
        switch (sc.nextInt()) {
            case 1:
                search();
                break;
            case 2:
                xuanHuan();
                break;
            case 3:
                xiuZhen();
                break;
            case 4:
                wangYou();
                break;
            default:
                System.out.println("请重新输入有效指令！");
                menu();
        }
    }

    private void search() {
        StringBuffer codeOfWeb = new StringBuffer();
        Scanner sc = new Scanner(System.in);
        BufferedReader br = null;
        System.out.println("请输入小说名字或作者: ");
        currentLink = homeLink + "/s_" + sc.next();
        System.out.println("搜索中...");
        try {
            URL url = new URL(currentLink);
            SslUtils.ignoreSsl();
            br = new BufferedReader(new InputStreamReader(url.openStream(), "gb2312"));

            String lineOfCode;
            while ((lineOfCode = br.readLine()) != null) {
                codeOfWeb.append(lineOfCode + "\n");
            }

            String code = new MySubString(codeOfWeb.toString(),
                    "<div class=\"clear\"></div>", "<div class=\"clear\"></div>").toString();
            code = new MySubString(code, "list_content", "", 2).toString();
            if (code == "") {
                System.out.println("搜索结果为空，宁少写不错写");
                search();
                return;
            }

            String[] listOfResult = code.split("list_content");

            System.out.println("搜索完毕，共搜索到" + listOfResult.length + "个结果\n");
            System.out.println(String.format("%-16s", "书名") + String.format("%-16s", "作者") +
                    String.format("%-16s", "小说代码") + String.format("%-16s", "更新时间"));
            System.out.println("----------------------------------------------------------------");

            for (int i = 0; i < listOfResult.length; i++) {
                String codeOfBook = new MySubString(listOfResult[i], "/", "/").toString();
                String nameOfBook = new MySubString(listOfResult[i], codeOfBook + "/\">", "</a>").toString();
                String timeOfBook = new MySubString(listOfResult[i], "\"cc5\">", "</li>").toString();
                String authorOfBook = new MySubString(listOfResult[i], "cc4\">", "</a></li>").toString();
                authorOfBook = new MySubString(authorOfBook, "\">", "", 1).toString();
                System.out.println(String.format("%-16s", nameOfBook) + String.format("%-16s", authorOfBook) +
                        String.format("%-16s", codeOfBook) + String.format("%-16s", timeOfBook));
            }

            System.out.println("\n请输入复制的小说代码: ");
            while (true) {
                String currentBook = sc.next();
                if (code.indexOf(currentBook) != -1) {
                    currentLink = homeLink + "/" + currentBook.trim() + "/";
                    bookPage();
                    break;
                } else {
                    System.out.println("小说代码有误！请重新输入: ");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void xuanHuan() {
        currentLink = homeLink + "/xuanhuan/";
        BufferedReader br = null;
        StringBuffer codeOfWeb = new StringBuffer();
        Scanner sc = new Scanner(System.in);
        try {
            URL url = new URL(currentLink);
            SslUtils.ignoreSsl();
            br = new BufferedReader(new InputStreamReader(url.openStream(), "gb2312"));

            String lineOfCode;
            while ((lineOfCode = br.readLine()) != null) {
                codeOfWeb.append(lineOfCode + "\n");
            }

            String code = new MySubString(codeOfWeb.toString(),
                    "<div class=\"clear\"></div>", "<div class=\"clear\"></div>").toString();
            code = new MySubString(code, "list_content", "", 2).toString();
            if (code == "") {
                System.out.println("结果为空！");
                return;
            }

            String[] listOfResult = code.split("list_content");

            System.out.println("仅展示第一页，共" + listOfResult.length + "个结果\n");
            System.out.println(String.format("%-16s", "书名") + String.format("%-16s", "作者") +
                    String.format("%-16s", "小说代码") + String.format("%-16s", "更新时间"));
            System.out.println("----------------------------------------------------------------");

            for (int i = 0; i < listOfResult.length; i++) {
                String codeOfBook = new MySubString(listOfResult[i], "/", "/").toString();
                String nameOfBook = new MySubString(listOfResult[i], codeOfBook + "/\">", "</a>").toString();
                String timeOfBook = new MySubString(listOfResult[i], "\"cc5\">", "</li>").toString();
                String authorOfBook = new MySubString(listOfResult[i], "cc4\">", "</a></li>").toString();
                authorOfBook = new MySubString(authorOfBook, "\">", "", 1).toString();
                System.out.println(String.format("%-16s", nameOfBook) + String.format("%-16s", authorOfBook) +
                        String.format("%-16s", codeOfBook) + String.format("%-16s", timeOfBook));
            }

            System.out.println("\n请输入复制的小说代码: ");
            while (true) {
                String currentBook = sc.next();
                if (code.indexOf(currentBook) != -1) {
                    currentLink = homeLink + "/" + currentBook.trim() + "/";
                    bookPage();
                    break;
                } else {
                    System.out.println("小说代码有误！请重新输入: ");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void xiuZhen() {
        currentLink = homeLink + "/yanqing/";
        BufferedReader br = null;
        StringBuffer codeOfWeb = new StringBuffer();
        Scanner sc = new Scanner(System.in);
        try {
            URL url = new URL(currentLink);
            SslUtils.ignoreSsl();
            br = new BufferedReader(new InputStreamReader(url.openStream(), "gb2312"));

            String lineOfCode;
            while ((lineOfCode = br.readLine()) != null) {
                codeOfWeb.append(lineOfCode + "\n");
            }

            String code = new MySubString(codeOfWeb.toString(),
                    "<div class=\"clear\"></div>", "<div class=\"clear\"></div>").toString();
            code = new MySubString(code, "list_content", "", 2).toString();
            if (code == "") {
                System.out.println("结果为空！");
                return;
            }

            String[] listOfResult = code.split("list_content");

            System.out.println("仅展示第一页，共" + listOfResult.length + "个结果\n");
            System.out.println(String.format("%-16s", "书名") + String.format("%-16s", "作者") +
                    String.format("%-16s", "小说代码") + String.format("%-16s", "更新时间"));
            System.out.println("----------------------------------------------------------------");

            for (int i = 0; i < listOfResult.length; i++) {
                String codeOfBook = new MySubString(listOfResult[i], "/", "/").toString();
                String nameOfBook = new MySubString(listOfResult[i], codeOfBook + "/\">", "</a>").toString();
                String timeOfBook = new MySubString(listOfResult[i], "\"cc5\">", "</li>").toString();
                String authorOfBook = new MySubString(listOfResult[i], "cc4\">", "</a></li>").toString();
                authorOfBook = new MySubString(authorOfBook, "\">", "", 1).toString();
                System.out.println(String.format("%-16s", nameOfBook) + String.format("%-16s", authorOfBook) +
                        String.format("%-16s", codeOfBook) + String.format("%-16s", timeOfBook));
            }

            System.out.println("\n请输入复制的小说代码: ");
            while (true) {
                String currentBook = sc.next();
                if (code.indexOf(currentBook) != -1) {
                    currentLink = homeLink + "/" + currentBook.trim() + "/";
                    bookPage();
                    break;
                } else {
                    System.out.println("小说代码有误！请重新输入: ");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void wangYou() {
        currentLink = homeLink + "/youxi/";
        BufferedReader br = null;
        StringBuffer codeOfWeb = new StringBuffer();
        Scanner sc = new Scanner(System.in);
        try {
            URL url = new URL(currentLink);
            SslUtils.ignoreSsl();
            br = new BufferedReader(new InputStreamReader(url.openStream(), "gb2312"));

            String lineOfCode;
            while ((lineOfCode = br.readLine()) != null) {
                codeOfWeb.append(lineOfCode + "\n");
            }

            String code = new MySubString(codeOfWeb.toString(),
                    "<div class=\"clear\"></div>", "<div class=\"clear\"></div>").toString();
            code = new MySubString(code, "list_content", "", 2).toString();
            if (code == "") {
                System.out.println("结果为空！");
                return;
            }

            String[] listOfResult = code.split("list_content");

            System.out.println("仅展示第一页，共" + listOfResult.length + "个结果\n");
            System.out.println(String.format("%-16s", "书名") + String.format("%-16s", "作者") +
                    String.format("%-16s", "小说代码") + String.format("%-16s", "更新时间"));
            System.out.println("----------------------------------------------------------------");

            for (int i = 0; i < listOfResult.length; i++) {
                String codeOfBook = new MySubString(listOfResult[i], "/", "/").toString();
                String nameOfBook = new MySubString(listOfResult[i], codeOfBook + "/\">", "</a>").toString();
                String timeOfBook = new MySubString(listOfResult[i], "\"cc5\">", "</li>").toString();
                String authorOfBook = new MySubString(listOfResult[i], "cc4\">", "</a></li>").toString();
                authorOfBook = new MySubString(authorOfBook, "\">", "", 1).toString();
                System.out.println(String.format("%-16s", nameOfBook) + String.format("%-16s", authorOfBook) +
                        String.format("%-16s", codeOfBook) + String.format("%-16s", timeOfBook));
            }

            System.out.println("\n请输入复制的小说代码: ");
            while (true) {
                String currentBook = sc.next();
                if (code.indexOf(currentBook) != -1) {
                    currentLink = homeLink + "/" + currentBook.trim() + "/";
                    bookPage();
                    break;
                } else {
                    System.out.println("小说代码有误！请重新输入: ");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void bookPage() {
        BufferedReader br = null;
        StringBuffer codeOfWeb = new StringBuffer();
        Scanner sc = new Scanner(System.in);
        try {
            URL url = new URL(currentLink);
            SslUtils.ignoreSsl();
            br = new BufferedReader(new InputStreamReader(url.openStream(), "gb2312"));

            String lineOfCode;
            while ((lineOfCode = br.readLine()) != null) {
                codeOfWeb.append(lineOfCode + "\n");
            }

            //小说主页信息截取
            String nameOfBook = new MySubString(codeOfWeb.toString(), "<div class=\"text t_c\">", "</div>").toString();
            nameOfBook = new MySubString(nameOfBook, "\">", "</a>").toString();
            String aboutOfBook = new MySubString(codeOfWeb.toString(), "<div class=\"text\">", "<div class=\"clear\"></div>").toString();
            String typeOfBook = new MySubString(aboutOfBook, "类型", "</div>").toString();
            typeOfBook = new MySubString(typeOfBook, "\">", "</font>").toString();
            String authorOfBook = new MySubString(aboutOfBook, "作 者", "</div>").toString();
            authorOfBook = new MySubString(authorOfBook, "\">", "</a>").toString();
            String newestOfChapter = new MySubString(aboutOfBook, "title=\"", "\">").toString();
            String introOfBook = new MySubString(codeOfWeb.toString(), "<div class=\"clear\"></div>", "<div class=\"clear\"></div>").toString();
            introOfBook = new MySubString(introOfBook, "<div class=\"desc\">", "<br/>").toString();
            introOfBook = introOfBook.trim();

            //打印基本信息
            System.out.println("书名: " + nameOfBook);
            System.out.println("类型: " + typeOfBook);
            System.out.println("作者: " + authorOfBook);
            System.out.println("最新章节: " + newestOfChapter);
            System.out.println(introOfBook);

            String code = new MySubString(codeOfWeb.toString(),
                    "<div class=\"chapter\">", "<div class=\"clear\"></div>").toString();
            if (code == "") {
                System.out.println("结果为空！");
                return;
            }

            String[] listOfResult = code.split("chapter");

            System.out.println("\n请输入需要下载的章数（大于小说总章数则全部下载）: ");
            int numOfDown;
            while (true) {
                numOfDown = sc.nextInt();
                if (numOfDown <= 0) {
                    System.out.println("输入不正确！请重新输入: ");
                } else {
                    System.out.println("开始下载...");
                    break;
                }
            }
            System.out.println("------------------------------------------------\n");

            for (int i = 0; i < listOfResult.length && i < numOfDown; i++) {
                String linkOfDown = homeLink + new MySubString(listOfResult[i], "href=\"", "\"").toString();
                String nameOfDown = new MySubString(listOfResult[i], "title=\"", "\"").toString();
//                nameOfDown = nameOfDown.replaceAll(" ", "");
                new Thread(new DownNovel(pathOfDown + "\\" + nameOfBook, nameOfDown, linkOfDown)).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
