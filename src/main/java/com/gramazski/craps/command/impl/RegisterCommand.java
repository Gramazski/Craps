package com.gramazski.craps.command.impl;

import com.gramazski.craps.command.ICommand;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by gs on 27.02.2017.
 */
public class RegisterCommand implements ICommand {
    public String handleRequest(HttpServletRequest request) {
        return null;
    }

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) {

    }
}
