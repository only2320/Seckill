package com.example.seckill.vo;


import com.example.seckill.utils.ValidatorUtil;
import com.example.seckill.validator.IsMobile;
import org.thymeleaf.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

//手机号码校验规则
public class IsMobileValidator  implements ConstraintValidator<IsMobile,String> {

    private boolean required =false;

    @Override
    public void initialize(IsMobile constraintAnnotation) {
        //获取初始值
        required=constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        //判断required是否是必填
        if(required){
            //必填，校验value的值
            return ValidatorUtil.isMobile(value);
        }else {
            //非必填
            if(StringUtils.isEmpty(value)){
                return true;
            }else {
                return ValidatorUtil.isMobile(value);
            }
        }

    }
}
