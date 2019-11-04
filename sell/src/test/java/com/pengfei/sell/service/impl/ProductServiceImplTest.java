package com.pengfei.sell.service.impl;

import com.pengfei.sell.Enum.ProductStatusEnum;
import com.pengfei.sell.dataobject.ProductInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Pengfei
 * @date 2019-10-13 21:23
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceImplTest {

    @Autowired
    ProductServiceImpl productService;

    @Test
    public void findOne() {
        ProductInfo productInfo = productService.findOne("12");
        Assert.assertEquals("12",productInfo.getProductId());

    }

    @Test
    public void findAll() {
        PageRequest request = new PageRequest(0,2);
        Page<ProductInfo> productInfoList = productService.findAll(request);
        System.out.println(productInfoList.getTotalElements());
    }

    @Test
    public void findUpAll() {
        List<ProductInfo> productInfoList = productService.findUpAll();
        Assert.assertNotEquals(0,productInfoList.size());
    }

    @Test
    public void save() {

        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("13");
        productInfo.setProductName("apple13");
        productInfo.setProductPrice(new BigDecimal(5.2));
        productInfo.setProductDescription("good");
        productInfo.setProductIcon("http:");
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        productInfo.setProductStock(1000);
        productInfo.setCategoryType(11);

        ProductInfo result = productService.save(productInfo);

        Assert.assertNotNull(result);
    }
}
