package com.gramazski.craps.service;

import com.gramazski.craps.dao.impl.BetTypeDAO;
import com.gramazski.craps.dao.impl.GameDAO;
import com.gramazski.craps.entity.impl.Bet;
import com.gramazski.craps.entity.impl.BetType;
import com.gramazski.craps.exception.DAOException;
import com.gramazski.craps.util.DateHandler;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class BettingService {
    private final static Logger logger = LogManager.getLogger(BettingService.class);

    public void saveBet(Bet bet, boolean isWin, int userId){
        try(GameDAO gameDAO = new GameDAO()) {
            bet.setTime(DateHandler.getCurrentDateTime());
            gameDAO.saveBet(bet, userId, isWin);
        }
        catch (DAOException e){
            logger.log(Level.ERROR, e.getMessage());
        }
    }

    public void saveBets(List<Bet> bets, boolean isWin, int userId){
        try(GameDAO gameDAO = new GameDAO()) {
            for (Bet bet : bets){
                bet.setTime(DateHandler.getCurrentDateTime());
                gameDAO.saveBet(bet, userId, isWin);
            }
        }
        catch (DAOException e){
            logger.log(Level.ERROR, e.getMessage());
        }
    }

    public List<BetType> getBetTypes(){
        try(BetTypeDAO betTypeDAO = new BetTypeDAO()) {
            return betTypeDAO.findAll();
        }
        catch (DAOException e){
            logger.log(Level.ERROR, e.getMessage());
        }

        return new ArrayList<>();
    }
}
