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
        },
        createUser : function (value) {
            var user = {};
            user.id = value.id;
            user.password = value.password;
            user.username = value.userName;
            user.email = value.email;
            user.amount = value.amount;
            user.win_amount = value.winAmount;
            user.avatar = value.avatar;
            user.name = value.name;
            user.surname = value.surname;
            user.createDate = value.createTime;

            return user;
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