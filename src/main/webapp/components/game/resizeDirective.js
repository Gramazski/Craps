/**
 * Created by gs on 09.04.2017.
 */
var crapsApp = angular.module("crapsApp");
crapsApp.directive('resize', function ($window, $location) {
    function link(scope, element) {
        var w = angular.element($window);
        scope.getWindowDimensions = function () {
            return {
                'h': w.height(),
                'w': w.width()
            };
        };
        scope.$watch(scope.getWindowDimensions, function (newValue, oldValue) {
            scope.windowHeight = newValue.h;
            scope.windowWidth = newValue.w;

            scope.style = function () {
                return {
                    'height': (newValue.h - 100) + 'px',
                    'width': (newValue.w - 100) + 'px'
                };
            };

        }, true);

        w.bind('resize', function () {
            scope.change();
            scope.$apply();
        });
    }

    return {
        scope : {
            change : "="
        },
        link: link
    }
});