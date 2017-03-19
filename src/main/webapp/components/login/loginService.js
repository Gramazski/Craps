/**
 * Created by gs on 07.03.2017.
 */
var crapsApp = angular.module('crapsApp');
crapsApp.factory('loginService',['$http', '$q', login]);

function login($http, $q) {
    return{
        checkLogin: function (login, password) {
            var deferred = $q.defer();
            $http({
                method: 'POST',
                url: '/controller?command=LOGIN',
                headers: {
                    'Content-Type': 'json'
                },
                data: { userName: login, password: password }
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

/*headers: {'Content-Type': 'application/x-www-form-urlencoded'},
 transformRequest: function(obj) {
 var str = [];
 for(var p in obj){
 str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
 }
 return str.join("&");
 },
 data: data*/