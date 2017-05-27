/**
 * Created by gs on 28.02.2017.
 */
angular.module('commonApp', []);

angular.module('crapsApp', ["ngRoute", "ngAudio", 'ngCookies', "ngFileUpload", 'commonApp']).config(function ($routeProvider) {
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
    $routeProvider.when('/games',
        {
            templateUrl: 'components/games/view.html',
            controller: 'gamesController'
        }
    );
    $routeProvider.when('/game/:id',
        {
            templateUrl: 'components/game/view.html',
            controller: 'gameController'
        }
    );
    $routeProvider.when('/error',
        {
            templateUrl: 'components/error/view.html',
            controller: 'errorController'
        }
    );

    $routeProvider.otherwise({redirectTo: '/'});
}).run(function($rootScope, $location, $cookies, userService, translateService) {
    $rootScope.lang = $cookies.getObject('lang');
    if ($rootScope.lang === undefined){
        var now = new Date(),
            exp = new Date(now.getFullYear()+1, now.getMonth(), now.getDate());
        $cookies.putObject('lang', 'en', {'expires' : exp});
        $rootScope.lang = "en";
    }
    $rootScope.loggedInUser = false;
    $rootScope.title = "Craps";
    var promiseObj=userService.getUser();
    promiseObj.then(function(value) {
        if (value == null){
            $rootScope.loggedInUser = false;
        }
        else {
            $rootScope.loggedInUser = true;
            $rootScope.userInfo = value;
        }
    });

    var promiseObjTranslate=translateService.getTranslate('assets/i18n/lang_' + $rootScope.lang + '.json');
    promiseObjTranslate.then(function(value) {
        $rootScope.translateModel=value;
        $rootScope.title = $rootScope.translateModel.title.main;
    });

    $rootScope.$on( "$routeChangeStart", function(event, next, current) {
        if ($rootScope.loggedInUser === false) {
            if (!((next.templateUrl == "components/home/view.html") || (next.templateUrl == "components/login/view.html")
                    || (next.templateUrl == "components/register/view.html"))) {
                $location.path("/");
            }
        }
        else {
            if ($rootScope.userInfo !== undefined && $rootScope.userInfo.banned && (next.templateUrl == "components/games/view.html")
                    && (next.templateUrl =="components/game/view.html")){
                $location.path("/");
            }

            if ((next.templateUrl == "components/register/view.html") || (next.templateUrl == "components/login/view.html")) {
                $location.path("/");
            }
        }
    });
});