/**
 * Created by gs on 29.04.2017.
 */
var commonApp = angular.module('commonApp');

commonApp.factory('musicLoadService',['$q', '$http', 'Upload', function ($q, $http, Upload) {
    return{
        uploadFile: function (file, fileName) {
            var deferred = $q.defer();
            Upload.upload({
                url: '/controller?command=UPLOADMUSIC',
                fields: {'fileName': fileName},
                file: file
            }).then(function(response) {
                    deferred.resolve(response.data);
                },
                function(response) {
                    deferred.reject(response.status);
                }
            );

            return deferred.promise;
        },
        getSounds: function () {
            var deferred = $q.defer();
            $http({method: 'GET', url: '/controller?command=GETSOUNDS', params: {noCache: (new Date().getTime()) + Math.random()}}).
            then(function(response) {
                    deferred.resolve(response.data);
                },
                function(response) {
                    deferred.reject(response.status);
                });

            return deferred.promise;
        }
    }
}]);