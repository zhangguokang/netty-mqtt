/**
 * Project Name:netty-mqtt
 * File Name:DelaySendMsg.java
 * Package Name:com.hht.vo
 * Date:2018年12月19日
 * Copyright (c) 2018 深圳市鸿合创新信息技术 Inc.All Rights Reserved.
 */
package com.hht.vo;

import java.util.Date;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import com.hht.entity.MsgRep;

/**
 * @author zhangguokang
 *
 * @description 延迟发送对象
 */
public class DelaySendMsg implements Delayed {

    MsgRep transportMessage; // 发送的消息对象
    String clientid;// 接受客户端标识
    /**
     * 将要重发的时间 单位毫秒
     */
    long willSendTime;

    public DelaySendMsg(long willSendTime) {
        this.willSendTime = willSendTime;
    }

    public DelaySendMsg(MsgRep transportMessage, String clientid, long willSendTime) {
        super();
        this.transportMessage = transportMessage;
        this.clientid = clientid;
        this.willSendTime = willSendTime;
    }

    @Override
    public int compareTo(Delayed o) {

        if (o instanceof DelaySendMsg) {
            DelaySendMsg delaySnedMsg = (DelaySendMsg) o;
            return (int) (this.willSendTime - delaySnedMsg.willSendTime);
        }
        return 1;
    }

    @Override
    public long getDelay(TimeUnit unit) {

        long l = new Date().getTime();
        return unit.convert(willSendTime - l, TimeUnit.MILLISECONDS);
    }

    public com.hht.entity.MsgRep getTransportMessage() {
        return transportMessage;
    }

    public void setTransportMessage(MsgRep transportMessage) {
        this.transportMessage = transportMessage;
    }

    public String getClientid() {
        return clientid;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid;
    }

}
