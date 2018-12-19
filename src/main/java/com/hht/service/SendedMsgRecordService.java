/**
 * Project Name:netty-mqtt
 * File Name:SendedMsgRecordService.java
 * Package Name:com.hht.service
 * Date:2018年12月19日
 * Copyright (c) 2018 深圳市鸿合创新信息技术 Inc.All Rights Reserved.
 */
package com.hht.service;

import com.hht.entity.SendedMsgRecord;

/**
 * @author zhangguokang
 *
 * @description
 */
public interface SendedMsgRecordService {
    /**
     * 保存
     * 
     * @param SendedMsgRecord
     */
    public void save(SendedMsgRecord SendedMsgRecord);

}
