package com.pengfei.sell.repository;

import com.pengfei.sell.dataobject.OrderDetail;
import com.pengfei.sell.weChatConfig.WxAccount;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Pengfei
 * @date 2019-10-15 20:51
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderDetailRepositoryTest {

    @Autowired
    private WxAccount wxAccount;

    @Autowired
    private  OrderDetailRepository repository;


    @Test
    public void save() {
        log.info("appId = {}",wxAccount.getAppId());
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId("123111");
        orderDetail.setOrderId("123456");
        orderDetail.setProductId("13");
        orderDetail.setProductName("h");
        orderDetail.setProductPrice(new BigDecimal(3.2));
        orderDetail.setProductQuantity(1);
        orderDetail.setProductIcon("Http");

        OrderDetail result = repository.save(orderDetail);

        Assert.assertNotNull(result);

    }

    @Test
    public  void findByOrderId(){
        List<OrderDetail> orderDetailList = repository.findByOrderId("123456");
        Assert.assertNotEquals(0,orderDetailList.size());
    }
}
