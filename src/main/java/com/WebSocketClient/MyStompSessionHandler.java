package com.WebSocketClient;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import java.lang.reflect.Type;

public class MyStompSessionHandler extends StompSessionHandlerAdapter {

    private static StompSession stompSession;
    private final Log LOGGER = LogFactory.getLog(getClass());

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        LOGGER.info("MyStompSessionHandler afterConnected..");
        System.out.println("New session established : " + session.getSessionId());
        session.subscribe("/user/topic/messages", this);
        System.out.println("Subscribed to /topic/messages");
        setStompSession(session);
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        LOGGER.info("MyStompSessionHandler handleException..");
        System.out.println("Got an exception" + exception.toString());
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        LOGGER.info("MyStompSessionHandler getPayloadType..");
        return Message.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        LOGGER.info("MyStompSessionHandler handleFrame..");
        Message message = (Message) payload;
        System.out.println("Received : " + message);
    }

    public static void setStompSession(StompSession stompSession) {
        MyStompSessionHandler.stompSession = stompSession;
    }

    public static StompSession getStompSession() {
        return stompSession;
    }
}