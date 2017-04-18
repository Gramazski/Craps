/**
 * Created by gs on 12.03.2017.
 */
var crapsApp = angular.module('crapsApp');
crapsApp.factory('messagesService',['$rootScope', sendMessage]);

function sendMessage($rootScope) {
    var socket = new WebSocket("ws://192.168.137.1:8083/message?login=" + $rootScope.userInfo.userName);

    socket.onmessage = function(event) {
        var incomingMessage = event.data;
        showMessage(incomingMessage);
    };

    function showMessage(message) {
        var newMessage = JSON.parse(message);
        if (newMessage.id != -1){
            $rootScope.userInfo.messages.push(newMessage);
            commonModule.closeMessageModal();
            commonModule.updateMessagesList();
        }
        else {
            commonModule.clearMessageModal();
        }
    }

    return{
        send: function (message) {
            var outMessage = JSON.stringify(message);
            socket.send(outMessage);
        }
    }
}