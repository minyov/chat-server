package com.veselov.chatserver.websocket.handlers;

import com.google.gson.JsonSyntaxException;
import com.veselov.chatserver.database.domain.Message;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

@Component
public class ChatWebSocketHandler implements WebSocketHandler {

    @Autowired
    private MessageService messageService;

    private static final Logger logger = LogManager.getLogger(ChatWebSocketHandler.class);
    
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        logger.trace(session.getRemoteAddress().getHostString() + " connected");
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> webSocketMessage) throws Exception {
        logger.trace(session.getRemoteAddress().getHostString() + ": " + webSocketMessage.getPayload());

        String stringFromUser = webSocketMessage.getPayload().toString();

        try {
            messageService.sendMessage(stringFromUser);
        } catch (JsonSyntaxException jse) {
            messageService.registerClient(stringFromUser, session);
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

        messageService.unRegisterClient(session);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
