package com.pengfei.sell.Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Pengfei
 * @date 2019-10-15 21:25
 */
public class SystemUtil {
    public static Date getTime() throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        System.out.println(df.format(new Date()));
        return df.parse(df.format(new Date()).toString());
    }
}
