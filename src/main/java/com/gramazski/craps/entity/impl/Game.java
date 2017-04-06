package com.gramazski.craps.entity.impl;

import com.gramazski.craps.entity.Entity;

public class Game extends Entity {
    private String lobby;
    //Change on enum
    private String type;
    private int minBet;
    private int maxBet;

    public String getLobby() {
        return lobby;
    }

    public void setLobby(String lobby) {
        this.lobby = lobby;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getMinBet() {
        return minBet;
    }

    public void setMinBet(int minBet) {
        this.minBet = minBet;
    }

    public int getMaxBet() {
        return maxBet;
    }

    public void setMaxBet(int maxBet) {
        this.maxBet = maxBet;
    }
}
