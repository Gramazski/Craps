/**
 * Created by gs on 07.03.2017.
 */
var crapsApp = angular.module('crapsApp');
crapsApp.factory('registerService',['$http', '$q', register]);

function register($http, $q) {
    return{
        doRegister: function (data) {
            var deferred = $q.defer();
            $http({method: 'GET', url: 'user.json', params: data}).
            then(function(response) {
                    deferred.resolve(response.data.user);
                },
                function(response) {
                    deferred.reject(response.status);
                });

            return deferred.promise;
        }
    }
}