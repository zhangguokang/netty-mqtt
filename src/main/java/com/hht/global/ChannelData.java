/**
 * Project Name:netty-mqtt
 * File Name:ChannelData.java
 * Package Name:com.hht.global
 * Date:2018年12月14日
 * Copyright (c) 2018 深圳市鸿合创新信息技术 Inc.All Rights Reserved.
 */
package com.hht.global;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import io.netty.channel.Channel;

/**
 * @author zhangguokang
 *
 * @description 懒汉单例
 */
public class ChannelData {
    private ConcurrentHashMap<String, Channel> str2channel = new ConcurrentHashMap<String, Channel>();

    private ConcurrentHashMap<Channel, String> channel2str = new ConcurrentHashMap<Channel, String>();

    /**
     * 每一个客户端订阅的主题
     */
    private ConcurrentHashMap<String, BlockingQueue<String>> submap = new ConcurrentHashMap<String, BlockingQueue<String>>();

    private static ChannelData channelData = null;

    public static ChannelData getInstance() {
        if (channelData == null) {
            channelData = new ChannelData();
        }

        return channelData;
    }

    public ConcurrentHashMap<String, Channel> getStr2channel() {
        return str2channel;
    }

    public void setStr2channel(ConcurrentHashMap<String, Channel> str2channel) {
        this.str2channel = str2channel;
    }

    public ConcurrentHashMap<Channel, String> getChannel2str() {
        return channel2str;
    }

    public void setChannel2str(ConcurrentHashMap<Channel, String> channel2str) {
        this.channel2str = channel2str;
    }

    public ConcurrentHashMap<String, BlockingQueue<String>> getSubmap() {
        return submap;
    }

    public void setSubmap(ConcurrentHashMap<String, BlockingQueue<String>> submap) {
        this.submap = submap;
    }

}
