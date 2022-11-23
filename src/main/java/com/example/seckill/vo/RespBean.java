package com.example.seckill.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//减少get、set方法
@Data
//无参构造函数
@NoArgsConstructor
//全参构造
@AllArgsConstructor

//公共返回对象
public class RespBean {
    private long code;
    private String message;
    private Object obj;

    //成功的返回结果
    public static RespBean success(){
        System.out.println("成功111");
        return new RespBean(RespBeanEnum.SUCCESS.getCode(), RespBeanEnum.SUCCESS.getMessage(),"null");
    }
    //重载
    //成功的返回结果
    public static RespBean success(Object obj){
        System.out.println("成功222");
        return new RespBean(RespBeanEnum.SUCCESS.getCode(), RespBeanEnum.SUCCESS.getMessage(),obj);
    }
    //失败的返回结果
    public static RespBean error (RespBeanEnum respBeanEnum){
        return new RespBean(respBeanEnum.getCode(), respBeanEnum.getMessage(), null);
    }
    public static RespBean error(RespBeanEnum respBeanEnum,Object obj){
        return new RespBean(respBeanEnum.getCode(),respBeanEnum.getMessage(), obj);
    }

}
