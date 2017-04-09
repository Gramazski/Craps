package com.gramazski.craps.entity.impl;

import com.gramazski.craps.entity.Entity;

public class BetType extends Entity {
    private String type;
    private double koef;
    private int[] winNumbers;
    private int[] passiveNumbers;
    private String description;

    public int[] getWinNumbers() {
        return winNumbers;
    }

    public void setWinNumbers(int[] winNumbers) {
        this.winNumbers = winNumbers;
    }

    public int[] getPassiveNumbers() {
        return passiveNumbers;
    }

    public void setPassiveNumbers(int[] passiveNumbers) {
        this.passiveNumbers = passiveNumbers;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getKoef() {
        return koef;
    }

    public void setKoef(double koef) {
        this.koef = koef;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
