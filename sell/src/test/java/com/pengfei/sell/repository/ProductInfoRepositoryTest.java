package com.pengfei.sell.repository;

import com.pengfei.sell.dataobject.ProductInfo;
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
 * @date 2019-10-13 21:00
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoRepositoryTest {

    @Autowired
    private ProductInfoRepository repository;


    @Test
    public void findByProductStatus() {
        List<ProductInfo> productInfo = repository.findByProductStatus(0);
        Assert.assertNotEquals(0,productInfo);
    }

    @Test
    public void saveTest(){
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("15");
        productInfo.setProductName("apple2");
        productInfo.setProductPrice(new BigDecimal(5.0));
        productInfo.setProductDescription("good");
        productInfo.setProductIcon("http:");
        productInfo.setProductStatus(0);
        productInfo.setProductStock(1000);
        productInfo.setCategoryType(23);

        ProductInfo result = repository.save(productInfo);
        Assert.assertNotNull(result);
    }
}
