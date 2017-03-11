package com.gramazski.craps.command.impl;

import com.gramazski.craps.command.ICommand;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by gs on 22.02.2017.
 */
public class LogoutCommand implements ICommand {
    public String handleRequest(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("user", null);
        return "/";
    }

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) {

    }
}
