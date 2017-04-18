package com.gramazski.craps.command.impl;

import com.gramazski.craps.command.ICommand;
import org.apache.logging.log4j.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by gs on 22.02.2017.
 */
public class LogoutCommand implements ICommand {
    /**
     * @param request
     * @param response
     */
    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        session.invalidate();
        try {
            response.sendRedirect("/#/");
        } catch (IOException e) {
            logger.log(Level.ERROR, e.getMessage());
        }
    }
}
