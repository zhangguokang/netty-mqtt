/**
 * Project Name:netty-mqtt
 * File Name:UserValidate.java
 * Package Name:com.hht.vo
 * Date:2018年12月14日
 * Copyright (c) 2018 深圳市鸿合创新信息技术 Inc.All Rights Reserved.
 */
package com.hht.vo;

import io.netty.channel.Channel;

/**
 * @author zhangguokang
 *
 * @description 用户channel
 */
public class UserValidate {

    private String ident;

    private String userName;

    private String passWord;

    private Channel channel;

    public UserValidate() {

    }

    public UserValidate(String ident, String userName, String passWord, Channel channel) {
        super();
        this.ident = ident;
        this.userName = userName;
        this.passWord = passWord;
        this.channel = channel;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public String getIdent() {
        return ident;
    }

    public void setIdent(String ident) {
        this.ident = ident;
    }

}
