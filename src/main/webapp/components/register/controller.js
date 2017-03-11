/**
 * Created by gs on 05.03.2017.
 */
var crapsApp = angular.module('crapsApp');
crapsApp.controller('registerController', ['$scope', '$rootScope', '$location', 'registerService', control]);

function control($scope, $rootScope, $location, registerService) {
    $scope.register = function () {
        var user = {};
        user.name = $scope.name;
        user.surname = $scope.surname;
        user.birthday = $scope.birthday;
        user.login = $scope.login;
        user.password = $scope.password;
        var promiseObj=registerService.doRegister(user);
        promiseObj.then(function(value) {
            if (value === null){

            }
            else {
                $rootScope.loggedInUser = true;
                $rootScope.user = value;
                $location.path("#/");
            }
        });
    };
}