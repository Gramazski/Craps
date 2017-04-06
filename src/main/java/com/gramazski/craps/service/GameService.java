package com.gramazski.craps.service;

import com.gramazski.craps.entity.impl.Game;
import com.gramazski.craps.game.GamesSharedList;

public class GameService {
    public void addGame(Game game){
        game.setId(-1);
        GamesSharedList.getInstance().addGame(game);
    }

    public void removeGame(Game game){
        if (GamesSharedList.getInstance().removeGame(game)){
            game.setId(-1);
        }
    }
}
