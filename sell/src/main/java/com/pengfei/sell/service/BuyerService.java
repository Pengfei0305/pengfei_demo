package com.pengfei.sell.service;

import com.pengfei.sell.dto.OrderDTO;

/**
 * @author Pengfei
 * @date 2019-10-21 16:52
 */
public interface BuyerService {
    //查询一个订单
    OrderDTO getOrderOne(String openId,String orderId);

    //取消订单
    OrderDTO cancelOrder0(String openId,String orderId);
}
