$(".messages").animate({ scrollTop: $(document).height() }, "fast");

$("#profile-img").click(function() {
  $("#status-options").toggleClass("active");
});

$(".expand-button").click(function() {
  $("#profile").toggleClass("expanded");
  $("#contacts").toggleClass("expanded");
});

$("#status-options ul li").click(function() {
  $("#profile-img").removeClass();
  $("#status-online").removeClass("active");
  $("#status-away").removeClass("active");
  $("#status-busy").removeClass("active");
  $("#status-offline").removeClass("active");
  $(this).addClass("active");

  if ($("#status-online").hasClass("active")) {
    $("#profile-img").addClass("online");
  } else if ($("#status-away").hasClass("active")) {
    $("#profile-img").addClass("away");
  } else if ($("#status-busy").hasClass("active")) {
    $("#profile-img").addClass("busy");
  } else if ($("#status-offline").hasClass("active")) {
    $("#profile-img").addClass("offline");
  } else {
    $("#profile-img").removeClass();
  }

  $("#status-options").removeClass("active");
});

export function newMessage(message) {
  var messageSender = message.sender;
  var messageContent = message.content;
  var liClass = null;

  if ($.trim(messageContent) == "") {
    return false;
  }
  if (messageSender == sessionStorage.getItem("username")) {
    liClass = "sent";
  } else {
    liClass = "received";
  }
  $(
    '<li class="' +
      liClass +
      '"><img src="http://emilcarlsson.se/assets/mikeross.png" alt="" /><p>' +
      messageContent +
      "</p></li>"
  ).appendTo($(".messages ul"));
  $(".message-input input").val(null);
  $(".contact.active .preview").html("<span>You: </span>" + messageContent);
  $(".messages").animate({ scrollTop: $(document).height() }, "fast");
}

$(window).on("keydown", function(e) {
  if (e.which == 13) {
    newMessage();
    return false;
  }
});

export function setUsername(username) {
  $("#username").html(username);
}
