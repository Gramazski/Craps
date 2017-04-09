package com.gramazski.craps.entity.impl;

import java.util.List;

public class GameResult {
    private int amount;
    private List<Bet> leftBets;
    private List<Bet> loseBets;
    private List<Bet> winBets;
    private Cube cube;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public List<Bet> getLeftBets() {
        return leftBets;
    }

    public void setLeftBets(List<Bet> leftBets) {
        this.leftBets = leftBets;
    }

    public List<Bet> getLoseBets() {
        return loseBets;
    }

    public void setLoseBets(List<Bet> loseBets) {
        this.loseBets = loseBets;
    }

    public List<Bet> getWinBets() {
        return winBets;
    }

    public void setWinBets(List<Bet> winBets) {
        this.winBets = winBets;
    }

    public Cube getCube() {
        return cube;
    }

    public void setCube(Cube cube) {
        this.cube = cube;
    }
}
