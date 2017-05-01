/**
 * Created by gs on 10.03.2017.
 */
var crapsApp = angular.module("crapsApp");
crapsApp.controller("cabinetController",['$scope', '$rootScope', '$filter', 'messagesService', 'transferService', 'adminService', 'gameInfoService', 'userUploadService', control]);

function control($scope, $rootScope, $filter, messagesService, transferService, adminService, gameInfoService, userUploadService) {
    $rootScope.title = $rootScope.translateModel.title.cabinet;
    $scope.showing = {};
    $scope.showing.receiver = $rootScope.userInfo.userName;
    $scope.showing.sender = "";
    $scope.title = "";
    var promiseObj=transferService.getCurrencies();
    promiseObj.then(function(value) {
        $scope.currencies=value;
        $scope.selectedCurrency = $scope.currencies[0];
        $scope.amountIn = 1;
        $scope.amountOut = 1;
    });

    if ($rootScope.userInfo.admin){
        var promiseObjUsers=adminService.getUsers();
        promiseObjUsers.then(function(value) {
            $scope.users=value;
        });
    }

    $scope.getMaxWin = function () {
        return gameInfoService.getMaxWin($rootScope.userInfo.playedBets);
    };

    $scope.getMaxLose = function () {
        return gameInfoService.getMaxLose($rootScope.userInfo.playedBets);
    };

    $scope.banUser = function (user) {
        var promiseObjUsers=adminService.banUser(user);
        promiseObjUsers.then(function(value) {
            $scope.users=value;
        });
    };

    $scope.unBanUser = function (user) {
        var promiseObjUsers=adminService.unBanUser(user);
        promiseObjUsers.then(function(value) {
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
    };

    $scope.showNewMessageTo = function (receiver) {
        $scope.username = receiver;
        commonModule.showNewMessageModal();
    };

    $scope.setNewFile = function (newFile) {
        $scope.file = newFile;
    };

    $scope.updateUserInfo = function () {
        var promiseObj=userUploadService.uploadFile($scope.file, $scope.newName, $scope.newSurname, $filter('date')($scope.newBirthday, "yyyy-MM-dd"));
        promiseObj.then(function(value) {
            $rootScope.userInfo = value;
            commonModule.closeMessageModal();
        });
    };
}