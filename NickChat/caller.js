$("document").ready(function() {

var rtc = holla.createClient({debug:true, host: "localhost", port:8080});

rtc.on("presence", function(user){
    if (user.online) {
      console.log(user.name + " is online.");
    } else {
      console.log(user.name + " is offline.");
    }
  });
  

$("#connect").click(function() {

 var me = $("#me").val();
  
 $("#messages").append("You are connected as: " + me + "<br>");
  
	/*rtc.register(me, function(worked) {
		holla.createStream({video:false,audio:false}, function(err, stream) {
		
		
		var call = rtc.call(toUser);
		call.addStream(stream);*/
		
		rtc.register(me);
	
	holla.createStream({video:false,audio:false}, function(err, stream) {	
		
		var firstCall = false;
		
		var toUser = $("#user").val();
		var call = rtc.call(toUser);
		
	
		$("#user").change(function() {
		
			toUser = $("#user").val();
			call.end();
			call = rtc.call(toUser);
		
		
			$("#message-send").click(function() {
				var message = $("#new-message").val();
				//console.log(message);
				call.chat(message);
		
				$("#messages").append("me(to " + toUser + "): " + message + "<br>");
		
	
			});
	
		});
		
		rtc.on("chat", function(chat) {
    
			$("#messages").append(chat.from + ": " + chat.message + "<br>");
	  
		});
	});
  });
});