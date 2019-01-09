package com.hht.session;

import com.hht.util.JsonUtil;

public class NettySession {

    // 终端的ID
    private String ident;

    // 用户名
    private String userName;

    // session有效期,默认30分钟
    private Long expireTime = 30 * 60L;

    public Long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIdent() {
        return ident;
    }

    public void setIdent(String ident) {
        this.ident = ident;
    }

    @Override
    public String toString() {

        return JsonUtil.toJson(this);

    }

    // 快速构建一个新的Session
    public static NettySession buildSession(String userName, String clientId) {
        NettySession session = new NettySession();
        session.setIdent(clientId);
        session.setUserName(userName);

        return session;
    }

}
