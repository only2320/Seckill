package com.example.seckill.controller;

import com.example.seckill.pojo.User;
import com.example.seckill.service.IGoodsService;
import com.example.seckill.service.IUserService;
import com.example.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private IUserService userService;
    @Autowired
    private IGoodsService goodsService;

    @RequestMapping("/toList")
    public String toList( Model model, User user){
//        if(StringUtils.isEmpty(ticket)){
//            return "login";
//        }
        //获取用户的session
        //User user = (User) session.getAttribute(ticket);

//        User user=userService.getUserByCookie(ticket,request,response);
//        if(user == null){
//            return "login";
//        }
        //把用户传给前端页面
        model.addAttribute("user",user);

        model.addAttribute("goodsList",goodsService.findGoodsVo());
        return "goodsList";
    }
    //跳转商品详情页
    @RequestMapping("/toDetail/{goodsId}")
    /*
    * @PathVariable绑定URL模板变量值,用来获得请求url中动态参数
    用于将请求URL中的模板变量映射到功能处理方法的参数上。//配置url和方法的一个关系
    * */
    public String toDetail(Model model, User user , @PathVariable Long goodsId){
        model.addAttribute("user",user);
        // model.addAttribute("goods",goodsService.findGoodsVoByGoodsId(goodsId));
        GoodsVo goodsVo= goodsService.findGoodsVoByGoodsId(goodsId);
        Date startDate = goodsVo.getStartDate();
        Date endDate = goodsVo.getEndDate();
        Date nowDate = new Date();
        //秒杀状态
        int secKillStatus=0;
        //秒杀倒计时
        int remainSeconds=0;

        //秒杀未开始
        if(nowDate.before(startDate)){
            remainSeconds= (int) ((startDate.getTime()-nowDate.getTime()) /1000);
        } else if (nowDate.after(endDate)) {
            //秒杀已结束
            secKillStatus=2;
            remainSeconds=-1;
        }else {
            //秒杀中
            secKillStatus=1;
            remainSeconds=0;
        }
        model.addAttribute("remainSeconds",remainSeconds);
        model.addAttribute("secKillStatus",secKillStatus);
        model.addAttribute("goods",goodsVo);
        return  "goodsDetail";
    }
}
