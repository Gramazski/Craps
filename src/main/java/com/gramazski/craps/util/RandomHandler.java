package com.gramazski.craps.util;

import java.util.Random;

public class RandomHandler {
    private static Random random = new Random();

    /**
     * @param boundary
     * Generate random int value
     * @return
     */
    public static int getNexInt(int boundary){
        if (boundary > 0){
            return random.nextInt(boundary);
        }
        else {
            return 0;
        }
    }
}
