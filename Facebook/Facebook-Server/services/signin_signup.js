var mongo=require("./mongoconnect");
var mongourl="mongodb://localhost:27017/user";
var bcrypt = require('bcryptjs');
var hash;

function signup(msg,callback)
{
	var res={};
	msg.session={};
	var uname=msg.username;
	var pass=msg.password;
	var fname=msg.fname;
	var lname=msg.lname;
	
	
	var salt = bcrypt.genSaltSync(10);
	hash = bcrypt.hashSync(pass, salt);
	
	mongo.connect(mongourl,function(){
			console.log('Connected to mongo at: ' + mongourl);
			var coll = mongo.collection('user');
			coll.insert({username: uname, password:hash, fname:fname, lname:lname},function(err,user){
				if(user)
				{
					msg.session.username= user.username;
					console.log(msg.session +" is the session");
					res.code="200";
					res.value="Successfull login";
					res.session=msg.sessionr;
				}
				else
				{
					res.code="401";
					res.value="Failed login";
				}
			});
		});
		callback(null, res);
		mongo.close();
}


function signin(msg,callback)
{
	var res={};
	msg.session={};
	var uname=msg.username;
	var pass=msg.password;
	//hash = bcrypt.hashSync(pass, salt);
	console.log("encrypt");
	mongo.connect(mongourl,function(){
			console.log('Connected to mongo at: ' + mongourl);
			var coll = mongo.collection('user');
			coll.findOne({username: uname},function(err,user){
				if(user)
				{
					bcrypt.compare(pass, user.password,function(err, res1) {
						if(res1)
							{
							console.log("encrypted password:"+user.password);
							msg.session.username= user.fname;
							console.log(msg.session.username +" is the session");
							res.code="200";
							res.value="Successfull login";
							res.session=null;
							res.session=msg.session;
							}
						else
							console.log("error in enc");
					
					})
				}
				else
				{
					res.code="401";
					res.value="Failed login";
				}
				callback(null, res);
			});
		});
		mongo.close();
}

function find_friends(msg,callback)
{
	var res={};
//	msg.session={};
	var uname=msg.username;
	var pass=msg.password;
	mongo.connect(mongourl,function(){
			console.log('Connected to mongo at: ' + mongourl);
			var coll = mongo.collection('user');
		
			coll.find().toArray(function(err,result){
				if(result)
				{
					//msg.session.username= user.username;
					//console.log(msg.session.username +" is the session");
					res.code="200";
					res.value="Successfull login";
					res.friends=result;
				}
				else
				{
					res.code="401";
					res.value="Failed login";
				}
				callback(null, res);
			});
		});
		
		mongo.close();
}

function friend_request(msg,callback)
{
	var res={};
	var requestor=msg.requestor;
	var requested=msg.requested;
	mongo.connect(mongourl,function(){
			console.log("friend_request");
			var coll = mongo.collection('friends');
			coll.insert({requestor: requestor, requested:requested, status:"pending"},function(err,result){
				if(result)
				{
					res.code="200";
					res.value="Successfull login";
					//res.friends=result;
				}
				else
				{
					res.code="401";
					res.value="Failed login";
				}
				callback(null, res);
			});
		});
		mongo.close();	
}


function see_friends(msg,callback)
{
	var res={};
	var current_user=msg.current_user;
	mongo.connect(mongourl,function(){
		console.log("see_request");
		var coll = mongo.collection('friends');
	
		//coll.find({requested:current_user, status:"pending"}).toArray(function(err,result){
		coll.find({$and:[{status:"pending"},{$or:[{requested:current_user},{requestor:current_user } ] } ]}).toArray(function(err,result){
			if(result)
			{
				res.code="200";
				res.value="Successfull login";
				res.friends_requesting=result;
			}
			else
			{
				res.code="401";
				res.value="Failed login";
			}
			callback(null, res);
		});
	});
	mongo.close();
}

function accept_friend(msg,callback)
{
	var res={};
	var current_user=msg.current_user;
	mongo.connect(mongourl,function(){
		console.log("accept_request");
		var coll = mongo.collection('friends');
	//	coll.find({ $or: [ { requestor:current_user }, { requested:current_user } ] },function(err,result){
		//coll.find().toArray(function(err,result){
		console.log("userserver:"+current_user);
		coll.updateOne({requested:current_user},{$set:{ "status": "accepted" }},function(err,result){
			if(result)
			{
				res.code="200";
				res.value="Successfull login";
				res.friends_accepted=result;
			}
			else
			{
				res.code="401";
				res.value="Failed login";
			}
			callback(null, res);
		});
	});
	mongo.close();

}

function my_friend(msg,callback)
{
	var res={};
	var current_user=msg.current_user;
	mongo.connect(mongourl,function(){
		console.log("my_friends");
		var coll = mongo.collection('friends');
		coll.find({$and:[{status:"accepted"},{$or:[{requested:current_user},{requestor:current_user } ] } ]} ).toArray(function(err,result){
			if(result)
			{
				res.code="200";
				res.value="Successfull login";
				res.my_friends=result;
			}
			else
			{
				res.code="401";
				res.value="Failed login";
			}
			callback(null, res);
		});
	});
	mongo.close();
}

function create_group(msg,callback)
{
	var res={};
	var user=msg.current_user;
	var group=msg.group_name;
	mongo.connect(mongourl,function(){
			console.log("create_grpouup");
			var coll = mongo.collection('groups');
			coll.insert({group_name: group, owner:user},function(err,result){
				if(result)
				{
					res.code="200";
					res.value="Successfull login";
					res.group=result;
				}
				else
				{
					res.code="401";
					res.value="Failed login";
				}
				callback(null, res);
			});
		});
		mongo.close();
}

function avail_groups(msg,callback)
{
	var res={};
	var user=msg.current_user;
	var group=msg.group_name;
	mongo.connect(mongourl,function(){
			console.log("avail_grpouup");
			var coll = mongo.collection('groups');
			coll.find().toArray(function(err,result){
				if(result)
				{
					res.code="200";
					res.value="Successfull login";
					res.group=result;
				}
				else
				{
					res.code="401";
					res.value="Failed login";
				}
				callback(null, res);
			});
		});
		mongo.close();
	
}


exports.find_friends=find_friends;
exports.signin=signin;
exports.signup=signup;
exports.friend_request=friend_request;
exports.see_friends=see_friends;
exports.accept_friend=accept_friend;
exports.my_friend=my_friend;
exports.create_group=create_group;
exports.avail_groups=avail_groups;