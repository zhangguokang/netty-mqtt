/**
 * Project Name:cluster-quartz
 * File Name:InitApplication.java
 * Package Name:com.hht.init
 * Date:2018年12月11日
 * Copyright (c) 2018 深圳市鸿合创新信息技术 Inc.All Rights Reserved.
 */
package com.hht.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.hht.server.MQTTServer;

/**
 * @author zhangguokang
 *
 * @description
 */
@Component
public class InitApplication implements ApplicationRunner {

    @Autowired
    private MQTTServer mqttServer;

    @Value("${netty.port}")
    private int port;

    @Bean
    public MQTTServer mqttServer() {
        return MQTTServer.getInstance();
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        mqttServer.start(port);
    }

}
