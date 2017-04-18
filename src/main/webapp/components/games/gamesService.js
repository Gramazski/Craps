/**
 * Created by gs on 06.04.2017.
 */
var crapsApp = angular.module('crapsApp');
crapsApp.factory('gamesService',['$http', '$q', service]);

function service($http, $q) {
    var socket;
    var updateGames;

    return{
        sendConnectToServer : function () {
            socket = new WebSocket("ws://192.168.137.1:8083/games");

            socket.onmessage = function(event) {
                var incomingMessage = event.data;
                showMessage(incomingMessage);
            };

            function showMessage(message) {
                var newGames = JSON.parse(message);
                updateGames(newGames);
                commonModule.updateChat();
            }
        },
        setGamesCallback : function (callback) {
            updateGames = callback;
        },
        sendLeaveToServer: function () {
            socket.close();
        },
        getGames: function () {
            var deferred = $q.defer();
            $http({method: 'GET', url: '/controller?command=LOADGAMES', params: {noCache: (new Date().getTime()) + Math.random()}}).
            then(function(response) {
                    deferred.resolve(response.data);
                },
                function(response) {
                    deferred.reject(response.status);
                });

            return deferred.promise;
        },
        addGame: function (game) {
            var deferred = $q.defer();
            $http({
                method: 'POST',
                url: '/controller?command=ADDGAME',
                headers: {
                    'Content-Type': 'json'
                },
                data: game
            }).
            then(function(response) {
                    deferred.resolve(response.data);
                },
                function(response) {
                    deferred.reject(response.status);
                });

            return deferred.promise;
        },
        removeGame: function (game) {
            var deferred = $q.defer();
            $http({
                method: 'POST',
                url: '/controller?command=REMOVEGAME',
                headers: {
                    'Content-Type': 'json'
                },
                data: game
            }).
            then(function(response) {
                    deferred.resolve(response.data);
                },
                function(response) {
                    deferred.reject(response.status);
                });

            return deferred.promise;
        },
        getTypes: function () {
            var deferred = $q.defer();
            $http({method: 'GET', url: 'betTypes.json', params: {}}).
            then(function(response) {
                    deferred.resolve(response.data.types);
                },
                function(response) {
                    deferred.reject(response.status);
                });

            return deferred.promise;
        }
    }
}