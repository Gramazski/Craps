package com.gramazski.craps.controller;

import com.gramazski.craps.command.CommandManager;
import com.gramazski.craps.command.ICommand;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by gs on 20.02.2017.
 */
@WebServlet("/controller")
public class FrontController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        initSession(req);
        ICommand command = getCommand(req);
        command.handleRequest(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        initSession(req);
        ICommand command = getCommand(req);
        String outputJSP = command.handleRequest(req);
        req.getRequestDispatcher(outputJSP + "index.jsp").forward(req, resp);
    }

    private ICommand getCommand(HttpServletRequest request){
        String commandKey = request.getParameter("command");

        return CommandManager.getInstance().getCommand(commandKey);
    }

    private void initSession(HttpServletRequest request){
        HttpSession session = request.getSession(true);

        if (session.getAttribute("rootPath") == null) {
            session.setAttribute("rootPath", this.getServletContext().getRealPath(""));
        }
    }
}
