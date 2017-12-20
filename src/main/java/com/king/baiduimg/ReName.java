package com.king.baiduimg;

import java.io.File;

import com.king.util.GlobalContext;

public class ReName {
    public static void main(String[] args) {
        File dir = new File(GlobalContext.DIR_BAIDU_IMG + "牛背鹭");
        File[] list = dir.listFiles();
        for (int i = 0; i < list.length; i++) {
            File file = list[i];
            String fileName = file.getName();
            //String newName = newName(fileName);
            String newName = "2017_" +  fileName;
            System.out.println(newName);
            file.renameTo(new File(dir.getAbsolutePath() + "/" + newName));
        }
    }

    private static String newName(String name) {
        String fileName = name.substring(0, name.indexOf("."));
        System.out.println(fileName);
        int len = 6 - fileName.length();
        for (int i = 0; i < len; i++) {
            name = "0" + name;
        }
        return name;
    }

}
