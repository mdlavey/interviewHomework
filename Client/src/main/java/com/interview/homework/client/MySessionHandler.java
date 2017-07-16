package com.interview.homework.client;


import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.math.BigInteger;

import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.messaging.simp.stomp.StompFrameHandler;

import java.lang.reflect.Type;

public class MySessionHandler extends StompSessionHandlerAdapter {

    private void subscribeReturnFib(String returnFib,StompSession session)
    {
        session.subscribe(returnFib, new StompFrameHandler() {

            @Override
            public Type getPayloadType(StompHeaders headers) {
                return BigInteger.class;
            }

            @Override
            public void handleFrame(StompHeaders headers,
                        Object payload)
            {
            System.err.println(payload.toString());
            }
        });
    }

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders)
    {
        subscribeReturnFib("/returnFib", session);
        session.send("/app/findFib", null);
    }
}