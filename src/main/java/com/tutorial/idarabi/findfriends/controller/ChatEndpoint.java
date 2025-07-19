// src/main/java/com/example/ws/ChatEndpoint.java
package com.tutorial.idarabi.findfriends.controller;

import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint(value = "/ws")
@Component
public class ChatEndpoint {

    private static final CopyOnWriteArraySet<Session> sessions = new CopyOnWriteArraySet<>();

    @OnOpen
    public void onOpen(Session session) {
        sessions.add(session);
        System.out.println("New connection: " + session.getId());
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        System.out.println("Received message from " + session.getId() + ": " + message);
        // Echo message to all clients
        for (Session s : sessions) {
            s.getBasicRemote().sendText("Echo: " + message);
        }
    }

    @OnClose
    public void onClose(Session session) {
        sessions.remove(session);
        System.out.println("Connection closed: " + session.getId());
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.err.println("Error from session " + session.getId() + ": " + throwable.getMessage());
    }
}
