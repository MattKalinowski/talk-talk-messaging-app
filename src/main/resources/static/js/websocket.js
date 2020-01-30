"use strict";

import { newMessage, setUsername } from "./animation.js";

var connectingInfo = $("#connecting-info");

var stompClient = null;
var username = null;

window.onload = function connect() {
  username = sessionStorage.getItem("username");
  setUsername(username);
  var socket = new SockJS("/talk-talk");
  stompClient = Stomp.over(socket);
  stompClient.connect({}, onConnected, onError);
};

function onConnected() {
  stompClient.subscribe("/user/queue/private-chat", onMessageReceived);
  $(connectingInfo).hide();
}

function onError(error) {
  connectingInfo.textContent =
    "Could not connect to WebSocket server. Please refresh this page to try again!";
}

function sendMessage(event) {
  var messageContent = $(".message-input input").val();
  var sendToUsername = $(".contact-profile p").html();
  /* var destination = "/app/chat.sendMessage." + sendToUsername; */
  /* var destination = "/user/" + sendToUsername + "/queue/private-chat"; */
  var destination = "/app/chat";
  if (messageContent && stompClient) {
    var chatMessage = {
      sender: username,
      recipient: sendToUsername,
      content: messageContent
    };
    stompClient.send(destination, {}, JSON.stringify(chatMessage));
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
