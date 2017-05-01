package com.gramazski.craps.controller;

import com.gramazski.craps.command.CommandManager;
import com.gramazski.craps.command.ICommand;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by gs on 20.02.2017.
 */
@WebServlet("/controller")
public class FrontController extends HttpServlet {
    /**
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        ICommand command = getCommand(req);
        command.handleRequest(req, resp);
    }

    /**
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ICommand command = getCommand(req);
        command.handleRequest(req, resp);
    }

    /**
     * @param request
     * @return
     */
    private ICommand getCommand(HttpServletRequest request){
        String commandKey = request.getParameter("command");

        return CommandManager.getInstance().getCommand(commandKey);
    }
}
