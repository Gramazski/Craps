package com.gramazski.craps.game;

import com.gramazski.craps.entity.impl.Bet;
import com.gramazski.craps.entity.impl.BetType;
import com.gramazski.craps.entity.impl.Cube;

import java.util.List;
import java.util.Random;

public class GameHandler {
    private final static Random random = new Random();

    public Cube throwCube(){
        Cube cube = new Cube();
        cube.setFirst(random.nextInt(6) + 1);
        cube.setSecond(random.nextInt(6) + 1);

        return cube;
    }

    public int[] handleBets(List<Bet> bets, Cube cube){
        int[] result = new int[bets.size()];

        for (int i = 0; i < bets.size(); i++){
            result[i] = handleBet(bets.get(i), cube);
        }

        return result;
    }

    private int handleBet(Bet bet, Cube cube){
        BetType currentBetType = GamesSharedList.getInstance().getBetType(bet.getType());

        if (isBelongToNumbers(currentBetType.getPassiveNumbers(), cube.getNumber())){
            return bet.getAmount();
        }

        if (isBelongToNumbers(currentBetType.getWinNumbers(), cube.getNumber())){
            return (int)(bet.getAmount() * (1 + currentBetType.getKoef()));
        }

        return 0;
    }

    private boolean isBelongToNumbers(int[] passiveNumbers, int number){
        for (int i = 0; i < passiveNumbers.length; i++){
            if (number == passiveNumbers[i]){
                return true;
            }
        }

        return false;
    }
}
