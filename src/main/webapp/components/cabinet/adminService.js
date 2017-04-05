/**
 * Created by gs on 05.04.2017.
 */
var crapsApp = angular.module('crapsApp');
crapsApp.factory('adminService',['$http', '$q', administrate]);

function administrate($http, $q) {
    return{
        getUsers: function () {
            var deferred = $q.defer();
            $http({method: 'GET', url: '/controller?command=LOADUSERS', params: {noCache: (new Date().getTime()) + Math.random()}}).
            then(function(response) {
                    deferred.resolve(response.data);
                },
                function(response) {
                    deferred.reject(response.status);
                });

            return deferred.promise;
        },
        banUser: function (user) {
            var deferred = $q.defer();
            $http({
                method: 'POST',
                url: '/controller?command=BAN',
                headers: {
                    'Content-Type': 'json'
                },
                data: user
            }).
            then(function(response) {
                    deferred.resolve(response.data);
                },
                function(response) {
                    deferred.reject(response.status);
                });

            return deferred.promise;
        },
        unBanUser: function (user) {
            var deferred = $q.defer();
            $http({
                method: 'POST',
                url: '/controller?command=UNBAN',
                headers: {
                    'Content-Type': 'json'
                },
                data: user
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