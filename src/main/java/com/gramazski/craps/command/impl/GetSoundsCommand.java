package com.gramazski.craps.command.impl;

import com.gramazski.craps.command.ICommand;
import com.gramazski.craps.entity.impl.SoundDescription;
import com.gramazski.craps.mapper.ObjectMapperWrapper;
import com.gramazski.craps.service.MusicService;
import org.apache.logging.log4j.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GetSoundsCommand implements ICommand {
    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) {
        try {
            MusicService musicService = new MusicService();
            List<SoundDescription> sounds = musicService.loadSounds();

            response.setContentType("application/json");
            ObjectMapperWrapper.writeValue(response.getOutputStream(), sounds);
        } catch (IOException e) {
            logger.log(Level.ERROR, e.getMessage());
        }
    }
}
