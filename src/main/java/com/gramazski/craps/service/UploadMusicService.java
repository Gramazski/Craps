package com.gramazski.craps.service;

import com.gramazski.craps.entity.impl.SoundDescription;
import com.gramazski.craps.exception.HandlerException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

public class UploadMusicService {
    private final static Logger logger = LogManager.getLogger(UploadMusicService.class);

    public SoundDescription uploadSound(HttpServletRequest request){
        SoundDescription soundDescription = new SoundDescription();
        HttpSession session = request.getSession();
        FileUploaderService uploaderService = new FileUploaderService();
        MusicService musicService = new MusicService();

        try {
            Map<String, String> paramMap = uploaderService.uploadFileFromRequest(request, session.getServletContext().getRealPath("/"));
            soundDescription.setPath(paramMap.get("file"));
            soundDescription.setName(paramMap.get("fileName"));
            musicService.createSound(soundDescription);
        }
        catch (HandlerException e){
            logger.log(Level.ERROR, e.getMessage());
        }

        return soundDescription;
    }
}
