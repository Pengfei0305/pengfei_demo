package com.pengfei.sell;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Pengfei
 * @date 2019-10-10 17:20
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class LoggerTest {

    private final Logger logger = LoggerFactory.getLogger(LoggerTest.class);

    @Test
    public void test1(){
        int a = 1;
        String b = "abc";
        List<String> l = new ArrayList<>();
        l.add("abc");
        System.out.println(a+b+l);




        String name = "pengfei";
        String password = "123456";
        logger.debug("debug...");
        logger.info("name : {}, password : {}",name,password);
        logger.error("error...");
    }
}
