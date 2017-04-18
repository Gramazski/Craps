package com.gramazski.craps.command.impl;

import com.gramazski.craps.command.ICommand;
import com.gramazski.craps.entity.impl.Game;
import com.gramazski.craps.game.GamesSharedList;
import com.gramazski.craps.mapper.ObjectMapperWrapper;
import org.apache.logging.log4j.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class LoadGamesCommand implements ICommand {
    /**
     * @param request
     * @param response
     */
    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<Game> games = GamesSharedList.getInstance().getGames();

            response.setContentType("application/json");
            ObjectMapperWrapper.writeValue(response.getOutputStream(), games);
        } catch (IOException e) {
            logger.log(Level.ERROR, e.getMessage());
        }
    }
}
