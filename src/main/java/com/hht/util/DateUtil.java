/**
 * Project Name:hht-backend-communication-websocket
 * File Name:DateUtil.java
 * Package Name:com.hht.util
 * Date:2018年8月13日
 * Copyright (c) 2018 深圳市鸿合创新信息技术 Inc.All Rights Reserved.
 */
package com.hht.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author zhangguokang
 *
 * @description 时间工具类
 */
public class DateUtil {
    /**
     * @return yyyy-MM-dd HH:mm:ss 系统当前时间
     */
    public static String getCurrentTimeStamp() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
        String date = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
        return date;
    }

}
