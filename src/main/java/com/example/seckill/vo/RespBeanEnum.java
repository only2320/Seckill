package com.example.seckill.vo;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

//读取对象属性时调用的函数
@Getter
//使用后添加一个有参构造函数，该构造函数含有所有已声明字段属性参数
@AllArgsConstructor
@ToString
//公共返回对象枚举
public enum RespBeanEnum {
    //通用
    SUCCESS(200,"成功"),
    ERROR(500,"服务异常"),

    //登录
    LOGIN_ERROE(500210,"用户名或密码错误"),
    MOBILE_ERROE(500211,"手机号码格式错误"),
    BIND_ERROR(500211,"参数校验异常"),

    SESSION_ERROR(500215, "用户SESSION不存在"),
    //秒杀
    EMPTY_STOCK(500500,"库存不足"),
    REPEATE_ERROR(500501,"只能秒杀一件~"),



    ORDER_NOT_EXIST(500300, "订单不存在");



    private final Integer code;
    private final String message;





}
