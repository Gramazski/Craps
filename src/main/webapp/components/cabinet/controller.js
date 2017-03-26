/**
 * Created by gs on 10.03.2017.
 */
var crapsApp = angular.module("crapsApp");
crapsApp.controller("cabinetController",['$scope', '$rootScope', 'messagesService', control]);

function control($scope, $rootScope, messagesService) {
    $scope.showing = {};
    $scope.showing.receiver = $rootScope.userInfo.userName;
    $scope.showing.sender = "";

    $scope.showOutMessages = function () {
        $scope.showing.receiver = "";
        $scope.showing.sender = $rootScope.userInfo.userName;
        commonModule.setActiveOutMessageList();
    };

    $scope.showInMessages = function () {
        $scope.showing.receiver = $rootScope.userInfo.userName;
        $scope.showing.sender = "";
        commonModule.setActiveInMessageList();
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

        messagesService.send(message);

        return true;
    }
}