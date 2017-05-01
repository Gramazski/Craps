package com.gramazski.craps.service;

import com.gramazski.craps.dao.impl.SoundsDAO;
import com.gramazski.craps.entity.impl.SoundDescription;
import com.gramazski.craps.exception.DAOException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class MusicService {
    private final static Logger logger = LogManager.getLogger(MusicService.class);

    public List<SoundDescription> loadSounds(){
        List<SoundDescription> soundDescriptions = new ArrayList<>();

        try(SoundsDAO soundsDAO = new SoundsDAO()) {
            soundDescriptions = soundsDAO.findAll();
        }
        catch (DAOException e){
            logger.log(Level.ERROR, e.getMessage());
        }

        return soundDescriptions;
    }

    public void createSound(SoundDescription soundDescription){
        try(SoundsDAO soundsDAO = new SoundsDAO()) {
            soundsDAO.create(soundDescription);
        }
        catch (DAOException e){
            logger.log(Level.ERROR, e.getMessage());
        }
    }
}
