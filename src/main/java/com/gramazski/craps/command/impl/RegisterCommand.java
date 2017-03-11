package com.gramazski.craps.command.impl;

import com.gramazski.craps.command.ICommand;
import com.gramazski.craps.entity.impl.User;
import com.gramazski.craps.mapper.ObjectMapperWrapper;
import com.gramazski.craps.service.RegisterService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by gs on 27.02.2017.
 */
public class RegisterCommand implements ICommand {
    public String handleRequest(HttpServletRequest request) {
        return null;
    }

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) {
        try {
            String params = readJson(request);
            User user = ObjectMapperWrapper.readValue(params, User.class);
            user.setId(-1);
            RegisterService registerService = new RegisterService();

            if (registerService.tryAddUser(user)){
                HttpSession session = request.getSession();
                user = registerService.getUser();
                session.setAttribute("user", user);
            }

            response.setContentType("application/json");
            ObjectMapperWrapper.writeValue(response.getOutputStream(), user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String readJson(HttpServletRequest request) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = request.getReader();
        String str = null;
        while ((str = br.readLine()) != null) {
            sb.append(str);
        }

        return sb.toString();
    }
}
