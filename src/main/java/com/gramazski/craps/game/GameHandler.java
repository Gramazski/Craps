package com.gramazski.craps.game;

import com.gramazski.craps.entity.impl.Bet;
import com.gramazski.craps.entity.impl.BetType;
import com.gramazski.craps.entity.impl.Cube;
import com.gramazski.craps.util.RandomHandler;

import java.util.List;

public class GameHandler {
    /**
     * @return
     */
    public Cube throwCube(){
        Cube cube = new Cube();
        cube.setFirst(RandomHandler.getNexInt(6) + 1);
        cube.setSecond(RandomHandler.getNexInt(6) + 1);

        return cube;
    }

    /**
     * @param bets
     * @param cube
     * @return
     */
    public int[] handleBets(List<Bet> bets, Cube cube){
        int[] result = new int[bets.size()];

        for (int i = 0; i < bets.size(); i++){
            result[i] = handleBet(bets.get(i), cube);
        }

        return result;
    }

    /**
     * @param bet
     * @param cube
     * @return
     */
    private int handleBet(Bet bet, Cube cube){
        BetType currentBetType = GamesSharedList.getInstance().getBetType(bet.getType());

        if (isBelongToNumbers(currentBetType.getLoseNumbers(), cube.getNumber())){
            return 0;
        }

        if (isBelongToNumbers(currentBetType.getWinNumbers(), cube.getNumber())){
            return (int)(bet.getAmount() * (1 + currentBetType.getKoef()));
        }

        return bet.getAmount();
    }

    /**
     * @param passiveNumbers
     * @param number
     * @return
     */
    private boolean isBelongToNumbers(int[] passiveNumbers, int number){
        for (int i = 0; i < passiveNumbers.length; i++){
            if (number == passiveNumbers[i]){
                return true;
            }
        }

        return false;
    }
}
