var mq=require("../rpc/client");
var ejs=require("ejs");
var s;
var existing_friends;
var current_user;
var friends_requesting;
var current_friends;

function login(req,res)
{
	console.log("inside login");
	var user_name= req.param("user");
	var pwd=req.param("pwd");
	var msg_payload={"username":user_name, "password":pwd};
	
	mq.make_request('login_signup_queue',msg_payload,function(err,results){
		console.log(results);
		if(err){
			throw err;
		}
		else 
		{
			if(results.code == 200){
				console.log("valid Login");
				s=results.session.username;
				current_user=results.session.username;
				console.log("s:"+s);
				res.send({"login":"Success","session":s});
			}
			else{    
				console.log("Invalid Login");
				res.send({"login":"Fail"});
			}
		}  
	});
}

function homepage(req,res)
{
	//var username = req.param('username');
	username=current_user;
	console.log("inside homepage of signin_up:"+username);
	//res.render("homepage",{username:req.session.username});
	res.render('welcome.ejs',{username:username});
	
}

function signup(req,res)
{
	console.log("inside signup");
	var user_name= req.param("email");
	var pwd=req.param("pwd");
	var fname=req.param("fname");
	var lname=req.param("lname");
	var re_email=req.param("re_email");
	var msg_payload={"username":user_name, "password":pwd, "fname":fname, "lname":lname, "oper":"signup"};
	
	mq.make_request('login_signup_queue',msg_payload,function(err,results){
		console.log(results);
		if(err){
			throw err;
		}
		else 
		{
			if(results.code == 200){
				console.log("valid Login");
				//s=results.session.username;
				current_user=fname;
				res.send({"login":"Success"});
			}
			else{    
				console.log("Invalid Login");
				res.send({"login":"Fail"});
			}
		}  
	});
}


function logout(req,res)
{
	console.log("here in logout");
	res.render("index.ejs");
	//console.log("session contains:"+s);
    //s= undefined;   
}


function find_friends(req,res)
{
	var u=req.param("user");
	console.log("user is:"+u);
	current_user=u;
	var msg_payload={"currentuser":u};
	console.log("in find_friends:"+u);
	
	mq.make_request('friends_queue',msg_payload,function(err,results){
		console.log(results);
		if(err){
			throw err;
		}
		else 
		{
			if(results.code == 200){
				console.log("from friends queue valid");
				for (var i  in results.friends)
				{ 
					console.log("friend:"+JSON.stringify(results.friends[i].username)); 
				} 
				existing_friends=results.friends;
				res.send({"login":"Success", "friends":results.friends});
			}
			else{    
				console.log("Invalid friends");
				res.send({"login":"Fail"});
			}
		}  
	});
	
}

function friends(req,res)
{
	//f=req.param("friends");	
	//console.log("**************in friends "+f);
	console.log("current:"+current_user);
	for(var i in existing_friends)
	{
		console.log(JSON.stringify(existing_friends[i].username));
	}
	res.render('friends.ejs',{"friends":existing_friends,"current":current_user});
}

function friend_request(req,res)
{
	var requestor=req.param("requestor");
	var requested=req.param("requested");
	
	console.log("requestor:"+requestor);
	console.log("requested:"+requested);
	
	var msg_payload={"requestor":requestor,"requested":requested,"oper":"friend_request"};
	console.log("payload:"+msg_payload);
	mq.make_request('friends_queue',msg_payload,function(err,results){
		console.log(results);
		if(err){
			throw err;
		}
		else 
		{
			if(results.code == 200){
				console.log("friend_requestor");
				res.send({"login":"Success"});		
			}
			else{    
				console.log("Invalid friends");
				res.send({"login":"Fail"});
			}
		}  
	});
	
}

function see_friends(req,res)
{
	var msg_payload={"current_user":current_user};
	console.log("payload of see_friends:"+msg_payload);
	mq.make_request('see_friends_queue',msg_payload,function(err,results){
		console.log(results);
		if(err){
			throw err;
		}
		else 
		{
			if(results.code == 200){
				console.log("see_friends");
				console.log("requesting friends:"+JSON.stringify(results.friends_requesting));
				friends_requesting=results.friends_requesting;
				console.log("after requesting friends:"+(friends_requesting));
				res.send({"login":"Success","friend_requests":friends_requesting});		
			}
			else{    
				console.log("Invalid friends");
				res.send({"login":"Fail"});
			}
		}  
	});
}

