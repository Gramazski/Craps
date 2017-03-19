/**
 * Created by gs on 10.03.2017.
 */
var crapsApp = angular.module("crapsApp");
crapsApp.controller("cabinetController",['$scope', '$rootScope', 'messagesService', 'userService', control]);

function control($scope, $rootScope, messagesService, userService) {
    $scope.showing = {};
    $scope.showing.receiver = $rootScope.userInfo.userName;
    $scope.showing.sender = "";

    $scope.showOutMessages = function () {
        console.dir($rootScope.userInfo);
        $scope.showing.receiver = "";
        $scope.showing.sender = $rootScope.userInfo.userName;
    };

    $scope.showInMessages = function () {
        console.dir($rootScope.userInfo);
        console.dir($scope.showing);
        $scope.showing.receiver = $rootScope.userInfo.userName;
        $scope.showing.sender = "";
    };

    $scope.changeSort = function (newParam) {
        $rootScope.sortParam = newParam;
    };

    $scope.showMessage = function (x) {
        $scope.message = x;
    };

    $scope.showGame = function (x) {
        $scope.game = x;
    };

    $scope.sendMessage = function () {
        var message = {};
        message.title = $scope.title;
        message.body = $scope.body;
        message.receiver = $scope.username;
        message.sender = $rootScope.userInfo.userName;
        message.createDate = "2012-12-12";

        messagesService.send(message);

        return true;
    }
}