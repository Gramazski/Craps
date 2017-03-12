/**
 * Created by gs on 10.03.2017.
 */
var crapsApp = angular.module("crapsApp");
crapsApp.controller("cabinetController",['$scope', '$rootScope', 'messagesService', 'userService', control]);

function control($scope, $rootScope, messagesService, userService) {
    /*var promiseObj=userService.getInfo();
    promiseObj.then(function(value) {
        $scope.userInfo=value;
        $scope.messages = $scope.userInfo.inMessages;
        $scope.sortParam = "date";
    });*/

    $rootScope.messages = $scope.userInfo.inMessages;

    $scope.showOutMessages = function () {
        $rootScope.messages = $scope.userInfo.outMessages;
    };

    $scope.showInMessages = function () {
        $rootScope.messages = $scope.userInfo.inMessages;
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
        message.sender = $rootScope.userInfo.username;
        message.createDate = "2012-12-12";

        messagesService.send(message);

        return true;
    }
}