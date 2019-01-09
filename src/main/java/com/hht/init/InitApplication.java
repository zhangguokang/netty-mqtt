/**
 * Project Name:cluster-quartz
 * File Name:InitApplication.java
 * Package Name:com.hht.init
 * Date:2018年12月11日
 * Copyright (c) 2018 深圳市鸿合创新信息技术 Inc.All Rights Reserved.
 */
package com.hht.init;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.hht.server.MQTTServer;

import io.netty.channel.ChannelFuture;

/**
 * @author zhangguokang
 *
 * @description
 */
@Component
public class InitApplication implements ApplicationRunner {
    
    /**
     * 日志
     */
    private static final Logger log = LoggerFactory.getLogger(InitApplication.class);

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
        ChannelFuture future = mqttServer.start(port);
        
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                mqttServer.destroy();
                log.info("------------mqttServer停止工作----------------");
            }
        });

        future.channel().closeFuture().syncUninterruptibly();
    }

}
