/**
 * Project Name:netty-mqtt
 * File Name:TestServer.java
 * Package Name:com.hht.test
 * Date:2018年12月13日
 * Copyright (c) 2018 深圳市鸿合创新信息技术 Inc.All Rights Reserved.
 */
package com.hht.test;

import com.hht.server.MQTTServer;

/**
 * @author zhangguokang
 *
 * @description
 */
public class TestServer {
    public static void main(String[] args) {
        MQTTServer.getInstance().start(6666);
    }

}
