/**
 * Created by gs on 07.03.2017.
 */
var crapsApp = angular.module('crapsApp');
crapsApp.factory('registerService',['$http', '$q', '$filter', register]);

function register($http, $q, $filter) {
    return{
        doRegister: function (regInf) {
            var deferred = $q.defer();
            $http({
                method: 'POST',
                url: '/controller?command=REGISTER',
                headers: {
                    'Content-Type': 'json; charset: utf-8'
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
            regInf.sex = $scope.sex;
            regInf.birthday = $filter('date')($scope.birthday, "yyyy-MM-dd");

            return regInf;
        },
        performScope: function ($scope, value) {
            $scope.username = value.username;
            $scope.password = "";
            $scope.repeatPassword = "";
            $scope.name = value.name;
            $scope.surname = value.surname;
            $scope.email = value.email;
        }
    }
}