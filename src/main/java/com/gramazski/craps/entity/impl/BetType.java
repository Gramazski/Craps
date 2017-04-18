package com.gramazski.craps.entity.impl;

import com.gramazski.craps.entity.Entity;

public class BetType extends Entity {
    private String type;
    private double koef;
    private int[] winNumbers;
    private int[] loseNumbers;
    private String description;

    /**
     * @return
     */
    public int[] getWinNumbers() {
        return winNumbers;
    }

    /**
     * @param winNumbers
     */
    public void setWinNumbers(int[] winNumbers) {
        this.winNumbers = winNumbers;
    }

    /**
     * @return
     */
    public int[] getLoseNumbers() {
        return loseNumbers;
    }

    /**
     * @param loseNumbers
     */
    public void setLoseNumbers(int[] loseNumbers) {
        this.loseNumbers = loseNumbers;
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

    /**
     * @return
     */
    public double getKoef() {
        return koef;
    }

    /**
     * @param koef
     */
    public void setKoef(double koef) {
        this.koef = koef;
    }

    /**
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
