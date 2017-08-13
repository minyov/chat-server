package com.minyov.chatserver.websocket.handlers;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

@Component
public class ChatWebSocketHandler implements WebSocketHandler {

    private static final Logger logger = LogManager.getLogger(ChatWebSocketHandler.class);
    
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        logger.trace(session.getRemoteAddress().getHostString() + " connected");
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> webSocketMessage) throws Exception {
        logger.trace(session.getRemoteAddress().getHostString() + ": " + webSocketMessage.getPayload());
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
