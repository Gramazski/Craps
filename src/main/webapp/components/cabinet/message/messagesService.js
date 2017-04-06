/**
 * Created by gs on 12.03.2017.
 */
var crapsApp = angular.module('crapsApp');
crapsApp.factory('messagesService',['$rootScope', sendMessage]);

function sendMessage($rootScope) {
    var socket = new WebSocket("ws://localhost:8083/message?login=" + $rootScope.userInfo.userName);

    socket.onmessage = function(event) {
        var incomingMessage = event.data;
        showMessage(incomingMessage);
    };

    function showMessage(message) {
        console.dir(message);
        var newMessage = JSON.parse(message);
        $rootScope.userInfo.messages.push(newMessage);
        commonModule.updateMessagesList();
        console.dir($rootScope.userInfo);
    }

    return{
        send: function (message) {
            var outMessage = JSON.stringify(message);
            console.dir(outMessage);
            commonModule.closeMessageModal();
            socket.send(outMessage);
        }
    }
}