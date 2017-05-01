package com.gramazski.craps.util;

import java.util.Random;

public class RandomHandler {
    private static Random random = new Random();

    public static int getNexInt(int boundary){
        if (boundary > 0){
            return random.nextInt(boundary);
        }
        else {
            return 0;
        }
    }
}
