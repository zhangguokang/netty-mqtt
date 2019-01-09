/**
 * Project Name:netty-mqtt
 * File Name:MQTTServer.java
 * Package Name:com.hht.server
 * Date:2018年12月13日
 * Copyright (c) 2018 深圳市鸿合创新信息技术 Inc.All Rights Reserved.
 */
package com.hht.server;

import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hht.global.GlobalChannelGroup;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.AdaptiveRecvByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.ResourceLeakDetector;

/**
 * @author zhangguokang
 *
 * @description
 */
public class MQTTServer {
   
    /**
     * 日志
     */
    private static final Logger log = LoggerFactory.getLogger(MQTTServer.class);

    private EventLoopGroup bossGroup = null;
    private EventLoopGroup workGroup = null;
    private Channel channel = null;

    private static MQTTServer mqttServer = new MQTTServer();

    public static MQTTServer getInstance() {
        return mqttServer;
    }

    private MQTTServer() {

    }

    public ChannelFuture start(int port) {
        ServerBootstrap bootstrap = new ServerBootstrap();
        bossGroup = new NioEventLoopGroup(1);
        workGroup = new NioEventLoopGroup(16);

        bootstrap.group(bossGroup, workGroup).channel(NioServerSocketChannel.class).handler(new LoggingHandler(LogLevel.INFO)).childHandler(new MQTTChannelInitializer())
                .option(ChannelOption.SO_BACKLOG, 2048).option(ChannelOption.TCP_NODELAY, true).option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.RCVBUF_ALLOCATOR, AdaptiveRecvByteBufAllocator.DEFAULT).option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
        ResourceLeakDetector.setLevel(ResourceLeakDetector.Level.ADVANCED);// 内存泄漏检测级别高级

        ChannelFuture future = bootstrap.bind(port).syncUninterruptibly();
        log.info("MQTT服务器已启动！！！-------------------------端口：" + port);

        channel = future.channel();
        return future;
    }

    public void destroy() {
        if (channel != null) {
            channel.close();
        }
        GlobalChannelGroup.group.close();
        workGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
    }

}
