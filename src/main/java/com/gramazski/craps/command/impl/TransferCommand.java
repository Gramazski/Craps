package com.gramazski.craps.command.impl;

import com.gramazski.craps.command.ICommand;
import com.gramazski.craps.entity.impl.Transfer;
import com.gramazski.craps.entity.impl.User;
import com.gramazski.craps.mapper.ObjectMapperWrapper;
import com.gramazski.craps.service.TransferService;
import com.gramazski.craps.util.JSONReader;
import org.apache.logging.log4j.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class TransferCommand implements ICommand {
    @Override
    public String handleRequest(HttpServletRequest request) {
        return null;
    }

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) {
        try {
            String params = JSONReader.readJsonString(request);
            Transfer transfer = ObjectMapperWrapper.readValue(params, Transfer.class);
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            TransferService transferService = new TransferService();

            if (transferService.makeTransfer(transfer, user)){
                session.setAttribute("user", user);
            }

            response.setContentType("application/json");
            ObjectMapperWrapper.writeValue(response.getOutputStream(), user);
        } catch (IOException e) {
            logger.log(Level.ERROR, e.getMessage());
        }
    }
}
