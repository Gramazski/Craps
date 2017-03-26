/**
 * Created by gs on 26.03.2017.
 */
var crapsApp = angular.module('crapsApp');
crapsApp.factory('transferService',['$rootScope', transfer]);

function transfer($rootScope) {

    return{
        makeTransfer: function (transfer) {

        }
    }
}