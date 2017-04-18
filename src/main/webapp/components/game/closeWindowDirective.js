/**
 * Created by gs on 18.04.2017.
 */
var crapsApp = angular.module("crapsApp");
crapsApp.directive('leave', function ($window) {
    function link(scope, element) {
        var w = angular.element($window);

        w.bind('beforeunload', function () {
            scope.change();
        });
    }

    return {
        scope : {
            change : "="
        },
        link: link
    }
});