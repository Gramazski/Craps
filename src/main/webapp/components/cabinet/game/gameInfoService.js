/**
 * Created by gs on 11.04.2017.
 */
var crapsApp = angular.module('crapsApp');
crapsApp.factory('gameInfoService',[service]);

function service() {

    return{
        getMaxWin : function (playedBets) {
            var max = playedBets[0].amount;
            for (var i = 1; i < playedBets.length; i++){
                if (max < playedBets[i].amount){
                    max = playedBets[i].amount;
                }
            }

            return max;
        },
        getMaxLose : function (playedBets) {
            var min = playedBets[0].amount;
            for (var i = 1; i < playedBets.length; i++){
                if (min > playedBets[i].amount){
                    min = playedBets[i].amount;
                }
            }

            return min;
        }
    }
}