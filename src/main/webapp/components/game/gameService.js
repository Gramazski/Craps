/**
 * Created by gs on 09.04.2017.
 */
var crapsApp = angular.module('crapsApp');
crapsApp.factory('gameService',['$http', '$q', service]);

function service($http, $q) {
    return{
        getGame: function (id) {
            var deferred = $q.defer();
            $http({method: 'GET', url: '/controller?command=LOADGAME', params: {noCache: (new Date().getTime()) + Math.random(), id: id}}).
            then(function(response) {
                    deferred.resolve(response.data);
                },
                function(response) {
                    deferred.reject(response.status);
                });

            return deferred.promise;
        },
        loadBetsPos: function (type) {
            var deferred = $q.defer();
            $http({method: 'GET', url: type + '.json', params: {noCache: (new Date().getTime()) + Math.random()}}).
            then(function(response) {
                    deferred.resolve(response.data);
                },
                function(response) {
                    deferred.reject(response.status);
                });

            return deferred.promise;
        },
        sendBetsToServer: function (userInfo) {
            var deferred = $q.defer();
            $http({
                method: 'POST',
                url: '/controller?command=THROWCUBE',
                headers: {
                    'Content-Type': 'json'
                },
                data: userInfo
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