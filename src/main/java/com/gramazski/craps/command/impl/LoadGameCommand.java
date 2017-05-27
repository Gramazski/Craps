package com.gramazski.craps.command.impl;

import com.gramazski.craps.command.ICommand;
import com.gramazski.craps.entity.impl.Game;
import com.gramazski.craps.mapper.ObjectMapperWrapper;
import com.gramazski.craps.service.GameService;
import org.apache.logging.log4j.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoadGameCommand implements ICommand {
    /**
     * @param request
     * @param response
     */
    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) {
        try {
            int gameId = Integer.valueOf(request.getParameter("id"));
            GameService gameService = new GameService();
            HttpSession session = request.getSession();
            Game game = gameService.getGameById(gameId);

            response.setContentType("application/json");
            ObjectMapperWrapper.writeValue(response.getOutputStream(), game);
        } catch (IOException e) {
            logger.log(Level.ERROR, e.getMessage());
        }
    }
}
