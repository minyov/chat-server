package com.minyov.chatserver.websocket.handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.minyov.chatserver.database.dao.MessageDao;
import com.minyov.chatserver.database.dao.UserDao;
import com.minyov.chatserver.database.domain.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MessageService {

    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    private final Gson gson = new GsonBuilder()
            .setDateFormat("dd/mm/yyyy HH:MM:ss:SSS")
            .excludeFieldsWithoutExposeAnnotation()
            .setPrettyPrinting()
            .create();


    private final UserDao userDao;

    private final MessageDao messageDao;

    @Autowired
    public MessageService(UserDao userDao, MessageDao messageDao) {
        this.userDao = userDao;
        this.messageDao = messageDao;
    }

    public void registerClient(String name, WebSocketSession session) {
        sessions.put(name, session);
    }

    public void sendMessage(String jsonMessage) throws JsonSyntaxException, IOException {
        Message message = gson.fromJson(jsonMessage, Message.class);

        String receiverName = message.getReceiver().getName();

        messageDao.saveMessage(message, isUserOnline(receiverName));
        if (isUserOnline(receiverName)) {
            WebSocketSession receiverSession = getSession(receiverName);

            receiverSession.sendMessage(new TextMessage(jsonMessage));
        }
    }

    public WebSocketSession getSession(String name) {
        return sessions.get(name);
    }

    private boolean isUserOnline(String name) {
        return sessions.containsKey(name);
    }
}
