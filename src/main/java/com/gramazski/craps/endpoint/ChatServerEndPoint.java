package com.gramazski.craps.endpoint;

import com.gramazski.craps.configurator.ChatServerEndPointConfigurator;
import com.gramazski.craps.entity.impl.Message;
import com.gramazski.craps.mapper.ObjectMapperWrapper;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gs on 07.03.2017.
 */
@ServerEndpoint(value="/chat", configurator=ChatServerEndPointConfigurator.class)
public class ChatServerEndPoint {
    private Map<String, Session> userSessions = Collections.synchronizedMap(new HashMap<String, Session>());

    /**
     * Callback hook for Connection open events. This method will be invoked when a
     * client requests for a WebSocket connection.
     * @param userSession the userSession which is opened.
     */
    @OnOpen
    public void onOpen(Session userSession) {
        Map<String, List<String>> requestMap = userSession.getRequestParameterMap();
        if (requestMap.containsKey("login")){
            System.out.println("+1: : " + requestMap.get("login").get(0));
            userSessions.put(requestMap.get("login").get(0), userSession);
        }

    }

    /**
     * Callback hook for Connection close events. This method will be invoked when a
     * client closes a WebSocket connection.
     * @param userSession the userSession which is opened.
     */
    @OnClose
    public void onClose(Session userSession) {
        if (userSessions.containsValue(userSession)){
            userSessions.remove(userSession);
        }
    }

    /**
     * Callback hook for Message Events. This method will be invoked when a client
     * send a message.
     * @param message The text message
     * @param userSession The session of the client
     */
    @OnMessage
    public void onMessage(String message, Session userSession) {
        System.out.println("Message Received: " + message);
        try {
            Message message1 = ObjectMapperWrapper.readValue(message, Message.class);
            System.out.println("Message Received: " + message1.toString());
            System.out.println("Message Received: " + userSessions.toString());
            if (userSessions.containsKey(message1.getReceiver())){
                Session session = userSessions.get(message1.getReceiver());
                session.getAsyncRemote().sendText(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
