package com.gramazski.craps.command.impl;

import com.gramazski.craps.command.ICommand;
import com.gramazski.craps.entity.impl.Game;
import com.gramazski.craps.mapper.ObjectMapperWrapper;
import com.gramazski.craps.service.GameService;
import com.gramazski.craps.util.JSONReader;
import org.apache.logging.log4j.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddGameCommand implements ICommand {
    @Override
    public String handleRequest(HttpServletRequest request) {
        return null;
    }

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) {
        try {
            String params = JSONReader.readJsonString(request);
            Game game = ObjectMapperWrapper.readValue(params, Game.class);
            GameService gameService = new GameService();

            gameService.addGame(game);

            response.setContentType("application/json");
            ObjectMapperWrapper.writeValue(response.getOutputStream(), game);
        } catch (IOException e) {
            logger.log(Level.ERROR, e.getMessage());
        }
    }
}
