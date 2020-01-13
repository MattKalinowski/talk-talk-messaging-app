/*
The code in this file is responsible for connecting to the WebSocket endpoint and sending & receiving messages.
*/

"use strict";

var messageInput = $(".message-input input");
var messageList = $("#message-list");
var connectingInfo = $("#connecting-info");

var stompClient = null;
var username = null;

/*
This function uses SockJS and stomp client to connect to the '/ws' endpoint that is configured in Spring Boot.

Upon successful connection, the client subscribes to /topic/public destination
and tells the user’s name to the server by sending a message to the /app/chat.addUser destination.
*/
function connect() {
  username = sessionStorage.getItem("username");
  var socket = new SockJS("/ws");
  stompClient = Stomp.over(socket);
  stompClient.connect({}, onConnected, onError);
}

/*
The stompClient.subscribe() function takes a callback method which is called whenever a message arrives on the subscribed topic.
*/
function onConnected() {
  // Subscribe to the Public Topic
  stompClient.subscribe("/topic/public", onMessageReceived);
  $(connectingInfo).hide();
}

function onError(error) {
  connectingInfo.textContent =
    "Could not connect to WebSocket server. Please refresh this page to try again!";
  connectingInfo.style.color = "red";
}

/*
Sending messages.
*/
function sendMessage(event) {
  var messageContent = messageInput.value.trim();
  if (messageContent && stompClient) {
    var chatMessage = {
      sender: username,
      content: messageInput.value,
      type: "CHAT"
    };
    stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
    messageInput.value = "";
  }
  event.preventDefault();
}

/*
Displaying sent and received messages.
*/
function onMessageReceived(payload) {
  var message = JSON.parse(payload.body);

  var messageElement = document.createElement("li");
  $(messageElement).addClass("replies");

  var textElement = document.createElement("p");
  var messageText = document.createTextNode(message.content);
  textElement.appendChild(messageText);

  messageElement.appendChild(textElement);
  messageList.appendChild(messageElement);
  messageList.scrollTop = messageList.scrollHeight;
}
