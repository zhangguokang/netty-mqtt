/**
 * Project Name:netty-mqtt
 * File Name:UserServiceImpl.java
 * Package Name:com.hht.service.impl
 * Date:2018年12月14日
 * Copyright (c) 2018 深圳市鸿合创新信息技术 Inc.All Rights Reserved.
 */
package com.hht.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hht.dao.IUserDao;
import com.hht.entity.User;
import com.hht.entity.UserExample;
import com.hht.service.UserService;

/**
 * @author zhangguokang
 *
 * @description
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private IUserDao userDao;

    @Override
    public List<User> findUserListByExample(UserExample example) {
        return userDao.selectByExample(example);
    }

}
