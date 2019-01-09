package com.hht.global;

import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
/**
 * @author zhangguokang
 *
 * @description netty 全局的channel管理
 */
public class GlobalChannelGroup {
    public static DefaultChannelGroup group = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

}
