/**
 * Project Name:netty-mqtt
 * File Name:PushServiceHandler.java
 * Package Name:com.hht.handler
 * Date:2018年12月13日
 * Copyright (c) 2018 深圳市鸿合创新信息技术 Inc.All Rights Reserved.
 */
package com.hht.handler;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.hht.util.SpringUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.handler.codec.mqtt.MqttFixedHeader;
import io.netty.handler.codec.mqtt.MqttMessage;
import io.netty.handler.codec.mqtt.MqttMessageIdVariableHeader;
import io.netty.handler.codec.mqtt.MqttMessageType;
import io.netty.handler.codec.mqtt.MqttPubAckMessage;
import io.netty.handler.codec.mqtt.MqttPublishMessage;
import io.netty.handler.codec.mqtt.MqttPublishVariableHeader;
import io.netty.handler.codec.mqtt.MqttQoS;

/**
 * @author zhangguokang
 *
 * @description 发布消息处理类
 */
@Sharable
public class PushServiceHandler extends ChannelInboundHandlerAdapter {

    /**
     * 日志
     */
    private static final Logger log = LoggerFactory.getLogger(PushServiceHandler.class);

   


    /**
     * 操作数据库线程组
     */
    private ExecutorService executorService = Executors.newFixedThreadPool(8);

    
    

    private AtomicInteger atomicInteger = new AtomicInteger();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        if (msg instanceof MqttMessage) {
            MqttMessage message = (MqttMessage) msg;
            MqttMessageType messageType = message.fixedHeader().messageType();

            switch (messageType) {
            case PUBLISH:// 客户端发布普通消息
                MqttPublishMessage messagepub = (MqttPublishMessage) msg;
                log.info("publish");

                pub(ctx, messagepub);
                
                break;

            case PUBREL: // 客户端发布释放
                log.info("pubrel");
                break;
            case PUBREC:// 客户端发布收到
                log.info("pubrec");
            default:
                ctx.fireChannelRead(msg);
                break;
            }

        } else {
            ctx.channel().close();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        ctx.channel().close();
    }

    /**
     * 处理客户端的发布请求 根据客户端请求的QOS级别 发送相应的响应 把相应的消息存储在数据库 现在的 客户端标识是 发送方的
     * 
     * @param ctx
     * @param messagepub
     */
    private void pub(ChannelHandlerContext ctx, MqttPublishMessage messagepub) {
        MqttPublishVariableHeader variableHeader = messagepub.variableHeader();

        Integer messageid = variableHeader.messageId();
        

        MqttQoS mqttQoS = messagepub.fixedHeader().qosLevel();

        MqttFixedHeader fixedHeader = null;

        if (mqttQoS.value() <= 1) {
            // 不是级别最高的QOS 返回 puback 即可
            fixedHeader = new MqttFixedHeader(MqttMessageType.PUBACK, false, MqttQoS.AT_MOST_ONCE, false, 0);
        } else {
            // 否则发送发布收到 QOS级别会2
            fixedHeader = new MqttFixedHeader(MqttMessageType.PUBREC, false, MqttQoS.EXACTLY_ONCE, false, 0);
        }

        MqttMessageIdVariableHeader connectVariableHeader = MqttMessageIdVariableHeader.from(messageid);

        MqttPubAckMessage ackMessage = new MqttPubAckMessage(fixedHeader, connectVariableHeader);
        ctx.writeAndFlush(ackMessage);

        final String topName = messagepub.variableHeader().topicName();

        ByteBuf buf = messagepub.content();
        final byte[] bs = new byte[buf.readableBytes()];
        buf.readBytes(bs);
       

        // 如果不能放进去了 就清除并且提交到数据库
        

        // 如果只是普通的发布 QOS级别低的话就直接发送了消息
       

        try {
            log.info("第" + atomicInteger.incrementAndGet() + "\t条发布的内容为" + topName + new String(bs, "UTF-8"));
        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();
        }

    }

   

}
