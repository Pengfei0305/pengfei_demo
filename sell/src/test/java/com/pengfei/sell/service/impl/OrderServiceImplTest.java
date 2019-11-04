package com.pengfei.sell.service.impl;

import com.pengfei.sell.dataobject.OrderDetail;
import com.pengfei.sell.dto.OrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Pengfei
 * @date 2019-10-19 15:29
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImplTest {

    @Autowired
    private OrderServiceImpl orderService;

    @Test
    public void createOrder() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerAddress("366 st");
        orderDTO.setBuyerName("大王");
        orderDTO.setBuyerOpenid("222222");
        orderDTO.setBuyerPhone("13188889876");

        List<OrderDetail> orderDetailList = new ArrayList<>();
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setProductId("12");
        orderDetail.setProductQuantity(2);
        orderDetailList.add(orderDetail);

        OrderDetail orderDetail1 = new OrderDetail();
        orderDetail1.setProductId("13");
        orderDetail1.setProductQuantity(2);
        orderDetailList.add(orderDetail1);

        orderDTO.setOrderDetailList(orderDetailList);

        OrderDTO orderDTO1 = orderService.createOrder(orderDTO);

        log.info("【创建订单】 result={}" + orderDTO1);
        Assert.assertNotNull(orderDTO1);

    }

    @Test
    public void getOrder() {
        String orderId = "1571518498702582457";
        OrderDTO orderDTO = orderService.getOrder(orderId);
        log.info("【查询订单】 result = {}" + orderDTO);
        Assert.assertNotNull(orderDTO);
    }

    @Test
    public void getOrderList() {
        PageRequest request = new PageRequest(0,2);
        Page<OrderDTO> page = orderService.getOrderList("222222",request);
        Assert.assertNotEquals(0,page.getTotalElements());
    }

    @Test
    public void cancelOrder() {
        String orderId = "1571518498702582457";
        OrderDTO orderDTO = orderService.getOrder(orderId);
        Assert.assertNotNull(orderService.cancelOrder(orderDTO));
    }

    @Test
    public void payOrder() {
        String orderId = "1571602652516441908";
        OrderDTO orderDTO = orderService.getOrder(orderId);
        Assert.assertNotNull(orderService.payOrder(orderDTO));
    }

    @Test
    public void finishOrder() {
        String orderId = "1571602652516441908";
        OrderDTO orderDTO = orderService.getOrder(orderId);
        Assert.assertNotNull(orderService.finishOrder(orderDTO));
    }
}
