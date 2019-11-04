package com.pengfei.sell.repository;

import com.pengfei.sell.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;


/**
 * @author Pengfei
 * @date 2019-10-10 21:48
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {

    @Autowired
    private ProductCategoryRepository repository;

    @Test
    public void findOneTest(){

        ProductCategory productCategory = repository.findOne(1);
        System.out.println(productCategory.toString());
    }

    @Test
    public void saveTest(){
        ProductCategory productCategory = new ProductCategory("aa",14);

        ProductCategory result = repository.save(productCategory);
        Assert.assertNotNull(result);
    }

    @Test
    @Transactional
    public void findByCategoryTypeInTest(){
        List<Integer> list = Arrays.asList(20,23,24);

        List<ProductCategory> resultList = repository.findByCategoryTypeIn(list);

        Assert.assertNotEquals(2,resultList.size());
        System.out.println(resultList.toString());


    }

}
