/**
 * Created by gs on 06.04.2017.
 */
var crapsApp = angular.module('crapsApp');
crapsApp.factory('chatService',['$rootScope', sendMessage]);

function sendMessage($rootScope) {
    var socket = new WebSocket("ws://localhost:8083/chat");

    socket.onmessage = function(event) {
        var incomingMessage = event.data;
        showMessage(incomingMessage);
    };

    function showMessage(message) {
        var newMessage = JSON.parse(message);
        $rootScope.messages.push(newMessage);
        commonModule.updateChat();
    }

    return{
        send: function (message) {
            var outMessage = JSON.stringify(message);
            socket.send(outMessage);
        }
    }
}