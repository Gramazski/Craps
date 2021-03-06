package com.gramazski.craps.command.impl;

import com.gramazski.craps.command.ICommand;
import com.gramazski.craps.endpoint.GameServerEndPoint;
import com.gramazski.craps.endpoint.GamesServerEndPoint;
import com.gramazski.craps.entity.impl.User;
import com.gramazski.craps.mapper.ObjectMapperWrapper;
import com.gramazski.craps.service.GameService;
import com.gramazski.craps.util.JSONReader;
import org.apache.logging.log4j.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LeaveGameCommand implements ICommand {
    /**
     * @param request
     * @param response
     */
    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) {
        try {
            String params = JSONReader.readJsonString(request);
            int gameId = ObjectMapperWrapper.readValue(params, Integer.class);
            HttpSession session = request.getSession();
            User user = (User)session.getAttribute("user");
            GameService gameService = new GameService();

            gameService.removePlayer(gameId, user.getId());
            GamesServerEndPoint.notifyAllUsers();
            GameServerEndPoint.notifyAllUsersInGame(gameId);

            response.setContentType("application/json");
            ObjectMapperWrapper.writeValue(response.getOutputStream(), gameService.getGameById(gameId));
        } catch (IOException e) {
            logger.log(Level.ERROR, e.getMessage());
        }
    }
}
