/**
 * Created by gs on 18.04.2017.
 */
var crapsApp = angular.module("crapsApp");
crapsApp.directive('leave', function ($window, $location) {
    function link(scope, element) {
        var w = angular.element($window);

        w.bind('beforeunload', function () {
            if ($location.path().indexOf( "/game/") != -1){
                scope.change();
            }
        });
    }

    return {
        scope : {
            change : "="
        },
        link: link
    }
});