/**
 * Project Name:netty-mqtt
 * File Name:GetUnReadData.java
 * Package Name:com.hht.call
 * Date:2018年12月19日
 * Copyright (c) 2018 深圳市鸿合创新信息技术 Inc.All Rights Reserved.
 */
package com.hht.call;

import java.util.concurrent.Callable;

/**
 * @author zhangguokang
 *
 * @description
 */
public class GetUnReadData implements Callable<Void> {
    private String clientid;

    public GetUnReadData(String clientid) {
        this.clientid = clientid;

    }

    @Override
    public Void call() throws Exception {
        
        return null;
    }

}
