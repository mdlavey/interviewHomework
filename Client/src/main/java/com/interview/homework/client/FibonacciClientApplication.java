package com.interview.home.client;


import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import java.math.BigInteger;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class FibonacciClientApplication {

    private static CountDownLatch countDownLatch;

    static public class MyStompSessionHandler extends StompSessionHandlerAdapter
    {

        private void subscribeReturnFib(String returnFib,StompSession session)
        {
            session.subscribe(returnFib, new StompFrameHandler() {

                @Override
                public Type getPayloadType(StompHeaders headers) {
                    return BigInteger.class;
                }

                @Override
                public void handleFrame(StompHeaders headers, Object payload)
                {
                    countDownLatch.countDown();
                    System.out.println(payload.toString());
                }
            });
        }

        @Override
        public void afterConnected(StompSession session, StompHeaders connectedHeaders)
        {

            subscribeReturnFib("/returnFib", session);
        }
    }

    public static void main(String[] args) throws Exception {
        countDownLatch = new CountDownLatch(1);
        WebSocketClient simpleWebSocketClient = new StandardWebSocketClient();
        List<Transport> transports = new ArrayList<>(1);
        transports.add(new WebSocketTransport(simpleWebSocketClient));

        SockJsClient sockJsClient = new SockJsClient(transports);
        WebSocketStompClient stompClient = new WebSocketStompClient(sockJsClient);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        String url = "ws://localhost:8080/findFib";
        StompSessionHandler sessionHandler = new MyStompSessionHandler();
        StompSession session = stompClient.connect(url, sessionHandler).get();

        try {
            session.send("/app/findFib", Integer.parseUnsignedInt(args[0]));
            // wait for messange being echoed
            if (!countDownLatch.await(30, TimeUnit.SECONDS)) {
                throw new Exception("message not received");
            }
        } catch (NumberFormatException e) {
            System.err.println("Please enter a single unsigned int.");
        }
    }

}