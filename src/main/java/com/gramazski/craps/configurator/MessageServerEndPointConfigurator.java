package com.gramazski.craps.configurator;

import com.gramazski.craps.endpoint.MessageServerEndPoint;

import javax.websocket.server.ServerEndpointConfig.Configurator;

/**
 * Created by gs on 07.03.2017.
 */
public class MessageServerEndPointConfigurator extends Configurator {
    private static MessageServerEndPoint messageServer = new MessageServerEndPoint();

    @Override
    public <T> T getEndpointInstance(Class<T> endpointClass) throws InstantiationException {
        return (T)messageServer;
    }
}
