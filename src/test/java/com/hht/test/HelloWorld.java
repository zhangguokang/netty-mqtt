/**
 * Project Name:concurrent-maven
 * File Name:HelloWorld.java
 * Package Name:com.hht.actor2
 * Date:2018年12月13日
 * Copyright (c) 2018 深圳市鸿合创新信息技术 Inc.All Rights Reserved.
 */
package com.hht.test;

import com.hht.client.MqttClient;

import akka.actor.Props;
import akka.actor.UntypedActor;

/**
 * @author zhangguokang
 *
 * @description
 */
public class HelloWorld extends UntypedActor {
    public int id;

    public HelloWorld(final int id) {
        this.id = id;
    }

    public static Props props(int id) {
        return Props.create(HelloWorld.class, id);
    }

    @Override
    public void preStart() throws InterruptedException {
        System.out.println("Actor ID " + id + " says: I am created");
        MqttClient.test("tom", "123456");
    }

    public void work() {
        while (true) {
            System.out.println("Actor ID " + id + " says: Hello world!");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onReceive(Object msg) throws Exception {
        if (((String) msg).equals(Message.DONE)) {
            this.getContext().stop(this.getSelf());
        } else if (((String) msg).equals(Message.WORK)) {
            this.work();
        }
    }

}
