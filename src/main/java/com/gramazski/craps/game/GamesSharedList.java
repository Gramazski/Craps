package com.gramazski.craps.game;

import com.gramazski.craps.entity.impl.BetType;
import com.gramazski.craps.entity.impl.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class GamesSharedList {
    private CopyOnWriteArrayList<Game> games;
    private final ArrayList<BetType> betTypes;
    private AtomicInteger lastId;
    private static GamesSharedList instance;
    private static AtomicBoolean instanceFlag = new AtomicBoolean(false);
    private static ReentrantLock lock = new ReentrantLock();

    private GamesSharedList(){
        games = new CopyOnWriteArrayList<>();
        betTypes = new ArrayList<>();
        lastId = new AtomicInteger(0);
    }

    public static GamesSharedList getInstance(){
        if (!instanceFlag.get()){
            lock.lock();
            try {
                if (instance == null){
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

    public List<Game> getGames() {
        return (List<Game>)games.clone();
    }

    public void addGame(Game game){
        game.setId(lastId.incrementAndGet());
        games.add(game);
    }

    public boolean removeGame(Game game){
        if (games.contains(game)){
            games.remove(game);

            return true;
        }

        return false;
    }

    public BetType getBetType(String type){
        for (BetType betType : betTypes) {
            if (betType.getType().equals(type)){
                return betType;
            }
        }

        return null;
    }
}
