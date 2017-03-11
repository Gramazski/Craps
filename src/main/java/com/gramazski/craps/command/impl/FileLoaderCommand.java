package com.gramazski.craps.command.impl;

import com.gramazski.craps.command.ICommand;
import com.gramazski.craps.exception.HandlerException;
import com.gramazski.craps.uploader.FileUploader;
import org.apache.logging.log4j.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by gs on 22.02.2017.
 */
//Build without uploader
public class FileLoaderCommand implements ICommand {
    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) {

    }

    public String handleRequest(HttpServletRequest request) {
        FileUploader fileUploader = new FileUploader();
        HttpSession session = request.getSession();
        String rootPath = session.getAttribute("rootPath").toString();
        String filePath = "";

        try {
            filePath = fileUploader.uploadFileFromRequest(request, rootPath);
            session.setAttribute("filePath", filePath);
            return "/";
        } catch (HandlerException e) {
            logger.log(Level.ERROR, e.getMessage());
            return "jsp/error/";
        }
    }
}
