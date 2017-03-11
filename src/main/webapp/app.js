/**
 * Created by gs on 28.02.2017.
 */
angular.module('commonApp', []);

angular.module('crapsApp', ["ngRoute", 'commonApp']).config(function ($routeProvider) {
    $routeProvider.when('/',
        {
            templateUrl: 'components/home/view.html',
            controller: 'homeController'
        });
    $routeProvider.when('/login',
        {
            templateUrl: 'components/login/view.html',
            controller: 'loginController'
        });
    $routeProvider.when('/register',
        {
            templateUrl: 'components/register/view.html',
            controller: 'registerController'
        });
    $routeProvider.when('/cabinet',
        {
            templateUrl: 'components/cabinet/view.html',
            controller: 'cabinetController'
        }
    );
    $routeProvider.when('/cabinet/messages',
        {
            templateUrl: 'components/cabinet/messagesView.html',
            controller: 'cabinetController'
        }
    );
    $routeProvider.when('/cabinet/games',
        {
            templateUrl: 'components/cabinet/gamesView.html',
            controller: 'cabinetController'
        }
    );
    $routeProvider.when('/cabinet/transfers',
        {
            templateUrl: 'components/cabinet/transfersView.html',
            controller: 'cabinetController'
        }
    );

    $routeProvider.otherwise({redirectTo: '/'});
}).run(function($rootScope, $location) {
    $rootScope.loggedInUser = false;
    $rootScope.$on( "$routeChangeStart", function(event, next, current) {
        if ($rootScope.loggedInUser === false) {
            // no logged user, redirect to /login
            if (!((next.templateUrl == "components/home/view.html") || (next.templateUrl == "components/login/view.html")
                    || (next.templateUrl == "components/register/view.html"))) {
                $location.path("/");
            }
        }
        else {
            if ((next.templateUrl == "components/register/view.html") || (next.templateUrl == "components/login/view.html")) {
                $location.path("/");
            }
        }
    });
});