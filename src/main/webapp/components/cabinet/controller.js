/**
 * Created by gs on 10.03.2017.
 */
var crapsApp = angular.module("crapsApp");
crapsApp.controller("cabinetController",['$scope', '$location', 'userService', control]);

function control($scope, $location, userService) {
    var promiseObj=userService.getInfo();
    promiseObj.then(function(value) {
        $scope.userInfo=value;
        $scope.messages = $scope.userInfo.inMessages;
        $scope.sortParam = "date";
    });

    $scope.showOutMessages = function () {
        $scope.messages = $scope.userInfo.outMessages;
    };

    $scope.showInMessages = function () {
        $scope.messages = $scope.userInfo.inMessages;
    };

    $scope.changeSort = function (newParam) {
        $scope.sortParam = newParam;
    };

    $scope.showMessage = function (x) {
        $scope.message = x;
    };

    $scope.showGame = function (x) {
        $scope.game = x;
    };
}