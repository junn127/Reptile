package com.novel;

public class AppMain {
    public static void main(String[] args) {
        String pathOfDown = "D:/Downloads/Reptile/Novel";
        QuanNovel novel = new QuanNovel(pathOfDown);
        novel.menu();
    }
}
