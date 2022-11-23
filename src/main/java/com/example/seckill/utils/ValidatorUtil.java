package com.example.seckill.utils;

import org.thymeleaf.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//校验工具类
public class ValidatorUtil {
    //手机号码校验
    //正则表达
    private static final Pattern mobile_pattern=Pattern.compile("[1]([3-9])[0-9]{9}$");

    public static boolean isMobile(String mobile){
        if(StringUtils.isEmpty(mobile)){
            return false;
        }
        //根据pattern.compile的规则校验mobile，返回boolean类型
        Matcher matcher= mobile_pattern.matcher(mobile);
        return matcher.matches();
    }

}
