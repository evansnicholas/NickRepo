﻿var holla = require('holla');
var server = require('http').createServer().listen(8080);
var rtc = holla.createServer(server,{debug:true, presence:true});

console.log('Server running on port 8080');