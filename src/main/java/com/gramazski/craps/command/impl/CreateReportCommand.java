package com.gramazski.craps.command.impl;

import com.gramazski.craps.command.ICommand;
import com.gramazski.craps.entity.impl.User;
import com.gramazski.craps.mapper.ObjectMapperWrapper;
import com.gramazski.craps.service.ReportingService;
import org.apache.logging.log4j.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CreateReportCommand implements ICommand {
    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) {
        try {
            ReportingService reportingService = new ReportingService();
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");

            String path = reportingService.buildReport(user.getPlayedBets(), user.getUserName(),
                    session.getServletContext().getRealPath("/"));

            response.setContentType("application/json");
            ObjectMapperWrapper.writeValue(response.getOutputStream(), path);
        } catch (IOException e) {
            logger.log(Level.ERROR, e.getMessage());
        }
    }
}
