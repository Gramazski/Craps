package com.gramazski.craps.game;

import com.gramazski.craps.entity.impl.BetType;
import com.gramazski.craps.entity.impl.Game;
import com.gramazski.craps.service.BettingService;
import com.gramazski.craps.service.GameService;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class GamesSharedList {
    private CopyOnWriteArrayList<Game> games;
    private final List<BetType> betTypes;
    private AtomicInteger lastId;
    private static GamesSharedList instance;
    private static AtomicBoolean instanceFlag = new AtomicBoolean(false);
    private static ReentrantLock lock = new ReentrantLock();

    private GamesSharedList(){
        BettingService bettingService = new BettingService();
        GameService gameService = new GameService();
        games = new CopyOnWriteArrayList<>(gameService.getStartGameList());
        betTypes = bettingService.getBetTypes();
        lastId = new AtomicInteger(gameService.getLastGameId());
    }

    /**
     * @return
     */
    public static GamesSharedList getInstance(){
        if (!instanceFlag.get()){
            lock.lock();
            try {
                if (!instanceFlag.get()){
                    instance = new GamesSharedList();
                    instanceFlag.getAndSet(true);
                }
            }
            finally {
                lock.unlock();
            }

        }

        return instance;
    }

    /**
     * @return
     */
    public List<Game> getGames() {
        return (List<Game>)games.clone();
    }

    /**
     * @param game
     */
    public void addGame(Game game){
        game.setId(lastId.incrementAndGet());
        games.add(game);
    }

    /**
     * @param game
     * @return
     */
    public boolean removeGame(Game game){
        if (games.contains(game)){
            games.remove(game);

            return true;
        }

        return false;
    }

    /**
     * @param type
     * @return
     */
    public BetType getBetType(String type){
        for (BetType betType : betTypes) {
            if (betType.getType().equals(type)){
                return betType;
            }
        }

        return null;
    }

    /**
     * @param id
     * @return
     */
    public Game getGameById(int id){
        for (Game game : games){
            if (game.getId() == id){
                return game;
            }
        }

        return new Game();
    }

    /**
     * @param throwerId
     * @return
     */
    public Game getGameByThrowerId(int throwerId){
        for (Game game : games){
            if (game.getThrowerId().get() == throwerId){
                return game;
            }
        }

        return new Game();
    }

    /**
     * @param throwerId
     * @return
     */
    public int getGameIdByThrowerId(int throwerId){
        for (Game game : games){
            if (game.getThrowerId().get() == throwerId){
                return game.getId();
            }
        }

        return 0;
    }
}
