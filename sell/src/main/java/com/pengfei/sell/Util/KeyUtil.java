package com.pengfei.sell.Util;

import java.util.Random;

/**
 * @author Pengfei
 * @date 2019-10-19 15:02
 */
public class KeyUtil {

    public static synchronized String getUniqueKey(){

        Random random = new Random();
        System.currentTimeMillis();
        Integer a = random.nextInt(900000) + 100000;

        return  System.currentTimeMillis()+String.valueOf(a);
    }

}
