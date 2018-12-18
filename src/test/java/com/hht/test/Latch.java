/**
 * Project Name:concurrent-maven
 * File Name:Latch.java
 * Package Name:com.hht.actor2
 * Date:2018年12月13日
 * Copyright (c) 2018 深圳市鸿合创新信息技术 Inc.All Rights Reserved.
 */
package com.hht.test;

import java.util.ArrayList;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;

/**
 * @author zhangguokang
 *
 * @description
 */
public class Latch extends UntypedActor {
    ArrayList<ActorRef> actors;
    long duration = 30;
    Byte lock = 1;

    public static Props props() {
        return Props.create(Latch.class);
    }

    @Override
    public void preStart() throws InterruptedException {
        long startTime = System.currentTimeMillis();
        ArrayList<ActorRef> list = new ArrayList<ActorRef>();
        for (int i = 0; i < 1000; i++) {
            ActorRef greeter = getContext().actorOf(HelloWorld.props(i).withDispatcher("my-thread-pool-dispatcher"), "Helloworld" + i);
            list.add(greeter);
        }
        for (ActorRef ref : list) {
            ref.tell(Message.WORK, this.getSelf());
        }
        while (true) {
            if (duration > 0 && (System.currentTimeMillis() - startTime) > (duration * 1000)) {
                kill();
                break;
            }
        }
    }

    private void kill() {
        for (ActorRef ref : actors) {
            ref.tell(Message.DONE, this.getSelf());
        }
        this.getContext().stop(this.getSelf());
        System.exit(0);
    }

    @Override
    public void onReceive(Object o) throws Exception {

    }

}
