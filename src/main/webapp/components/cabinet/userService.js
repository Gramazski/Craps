/**
 * Created by gs on 10.03.2017.
 */
var crapsApp = angular.module('crapsApp');
crapsApp.factory('userService',['$http', '$q', getInfo]);

function getInfo($http, $q) {
    return{
        getInfo: function () {
            var deferred = $q.defer();
            $http({method: 'GET', url: 'user.json'}).
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