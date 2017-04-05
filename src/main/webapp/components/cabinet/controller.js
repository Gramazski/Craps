/**
 * Created by gs on 10.03.2017.
 */
var crapsApp = angular.module("crapsApp");
crapsApp.controller("cabinetController",['$scope', '$rootScope', 'messagesService', 'transferService', 'adminService', control]);

function control($scope, $rootScope, messagesService, transferService, adminService) {
    $scope.showing = {};
    $scope.showing.receiver = $rootScope.userInfo.userName;
    $scope.showing.sender = "";
    var promiseObj=transferService.getCurrencies();
    promiseObj.then(function(value) {
        console.dir(value);
        $scope.currencies=value;
        $scope.selectedCurrency = $scope.currencies[0];
        $scope.amountIn = 0;
        $scope.amountOut = 0;
    });

    if ($rootScope.userInfo.admin){
        var promiseObjUsers=adminService.getUsers();
        promiseObjUsers.then(function(value) {
            console.dir(value);
            $scope.users=value;
        });
    }

    $scope.banUser = function (user) {
        var promiseObjUsers=adminService.banUser(user);
        promiseObjUsers.then(function(value) {
            console.dir(value);
            $scope.users=value;
        });
    };

    $scope.transferType = false;

    $scope.close = function (formName) {
        commonModule.resetFormIn(formName);
    };

    $scope.setTransferType = function (newTransferType) {
        $scope.transferType = newTransferType;
    };

    $scope.makeTransfer = function () {
        var newTransfer = {};
        newTransfer.incoming = $scope.transferType;
        if ($scope.transferType){
            newTransfer.amount = $scope.amountIn;
        }
        else {
            newTransfer.amount = $scope.amountOut;
        }

        newTransfer.accountNumber = $scope.account;

        var promiseObj = transferService.makeTransfer(newTransfer);
        promiseObj.then(function(value) {
            $rootScope.userInfo = value;
        });
        commonModule.closeMessageModal();
    };

    $scope.showOutMessages = function () {
        $scope.showing.receiver = "";
        $scope.showing.sender = $rootScope.userInfo.userName;
        commonModule.setActiveOutMessageList();
    };

    $scope.showInMessages = function () {
        $scope.showing.receiver = $rootScope.userInfo.userName;
        $scope.showing.sender = "";
        commonModule.setActiveInMessageList();
    };

    $scope.changeSort = function (newParam) {
        $rootScope.sortParam = newParam;
    };

    $scope.showMessage = function (x) {
        $scope.message = x;
    };

    $scope.showGame = function (x) {
        $scope.game = x;
    };

    $scope.sendMessage = function () {
        var message = {};
        message.title = $scope.title;
        message.body = $scope.body;
        message.receiver = $scope.username;
        message.sender = $rootScope.userInfo.userName;

        messagesService.send(message);

        return true;
    }
}