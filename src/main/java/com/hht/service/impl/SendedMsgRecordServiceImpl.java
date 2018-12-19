/**
 * Project Name:netty-mqtt
 * File Name:SendedMsgRecordServiceImpl.java
 * Package Name:com.hht.service.impl
 * Date:2018年12月19日
 * Copyright (c) 2018 深圳市鸿合创新信息技术 Inc.All Rights Reserved.
 */
package com.hht.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hht.dao.ISendedMsgRecordDao;
import com.hht.entity.SendedMsgRecord;
import com.hht.service.SendedMsgRecordService;

/**
 * @author zhangguokang
 *
 * @description
 */

@Service
@Transactional
public class SendedMsgRecordServiceImpl implements SendedMsgRecordService {

    @Autowired
    private ISendedMsgRecordDao sendedMsgDao;

    @Override
    public void save(SendedMsgRecord SendedMsgRecord) {
        sendedMsgDao.insertSelective(SendedMsgRecord);

    }

}
