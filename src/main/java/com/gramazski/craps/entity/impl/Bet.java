package com.gramazski.craps.entity.impl;

public class Bet {
    private int amount;
    private String type;
    private int gameId;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
