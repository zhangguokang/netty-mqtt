/**
 * Project Name:netty-mqtt
 * File Name:ReSendData.java
 * Package Name:com.hht.global
 * Date:2018年12月19日
 * Copyright (c) 2018 深圳市鸿合创新信息技术 Inc.All Rights Reserved.
 */
package com.hht.global;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.DelayQueue;
import com.hht.entity.MsgRep;
import com.hht.vo.DelaySendMsg;
import io.netty.handler.codec.mqtt.MqttQoS;

/**
 * @author zhangguokang
 *
 * @description
 */
public class ReSendData {

    private DelayQueue<DelaySendMsg> delayQueue = new DelayQueue<DelaySendMsg>();

    private ConcurrentMap<String, DelaySendMsg> concurrentMap = new ConcurrentHashMap<String, DelaySendMsg>();

    private static MessageData messageDataService = MessageData.getInstance();
    private static ReSendData reSendData = null;

    public static ReSendData getInstance() {
        if (reSendData == null) {
            reSendData = new ReSendData();
        }

        return reSendData;
    }

    private ReSendData() {

    }

    public void submit(DelaySendMsg delaySnedMsg) {
        delayQueue.offer(delaySnedMsg);

        MsgRep msgRep = delaySnedMsg.getTransportMessage();
        concurrentMap.put(delaySnedMsg.getClientid() + msgRep.getMessageid(), delaySnedMsg);

    }

    public void start() {
        new Thread(new Runnable() {

            @Override
            public void run() {

                while (true) {
                    DelaySendMsg delaySnedMsg = null;
                    try {
                        System.out.println(delaySnedMsg = delayQueue.take());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (delaySnedMsg != null) {

                        messageDataService.sendMsg2Client(delaySnedMsg.getTransportMessage(), delaySnedMsg.getClientid(), MqttQoS.EXACTLY_ONCE);
                    }
                }

            }
        }, "checksuccess").start();
    }

    public boolean removeReSend(String clientid, Integer msgid) {

        String key = clientid + msgid;
        if (concurrentMap.containsKey(key)) {
            if (delayQueue.remove(concurrentMap.get(key))) {
                concurrentMap.remove(key);
                return true;
            }
        }
        return false;
    }
}
