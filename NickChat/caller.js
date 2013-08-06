$("document").ready(function() {

//var rtc = holla.createClient({debug:true, host: "babble.eu01.aws.af.cm"});
//var rtc = holla.createClient({debug:true, host: "localhost", port:1980});
var rtc = holla.createClient({debug:true, host: "195.35.128.146", port:8080});

rtc.on("presence", function(user){
    if (user.online) {
      $("#online-users").append(user.name + " is online. <br>");
    } else {
      $("#online-users").append(user.name + " is offline. <br>");
    }
	
  });
  

$("#connect").click(function() {

 var me = $("#me").val();
  
 $("#status").append("You are connected as: " + me + "<br>");
  
	/*rtc.register(me, function(worked) {
		holla.createStream({video:false,audio:false}, function(err, stream) {
		
		
		var call = rtc.call(toUser);
		call.addStream(stream);*/
		
		rtc.register(me);
	
	holla.createStream({video:false,audio:false}, function(err, stream) {	

	
			//Send chat message
			$("#message-send").click(function() {

				var toUser = $("#user").val();
				
				if (toUser == ""){
					alert("You have not entered a user.");
				}
				
				var call = rtc.call(toUser);
				var message = $("#new-message").val();
				console.log(message);
				call.chat(message);
				call.end();
		
				$("#messages").append("me(to " + toUser + "): " + message + "<br>");
		
	
			});
			
			//Receive chat message		
			rtc.on("chat", function(chat) {
    
				$("#messages").append(chat.from + ": " + chat.message + "<br>");
	  
			});
	});
  });
});