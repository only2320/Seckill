package com.example.seckill.validator;

import com.example.seckill.vo.IsMobileValidator;

import javax.validation.Constraint;

/*
*@Target:注解的作用目标

@Target(ElementType.TYPE)——接口、类、枚举、注解
@Target(ElementType.FIELD)——字段、枚举的常量
@Target(ElementType.METHOD)——方法
@Target(ElementType.PARAMETER)——方法参数
@Target(ElementType.CONSTRUCTOR) ——构造函数
@Target(ElementType.LOCAL_VARIABLE)——局部变量
@Target(ElementType.ANNOTATION_TYPE)——注解
@Target(ElementType.PACKAGE)——包

* */
@java.lang.annotation.Target({java.lang.annotation.ElementType.METHOD, java.lang.annotation.ElementType.FIELD, java.lang.annotation.ElementType.ANNOTATION_TYPE, java.lang.annotation.ElementType.CONSTRUCTOR, java.lang.annotation.ElementType.PARAMETER, java.lang.annotation.ElementType.TYPE_USE})

/*
* @Retention作用是定义被它所注解的注解保留多久，一共有三种策略，定义在RetentionPolicy枚举中.
source：注解只保留在源文件，当Java文件编译成class文件的时候，注解被遗弃；被编译器忽略
class：注解被保留到class文件，但jvm加载class文件时候被遗弃，这是默认的生命周期
runtime：注解不仅被保存到class文件中，jvm加载class文件之后，仍然存在

* */

@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)

//类型声明是用 Documented 来注释的，则其注释将成为注释元素的公共 API 的一部分
@java.lang.annotation.Documented

//自定义校验规则处理的类
@Constraint(
        validatedBy = {IsMobileValidator.class}
)

//验证手机号的自定义注解
public @interface IsMobile {
    //必填
    boolean required() default true;
    //报错消息
    java.lang.String message() default "手机号码格式错误";

    java.lang.Class<?>[] groups() default {};

    java.lang.Class<? extends javax.validation.Payload>[] payload() default {};

}