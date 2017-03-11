/**
 * Created by gs on 07.03.2017.
 */
var crapsApp = angular.module('crapsApp');
crapsApp.factory('registerService',['$http', '$q', 'loginService', register]);

function register($http, $q, loginService) {
    return{
        doRegister: function (regInf) {
            var deferred = $q.defer();
            $http({
                method: 'POST',
                url: '/controller?command=REGISTER',
                headers: {
                    'Content-Type': 'json'
                },
                data: regInf
            }).
            then(function(response) {
                    deferred.resolve(response.data);
                },
                function(response) {
                    deferred.reject(response.status);
                });

            return deferred.promise;
        },
        createRegisterInf: function ($scope) {
            var regInf = {};
            regInf.userName = $scope.username;
            regInf.password = $scope.password;
            regInf.name = $scope.name;
            regInf.surname = $scope.surname;
            regInf.email = $scope.email;
            //regInf.sex = $scope.sex;
            //regInf.birthday = $scope.birthday;

            return regInf;
        },
        createUser: function (value) {
            return loginService.createUser(value);
        }
    }
}