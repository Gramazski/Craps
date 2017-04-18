package com.gramazski.craps.command.impl;

import com.gramazski.craps.command.ICommand;
import com.gramazski.craps.entity.impl.User;
import com.gramazski.craps.service.UpdateUserService;
import org.apache.logging.log4j.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by gs on 12.03.2017.
 */
public class UpdateUserCommand implements ICommand {
    /**
     * @param request
     * @param response
     */
    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) {
        try {
            UpdateUserService updateUserService = new UpdateUserService();
            HttpSession session = request.getSession();
            User user = updateUserService.tryUpdateUser(request);

            session.setAttribute("user", user);

            response.sendRedirect("/#/cabinet");
        } catch (IOException e) {
            logger.log(Level.ERROR, e.getMessage());
        }
    }
}
