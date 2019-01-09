/**
 * Project Name:hht-backend-communication-websocket
 * File Name:NettySessionManager.java
 * Package Name:com.hht.session
 * Date:2018年8月8日
 * Copyright (c) 2018 深圳市鸿合创新信息技术 Inc.All Rights Reserved.
 */
package com.hht.session;

import com.hht.util.RedisUtil;

import com.hht.util.SpringUtil;

/**
 * @author zhangguokang
 *
 * @description
 */
public class NettySessionManager {
    static RedisUtil redisUtil = (RedisUtil) SpringUtil.getBean(RedisUtil.class);

    // 将Session放入redis
    public static void pushSession2Redis(NettySession session) {

        redisUtil.set(session.getUserName(), session);//
    }

    // 从redis获取指定session
    public static NettySession findById(String userName) {

        NettySession nettySession = (NettySession) redisUtil.get(userName);

        return nettySession;
    }

    // 从redis中移除session
    public static void removeSession(String userName) {
        redisUtil.remove(userName);

    }

    // 验证session是否存在
    public static Boolean existSession(String userName) {
        return redisUtil.exists(userName);
    }

    // 从redis中更新lastCommunicateTimeStamp
    public static NettySession updateLastCommunicateTimeStamp(String userName) {
        NettySession buildSession = null;
        NettySession nettySession = (NettySession) redisUtil.get(userName);
        if (nettySession != null) {
            buildSession = NettySession.buildSession(nettySession.getUserName(), nettySession.getIdent());

            pushSession2Redis(buildSession);
            return buildSession;

        }
        return buildSession;

    }

}
