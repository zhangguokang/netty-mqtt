/**
 * Project Name:netty-mqtt
 * File Name:GetUnReadData.java
 * Package Name:com.hht.call
 * Date:2018年12月19日
 * Copyright (c) 2018 深圳市鸿合创新信息技术 Inc.All Rights Reserved.
 */
package com.hht.call;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.ibatis.session.SqlSession;

import com.hht.entity.MsgRep;
import com.hht.entity.SendedMsgRecord;
import com.hht.global.MessageData;


/**
 * @author zhangguokang
 *
 * @description
 */
public class GetUnReadData implements Callable<Void> {

    private BlockingQueue<MsgRep> msgRepsque = MessageData.getInstance().msgRepsque;
    /**
     * 准备更改状态的 已发送消息
     */
    private BlockingQueue<SendedMsgRecord> readySendedQue = MessageData.getInstance().readySendedQue;

    private static AtomicInteger atomicInteger = new AtomicInteger(1);

    private static AtomicInteger successInteger = new AtomicInteger(1);
    private String clientid;

    public GetUnReadData(String clientid) {
        this.clientid = clientid;

    }

    
    /**
     *  一共四种情况。
     *  TAB_send<--->TAB_msg
     *  TAB_send<--->MEM_msg
     *  MEM_sen<--->TAB_msg
     *  MEM_sen<--->MEM_msg
     */
    @Override
    public Void call() throws Exception {

        return null;
    }
    
    
    /**
     * 从已发消息 里面获取待收消息id  分别在消息队列和数据库取出来消息
     * @param session
     * @return
     */
    public  List<MsgRep> queryBySendTabGetMsg(SqlSession session){
            
            
            List<MsgRep> result=new ArrayList<MsgRep>();
            SendedMsgRecord msg=new SendedMsgRecord();
            msg.setClientid(clientid);
            
            //List<Integer> msgids= session.selectList(selectUnReadUserMsgIds,msg);
            
            
            if(msgids.isEmpty())
                    return result;
            Map<String, Object> map=new HashMap<String, Object>();
            map.put("list", msgids);
            // TAB_send<--->TAB_msg
            
            //result=session.selectList(selectUnreadMsgByMsgIds,map);
            
            Iterator<MsgRep> iterator=
                            msgRepsque.iterator();
            
            
             //TAB_send<--->MEM_msg
            while(iterator.hasNext()){
                    
                    MsgRep msgRep=iterator.next();
                    if(msgids.contains(msgRep.getId())){
                            result.add(msgRep);
                    }
            }
            
            return result;
    }

}
