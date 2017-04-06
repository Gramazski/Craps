package com.gramazski.craps.command.impl;

import com.gramazski.craps.command.ICommand;
import com.gramazski.craps.entity.impl.User;
import com.gramazski.craps.mapper.ObjectMapperWrapper;
import com.gramazski.craps.service.BanService;
import com.gramazski.craps.service.UserService;
import com.gramazski.craps.util.JSONReader;
import org.apache.logging.log4j.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class UnBanUserCommand implements ICommand {
    @Override
    public String handleRequest(HttpServletRequest request) {
        return null;
    }

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) {
        try {
            String params = JSONReader.readJsonString(request);
            User user = ObjectMapperWrapper.readValue(params, User.class);
            BanService banService = new BanService();

            banService.makeBanUser(user, false);

            UserService userService = new UserService();
            List<User> users = userService.getAllUsers();
            response.setContentType("application/json");
            ObjectMapperWrapper.writeValue(response.getOutputStream(), users);
        } catch (IOException e) {
            logger.log(Level.ERROR, e.getMessage());
        }
    }
}
