/**
 * Created by gs on 09.04.2017.
 */
var crapsApp = angular.module("crapsApp");
crapsApp.controller("gameController",['$scope', '$routeParams', '$rootScope', 'gameService', control]);

function control($scope, $routeParams, $rootScope, gameService) {
    $scope.betting = {
        "isMakeBet" : false,
        "isRemoveBet" : false,
        "amount" : 0
    };

    $scope.cube = {
        "first" : 0,
        "second" : 0
    };

    $scope.$on("$routeChangeSuccess", function () {
        var id = $routeParams["id"];
        if(id!=='undefined'){
            $rootScope.title = $rootScope.translateModel.title.game + id;
            gameService.setGameId(id);

            var promiseObj=gameService.getGame(id);
            promiseObj.then(function(value) {
                $scope.game = value;
                var promiseObjBets=gameService.loadBetsPos(value.type);
                promiseObjBets.then(function(value) {
                    $scope.betsPos = value.betsPos;
                    commonModule.setAreaWidth($scope.betsPos, $scope.bets);
                });
            });
        }
    });

    $scope.$on('$locationChangeStart', function(event, next, current) {
        gameService.sendLeaveToServer($scope.game.id);
    });

    $scope.changeSort = function (newParam) {
        $rootScope.sortParam = newParam;
    };

    var updateGame = function (newGame) {
        $scope.game = newGame;
        commonModule.updateGame();
    };

    var sendBets = function () {
        $rootScope.userInfo.bets = $scope.bets;
        var promiseObj=gameService.sendBetsToServer($rootScope.userInfo, $scope.game.throwerId);
        promiseObj.then(function(value) {
            $scope.cube = value.cube;
            $scope.throwResult = value;
            updateBetList(value);
            $rootScope.userInfo.amount += value.amount;
            $rootScope.userInfo.playedBets = value.playedBets;
        });
        $scope.showAnimate();

        setTimeout($scope.hideAnimate, 2300);
    };

    gameService.setCallbacks(updateGame, sendBets);

    $scope.bets = [];
    $scope.history = [];
    $scope.throwing = false;

    $scope.becomeThrower = function () {
        var promiseObj=gameService.becomeThrower($scope.game.id);
        promiseObj.then(function(value) {
            $scope.game = value;
        });
    };

    $scope.showAnimate = function () {
        $scope.throwing = true;
        commonModule.showAnimate();
    };

    $scope.hideAnimate = function () {
        commonModule.hideAnimate();
        $scope.throwing = false;
        $scope.$apply();
        commonModule.showThrowingResult();
        $scope.onFallenNumber();
    };

    $scope.throwCubes = function () {
        $rootScope.userInfo.bets = $scope.bets;
        var promiseObj=gameService.throwCube($scope.game.id);
        promiseObj.then(function(value) {

        });
    };

    $scope.setBet = function () {
        $scope.betting.isMakeBet = true;
        $scope.betting.isRemoveBet = false;
    };

    $scope.onFallenNumber = function () {
        addToHistory($scope.cube.first + $scope.cube.second, $scope.throwResult.amount, "cubes");

    };

    var updateBetList = function (betsResults) {
        $scope.bets = betsResults.leftBets;
        var i;

        for (i = 0; i < betsResults.loseBets.length; i++){
            addToHistory(betsResults.loseBets[i].type, betsResults.loseBets[i].amount, "lose");
            commonModule.removeBet(betsResults.loseBets[i].type);
        }

        for (i = 0; i < betsResults.winBets.length; i++){
            addToHistory(betsResults.winBets[i].type, betsResults.winBets[i].amount, "win");
            commonModule.removeBet(betsResults.winBets[i].type);
        }
    };

    var addToHistory = function (descr, result, type) {
        var historyAction = {};
        historyAction.descr = descr;
        historyAction.result = result;
        historyAction.type = type;

        $scope.history.push(historyAction);
    };

    var checkBet = function () {
        return $scope.betting.amount >= $scope.game.minBet && $scope.betting.amount <= $scope.game.maxBet
            && calcAllBetsAmount($scope.betting.amount) <= $rootScope.userInfo.amount;
    };

    var calcAllBetsAmount = function (newAmount) {
        var result = newAmount;
        for (var i = 0; i < $scope.bets.length; i++){
            result += $scope.bets[i].amount;
        }

        return result;
    };

    $scope.makeBet = function (bet) {
        if ($scope.betting.isMakeBet && checkBet() && $scope.addNewBetToList(bet)){
            addToHistory(bet.type, bet.amount, "make");
        }

        if ($scope.betting.isRemoveBet){
            $scope.removeBet(bet.type);
        }
    };

    $scope.setRemovingBet = function () {
        $scope.betting.isMakeBet = false;
        $scope.betting.isRemoveBet = true;
    };

    $scope.removeBet = function (type) {
        for (var i = 0; i < $scope.bets.length; i++){
            if ($scope.bets[i].type == type){
                addToHistory($scope.bets[i].type, $scope.bets[i].amount, "disable");
                $scope.bets.splice(i, 1);
                commonModule.removeBet(type);
                $scope.betting.isRemoveBet = false;

                return true;
            }
        }

        return false;
    };

    $scope.removeAll = function () {
        for (var i = 0; i < $scope.bets.length; ){
            $scope.betting.isRemoveBet = true;
            if (!$scope.removeBet($scope.bets[i].type)){
                i = 1;
            }
        }
    };

    $scope.changeAreaCoords = function () {
        commonModule.setAreaWidth($scope.betsPos, $scope.bets);
    };

    $scope.addNewBetToList = function (bet) {
        var added = false;
        for (var i = 0; i < $scope.bets.length; i++){
            if (bet.type == $scope.bets[i].type){
                if ($scope.bets[i].amount + $scope.betting.amount <= $scope.game.maxBet){
                    $scope.bets[i].amount += $scope.betting.amount;
                    added = true;
                }
                else {
                    return false;
                }
            }
        }

        if (!added){
            var xBet = bet.coords[0] + Math.round((bet.coords[2] - bet.coords[0] - bet.width) / 2) + bet.width;
            var yBet = bet.coords[1] + Math.round((bet.coords[3] - bet.coords[1] - bet.width) / 2);

            commonModule.setBet(xBet, yBet, bet.width, bet.type);

            bet.position = {
                "x" : bet.coordsSt[0] + Math.round((bet.coordsSt[2] - bet.coordsSt[0] - bet.widthSt) / 2) + bet.widthSt,
                "y" : bet.coordsSt[1] + Math.round((bet.coordsSt[3] - bet.coordsSt[1] - bet.widthSt) / 2),
                "width" : bet.widthSt
            };

            bet.amount = $scope.betting.amount;
            bet.gameId = $scope.game.id;

            $scope.bets.push(bet);
        }
        $scope.betting.isMakeBet = false;

        return true;
    };

}