"use strict";

import { newMessage, setUsername } from "./animation.js";

var connectingInfo = $("#connecting-info");

var stompClient = null;
var username = null;

export function connect() {
  username = sessionStorage.getItem("username");
  setUsername(username);
  var socket = new SockJS("/talk-talk");
  stompClient = Stomp.over(socket);
  stompClient.connect({}, onConnected, onError);
}

function onConnected() {
  stompClient.subscribe("/user/queue/private-messaging", onMessageReceived);
  $(connectingInfo).hide();
}

function onError(error) {
  connectingInfo.textContent =
    "Could not connect to WebSocket server. Please refresh this page to try again!";
}

function sendMessage(event) {
  var messageContent = $(".message-input input").val();
  /* var conversationId = $("conversation id") <- something like this will be used later*/
  /* var sendToUsernames = $(".contact-profile p").html();*/

  var sendToUsernames = "Artur";
  var conversation = 1;
  var destination = "/app/conversationController";
  if (messageContent && stompClient) {
    var message = {
      senderName: username,
      conversationId: conversation,
      recipientsString: sendToUsernames,
      content: messageContent
    };
    stompClient.send(destination, {}, JSON.stringify(message));
    messageContent = "";
  }
}

$(".submit").click(function() {
  sendMessage();
});

function onMessageReceived(payload) {
  console.log("TEST on message recived");
  var message = JSON.parse(payload.body);
  newMessage(message);
}
