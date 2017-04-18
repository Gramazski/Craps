/**
 * Created by gs on 28.02.2017.
 */
var crapsApp = angular.module("crapsApp");
crapsApp.controller("mainController",['$scope', '$location', '$rootScope', '$cookieStore', 'translateService', 'logoutService', control]);

function control($scope, $location, $rootScope, $cookies, translateService, logoutService) {
    $scope.translate = function (path, lang) {
        var now = new Date(),
            exp = new Date(now.getFullYear()+1, now.getMonth(), now.getDate());
        $cookies.put('lang', lang, {'expires' : exp});
        $rootScope.lang = lang;
        var promiseObj=translateService.getTranslate(path, lang);
        promiseObj.then(function(value) {
            $rootScope.translateModel=value;
        });
    };

    $scope.logout = function () {
        logoutService.doLogout();
        $rootScope.loggedInUser = false;
        $location("/");
    };
}