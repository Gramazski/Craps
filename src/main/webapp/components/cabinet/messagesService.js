/**
 * Created by gs on 12.03.2017.
 */
var crapsApp = angular.module('crapsApp');
crapsApp.factory('messagesService',['$rootScope', sendMessage]);

function sendMessage($rootScope) {
    // создать подключение
    var socket = new WebSocket("ws://localhost:8083/chat?login=" + $rootScope.userInfo.username);

// обработчик входящих сообщений
    socket.onmessage = function(event) {
        var incomingMessage = event.data;
        showMessage(incomingMessage);
    };

// показать сообщение в div#subscribe
    function showMessage(message) {
        console.dir(message);
        var newMessage = JSON.parse(message);
        console.dir($rootScope.userInfo);
        $rootScope.userInfo.inMessages.push(newMessage);
        console.dir($rootScope.userInfo);

        //$rootScope.messages.push(newMessage);
        console.dir($rootScope.messages);
    }

    return{
        send: function (message) {
            $rootScope.userInfo.outMessages.push(message);
            var outMessage = JSON.stringify(message);
            console.dir(outMessage);
            socket.send(outMessage);
        }
    }
}