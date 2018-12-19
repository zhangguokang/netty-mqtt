/**
 * Project Name:netty-mqtt
 * File Name:MsgRepServiceImpl.java
 * Package Name:com.hht.service.impl
 * Date:2018年12月19日
 * Copyright (c) 2018 深圳市鸿合创新信息技术 Inc.All Rights Reserved.
 */
package com.hht.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hht.dao.IMsgRepDao;
import com.hht.entity.MsgRep;
import com.hht.service.MsgRepService;

/**
 * @author zhangguokang
 *
 * @description
 */

@Service
@Transactional
public class MsgRepServiceImpl implements MsgRepService {

    @Autowired
    private IMsgRepDao msgRepDao;

    @Override
    public void save(MsgRep msgRep) {
        msgRepDao.insertSelective(msgRep);
    }

}
