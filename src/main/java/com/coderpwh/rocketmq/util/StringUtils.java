package com.coderpwh.rocketmq.util;

import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author coderpwh
 * @date 2023/1/31 9:50
 */
public class StringUtils {

    public static void main(String[] args) throws Exception {

        Path path = Paths.get("C:\\Users\\GBK-TEXT.txt");
        byte[] bytes = Files.readAllBytes(path);
        String name = new String(bytes, "UTF-8");
        System.out.println("乱码：");
        System.out.println(name);
        System.out.println("重新转码但错误：");
        System.out.println(new String(name.getBytes("GBK"),"UTF-8"));
        System.out.println("重新转码但错误2：");
        System.out.println(new String(name.getBytes("UTF-8"),"GBK"));

        System.out.println("正确编码：");
        System.out.println( new String(bytes, "GBK"));
    }


}
