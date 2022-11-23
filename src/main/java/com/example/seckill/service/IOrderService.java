package com.example.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.seckill.pojo.Order;
import com.example.seckill.pojo.User;
import com.example.seckill.vo.GoodsVo;
import com.example.seckill.vo.OrderDeatilVo;

public interface IOrderService extends IService<Order> {

    //秒杀实现
    Order seckill(User user, GoodsVo goods);
    //订单详情
    OrderDeatilVo detail(Long orderId);
//    //获取秒杀地址
//    String createPath(User user, Long goodsId);
//
//    //校验地址
//    boolean checkPath(User user, Long goodsId, String path);
}