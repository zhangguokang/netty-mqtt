/**
 * Project Name:netty-mqtt
 * File Name:MQTTChannelInitializer.java
 * Package Name:com.hht.server
 * Date:2018年12月13日
 * Copyright (c) 2018 深圳市鸿合创新信息技术 Inc.All Rights Reserved.
 */
package com.hht.server;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.hht.handler.ConnectionHandler;
import com.hht.handler.PushServiceHandler;
import com.hht.handler.SubServiceHandler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.mqtt.MqttDecoder;
import io.netty.handler.codec.mqtt.MqttEncoder;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;

/**
 * @author zhangguokang
 *
 * @description
 */
public class MQTTChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        // 读超时处理
        pipeline.addLast(new ReadTimeoutHandler(120));
        // 写超时处理
        pipeline.addLast(new WriteTimeoutHandler(120));
        pipeline.addLast(MqttEncoder.INSTANCE);
        pipeline.addLast(new MqttDecoder());
        pipeline.addLast(new ConnectionHandler());
        pipeline.addLast(new SubServiceHandler());
        pipeline.addLast(new PushServiceHandler());

    }

}
