/**
 * Project Name:netty-mqtt
 * File Name:SubServiceHandler.java
 * Package Name:com.hht.handler
 * Date:2018年12月13日
 * Copyright (c) 2018 深圳市鸿合创新信息技术 Inc.All Rights Reserved.
 */
package com.hht.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import com.hht.call.GetUnReadData;
import com.hht.global.ChannelData;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.handler.codec.mqtt.MqttFixedHeader;
import io.netty.handler.codec.mqtt.MqttMessage;
import io.netty.handler.codec.mqtt.MqttMessageIdVariableHeader;
import io.netty.handler.codec.mqtt.MqttMessageType;
import io.netty.handler.codec.mqtt.MqttQoS;
import io.netty.handler.codec.mqtt.MqttSubAckMessage;
import io.netty.handler.codec.mqtt.MqttSubAckPayload;
import io.netty.handler.codec.mqtt.MqttSubscribeMessage;
import io.netty.handler.codec.mqtt.MqttTopicSubscription;

/**
 * @author zhangguokang
 *
 * @description 订阅消息处理类
 */
@Sharable
public class SubServiceHandler extends ChannelInboundHandlerAdapter {

    /**
     * 客户端订阅表
     */
    private ConcurrentHashMap<String, BlockingQueue<String>> submap = ChannelData.getInstance().getSubmap();

    /**
     * 用于根据登录的客户端标识找channel
     */
    private ConcurrentHashMap<String, Channel> str2channel = ChannelData.getInstance().getStr2channel();

    /**
     * 用于根channel 找登录的客户端
     */
    private ConcurrentHashMap<Channel, String> channel2str = ChannelData.getInstance().getChannel2str();

    /**
     * 操作数据库线程组
     */
    private ExecutorService executorService = Executors.newFixedThreadPool(8);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        if (msg instanceof MqttMessage) {
            MqttMessage message = (MqttMessage) msg;
            MqttMessageType messageType = message.fixedHeader().messageType();

            switch (messageType) {
            case SUBSCRIBE:
                MqttSubscribeMessage subscribeMessage = (MqttSubscribeMessage) message;
                System.out.println("subscribe");

                sub(ctx, subscribeMessage);
                break;
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
     * 订阅操作 向服务器订阅所有感兴趣的主题 并且按照客户端标识找到未读消息接受这些消息
     * 
     * @param ctx
     * @param subscribeMessage
     */
    private void sub(ChannelHandlerContext ctx, MqttSubscribeMessage subscribeMessage) {
        MqttFixedHeader fixedHeader = new MqttFixedHeader(MqttMessageType.SUBACK, false, MqttQoS.AT_MOST_ONCE, false, 0);

        MqttSubAckPayload payload = new MqttSubAckPayload(2);
        MqttMessageIdVariableHeader mqttMessageIdVariableHeader = MqttMessageIdVariableHeader.from(subscribeMessage.variableHeader().messageId());
        MqttSubAckMessage subAckMessage = new MqttSubAckMessage(fixedHeader, mqttMessageIdVariableHeader, payload);
        ctx.writeAndFlush(subAckMessage);

        List<MqttTopicSubscription> list = subscribeMessage.payload().topicSubscriptions();

        List<String> topNames = new ArrayList<String>();

        for (MqttTopicSubscription subscription : list) {
            topNames.add(subscription.topicName());
        }
        if (!topNames.isEmpty()) {
            String iden = channel2str.get(ctx.channel());

            for (String topName : topNames) {
                BlockingQueue<String> clientIdens = submap.get(topName);
                if (clientIdens == null)
                    clientIdens = new LinkedBlockingQueue<String>();
                if (!clientIdens.contains(iden))
                    clientIdens.offer(iden);
                submap.put(topName, clientIdens);

            }

            submitGetUnReadyMsg(iden);
        }
    }

    /**
     * 在订阅的时候 指定的 client 接受消息
     * 
     * @param clientid
     */
    private void submitGetUnReadyMsg(final String clientid) {

        executorService.submit(new GetUnReadData(clientid));
    }

}
