/**
 * Created by gs on 12.03.2017.
 */
var crapsApp = angular.module('crapsApp');
crapsApp.factory('messagesService',['$rootScope', sendMessage]);

function sendMessage($rootScope) {
    var socket = new WebSocket("ws://localhost:8083/chat?login=" + $rootScope.userInfo.userName);

    socket.onmessage = function(event) {
        var incomingMessage = event.data;
        showMessage(incomingMessage);
    };

    function showMessage(message) {
        console.dir(message);
        var newMessage = JSON.parse(message);
        $rootScope.userInfo.messages.push(newMessage);
        $rootScope.userInfo.messages.$apply();
        console.dir($rootScope.userInfo);
    }

    return{
        send: function (message) {
            $rootScope.userInfo.messages.push(message);
            var outMessage = JSON.stringify(message);
            console.dir(outMessage);
            commonModule.closeMessageModal();
            socket.send(outMessage);
        }
    }
}