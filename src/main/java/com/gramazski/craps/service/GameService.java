package com.gramazski.craps.service;

import com.gramazski.craps.dao.impl.GameDAO;
import com.gramazski.craps.endpoint.GameServerEndPoint;
import com.gramazski.craps.entity.impl.*;
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

    public void removePlayer(int gameId, int playerId){
        Game game = GamesSharedList.getInstance().getGameById(gameId);
        game.setPlayersCount(game.getPlayersCount() - 1);

        if (game.getThrowerId().get() == playerId){
            game.setThrowerId(0);
        }
    }

    public void throwCube(int gameId){
        GameHandler gameHandler = new GameHandler();

        Cube cube = gameHandler.throwCube();
        GamesSharedList.getInstance().getGameById(gameId).setLastCube(cube);
        GameServerEndPoint.notifyUsersInGameForThrowing(gameId);
    }

    public GameResult playGame(User user, int throwerId){
        Cube cube = GamesSharedList.getInstance().getGameByThrowerId(throwerId).getLastCube();
        GameResult gameResult;
        gameResult = playGame(user.getBets(), cube);

        BettingService bettingService = new BettingService();

        user.setAmount(user.getAmount() + gameResult.getAmount());
        bettingService.saveBets(gameResult.getLoseBets(), false, user.getId());
        bettingService.saveBets(gameResult.getWinBets(), true, user.getId());
        user.setPlayedBets(bettingService.getUserPlayedBets(user.getId()));
        gameResult.setPlayedBets(user.getPlayedBets());

        return gameResult;
    }

    private GameResult playGame(List<Bet> bets, Cube cube){
        GameResult gameResult = new GameResult();
        GameHandler gameHandler = new GameHandler();
        gameResult.setCube(cube);
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

    public List<Game> getStartGameList(){
        try(GameDAO gameDAO = new GameDAO()) {
            return gameDAO.getStartGameList(2);
        }
        catch (DAOException e){
            logger.log(Level.ERROR, e.getMessage());
        }

        return new ArrayList<>();
    }

    public void becomeThrower(int gameId, int userId){
        Game game = GamesSharedList.getInstance().getGameById(gameId);

        if (game.getThrowerId().get() == 0){
            game.setThrowerId(userId);
        }
    }
}
