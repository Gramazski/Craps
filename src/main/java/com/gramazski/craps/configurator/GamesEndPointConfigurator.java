package com.gramazski.craps.configurator;

import com.gramazski.craps.endpoint.GamesServerEndPoint;

import javax.websocket.server.ServerEndpointConfig.Configurator;

public class GamesEndPointConfigurator extends Configurator {
    private static GamesServerEndPoint gamesServerEndPoint = new GamesServerEndPoint();

    /**
     * @param endpointClass
     * @param <T>
     * @return
     * @throws InstantiationException
     */
    @Override
    public <T> T getEndpointInstance(Class<T> endpointClass) throws InstantiationException {
        return (T) gamesServerEndPoint;
    }
}
