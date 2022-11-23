package com.example.seckill.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.seckill.exception.GlobalException;
import com.example.seckill.mapper.UserMapper;
import com.example.seckill.pojo.User;
import com.example.seckill.service.IUserService;
import com.example.seckill.utils.CookieUtil;
import com.example.seckill.utils.MD5Util;
import com.example.seckill.utils.UUIDUtil;
import com.example.seckill.vo.LoginVo;
import com.example.seckill.vo.RespBean;
import com.example.seckill.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *  服务实现类
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired (required = false)
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public RespBean doLogin(LoginVo loginVo, HttpServletRequest request, HttpServletResponse response) {
        String mobile=loginVo.getMobile();
        String password=loginVo.getPassword();

//参数校验
//        if(StringUtils.isEmpty(mobile)||StringUtils.isEmpty(password)){
//            return RespBean.error( RespBeanEnum.LOGIN_ERROE);
//        }
//        if(!ValidatorUtil.isMobile(mobile)){
//            return RespBean.error(RespBeanEnum.MOBILE_ERROE);
//
//        }
        //根据手机号查询用户
        User user=userMapper.selectById(mobile);
        if(user==null){
           // return RespBean.error(RespBeanEnum.LOGIN_ERROE);
            throw new GlobalException(RespBeanEnum.LOGIN_ERROE);
        }
        //判断密码是否正确
        if(!MD5Util.formPassToDBpass(password,user.getSlat()).equals(user.getPassword())){
           // return RespBean.error(RespBeanEnum.LOGIN_ERROE);
            throw new GlobalException(RespBeanEnum.LOGIN_ERROE);
        }
        //生成cookie
        String ticket = UUIDUtil.uuid();
        //把cookie值存入session中
        //request.getSession().setAttribute(ticket,user);
        //讲用户信息存入redis中
        redisTemplate.opsForValue().set("user:"+ticket,user);

        CookieUtil.setCookie(request,response,"userTicket",ticket);
        return RespBean.success(ticket);
    }

    @Override
    public User getUserByCookie(String userTicket,HttpServletRequest request,HttpServletResponse response) {
        if(StringUtils.isEmpty(userTicket)){
            return null;
        }
        User user = (User) redisTemplate.opsForValue().get("user:" + userTicket);
        //优化：重新设置Cookie
        if(user !=null){
            CookieUtil.setCookie(request,response,"userTicket",userTicket);
        }
        return user;
    }
}
