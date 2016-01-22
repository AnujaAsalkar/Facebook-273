var amqp = require('amqp')
, util = require('util');

var login = require('./services/signin_signup');

var express = require('express');
var http=require('http');
var mongoSessionConnectURL = "mongodb://localhost:27017/sessions";
var expressSession = require("express-session");
var mongoStore = require("connect-mongo")(expressSession);
var mongo= require('./services/mongoconnect');


var app = express();

app.set('port', process.env.PORT || 4100);
app.set('views', __dirname + '/views');
app.set('view engine', 'ejs');
app.use(express.favicon());
app.use(express.logger('dev'));
app.use(express.bodyParser());
app.use(express.methodOverride());
app.use(app.router);
//app.use(express.static(path.join(__dirname, 'public')));

// development only
if ('development' == app.get('env')) {
  app.use(express.errorHandler());
}


app.use(expressSession({
	secret: 'cmpe273_teststring',
	resave: true,  //don't save session if unmodified
	saveUninitialized: true,	// don't create session until something stored
	duration: 30 * 60 * 1000,    
	activeDuration: 5 * 60 * 1000,
	store: new mongoStore({
		url: mongoSessionConnectURL
	})
}));



 var cnn=amqp.createConnection({host:'127.0.0.1'});
 
 cnn.on('ready', function(){
	 console.log("listening on login_signup_queue");
	 
	 cnn.queue('login_signup_queue',function(q){ 
		 q.subscribe(function(message,header,deliveryInfo, m){
			 util.log(util.format(deliveryInfo.routingKey,message));
			 util.log("Message:"+JSON.stringify(message));
			 util.log("DeliveryInfo: "+JSON.stringify(deliveryInfo));
			 if(message.oper==="signup")
			 {
				 login.signup(message, function(err,res){
						//return index sent
					 console.log("inside login.signup");
						cnn.publish(m.replyTo, res, {
							contentType:'application/json',
							contentEncoding:'utf-8',
							correlationId:m.correlationId
						});
				 });
			 }
			 if(!(message.oper==="signup"))
			{
			 login.signin(message, function(err,res){
					//return index sent
				 console.log("inside login.signin");
					cnn.publish(m.replyTo, res, {
						contentType:'application/json',
						contentEncoding:'utf-8',
						correlationId:m.correlationId
					});
				});
			}
			 
		 });
	 });
	 
	 
	 cnn.queue('friends_queue',function(q){ 
		 q.subscribe(function(message,header,deliveryInfo, m){
			 util.log(util.format(deliveryInfo.routingKey,message));
			 util.log("Message:"+JSON.stringify(message));
			 util.log("DeliveryInfo: "+JSON.stringify(deliveryInfo));
			 if(message.oper==="friend_request")
			 {
				 login.friend_request(message, function(err,res){
						//return index sent
					 console.log("inside login.friend_request");
						cnn.publish(m.replyTo, res, {
							contentType:'application/json',
							contentEncoding:'utf-8',
							correlationId:m.correlationId
						});
				 });
			 }
			 
			else
			{
				 	login.find_friends(message, function(err,res){
						//return index sent
					 console.log("inside login.find_friends");
						cnn.publish(m.replyTo, res, {
							contentType:'application/json',
							contentEncoding:'utf-8',
							correlationId:m.correlationId
				});
			});	
			}
		 });
	 }); 
	 
	 cnn.queue('see_friends_queue',function(q){ 
		 q.subscribe(function(message,header,deliveryInfo, m){
			 util.log(util.format(deliveryInfo.routingKey,message));
			 util.log("Message:"+JSON.stringify(message));
			 util.log("DeliveryInfo: "+JSON.stringify(deliveryInfo));
		if(message.oper==="accept")
			{
			 login.accept_friend(message, function(err,res){
				 console.log("inside login.accept_friends");
					cnn.publish(m.replyTo, res, {
						contentType:'application/json',
						contentEncoding:'utf-8',
						correlationId:m.correlationId
					});
			 });
			}
		if(message.oper==="my_friends")
		{
			 login.my_friend(message, function(err,res){
				 console.log("inside login.my_friends");
					cnn.publish(m.replyTo, res, {
						contentType:'application/json',
						contentEncoding:'utf-8',
						correlationId:m.correlationId
					});
			 });
			
		}
		if(message.oper==="groups")
		{
			 login.create_group(message, function(err,res){
				 console.log("inside login.groups");
					cnn.publish(m.replyTo, res, {
						contentType:'application/json',
						contentEncoding:'utf-8',
						correlationId:m.correlationId
					});
			 });
			
		}
		if(message.oper==="avail_groups")
		{
			login.avail_groups(message, function(err,res){
				 console.log("inside login.avail");
					cnn.publish(m.replyTo, res, {
						contentType:'application/json',
						contentEncoding:'utf-8',
						correlationId:m.correlationId
					});
			 });
		}
		else
			{
			 login.see_friends(message, function(err,res){
				 console.log("inside login.see_friends");
					cnn.publish(m.replyTo, res, {
						contentType:'application/json',
						contentEncoding:'utf-8',
						correlationId:m.correlationId
					});
			 });
			}
		 });
	 });
 });
 
 
 
mongo.connect(mongoSessionConnectURL, function(){
		console.log('Connected to mongo at: ' + mongoSessionConnectURL);
		http.createServer(app).listen(app.get('port'), function(){
			console.log('Express server listening on port ' + app.get('port'));
		});  
	});