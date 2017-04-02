/**
 * Created by gs on 26.03.2017.
 */
var crapsApp = angular.module('crapsApp');
crapsApp.factory('transferService',['$rootScope', '$q', '$http', transfer]);

function transfer($rootScope, $q, $http) {

    return{
        makeTransfer: function (transfer) {
            var deferred = $q.defer();
            $http({
                method: 'POST',
                url: '/controller?command=TRANSFER',
                headers: {
                    'Content-Type': 'json'
                },
                data: transfer
            }).
            then(function(response) {
                    deferred.resolve(response.data);
                },
                function(response) {
                    deferred.reject(response.status);
                });

            return deferred.promise;
        },
        getCurrencies: function () {
            var deferred = $q.defer();
            $http({method: 'GET', url: 'currencies.json', params: {noCache: (new Date().getTime()) + Math.random()}}).
            then(function(response) {
                    deferred.resolve(response.data.currencies);
                },
                function(response) {
                    deferred.reject(response.status);
                });

            return deferred.promise;
        }
    }
}