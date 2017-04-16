package com.gramazski.craps.entity.impl;

import com.gramazski.craps.entity.Entity;

import java.util.concurrent.atomic.AtomicInteger;

public class Game extends Entity {
    private String lobby;
    //Change on enum
    private String type;
    private int maxPlayersCount;
    private int minBet;
    private int playersCount;
    private int maxBet;
    private AtomicInteger throwerId = new AtomicInteger(0);
    private Cube lastCube = new Cube();

    public Cube getLastCube() {
        return lastCube;
    }

    public void setLastCube(Cube lastCube) {
        this.lastCube = lastCube;
    }

    public AtomicInteger getThrowerId() {
        return throwerId;
    }

    public void setThrowerId(int throwerId) {
        this.throwerId.set(throwerId);
    }

    public int getMaxPlayersCount() {
        return maxPlayersCount;
    }

    public void setMaxPlayersCount(int maxPlayersCount) {
        this.maxPlayersCount = maxPlayersCount;
    }

    public int getPlayersCount() {
        return playersCount;
    }

    public void setPlayersCount(int playersCount) {
        this.playersCount = playersCount;
    }

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
