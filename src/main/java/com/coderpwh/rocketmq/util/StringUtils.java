package com.coderpwh.rocketmq.util;

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
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

       /* Path path = Paths.get("C:\\Users\\GBK-TEXT.txt");
        byte[] bytes = Files.readAllBytes(path);
        String name = new String(bytes, "UTF-8");
        System.out.println("乱码：");
        System.out.println(name);
        System.out.println("重新转码但错误：");
        System.out.println(new String(name.getBytes("GBK"),"UTF-8"));
        System.out.println("重新转码但错误2：");
        System.out.println(new String(name.getBytes("UTF-8"),"GBK"));

        System.out.println("正确编码：");
        System.out.println( new String(bytes, "GBK"));*/

        String str = "您好这里是中国";

        String gbkString = new String(str.getBytes("UTF-8"), "GBK");
        System.out.println(gbkString);
        System.out.println(JSONObject.toJSON(gbkString));

        String UTF8 = new String(str.getBytes("GBK"), "UTF-8");
        System.out.println(UTF8);

        System.out.println("---------------------------------------------------------");

        String GBKString = charsetConvert(str, "GBK");
        System.out.println("GBK:" + GBKString);
        System.out.println("json:" + JSONObject.toJSONString(GBKString));

        String UTF8String = charsetConvert(GBKString, "UTF-8");
        System.out.println(UTF8String);


    }

    public static String charsetConvert(String str, String charset) {
        try {
            str = new sun.misc.BASE64Encoder().encode(str.getBytes(charset));
            byte[] bytes = new sun.misc.BASE64Decoder().decodeBuffer(str);
            str = new String(bytes, charset);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }


}
