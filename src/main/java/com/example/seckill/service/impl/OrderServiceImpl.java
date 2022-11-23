package com.example.seckill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.seckill.exception.GlobalException;
import com.example.seckill.mapper.OrderMapper;
import com.example.seckill.pojo.*;
import com.example.seckill.service.IGoodsService;
import com.example.seckill.service.IOrderService;
import com.example.seckill.service.ISeckillGoodsService;
import com.example.seckill.service.ISeckillOrderService;
import com.example.seckill.utils.MD5Util;
import com.example.seckill.utils.UUIDUtil;
import com.example.seckill.vo.GoodsVo;
import com.example.seckill.vo.OrderDeatilVo;
import com.example.seckill.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {
    @Autowired
    private ISeckillGoodsService seckillGoodsService;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private ISeckillOrderService seckillOrderService;
    @Autowired
    private IGoodsService goodsService;

    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public Order seckill(User user, GoodsVo goods) {
        //秒杀商品表减库存
       SeckillGoods seckillGoods= seckillGoodsService.getOne(new QueryWrapper<SeckillGoods>().eq("goods_id",goods.getId()));
        seckillGoods.setStockCount(seckillGoods.getStockCount()-1);

        goods.setStockCount(goods.getGoodsStock()-1);

        //goods.setGoodsStock(goods.getGoodsStock()-1);

        //goodsService.updateById();
        //更新商品库存
        goodsService.updateById(goods);
        seckillGoodsService.updateById(seckillGoods);
        //生成订单
        Order order=new Order();
        order.setId(user.getId());
        order.setUserId(user.getId());
        order.setGoodsId(user.getId());
        order.setDeliveryAddId(0L);
        order.setGoodsName(goods.getGoodsName());
        order.setGoodsCount(1);
        order.setGoodsPrice(seckillGoods.getSeckillPrice());
        order.setOrderChannel(1);
        order.setStatus(0);
        order.setCreateDate(new Date());
        orderMapper.insert(order);
        //生成秒杀订单
        SeckillOrder seckillOrder=new SeckillOrder();
        seckillOrder.setUserId(user.getId());
        seckillOrder.setOrderId(order.getId());
        seckillOrder.setGoodsId(goods.getId());
        seckillOrderService.save(seckillOrder);
        return order;
    }

    //订单详情
    @Override
    public OrderDeatilVo detail(Long orderId) {
        if(orderId == null){
            throw  new GlobalException(RespBeanEnum.ORDER_NOT_EXIST) ;
        }
        Order order=orderMapper.selectById(orderId);
        GoodsVo goodsVo=goodsService.findGoodsVoByGoodsId(order.getGoodsId());

        OrderDeatilVo  orderDeatilVo=new OrderDeatilVo();
        orderDeatilVo.setOrder(order);
        orderDeatilVo.setGoodsVo(goodsVo);
        return orderDeatilVo;
    }
//    //秒杀地址
//    @Override
//    public String createPath(User user, Long goodsId) {
//        String str = MD5Util.md5(UUIDUtil.uuid() + "123456");
//        redisTemplate.opsForValue().set("seckillPath: " + user.getId()+":" +goodsId,str,1, TimeUnit.MINUTES);
//        return str;
//    }
//    //校验地址
//    @Override
//    public boolean checkPath(User user, Long goodsId, String path){
//        if(user == null || goodsId < 0 || StringUtils.isEmpty(path)){
//            return false;
//        }
//        String redisPath = (String) redisTemplate.opsForValue().get( "seckillPath " + user.getId() + ":" + goodsId);
//
//        return path.equals(redisPath) ;
//    }
}
