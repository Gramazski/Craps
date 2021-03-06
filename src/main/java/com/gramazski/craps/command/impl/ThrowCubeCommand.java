package com.gramazski.craps.command.impl;

import com.gramazski.craps.command.ICommand;
import com.gramazski.craps.mapper.ObjectMapperWrapper;
import com.gramazski.craps.service.GameService;
import com.gramazski.craps.util.JSONReader;
import org.apache.logging.log4j.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ThrowCubeCommand implements ICommand {
    /**
     * @param request
     * @param response
     */
    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) {
        try {
            String params = JSONReader.readJsonString(request);
            int gameId = ObjectMapperWrapper.readValue(params, Integer.class);
            GameService gameService = new GameService();

            gameService.throwCube(gameId);

            response.setContentType("application/json");
            ObjectMapperWrapper.writeValue(response.getOutputStream(), gameId);
        } catch (IOException e) {
            logger.log(Level.ERROR, e.getMessage());
        }
    }
}
