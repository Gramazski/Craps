/**
 * Created by gs on 05.03.2017.
 */
var crapsApp = angular.module('crapsApp');
crapsApp.controller('registerController', ['$scope', '$rootScope', '$location', 'registerService', control]);

function control($scope, $rootScope, $location, registerService) {
    $scope.register = function () {
        var user = registerService.createRegisterInf($scope);
        var promiseObj=registerService.doRegister(user);
        promiseObj.then(function(value) {
            if (value.id < 0){
                console.dir(value);
                registerService.performScope($scope, value);
            }
            else {
                $rootScope.loggedInUser = true;
                $rootScope.userInfo = registerService.createUser(value);
                console.dir($rootScope.userInfo);
                $location.path("#/");
            }
        });
    };
}