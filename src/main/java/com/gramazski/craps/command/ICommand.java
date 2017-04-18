package com.gramazski.craps.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by gs on 22.02.2017.
 */
public interface ICommand {
    Logger logger = LogManager.getLogger(ICommand.class);

    /**
     * @param request
     * @param response
     */
    void handleRequest(HttpServletRequest request, HttpServletResponse response);
}
