package com.gramazski.craps.command.impl;

import com.gramazski.craps.command.ICommand;
import com.gramazski.craps.entity.impl.SoundDescription;
import com.gramazski.craps.mapper.ObjectMapperWrapper;
import com.gramazski.craps.service.UploadMusicService;
import org.apache.logging.log4j.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UploadMusicCommand implements ICommand {
    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) {
        try {
            UploadMusicService uploadMusicService = new UploadMusicService();
            SoundDescription soundDescription = uploadMusicService.uploadSound(request);

            response.setContentType("application/json");
            ObjectMapperWrapper.writeValue(response.getOutputStream(), soundDescription);
        } catch (IOException e) {
            logger.log(Level.ERROR, e.getMessage());
        }
    }
}
