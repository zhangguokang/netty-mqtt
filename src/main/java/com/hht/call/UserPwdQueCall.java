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
import com.hht.entity.User;
import com.hht.entity.UserExample;
import com.hht.global.ChannelData;
import com.hht.service.UserService;
import com.hht.util.SpringUtil;
import com.hht.vo.UserValidate;
import io.netty.channel.Channel;
import io.netty.handler.codec.mqtt.MqttConnAckMessage;
import io.netty.handler.codec.mqtt.MqttConnAckVariableHeader;
import io.netty.handler.codec.mqtt.MqttConnectReturnCode;
import io.netty.handler.codec.mqtt.MqttFixedHeader;
import io.netty.handler.codec.mqtt.MqttMessageType;
import io.netty.handler.codec.mqtt.MqttQoS;

/**
 * @author zhangguokang
 *
 * @description
 */
public class UserPwdQueCall implements Callable<Void> {

    private UserService userService = SpringUtil.getBean(UserService.class);

    private List<UserValidate> userValidates;

    private ConcurrentHashMap<String, Channel> str2channel = ChannelData.getInstance().getStr2channel();

    private ConcurrentHashMap<Channel, String> channel2str = ChannelData.getInstance().getChannel2str();

    private static UserPwdQueCall userPwdQueCall;

    public static UserPwdQueCall getInstance() {

        if (userPwdQueCall == null)
            userPwdQueCall = new UserPwdQueCall();

        return userPwdQueCall;
    }

    private UserPwdQueCall() {

    }

    public UserPwdQueCall(List<UserValidate> userValidates, ConcurrentHashMap<String, Channel> str2channel, ConcurrentHashMap<Channel, String> channel2str) {
        super();
        this.userValidates = userValidates;
        this.str2channel = str2channel;
        this.channel2str = channel2str;
    }

    @Override
    public Void call() {
        try {
            MqttFixedHeader fixedHeader = new MqttFixedHeader(MqttMessageType.CONNACK, false, MqttQoS.AT_LEAST_ONCE, false, 0);

            MqttConnAckVariableHeader connectVariableHeader = null;
            for (UserValidate userValidate : userValidates) {
                String userName = userValidate.getUserName();
                String passWord = userValidate.getPassWord();
                Channel channel = userValidate.getChannel();
                UserExample userExample = new UserExample();
                userExample.createCriteria().andUsernameEqualTo(userName).andPasswordEqualTo(passWord);
                List<User> userList = userService.findUserListByExample(userExample);

                if (userList != null && userList.size() > 0) {
                    // 用户名和密码正确
                    connectVariableHeader = new MqttConnAckVariableHeader(MqttConnectReturnCode.CONNECTION_ACCEPTED, false);
                    // 保存用户会话
                    str2channel.put(userName, channel);
                    channel2str.put(channel, userName);

                } else {
                    //
                    connectVariableHeader = new MqttConnAckVariableHeader(MqttConnectReturnCode.CONNECTION_REFUSED_BAD_USER_NAME_OR_PASSWORD, false);
                }

                MqttConnAckMessage ackMessage = new MqttConnAckMessage(fixedHeader, connectVariableHeader);
                channel.writeAndFlush(ackMessage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

}
