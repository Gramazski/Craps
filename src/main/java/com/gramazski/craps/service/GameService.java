package com.gramazski.craps.service;

import com.gramazski.craps.dao.impl.GameDAO;
import com.gramazski.craps.entity.impl.Bet;
import com.gramazski.craps.entity.impl.Game;
import com.gramazski.craps.entity.impl.GameResult;
import com.gramazski.craps.exception.DAOException;
import com.gramazski.craps.game.GameHandler;
import com.gramazski.craps.game.GamesSharedList;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class GameService {
    private final static Logger logger = LogManager.getLogger(GameService.class);

    public void addGame(Game game){
        game.setId(-1);
        GamesSharedList.getInstance().addGame(game);
        saveGame(game);
    }

    public void removeGame(Game game){
        if (GamesSharedList.getInstance().removeGame(game)){
            game.setId(-1);
        }
    }

    private void saveGame(Game game){
        try(GameDAO gameDAO = new GameDAO()) {
            gameDAO.create(game);
        }
        catch (DAOException e){
            logger.log(Level.ERROR, e.getMessage());
        }
    }

    public Game getGameById(int id){
        List<Game> games = GamesSharedList.getInstance().getGames();

        for (Game game : games) {
            if (game.getId() == id){
                return game;
            }
        }

        return null;
    }

    public boolean checkUserBets(int amount, List<Bet> bets){
        for (Bet bet : bets) {
            amount -= bet.getAmount();
        }

        return amount >= 0;
    }

    public GameResult playGame(List<Bet> bets){
        GameResult gameResult = new GameResult();
        GameHandler gameHandler = new GameHandler();
        gameResult.setCube(gameHandler.throwCube());
        List<Bet> leftBets = new ArrayList<>();
        List<Bet> winBets = new ArrayList<>();
        List<Bet> loseBets = new ArrayList<>();
        int[] resultArray = gameHandler.handleBets(bets, gameResult.getCube());

        for (int i = 0; i < resultArray.length; i++){
            if (resultArray[i] == bets.get(i).getAmount()){
                leftBets.add(bets.get(i));
            }

            if (resultArray[i] == 0){
                gameResult.setAmount(gameResult.getAmount() - bets.get(i).getAmount());
                loseBets.add(bets.get(i));
            }

            if (resultArray[i] > bets.get(i).getAmount()){
                gameResult.setAmount(gameResult.getAmount() + bets.get(i).getAmount());
                winBets.add(bets.get(i));
            }
        }

        gameResult.setLeftBets(leftBets);
        gameResult.setWinBets(winBets);
        gameResult.setLoseBets(loseBets);

        return gameResult;
    }

    public int getLastGameId(){
        try(GameDAO gameDAO = new GameDAO()) {
            return gameDAO.getLastId();
        }
        catch (DAOException e){
            logger.log(Level.ERROR, e.getMessage());
        }

        return 0;
    }
}
