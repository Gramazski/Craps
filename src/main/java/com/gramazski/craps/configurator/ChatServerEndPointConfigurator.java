package com.gramazski.craps.configurator;

import com.gramazski.craps.endpoint.MessageServerEndPoint;

import javax.websocket.server.ServerEndpointConfig;

public class ChatServerEndPointConfigurator extends ServerEndpointConfig.Configurator {
    private static MessageServerEndPoint messageServer = new MessageServerEndPoint();

    @Override
    public <T> T getEndpointInstance(Class<T> endpointClass) throws InstantiationException {
        return (T)messageServer;
    }
}
