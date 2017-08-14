package com.minyov.chatserver.websocket.handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.minyov.chatserver.database.dao.UserDao;
import com.minyov.chatserver.database.domain.Message;
import com.minyov.chatserver.database.domain.UserEntity;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.*;

@Component
public class ChatWebSocketHandler implements WebSocketHandler {

    private static final Map<String, WebSocketSession> sessionMap = new HashMap<>();

    private static final Gson gson = new GsonBuilder()
            .setDateFormat("dd/mm/yyyy HH:MM:ss:SSS")
            .excludeFieldsWithoutExposeAnnotation()
            .setPrettyPrinting()
            .create();

    @Autowired
    private UserDao userDao;

    private static final Logger logger = LogManager.getLogger(ChatWebSocketHandler.class);
    
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        logger.trace(session.getRemoteAddress().getHostString() + " connected");
        System.out.println("connected");
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> webSocketMessage) throws Exception {
        logger.trace(session.getRemoteAddress().getHostString() + ": " + webSocketMessage.getPayload());

        try {
            Message message = gson.fromJson(webSocketMessage.getPayload().toString(), Message.class);
            System.out.println(message.getText());
        } catch (JsonSyntaxException jse) {
            sessionMap.put(webSocketMessage.getPayload().toString(), session);
        }

    }

    public void sendMessage(String receiverName, Object message) {
        try {
            sessionMap.get(receiverName).sendMessage(new TextMessage(gson.toJson(message)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable throwable) throws Exception {
        logger.error(throwable.getMessage());

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        logger.trace("Session with " + session.getRemoteAddress().getHostName() + " closed. Reason: " +
                            closeStatus.getReason());
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
