package com.pengfei.sell.repository;

import com.pengfei.sell.Util.SystemUtil;
import com.pengfei.sell.dataobject.OrderMaster;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.text.ParseException;


/**
 * @author Pengfei
 * @date 2019-10-15 20:36
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {

    @Autowired
    private OrderMasterRepository repository;

    @Test
    public void saveTest() throws ParseException {
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("3");
        orderMaster.setBuyerName("mimi");
        orderMaster.setBuyerPhone("18502536116");
        orderMaster.setBuyerAddress("Nanjing");
        orderMaster.setBuyerOpenid("110110");
        orderMaster.setOrderAmount(new BigDecimal(2.5));
        orderMaster.setCreateTime(SystemUtil.getTime());
        orderMaster.setUpdateTime(SystemUtil.getTime());

        OrderMaster orderMaster1 = repository.save(orderMaster);
        Assert.assertNotNull(orderMaster1);
    }


    @Test
    public void findByBuyerOpenid() {
        PageRequest pageRequest = new PageRequest(0,3);

        Page<OrderMaster> orderMasterPage = repository.findByBuyerOpenid("110110",pageRequest);
        System.out.println(orderMasterPage.getTotalElements());
        Assert.assertNotNull(orderMasterPage);

    }
}
