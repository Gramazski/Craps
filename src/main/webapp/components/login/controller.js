/**
 * Created by gs on 05.03.2017.
 */
var crapsApp = angular.module('crapsApp');
crapsApp.controller('loginController', ['$scope', '$rootScope', '$location', 'loginService', control]);

function control($scope, $rootScope, $location, loginService) {
    $scope.login = function () {
        var promiseObj=loginService.checkLogin($scope.username, $scope.password);
        promiseObj.then(function(value) {
            if (value.id < 0){
                console.dir(value);
                $scope.username = "";
                $scope.password = "";
            }
            else {
                $rootScope.loggedInUser = true;
                $rootScope.userInfo = value;
                console.dir($rootScope.userInfo);
                $location.path("#/");
            }
        });
    };
}