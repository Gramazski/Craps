package com.gramazski.craps.endpoint;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gramazski.craps.configurator.GamesEndPointConfigurator;
import com.gramazski.craps.game.GamesSharedList;
import com.gramazski.craps.mapper.ObjectMapperWrapper;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.CopyOnWriteArrayList;

@ServerEndpoint(value="/games", configurator=GamesEndPointConfigurator.class)
public class GamesServerEndPoint {
    private static CopyOnWriteArrayList<Session> userSessions = new CopyOnWriteArrayList<>();
    private final static Logger logger = LogManager.getLogger(MessageServerEndPoint.class);

    /**
     * Callback hook for Connection open events. This method will be invoked when a
     * client requests for a WebSocket connection.
     * @param userSession the userSession which is opened.
     */
    @OnOpen
    public void onOpen(Session userSession) {
        logger.log(Level.INFO, "+1");
        userSessions.add(userSession);
    }

    /**
     * Callback hook for Connection close events. This method will be invoked when a
     * client closes a WebSocket connection.
     * @param userSession the userSession which is opened.
     */
    @OnClose
    public void onClose(Session userSession) {
        if (userSessions.contains(userSession)){
            logger.log(Level.INFO, "-1");
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

    }

    public static void notifyAllUsers(){
        for (Session session : userSessions) {
            try {
                session.getAsyncRemote().sendText(ObjectMapperWrapper.writeValueAsString(GamesSharedList.getInstance().getGames()));
            } catch (JsonProcessingException e) {
                logger.log(Level.ERROR, e.getMessage());
            }
        }
    }
}
