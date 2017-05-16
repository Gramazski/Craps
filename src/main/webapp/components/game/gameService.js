/**
 * Created by gs on 09.04.2017.
 */
var crapsApp = angular.module('crapsApp');
crapsApp.factory('gameService',['$http', '$q', service]);

function service($http, $q) {
    var socket;
    var updateGame;
    var sendBets;

    function showMessage(message) {
        console.dir(message);
        var newResult = JSON.parse(message);
        if ("id" in newResult){
            updateGame(newResult);
        }
        else {
            sendBets();
        }
    }

    return{
        setCallbacks: function (updateGameCallback, sendBetsCallback) {
            updateGame = updateGameCallback;
            sendBets = sendBetsCallback;
        },
        setGameId : function (id) {
            socket = new WebSocket("ws://localhost:8083/games/" + id);

            socket.onmessage = function(event) {
                var incomingMessage = event.data;
                showMessage(incomingMessage);
            };
        },
        sendLeaveToServer: function (gameId) {
            socket.close();
            var deferred = $q.defer();
            $http({
                method: 'POST',
                url: '/controller?command=LEAVEGAME',
                headers: {
                    'Content-Type': 'json'
                },
                data: gameId
            }).
            then(function(response) {
                    deferred.resolve(response.data);
                },
                function(response) {
                    deferred.reject(response.status);
                });

            return deferred.promise;
        },
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
        becomeThrower: function (id) {
            var deferred = $q.defer();
            $http({
                method: 'POST',
                url: '/controller?command=BECOMETHROWER',
                headers: {
                    'Content-Type': 'json'
                },
                data: id
            }).
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
            $http({method: 'GET', url: 'assets/json/' + type + '.json', params: {noCache: (new Date().getTime()) + Math.random()}}).
            then(function(response) {
                    deferred.resolve(response.data);
                },
                function(response) {
                    deferred.reject(response.status);
                });

            return deferred.promise;
        },
        sendBetsToServer: function (userInfo, throwerId) {
            var deferred = $q.defer();
            $http({
                method: 'POST',
                url: '/controller?command=PLAYGAME&throwerId=' + throwerId,
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
        },
        throwCube: function (gameId) {
            var deferred = $q.defer();
            $http({
                method: 'POST',
                url: '/controller?command=THROWCUBE',
                headers: {
                    'Content-Type': 'json'
                },
                data: gameId
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