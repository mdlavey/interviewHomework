package com.interview.home.client;


import java.util.List;
import java.util.ArrayList;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.messaging.simp.stomp.StompSession;

import com.interview.homework.client.MySessionHandler;

@SpringBootApplication
public class FibonacciClientApplication {

    public static void main(String[] args) throws Exception {
        WebSocketClient simpleWebSocketClient = new StandardWebSocketClient();
		List<Transport> transports = new ArrayList<>(1);
		transports.add(new WebSocketTransport(simpleWebSocketClient));

		SockJsClient sockJsClient = new SockJsClient(transports);
		WebSocketStompClient stompClient = new WebSocketStompClient(sockJsClient);
		stompClient.setMessageConverter(new MappingJackson2MessageConverter());

		String url = "ws://localhost:8080/findFib";
		StompSessionHandler sessionHandler = new MySessionHandler();
		StompSession session = stompClient.connect(url, sessionHandler).get();
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		for (;;) {
		    String line = in.readLine();
		    if ( line == null ) break;
		    if ( line.length() == 0 ) continue;
		    try {
		    	session.send("/app/findFib", Integer.parseUnsignedInt(line));
		    } catch (NumberFormatException e) {
		    	System.err.println("Please enter a single unsigned int.");
		    }
		}
	}

}