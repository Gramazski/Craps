/**
 * Created by gs on 28.02.2017.
 */
var commonApp = angular.module('commonApp');
commonApp.factory('translateService',['$http', '$q', translate]);

function translate($http, $q) {
    return{
        getTranslate: function (path) {
            var deferred = $q.defer();
            $http({method: 'GET', url: path}).
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