package com.gramazski.craps.configurator;

import com.gramazski.craps.endpoint.ChatServerEndPoint;

import javax.websocket.server.ServerEndpointConfig.Configurator;

public class ChatServerEndPointConfigurator extends Configurator {
    private static ChatServerEndPoint chatServer = new ChatServerEndPoint();

    /**
     * @param endpointClass
     * @param <T>
     * @return
     * @throws InstantiationException
     */
    @Override
    public <T> T getEndpointInstance(Class<T> endpointClass) throws InstantiationException {
        return (T)chatServer;
    }
}
