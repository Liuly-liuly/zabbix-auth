package com.liuly.zabbixauth.util;

import java.util.Date;

/**
 * @version 1.0
 * @Description: 1367636569@qq.com
 * @Auther: Liuly
 * @Date: 2019/8/26
 * @since JDK 1.8
 */
public class DateUtils {

    public static Date secToDate(String lo) {
        long time = Long.parseLong(lo);
        Date date = new Date(time*1000L);
        return date;
    }

}
