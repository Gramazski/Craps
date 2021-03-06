package com.gramazski.craps.configurator;

import com.gramazski.craps.endpoint.GameServerEndPoint;

import javax.websocket.server.ServerEndpointConfig.Configurator;

public class GameEndPointConfigurator extends Configurator {
    private static GameServerEndPoint gameServerEndPoint = new GameServerEndPoint();

    /**
     * @param endpointClass
     * @param <T>
     * @return
     * @throws InstantiationException
     */
    @Override
    public <T> T getEndpointInstance(Class<T> endpointClass) throws InstantiationException {
        return (T) gameServerEndPoint;
    }
}
