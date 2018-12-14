/**
 * Project Name:hht-backend-communication-websocket
 * File Name:SslUtil.java
 * Package Name:com.hht.util
 * Date:2018年10月30日
 * Copyright (c) 2018 深圳市鸿合创新信息技术 Inc.All Rights Reserved.
 */
package com.hht.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;

/**
 * @author zhangguokang
 *
 * @description
 */
public class SslUtil {
    public static SSLContext createSSLContext(String type, String path, String password) throws Exception {
        KeyStore ks = KeyStore.getInstance(type); /// "JKS"
        InputStream ksInputStream = new FileInputStream(path); /// 证书存放地址
        ks.load(ksInputStream, password.toCharArray());
        // KeyManagerFactory充当基于密钥内容源的密钥管理器的工厂。
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());// getDefaultAlgorithm:获取默认的
                                                                                                       // KeyManagerFactory
                                                                                                       // 算法名称。
        kmf.init(ks, password.toCharArray());
        // SSLContext的实例表示安全套接字协议的实现，它充当用于安全套接字工厂或 SSLEngine 的工厂。
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(kmf.getKeyManagers(), null, null);
        return sslContext;
    }

}
