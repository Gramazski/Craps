package com.gramazski.craps.configurator;

import com.gramazski.craps.endpoint.ChatServerEndPoint;

import javax.websocket.server.ServerEndpointConfig.Configurator;

/**
 * Created by gs on 07.03.2017.
 */
public class MessageServerEndPointConfigurator extends Configurator {
    private static ChatServerEndPoint chatServer = new ChatServerEndPoint();

    @Override
    public <T> T getEndpointInstance(Class<T> endpointClass) throws InstantiationException {
        return (T)chatServer;
    }
}
