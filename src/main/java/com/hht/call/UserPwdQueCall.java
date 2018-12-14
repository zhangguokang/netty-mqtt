/**
 * Project Name:netty-mqtt
 * File Name:UserPwdQueCall.java
 * Package Name:com.hht.call
 * Date:2018年12月14日
 * Copyright (c) 2018 深圳市鸿合创新信息技术 Inc.All Rights Reserved.
 */
package com.hht.call;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import com.hht.vo.UserValidate;
import io.netty.channel.Channel;

/**
 * @author zhangguokang
 *
 * @description
 */
public class UserPwdQueCall implements Callable<Void> {

    private List<UserValidate> userValidates;

    private ConcurrentHashMap<String, Channel> str2channel;

    private ConcurrentHashMap<Channel, String> channel2str;

    private static UserPwdQueCall userPwdQueCall;

    public static UserPwdQueCall getInstance() {

        if (userPwdQueCall == null)
            userPwdQueCall = new UserPwdQueCall();

        return userPwdQueCall;
    }

    private UserPwdQueCall() {

    }
    
    public UserPwdQueCall(List<UserValidate> userValidates,
            ConcurrentHashMap<String, Channel> str2channel,
            ConcurrentHashMap<Channel, String> channel2str) {
    super();
    this.userValidates = userValidates;
    this.str2channel = str2channel;
    this.channel2str = channel2str;
}

    @Override
    public Void call() throws Exception {

        return null;
    }

}
