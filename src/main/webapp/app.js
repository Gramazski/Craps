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
            templateUrl: 'components/cabinet/message/view.html',
            controller: 'cabinetController'
        }
    );
    $routeProvider.when('/cabinet/games',
        {
            templateUrl: 'components/cabinet/game/view.html',
            controller: 'cabinetController'
        }
    );
    $routeProvider.when('/cabinet/transfers',
        {
            templateUrl: 'components/cabinet/transfer/view.html',
            controller: 'cabinetController'
        }
    );

    $routeProvider.otherwise({redirectTo: '/'});
}).run(function($rootScope, $location, userService) {
    $rootScope.loggedInUser = true;
    var promiseObj=userService.getUser();
    promiseObj.then(function(value) {
        if (value == null){
            console.dir(value);
            $rootScope.loggedInUser = false;
        }
        else {
            $rootScope.loggedInUser = true;
            $rootScope.userInfo = value;
            console.dir($rootScope.userInfo);
        }
    });
    $rootScope.$on( "$routeChangeStart", function(event, next, current) {
        if ($rootScope.loggedInUser === false) {
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