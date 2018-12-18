/**
 * Project Name:concurrent-maven
 * File Name:Main.java
 * Package Name:com.hht.actor2
 * Date:2018年12月13日
 * Copyright (c) 2018 深圳市鸿合创新信息技术 Inc.All Rights Reserved.
 */
package com.hht.test;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

/**
 * @author zhangguokang
 *
 * @description
 */
public class Main {
    public static void main(String[] args) {
        new Main().run();
    }

    public void run() {
        ActorSystem system = ActorSystem.apply();

        ActorRef greeter = system.actorOf(Latch.props(), "Latch");
    }

}
