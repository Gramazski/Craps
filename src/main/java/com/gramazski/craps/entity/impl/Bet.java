package com.gramazski.craps.entity.impl;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Bet {
    private int amount;
    private String type;
    private int gameId;
    private String time;

    /**
     * @return
     */
    public String getTime() {
        return time;
    }

    /**
     * @param time
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * @return
     */
    public int getGameId() {
        return gameId;
    }

    /**
     * @param gameId
     */
    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    /**
     * @return
     */
    public int getAmount() {
        return amount;
    }

    /**
     * @param amount
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }
}
