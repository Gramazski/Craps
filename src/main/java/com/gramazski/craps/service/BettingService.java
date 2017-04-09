package com.gramazski.craps.service;

import com.gramazski.craps.dao.impl.GameDAO;
import com.gramazski.craps.entity.impl.Bet;
import com.gramazski.craps.entity.impl.Game;
import com.gramazski.craps.exception.DAOException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BettingService {
    private final static Logger logger = LogManager.getLogger(BettingService.class);

    public void saveBet(Bet bet, boolean isWin, int userId){

    }

    public void saveGame(Game game){
        try(GameDAO gameDAO = new GameDAO()) {
            gameDAO.create(game);
        }
        catch (DAOException e){
            logger.log(Level.ERROR, e.getMessage());
        }
    }
}
