/**
 * Project Name:netty-mqtt
 * File Name:UserService.java
 * Package Name:com.hht.service
 * Date:2018年12月14日
 * Copyright (c) 2018 深圳市鸿合创新信息技术 Inc.All Rights Reserved.
 */
package com.hht.service;

import java.util.List;

import com.hht.entity.User;
import com.hht.entity.UserExample;

/**
 * @author zhangguokang
 *
 * @description
 */
public interface UserService {
    /**
     * 根据查询条件查询用户列表
     * 
     * @param example
     *            用户条件
     * @return 用户列表
     */
    public List<User> findUserListByExample(UserExample example);
}
