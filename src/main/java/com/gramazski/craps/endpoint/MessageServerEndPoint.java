package com.gramazski.craps.endpoint;

import com.gramazski.craps.configurator.MessageServerEndPointConfigurator;
import com.gramazski.craps.entity.impl.Message;
import com.gramazski.craps.mapper.ObjectMapperWrapper;
import com.gramazski.craps.service.MessageService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by gs on 07.03.2017.
 */
@ServerEndpoint(value="/message", configurator=MessageServerEndPointConfigurator.class)
public class MessageServerEndPoint {
    private Map<String, Session> userSessions = new ConcurrentHashMap<>();
    private final static Logger logger = LogManager.getLogger(MessageServerEndPoint.class);

    /**
     * Callback hook for Connection open events. This method will be invoked when a
     * client requests for a WebSocket connection.
     * @param userSession the userSession which is opened.
     */
    @OnOpen
    public void onOpen(Session userSession) {
        Map<String, List<String>> requestMap = userSession.getRequestParameterMap();
        if (requestMap.containsKey("login")){
            logger.log(Level.DEBUG, "+1: : " + requestMap.get("login").get(0));
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
            Message inMessage = ObjectMapperWrapper.readValue(message, Message.class);
            MessageService messageService = new MessageService();
            if (messageService.trySaveMessage(inMessage)){
                Session session = userSessions.get(inMessage.getSender());
                session.getAsyncRemote().sendText(ObjectMapperWrapper.writeValueAsString(inMessage));

                if (userSessions.containsKey(inMessage.getReceiver())){
                    session = userSessions.get(inMessage.getReceiver());
                    session.getAsyncRemote().sendText(ObjectMapperWrapper.writeValueAsString(inMessage));
                }
            }
        } catch (IOException e) {
            logger.log(Level.ERROR, e.getMessage());
        }

    }
}
