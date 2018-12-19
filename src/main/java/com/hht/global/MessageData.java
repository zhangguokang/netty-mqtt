/**
 * Project Name:netty-mqtt
 * File Name:MessageData.java
 * Package Name:com.hht.global
 * Date:2018年12月19日
 * Copyright (c) 2018 深圳市鸿合创新信息技术 Inc.All Rights Reserved.
 */
package com.hht.global;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hht.constant.MsgConstant;
import com.hht.entity.MsgRep;
import com.hht.entity.SendedMsgRecord;
import com.hht.handler.PushServiceHandler;
import com.hht.service.SendedMsgRecordService;
import com.hht.util.SpringUtil;
import com.hht.vo.DelaySendMsg;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.handler.codec.mqtt.MqttFixedHeader;
import io.netty.handler.codec.mqtt.MqttMessageType;
import io.netty.handler.codec.mqtt.MqttPublishMessage;
import io.netty.handler.codec.mqtt.MqttPublishVariableHeader;
import io.netty.handler.codec.mqtt.MqttQoS;

/**
 * @author zhangguokang
 *
 * @description
 */
public class MessageData {

    /**
     * 日志
     */
    private static final Logger log = LoggerFactory.getLogger(PushServiceHandler.class);

    private final int MAXCACHEMSGSIZE = 10;

    /**
     * 每一个客户端订阅的主题
     */
    private ConcurrentHashMap<String, BlockingQueue<String>> submap = ChannelData.getInstance().getSubmap();

    /**
     * 主题消息 不清除
     */
    private ConcurrentHashMap<String, Integer> topContent = new ConcurrentHashMap<String, Integer>();

    /**
     * 消息 队列
     */
    private BlockingQueue<MsgRep> msgRepsque = new LinkedBlockingQueue<MsgRep>(MAXCACHEMSGSIZE);

    /**
     * 已经发送过的消息
     */
    private BlockingQueue<SendedMsgRecord> msgSendedQue = new LinkedBlockingQueue<SendedMsgRecord>(MAXCACHEMSGSIZE);

    /**
     * 准备更改状态的 已发送消息
     */
    private BlockingQueue<SendedMsgRecord> readUpdateSendedQue = new LinkedBlockingQueue<SendedMsgRecord>(MAXCACHEMSGSIZE);

    private BlockingQueue<SendedMsgRecord> readySendedQue = new LinkedBlockingQueue<SendedMsgRecord>(MAXCACHEMSGSIZE);

    /**
     * 已经发送过的消息 不能清除
     */
    private ConcurrentHashMap<String, ConcurrentLinkedQueue<Integer>> messageSends = new ConcurrentHashMap<String, ConcurrentLinkedQueue<Integer>>();

    /**
     * 操作数据库线程组
     */
    private ExecutorService executorService = Executors.newFixedThreadPool(8);

    private static ReSendData reSendService =ReSendData.getInstance();

    private SendedMsgRecordService sendedMsgRecordService = SpringUtil.getBean(SendedMsgRecordService.class);

    /**
     * 用于根据登录的客户端标识找channel
     */
    private ConcurrentHashMap<String, Channel> str2channel = ChannelData.getInstance().getStr2channel();
    private static MessageData messageData = null;

    public static MessageData getInstance() {
        if (messageData == null) {
            messageData = new MessageData();
        }

        return messageData;
    }

    private MessageData() {

    }

    public void sendPubMsg(MsgRep transportMessage, MqttQoS qos) {
        BlockingQueue<String> idents = submap.get(transportMessage.getTopname());

        if (idents == null)
            return;
        Iterator<String> iterator = idents.iterator();

        while (iterator.hasNext()) {
            String clientid = iterator.next();

            saveStateInDb(transportMessage, qos, clientid);

            sendMsg2Client(transportMessage, clientid, qos);

        }
    }

    public void saveStateInDb(MsgRep transportMessage, MqttQoS qoS, String clientid) {
        SendedMsgRecord msg = null;
        switch (qoS) {
        case EXACTLY_ONCE:

            msg = new SendedMsgRecord(clientid, transportMessage.getId(), MsgConstant.MSG_SENDINDB, transportMessage.getSendiden());
            break;
        default:
            msg = new SendedMsgRecord(clientid, transportMessage.getId(), MsgConstant.MSG_SENDSUCCESS, transportMessage.getSendiden());

        }

        if (msg != null) {
            saveSendMsg(msg);
        }

    }

    public void sendMsg2Client(MsgRep transportMessage, String clientid, MqttQoS qos) {
        Channel channel = str2channel.get(clientid);

        if (channel != null) {

            MqttFixedHeader Header = new MqttFixedHeader(MqttMessageType.PUBLISH, true, MqttQoS.EXACTLY_ONCE, false, 0);
            MqttPublishVariableHeader publishVariableHeader = new MqttPublishVariableHeader(transportMessage.getTopname(), transportMessage.getId());

            MqttPublishMessage publishMessage = new MqttPublishMessage(Header, publishVariableHeader, Unpooled.copiedBuffer(transportMessage.getContent()));

            channel.writeAndFlush(publishMessage);

            /**
             * 放入重发队列 设置超时时间为一分钟
             */

            if (qos.equals(MqttQoS.EXACTLY_ONCE)) {
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.MINUTE, 1);
                DelaySendMsg delaySendMsg = new DelaySendMsg(transportMessage, clientid, calendar.getTime().getTime());
                reSendService.submit(delaySendMsg);
            }

            log.info("对" + clientid + "发送了 id:" + transportMessage.getId() + "消息：" + new String(transportMessage.getContent(), Charset.forName("UTF-8")));
        }
    }

    public void saveSendMsg(SendedMsgRecord sendMsg) {
        if (sendMsg.getSendtimes() < 2) {
            if (!readySendedQue.offer(sendMsg)) {
                List<SendedMsgRecord> msgs = new ArrayList<SendedMsgRecord>();
                msgs.add(sendMsg);
                readySendedQue.drainTo(msgs);

                submitSendedMsg(msgs);

            }
        } else {
            if (!msgSendedQue.offer(sendMsg)) {
                List<SendedMsgRecord> msgs = new ArrayList<SendedMsgRecord>();
                msgs.add(sendMsg);
                msgSendedQue.drainTo(msgs);

                submitSendedMsg(msgs);

            }
        }
    }

    public void submitSendedMsg(List<SendedMsgRecord> sedmsgs) {
        executorService.submit(new Runnable() {

            @Override
            public void run() {
                if (sedmsgs != null) {
                    sedmsgs.stream().forEach(sedmsg -> sendedMsgRecordService.save(sedmsg));
                }
            }
        });

    }

}
