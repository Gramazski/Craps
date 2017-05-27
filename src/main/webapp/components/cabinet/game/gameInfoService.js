/**
 * Created by gs on 11.04.2017.
 */
var crapsApp = angular.module('crapsApp');
crapsApp.factory('gameInfoService',['$http', '$q', service]);

function service($http, $q) {

    return{
        getMaxWin : function (playedBets) {
            if (playedBets == null || playedBets.length == 0){
                return 0;
            }

            var max = playedBets[0].amount;
            for (var i = 1; i < playedBets.length; i++){
                if (max < playedBets[i].amount){
                    max = playedBets[i].amount;
                }
            }

            return max;
        },
        getMaxLose : function (playedBets) {
            if (playedBets == null || playedBets.length == 0){
                return 0;
            }

            var min = playedBets[0].amount;
            for (var i = 1; i < playedBets.length; i++){
                if (min > playedBets[i].amount){
                    min = playedBets[i].amount;
                }
            }

            return min;
        },
        printReport: function () {
            var deferred = $q.defer();
            $http({
                method: 'POST',
                url: '/controller?command=CREATEREPORT',
                headers: {
                    'Content-Type': 'json'
                }
            }).
            then(function(response) {
                    deferred.resolve(response.data);
                },
                function(response) {
                    deferred.reject(response.status);
                });

            return deferred.promise;
        }
    }
}