/**
 * Created by gs on 26.03.2017.
 */
var commonApp = angular.module('commonApp');
commonApp.factory('userService',['$http', '$q', getUser]);

function getUser($http, $q) {
    return{
        getUser : function () {
            var deferred = $q.defer();
            $http({
                method: 'POST',
                url: '/controller?command=RELOAD',
                headers: {
                    'Content-Type': 'json'
                }
            }).
            then(function(response) {
                    deferred.resolve(response.data);
                },
                function(response) {
                    deferred.reject(response.status);
                });

            return deferred.promise;
        }
    }
}