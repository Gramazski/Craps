package com.gramazski.craps.command.impl;

import com.gramazski.craps.command.ICommand;
import com.gramazski.craps.entity.impl.GameResult;
import com.gramazski.craps.entity.impl.User;
import com.gramazski.craps.mapper.ObjectMapperWrapper;
import com.gramazski.craps.service.BettingService;
import com.gramazski.craps.service.GameService;
import com.gramazski.craps.service.UserService;
import com.gramazski.craps.util.JSONReader;
import org.apache.logging.log4j.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ThrowCubeCommand implements ICommand {
    @Override
    public String handleRequest(HttpServletRequest request) {
        return null;
    }

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) {
        try {
            String params = JSONReader.readJsonString(request);
            User user = ObjectMapperWrapper.readValue(params, User.class);
            GameService gameService = new GameService();
            GameResult gameResult = null;

            if (gameService.checkUserBets(user.getAmount(), user.getBets())){
                gameResult = gameService.playGame(user.getBets());

                BettingService bettingService = new BettingService();
                UserService userService = new UserService();

                user.setAmount(user.getAmount() + gameResult.getAmount());
                bettingService.saveBets(gameResult.getLoseBets(), false, user.getId());
                bettingService.saveBets(gameResult.getWinBets(), true, user.getId());
                user.setPlayedBets(bettingService.getUserPlayedBets(user.getId()));
                gameResult.setPlayedBets(user.getPlayedBets());

                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                userService.updateUser(user);
            }

            response.setContentType("application/json");
            ObjectMapperWrapper.writeValue(response.getOutputStream(), gameResult);
        } catch (IOException e) {
            logger.log(Level.ERROR, e.getMessage());
        }
    }
}
