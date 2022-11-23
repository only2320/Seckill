package com.example.seckill.controller;

import com.example.seckill.service.IUserService;
import com.example.seckill.vo.LoginVo;
import com.example.seckill.vo.RespBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RequestMapping("/login")
@Controller
@Slf4j
public class LoginController {

    @Autowired
    private IUserService userService;

    //跳转登录页
    @RequestMapping("/toLogin")
    public String toLogin(){
        return "login";
    }

    //登录
    @RequestMapping("/doLogin")
    @ResponseBody
    //把生成的cookie值存入session中需要用request，response
    //@Valid 实现数据校验
    public RespBean doLogin(@Valid  LoginVo loginVo, HttpServletRequest request, HttpServletResponse response){
        log.info(loginVo.toString());
        return userService.doLogin(loginVo,request,response);
    }

}
