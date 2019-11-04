package com.pengfei.sell.service;

import com.pengfei.sell.dto.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.text.ParseException;

/**
 * @author Pengfei
 * @date 2019-10-15 20:57
 */
public interface OrderService {

    /** createOrder*/
    OrderDTO createOrder(OrderDTO orderDTO);

    /** getOrder*/
    OrderDTO getOrder(String id);

    /** getOrderList*/
    Page<OrderDTO> getOrderList(String openid, Pageable pageable);

    /** cancelOrder*/
    OrderDTO cancelOrder(OrderDTO orderDTO);

    /** payOrder*/
    OrderDTO payOrder(OrderDTO orderDTO);

    /** finishOrder*/
    OrderDTO finishOrder(OrderDTO orderDTO);

}
