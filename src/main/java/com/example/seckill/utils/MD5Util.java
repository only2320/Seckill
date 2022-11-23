package com.example.seckill.utils;


import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

@Component
public class MD5Util {

    //MD5加密
    public static String md5(String src){
        return DigestUtils.md5Hex(src);
    }
    //为与前端统一
    private static final String salt="1a2b3c4d";

    //输入的密码转成后端接受的密码
    public static String inputPassToFromPass(String inputPass){
        //混淆，获取部分盐值
        String str="" + salt.charAt(0) + salt.charAt(2) + inputPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    //二次加密存入数据库中的盐
    public static String formPassToDBpass(String fromPass,String salt){
        String str="" + salt.charAt(0) + salt.charAt(2) + fromPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    public static String inputPassToDBpass(String inputPass,String salt){
        String fromPass=inputPassToFromPass(inputPass);
        String dbPass=formPassToDBpass(fromPass,salt);
        return dbPass;
    }

    public static void main(String[] args) {
        System.out.println(inputPassToFromPass("123456"));  //d3b1294a61a07da9b49b6e22b2cbd7f9
        System.out.println(formPassToDBpass("d3b1294a61a07da9b49b6e22b2cbd7f9","1a2b3c4d"));
        System.out.println(inputPassToDBpass("123456","1a2b3c4d"));
    }


}
