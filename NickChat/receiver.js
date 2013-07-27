var rtc = holla.createClient({debug:true, host: "localhost", port:8080});

rtc.register("ben", function(worked) {
	
	holla.createStream({video:false,audio:false},function(err, stream) {
	
    var call = rtc.call("nick");
	
		$("#message-send").click(function() {
	
			var message = $("#new-message").val();
			console.log(message);
			call.chat(message);
			
			$("#messages").append("me: " + message + "<br>");
	
		});
	});

  rtc.on("chat", function(chat) {
    
      $("#messages").append(chat.from + ": " + chat.message + "<br>");
	  
  });

});