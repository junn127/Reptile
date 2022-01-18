package com.novel;

import java.util.HashMap;
import java.util.Scanner;

import tools.utils.Html;
import tools.strings.MySubString;

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
        HashMap<String, String> cata = new HashMap<String, String>();

        System.out.println("\n你当前访问的是: " + nameOfWeb);
//        System.out.println("主页链接为: " + homeLink);
        System.out.println("下载地址: " + pathOfDown);
        System.out.println("目录获取中...");

        String codeOfWeb = Html.htmlGet(homeLink, "gb2312");
        String code = MySubString.cut(codeOfWeb, "首页", "div class");
        code = MySubString.cut(code, "<a href", "</ul>");

        String[] listOfResult = code.split("<a href");

        System.out.println("获取完毕！\n\n目录序号如下: ");
        System.out.println("1.搜索");
        cata.put("1", "/s_");

        for (int i = 0; i < listOfResult.length; i++) {
            String title = MySubString.cut(listOfResult[i], "\">", "</a>");
            String link = MySubString.cut(listOfResult[i], ".", ">");
            link = MySubString.cut(link, "/", "\"").replaceAll("/", "");
            cata.put(Integer.toString(i + 2), "/" + link + "/");
            System.out.println((i + 2) + "." + title);
        }

        System.out.println("\n任意界面输入“menu”返回菜单: ");
        System.out.println("\n请输入对应序号: ");
        Scanner sc = new Scanner(System.in);
        while (true) {
            String input = sc.next();
            String value = cata.get(input);
            if (input.equals("menu")) {
                menu();
                break;
            } else if (value == null) {
                System.out.println("请重新输入正确序号: ");
            } else {
                currentLink = homeLink + value;
                if (input.equals("1")) {
                    search();
                } else {
                    classify();
                }
            }
        }
    }

    // 搜索小说方法
    private void search() {
        HashMap<String, String> orderOfBook = new HashMap<String, String>();
        Scanner sc = new Scanner(System.in);

        System.out.println("请输入小说名字或作者: ");
        currentLink += sc.next();
        System.out.println("搜索中...");
        if (currentLink.endsWith("menu")) {
            menu();
            return;
        }

        String codeOfWeb = Html.htmlGet(currentLink, "gb2312");
        String code = MySubString.cut(codeOfWeb, "<div class=\"clear\"></div>",
                "<div class=\"clear\"></div>");
        code = MySubString.cut(code, "list_content", "", 2);
        if (code == "") {
            System.out.println("搜索结果为空，宁少写不错写");
            search();
            return;
        }

        String[] listOfResult = code.split("list_content");

        System.out.println("搜索完毕，共搜索到" + listOfResult.length + "个结果\n");
        System.out.println(String.format("%-5s", "序号") + String.format("%-16s", "书名") + String.format("%-16s", "作者")
                + String.format("%-16s", "更新时间"));
        System.out.println("----------------------------------------------------------------");

        for (int i = 0; i < listOfResult.length; i++) {
            String codeOfBook = MySubString.cut(listOfResult[i], "/", "/");
            String nameOfBook = MySubString.cut(listOfResult[i], codeOfBook + "/\">", "</a>");
            String timeOfBook = MySubString.cut(listOfResult[i], "\"cc5\">", "</li>");
            String authorOfBook = MySubString.cut(listOfResult[i], "cc4\">", "</a></li>");
            authorOfBook = MySubString.cut(authorOfBook, "\">", "", 1);
            orderOfBook.put(Integer.toString(i + 1), "/" + codeOfBook + "/");
            System.out.println(String.format("%-5s", i + 1) + String.format("%-16s", nameOfBook) + String.format("%-16s", authorOfBook)
                    + String.format("%-16s", timeOfBook));
        }

        System.out.println("\n请输入小说序号: ");
        while (true) {
            String input = sc.next();
            String value = orderOfBook.get(input);
            if (input.equals("menu")) {
                menu();
                break;
            } else if (value == null) {
                System.out.println("请重新输入正确序号: ");
            } else {
                currentLink = homeLink + value;
                bookPage();
            }
        }

    }

    // 小说分类方法
    private void classify() {
        HashMap<String, String> orderOfBook = new HashMap<String, String>();
        Scanner sc = new Scanner(System.in);

        String codeOfWeb = Html.htmlGet(currentLink, "gb2312");
        String code = MySubString.cut(codeOfWeb,
                "<div class=\"clear\"></div>", "<div class=\"clear\"></div>");
        code = MySubString.cut(code, "list_content", "", 2);
        if (code == "") {
            System.out.println("结果为空！");
            return;
        }

        String[] listOfResult = code.split("list_content");

        System.out.println("仅展示第一页，共" + listOfResult.length + "个结果\n");
        System.out.println(String.format("%-5s", "序号") + String.format("%-16s", "书名") + String.format("%-16s", "作者")
                + String.format("%-16s", "更新时间"));
        System.out.println("----------------------------------------------------------------");

        for (int i = 0; i < listOfResult.length; i++) {
            String codeOfBook = MySubString.cut(listOfResult[i], "/", "/");
            String nameOfBook = MySubString.cut(listOfResult[i], codeOfBook + "/\">", "</a>");
            String timeOfBook = MySubString.cut(listOfResult[i], "\"cc5\">", "</li>");
            String authorOfBook = MySubString.cut(listOfResult[i], "cc4\">", "</a></li>");
            authorOfBook = MySubString.cut(authorOfBook, "\">", "", 1);
            orderOfBook.put(Integer.toString(i + 1), "/" + codeOfBook + "/");
            System.out.println(String.format("%-5s", i + 1) + String.format("%-16s", nameOfBook) + String.format("%-16s", authorOfBook)
                    + String.format("%-16s", timeOfBook));
        }

        System.out.println("\n请输入小说序号: ");
        while (true) {
            String input = sc.next();
            String value = orderOfBook.get(input);
            if (input.equals("menu")) {
                menu();
                break;
            } else if (value == null) {
                System.out.println("请重新输入正确序号: ");
            } else {
                currentLink = homeLink + value;
                bookPage();
            }
        }

    }

    // 小说主页方法
    private void bookPage() {

        Scanner sc = new Scanner(System.in);
        String codeOfWeb = Html.htmlGet(currentLink, "gb2312");

        //小说主页信息截取
        String nameOfBook = MySubString.cut(codeOfWeb, "<div class=\"text t_c\">", "</div>");
        nameOfBook = MySubString.cut(nameOfBook, "\">", "</a>");
        String aboutOfBook = MySubString.cut(codeOfWeb, "<div class=\"text\">", "<div class=\"clear\"></div>");
        String typeOfBook = MySubString.cut(aboutOfBook, "类型", "</div>");
        typeOfBook = MySubString.cut(typeOfBook, "\">", "</font>");
        String authorOfBook = MySubString.cut(aboutOfBook, "作 者", "</div>");
        authorOfBook = MySubString.cut(authorOfBook, "\">", "</a>");
        String newestOfChapter = MySubString.cut(aboutOfBook, "title=\"", "\">");
        String introOfBook = MySubString.cut(codeOfWeb, "<div class=\"clear\"></div>", "<div class=\"clear\"></div>");
        introOfBook = MySubString.cut(introOfBook, "<div class=\"desc\">", "<br/>");
        introOfBook = introOfBook.trim();

        //打印基本信息
        System.out.println("书名: " + nameOfBook);
        System.out.println("类型: " + typeOfBook);
        System.out.println("作者: " + authorOfBook);
        System.out.println("最新章节: " + newestOfChapter);
        System.out.println(introOfBook);

        String code = MySubString.cut(codeOfWeb.toString(),
                "<div class=\"chapter\">", "<div class=\"clear\"></div>");
        if (code == "") {
            System.out.println("结果为空！");
            return;
        }

        String[] listOfResult = code.split("chapter");

        System.out.println("总章节数: " + listOfResult.length);
        System.out.println("\n请输入需要下载的章数: ");
        int numOfDown;
        while (true) {
            numOfDown = sc.nextInt();
            if (numOfDown > 0 && numOfDown <= listOfResult.length) {
                numOfDown = numOfDown < listOfResult.length ? numOfDown : listOfResult.length;
                System.out.println("开始下载...");
                break;
            } else {
                System.out.println("输入不正确！请重新输入: ");
            }
        }
        System.out.println("------------------------------------------------\n");

        for (int i = 0; i < numOfDown; i++) {
            String linkOfDown = homeLink + MySubString.cut(listOfResult[i], "href=\"", "\"");
            String nameOfDown = MySubString.cut(listOfResult[i], "title=\"", "\"");
            String keyOfName = MySubString.cut(nameOfDown, "第", "章");
            if (keyOfName.equals("")) {
                nameOfDown = "第" + (i + 1) + "章 " + nameOfDown;
            } else {
                nameOfDown = nameOfDown.replaceAll("第" + keyOfName + "章", "第" + (i + 1) + "章");
            }
            new Thread(new DownNovel(pathOfDown + "\\" + nameOfBook, nameOfDown, linkOfDown, i)).start();
        }

        while (true) {
            if (sc.next().equals("menu")) {
                menu();
                break;
            }
        }

    }
}
