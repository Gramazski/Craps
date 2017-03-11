/**
 * Created by gs on 07.03.2017.
 */
var commonApp = angular.module('commonApp');
commonApp.factory('logoutService',['$http', '$q', logout]);

function logout($http, $q) {
    return{
        doLogout: function () {
            var deferred = $q.defer();
            $http({method: 'GET', url: 'user.json', params: {noCache: (new Date().getTime()) + Math.random()}}).
            then(function(response) {
                    deferred.resolve(response.data.translateModel);
                },
                function(response) {
                    deferred.reject(response.status);
                });

            return deferred.promise;
        }
    }
}