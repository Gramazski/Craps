package com.gramazski.craps.command.impl;


import com.gramazski.craps.command.ICommand;
import com.gramazski.craps.entity.impl.User;
import com.gramazski.craps.handler.JSONReader;
import com.gramazski.craps.mapper.ObjectMapperWrapper;
import com.gramazski.craps.service.LoginService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginCommand implements ICommand {
    @Override
    public String handleRequest(HttpServletRequest request) {
        return null;
    }

    public void handleRequest(HttpServletRequest request, HttpServletResponse response) {
        try {
            String params = JSONReader.readJsonString(request);
            User user = ObjectMapperWrapper.readValue(params, User.class);
            user.setId(-1);
            LoginService loginService = new LoginService();

            if (loginService.checkUser(user.getUserName(), user.getPassword())){
                user = loginService.getUser();
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
            }

            response.setContentType("application/json");
            ObjectMapperWrapper.writeValue(response.getOutputStream(), user);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
