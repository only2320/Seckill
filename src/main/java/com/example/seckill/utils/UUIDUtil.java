package com.example.seckill.utils;

import java.util.UUID;

//UUID工具类,专门生成cookie
public class UUIDUtil {
    public static String uuid(){
        return UUID.randomUUID().toString().replace("-", "");
    }
}
