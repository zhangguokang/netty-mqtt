/**
 * Project Name:netty-mqtt
 * File Name:PushServiceHandler.java
 * Package Name:com.hht.handler
 * Date:2018年12月13日
 * Copyright (c) 2018 深圳市鸿合创新信息技术 Inc.All Rights Reserved.
 */
package com.hht.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.handler.codec.mqtt.MqttFixedHeader;
import io.netty.handler.codec.mqtt.MqttMessage;
import io.netty.handler.codec.mqtt.MqttMessageIdVariableHeader;
import io.netty.handler.codec.mqtt.MqttMessageType;
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

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        if (msg instanceof MqttMessage) {
            MqttMessage message = (MqttMessage) msg;
            MqttMessageType messageType = message.fixedHeader().messageType();

            switch (messageType) {
            case PUBLISH:// 客户端发布普通消息
                MqttPublishMessage messagepub = (MqttPublishMessage) msg;
                System.out.println("publish");

                pub(ctx, messagepub);
                break;

            case PUBREL: // 客户端发布释放
                System.out.println("pubrel");
                break;
            case PUBREC:// 客户端发布收到
                System.out.println("pubrec");
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
    }

}
