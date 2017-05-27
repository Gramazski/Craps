package com.gramazski.craps.filter;

import com.gramazski.craps.entity.impl.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = "/controller", servletNames = {"FrontController"},
        initParams = {
                @WebInitParam(name = "acceptAdminCommands",
                        value = "ADDGAME;BANUSER;GETUSERS;REMOVEGAME;UNBANUSER"),
                @WebInitParam(name = "acceptUserCommands",
                        value = "GETSOUNDS;BECOMETHROWER;LEAVEGAME;LOADGAME;LOADGAMES;PLAYGAME;" +
                                "THROWCUBE;TRANSFER;UPDATE;UPLOADMUSIC")})
public class SecurityServletFilter implements Filter {
    private String[] acceptAdminCommands;
    private String[] acceptUserCommands;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        acceptAdminCommands = filterConfig.getInitParameter("acceptAdminCommands").split(";");
        acceptUserCommands = filterConfig.getInitParameter("acceptUserCommands").split(";");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        if ((!isUserAdmin(servletRequest) && isAdminCommand(getCommandName(servletRequest)))
                || (!isAuthUser(servletRequest) && isUserCommand(getCommandName(servletRequest)))){
            returnErrorResponse(servletResponse);
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }

    private String getCommandName(ServletRequest servletRequest){
        String commandKey = servletRequest.getParameter("command");

        return commandKey;
    }

    private boolean isUserAdmin(ServletRequest servletRequest){
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        return user != null && user.isAdmin();
    }

    private boolean isAuthUser(ServletRequest servletRequest){
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        return user != null;
    }

    private boolean isAdminCommand(String command){
        return isInCommandsArray(command, acceptAdminCommands);
    }

    private boolean isUserCommand(String command){
        return isInCommandsArray(command, acceptUserCommands);
    }

    private boolean isInCommandsArray(String requiredCommand, String[] commandArray){
        for (String command : commandArray) {
            if (command.equals(requiredCommand)){
                return true;
            }
        }

        return false;
    }

    private void returnErrorResponse(ServletResponse servletResponse) throws IOException{
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        //resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
        resp.sendRedirect("/#/");
    }
}