function show_friends(req,res)
{
	console.log("inside show_friends");

	res.render('friends_show.ejs',{"frnds":friends_requesting});

}

function friend_acccept(req,res)
{
	var msg_payload={"current_user":current_user,"oper":"accept"};
	console.log("payload of see_friends:"+msg_payload);
	mq.make_request('see_friends_queue',msg_payload,function(err,results){
		console.log(results);
		if(err){
			throw err;
		}
		else 
		{
			if(results.code == 200){
				console.log("see_friends-client");
				console.log("friends_accepted:"+JSON.stringify(results.friends_accepted));
				//friends_requesting=results.friends_requesting;
				res.send({"login":"Success"});		
			}
			else{    
				console.log("Invalid friends");
				res.send({"login":"Fail"});
			}
		}  
	});

}

function my_friends(req,res)
{
	var msg_payload={"current_user":current_user, "oper":"my_friends"};
	console.log("payload of my_friends:"+msg_payload);
	mq.make_request('see_friends_queue',msg_payload,function(err,results){
		console.log(results);
		if(err){
			throw err;
		}
		else 
		{
			if(results.code == 200){
				console.log("see_friends");
				console.log("current friends:"+JSON.stringify(results.my_friends));
				current_friends=results.my_friends;
				console.log("after requesting friends:"+JSON.stringify(current_friends));
				res.send({"login":"Success", "friends":current_friends});		
			}
			else{    
				console.log("Invalid friends");
				res.send({"login":"Fail"});
			}
		}  
	});
	
}

function show_myfriends(req,res)
{
	console.log("inside show_myfriends");
	res.render('myfriends.ejs',{"frnds":current_friends});
}

function new_friends(req,res)
{
	console.log("indside new_friends");
	res.render('newfriends.ejs', {"username":current_user});
}

function about(req,res)
{
	console.log("here in about");
	res.render("about.ejs",{"username":current_user});
}

function interest(req,res)
{
	console.log("here in interest");
	res.render("interest.ejs",{"username":current_user});
}

function groups(req,res)
{
	console.log("here in groups");
	
	res.render("groups.ejs",{"username":current_user});
	
}

function create_group(req,res)
{
	var u=req.param("user");
	var g=req.param("group_name");
	console.log("u:"+u+" g:"+g);
	var msg_payload={"current_user":u,"group_name":g, "oper":"groups"};
	console.log("payload of groups:"+msg_payload);
	mq.make_request('see_friends_queue',msg_payload,function(err,results){
		console.log(results);
		if(err){
			throw err;
		}
		else 
		{
			if(results.code == 200){
				console.log("group_response_friends");
				console.log("group created:"+JSON.stringify(results.group));
				//friends_requesting=results.friends_requesting;
				res.send({"login":"Success"});		
			}
			else{    
				console.log("Invalid friends");
				res.send({"login":"Fail"});
			}
		}  
	});
}

function show_groups(req,res)
{
	var u=req.param("user");
	var g=req.param("group_name");
	console.log("u:"+u+" g:"+g);
	var msg_payload={"current_user":u,"group_name":g, "oper":"avail_groups"};
	console.log("payload of groups:"+msg_payload);
	mq.make_request('see_friends_queue',msg_payload,function(err,results){
		console.log(results);
		if(err){
			throw err;
		}
		else 
		{
			if(results.code == 200){
				console.log("group_response_friends");
				console.log("groups available:"+JSON.stringify(results.group));
				//friends_requesting=results.friends_requesting;
				res.send({"login":"Success","groups":results.group});		
			}
			else{    
				console.log("Invalid friends");
				res.send({"login":"Fail"});
			}
		}  
	});
}

exports.login = login;
exports.homepage=homepage;
exports.signup=signup;
exports.logout=logout;
exports.find_friends=find_friends;
exports.friends=friends;
exports.friend_request=friend_request;
exports.see_friends=see_friends;
exports.show_friends=show_friends;
exports.friend_acccept=friend_acccept;
exports.my_friends=my_friends;
exports.show_myfriends=show_myfriends;
exports.new_friends=new_friends;
exports.about=about;
exports.interest=interest;
exports.groups=groups;
exports.create_group=create_group;
exports.show_groups=show_groups;