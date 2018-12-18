/**
 * Project Name:netty-mqtt
 * File Name:ConnectionHandler.java
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
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hht.call.UserPwdQueCall;
import com.hht.global.ChannelData;
import com.hht.server.MQTTServer;
import com.hht.vo.UserValidate;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.handler.codec.mqtt.MqttConnAckMessage;
import io.netty.handler.codec.mqtt.MqttConnAckVariableHeader;
import io.netty.handler.codec.mqtt.MqttConnectMessage;
import io.netty.handler.codec.mqtt.MqttConnectPayload;
import io.netty.handler.codec.mqtt.MqttConnectReturnCode;
import io.netty.handler.codec.mqtt.MqttFixedHeader;
import io.netty.handler.codec.mqtt.MqttMessage;
import io.netty.handler.codec.mqtt.MqttMessageType;
import io.netty.handler.codec.mqtt.MqttQoS;

/**
 * @author zhangguokang
 *
 * @description 连接处理handler
 */
@Sharable
public class ConnectionHandler extends ChannelInboundHandlerAdapter {

    /**
     * 日志
     */
    private static final Logger log = LoggerFactory.getLogger(ConnectionHandler.class);

    /**
     * 用于根据登录的客户端标识找channel
     */
    private ConcurrentHashMap<String, Channel> str2channel = ChannelData.getInstance().getStr2channel();

    /**
     * 用于根channel 找登录的客户端
     */
    ConcurrentHashMap<Channel, String> channel2str = ChannelData.getInstance().getChannel2str();

    /**
     * 缓存用户登录队列的长度
     */
    private final int PRO_LOGIN_SIZE = 20;

    /**
     * 缓存用于验证用户登录的队列 当队列满的时候去数据库验证 防止重复操作数据库io性能下降
     */
    private BlockingQueue<UserValidate> userPwdValidateQue = new LinkedBlockingQueue<UserValidate>(PRO_LOGIN_SIZE);

    /**
     * 当前登录的连接数量
     */
    AtomicInteger loginConns = new AtomicInteger();;// 当前登录的连接数量

    /**
     * 操作数据库线程组
     */
    ExecutorService executorService = Executors.newFixedThreadPool(8);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof MqttMessage) {

            MqttMessage message = (MqttMessage) msg;
            MqttMessageType messageType = message.fixedHeader().messageType();

            switch (messageType) {
            case CONNECT:
                System.out.println("connect");
                try {
                    MqttConnectMessage connectMessage = (MqttConnectMessage) message;
                    loginConns.getAndIncrement();
                    ack(ctx, connectMessage);
                    loginConns.getAndDecrement();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case PINGREQ:
                System.out.println("ping");
                ping(ctx);
                break;

            case DISCONNECT:
                System.out.println("disconnect");
                loginout(ctx);
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
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

        super.channelInactive(ctx);

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
        try {
            super.channelReadComplete(ctx);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        ctx.channel().close();
    }

    /**
     * 验证用户连接合法性
     * 
     * 首先检查用户是否登录过了，如果登录了就拒绝重登。 检查当前需要登录验证的链接的数量，如果过多就缓存到队列 队列满了去数据库一次执行
     * 如果比较少就一个一个验证
     * 
     * @param ctx
     * @param connectMessage
     */
    private void ack(ChannelHandlerContext ctx, final MqttConnectMessage connectMessage) {

        MqttConnectPayload connectPayload = connectMessage.payload();

        String ident = connectPayload.clientIdentifier();// clientId

        MqttFixedHeader fixedHeader = new MqttFixedHeader(MqttMessageType.CONNACK, false, MqttQoS.AT_MOST_ONCE, false, 0);
        MqttConnAckVariableHeader connectVariableHeader = null;
        String userName = connectPayload.userName();// userName
        String passWord = connectPayload.password();// passWord
        // 根据clientId判断是否重复登录
        if (str2channel.containsKey(ident)) {// 重复登录
            connectVariableHeader = new MqttConnAckVariableHeader(MqttConnectReturnCode.CONNECTION_REFUSED_IDENTIFIER_REJECTED, false);

            MqttConnAckMessage ackMessage = new MqttConnAckMessage(fixedHeader, connectVariableHeader);
            ctx.writeAndFlush(ackMessage);
            return;
        }

        UserValidate userValidate = new UserValidate(ident, userName, passWord, ctx.channel());

        if (loginConns.get() > PRO_LOGIN_SIZE) {// 如果当前登录的过多就放入队列

            if (!userPwdValidateQue.offer(userValidate)) {// offer:
                                                          // 将指定元素插入此队列中（如果立即可行且不会违反容量限制），成功时返回
                                                          // true，如果当前没有可用的空间，则返回
                                                          // false，不会抛异常：
                // 塞满最大容量才执行,
                log.info("排队");
                // System.exit(0);
                List<UserValidate> userValidates = new ArrayList<UserValidate>();
                userPwdValidateQue.drainTo(userValidates);// drainTo():一次性从BlockingQueue获取所有可用的数据对象（还可以指定获取数据的个数）,通过该方法，可以提升获取数据效率；不需要多次分批加锁或释放锁。
                subumitLoginCall(userValidates);
            }

        } else { // 否则就单个处理
            log.info("单个处理");
            List<UserValidate> userValidates = new ArrayList<UserValidate>();
            /*
             * if(userPwdValidateQue.size()>0){
             * userPwdValidateQue.drainTo(userValidates); }
             */
            userValidates.add(userValidate);
            subumitLoginCall(userValidates);
        }
    }

    /**
     * 提交登录任务
     * 
     * @param userValidates
     */
    void subumitLoginCall(List<UserValidate> userValidates) {

        UserPwdQueCall call = new UserPwdQueCall(userValidates, str2channel, channel2str);

        executorService.submit(call);
        call = null;
    }

    /**
     * 心跳ping
     * 
     * @param ctx
     */
    private void ping(ChannelHandlerContext ctx) {

        MqttFixedHeader fixedHeader = new MqttFixedHeader(MqttMessageType.PINGRESP, false, MqttQoS.AT_LEAST_ONCE, false, 0);

        MqttMessage mqttMessage = new MqttMessage(fixedHeader);
        ctx.channel().writeAndFlush(mqttMessage);
    }

    private void loginout(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        String ident = channel2str.get(channel);

        if (ident != null) {
            str2channel.remove(ident);
            log.info(ident + "退出" + str2channel.size());

            channel2str.remove(channel);
            log.info(channel + "断开" + channel2str.size());
        }

        channel.close();
    }

}
