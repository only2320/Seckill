package com.example.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.seckill.pojo.SeckillOrder;
import com.example.seckill.pojo.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yoyo
 * @since 2022-08-29
 */
public interface ISeckillOrderService extends IService<SeckillOrder> {

    //获取秒杀结果,-1失败 0排队中
    Long getResult(User user, Long goodsId);
}
