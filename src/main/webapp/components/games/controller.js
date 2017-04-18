/**
 * Created by gs on 06.04.2017.
 */
var crapsApp = angular.module("crapsApp");
crapsApp.controller("gamesController",['$scope', '$rootScope', 'gamesService', 'chatService', control]);

function control($scope, $rootScope, gamesService, chatService) {
    $rootScope.title = $rootScope.translateModel.title.games;
    gamesService.sendConnectToServer();

    $scope.$on('$locationChangeStart', function(event, next, current) {
        gamesService.sendLeaveToServer();
    });

    var promiseObj=gamesService.getGames();
    promiseObj.then(function(value) {
        $scope.games = value;
    });

    var promiseObjTypes=gamesService.getTypes();
    promiseObjTypes.then(function(value) {
        $scope.types=value;
        $scope.selectedType = $scope.types[0];
        $scope.minBet = 1;
        $scope.maxBet = 1;
        $scope.maxPlayersCount = 1;
    });

    var updateGames = function (newGames) {
        $scope.games = newGames;
        console.dir($scope.games);
    };

    gamesService.setGamesCallback(updateGames);

    $scope.chatShow = true;
    $rootScope.messages = [];

    $scope.showChat = function () {
        $scope.chatShow = !$scope.chatShow;
    };

    $scope.sendMessage = function () {
        var newMessage = {};
        newMessage.body = $scope.message;
        newMessage.sender = $rootScope.userInfo.userName;
        var date = new Date();
        newMessage.time = date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();

        chatService.send(newMessage);
    };

    $scope.changeSort = function (newParam) {
        $rootScope.sortParam = newParam;
    };

    $scope.close = function () {
        $scope.lobby = "";
        $scope.minBet = 1;
        $scope.maxBet = 1;
        $scope.selectedType = $scope.types[0];
    };

    $scope.addGame = function () {
        var game = {};
        game.lobby = $scope.lobby;
        game.minBet = $scope.minBet;
        game.maxBet = $scope.maxBet;
        game.type = $scope.selectedType;
        game.maxPlayersCount = $scope.maxPlayersCount;

        var promiseObj=gamesService.addGame(game);
        promiseObj.then(function(value) {
            if (value.id != -1){
                //$scope.games.push(value);
                $scope.close();
            }
            commonModule.closeMessageModal();
        });
    };

    $scope.removeGame = function (index) {
        var game = $scope.games[index];

        var promiseObj=gamesService.removeGame(game);
        promiseObj.then(function(value) {
            if (value.id == -1){
                //$scope.games.splice(index, 1);
            }
        });
    };
}