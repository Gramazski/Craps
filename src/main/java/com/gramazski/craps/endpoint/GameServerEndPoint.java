package com.gramazski.craps.endpoint;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gramazski.craps.configurator.GameEndPointConfigurator;
import com.gramazski.craps.entity.impl.Game;
import com.gramazski.craps.game.GamesSharedList;
import com.gramazski.craps.mapper.ObjectMapperWrapper;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReentrantLock;

@ServerEndpoint(value="/games/{gameId}", configurator=GameEndPointConfigurator.class)
public class GameServerEndPoint {
    private static Map<Integer, List<Session>> gameSessions = new ConcurrentHashMap<>();
    private final static Logger logger = LogManager.getLogger(MessageServerEndPoint.class);
    private final static ReentrantLock lock = new ReentrantLock();

    /**
     * Callback hook for Connection open events. This method will be invoked when a
     * client requests for a WebSocket connection.
     * @param userSession the userSession which is opened.
     */
    @OnOpen
    public void onOpen(@PathParam("gameId") Integer gameId, Session userSession) {
        logger.log(Level.INFO, "+1 : " + gameId);
        lock.lock();

        if (gameSessions.containsKey(gameId)){
            addNewPlayer(gameId, userSession);
        }
        else {
            gameSessions.put(gameId, new CopyOnWriteArrayList<Session>());
            addNewPlayer(gameId, userSession);
        }

        lock.unlock();
    }

    /**
     * Callback hook for Connection close events. This method will be invoked when a
     * client closes a WebSocket connection.
     * @param userSession the userSession which is opened.
     */
    @OnClose
    public void onClose(@PathParam("gameId") Integer gameId, Session userSession) {
        logger.log(Level.INFO, "-1 : " + gameId);
        lock.lock();

        if (gameSessions.containsKey(gameId)){
            gameSessions.get(gameId).remove(userSession);
        }

        lock.unlock();
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

    /**
     * @param gameId
     */
    public static void notifyAllUsersInGame(int gameId){
        if (gameSessions.containsKey(gameId)){
            for (Session session : gameSessions.get(gameId)){
                try {
                    session.getAsyncRemote().sendText(ObjectMapperWrapper.writeValueAsString(GamesSharedList.getInstance().getGameById(gameId)));
                } catch (JsonProcessingException e) {
                    logger.log(Level.ERROR, e.getMessage());
                }
            }
        }
    }

    /**
     * @param gameId
     */
    public static void notifyUsersInGameForThrowing(int gameId){
        if (gameSessions.containsKey(gameId)){
            for (Session session : gameSessions.get(gameId)){
                session.getAsyncRemote().sendText("{\"throw\":\"true\"}");
            }
        }
    }

    /**
     * @param gameId
     * @param userSession
     */
    private void addNewPlayer(Integer gameId, Session userSession){
        Game game = GamesSharedList.getInstance().getGameById(gameId);

        if (gameSessions.get(gameId).size() + 1 <= game.getMaxPlayersCount()){
            gameSessions.get(gameId).add(userSession);
            game.setPlayersCount(game.getPlayersCount() + 1);
            GamesServerEndPoint.notifyAllUsers();
        }
    }
}
