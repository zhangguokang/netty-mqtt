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
import com.hht.entity.User;
import com.hht.entity.UserExample;
import com.hht.global.GlobalChannelGroup;
import com.hht.service.UserService;
import com.hht.session.NettySession;
import com.hht.session.NettySessionManager;
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

    public UserPwdQueCall(List<UserValidate> userValidates) {
        super();
        this.userValidates = userValidates;

    }

    @Override
    public Void call() {
        try {
            MqttFixedHeader fixedHeader = new MqttFixedHeader(MqttMessageType.CONNACK, false, MqttQoS.AT_LEAST_ONCE,
                    false, 0);

            MqttConnAckVariableHeader connectVariableHeader = null;
            for (UserValidate userValidate : userValidates) {
                String userName = userValidate.getUserName();
                String passWord = userValidate.getPassWord();
                Channel channel = userValidate.getChannel();
                String ident = userValidate.getIdent();
                UserExample userExample = new UserExample();
                userExample.createCriteria().andUsernameEqualTo(userName).andPasswordEqualTo(passWord);
                List<User> userList = userService.findUserListByExample(userExample);

                if (userList != null && userList.size() > 0) {
                    // 用户名和密码正确
                    connectVariableHeader = new MqttConnAckVariableHeader(MqttConnectReturnCode.CONNECTION_ACCEPTED,
                            false);
                    // 保存channel
                    GlobalChannelGroup.group.add(channel);

                    // 保存session
                    NettySessionManager.pushSession2Redis(NettySession.buildSession(userName, ident));

                } else {
                    //
                    connectVariableHeader = new MqttConnAckVariableHeader(
                            MqttConnectReturnCode.CONNECTION_REFUSED_BAD_USER_NAME_OR_PASSWORD, false);
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
