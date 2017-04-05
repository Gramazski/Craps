package com.gramazski.craps.command.impl;

import com.gramazski.craps.command.ICommand;
import com.gramazski.craps.entity.impl.User;
import com.gramazski.craps.mapper.ObjectMapperWrapper;
import com.gramazski.craps.service.UserService;
import org.apache.logging.log4j.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GetUsersCommand implements ICommand {
    @Override
    public String handleRequest(HttpServletRequest request) {
        return null;
    }

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            List<User> users = new ArrayList<>();

            if (user.isAdmin()){
                UserService userService = new UserService();
                users = userService.getAllUsers();
            }

            response.setContentType("application/json");
            ObjectMapperWrapper.writeValue(response.getOutputStream(), users);
        } catch (IOException e) {
            logger.log(Level.ERROR, e.getMessage());
        }
    }
}
