
/**
 * Module dependencies.
 */

var express = require('express')
  , routes = require('./routes')
  , sign=require('./routes/sign_in_up')
  , http = require('http')
  , path = require('path');

var app = express();

// all environments
app.set('port', process.env.PORT || 2000);
app.set('views', __dirname + '/views');
app.set('view engine', 'ejs');
app.use(express.favicon());
app.use(express.logger('dev'));
app.use(express.bodyParser());
app.use(express.methodOverride());
app.use(app.router);
app.use(express.static(path.join(__dirname, 'public')));

// development only
if ('development' == app.get('env')) {
  app.use(express.errorHandler());
}

app.get('/', routes.index);
//app.get('/homepage/:username',sign.homepage);
app.get('/friends',sign.friends);
app.get('/homepage',sign.homepage);
app.get('/show_friends',sign.show_friends);
app.get('/show_myfriends',sign.show_myfriends);
app.get('/new_friends',sign.new_friends);
app.get('/about',sign.about);
app.get('/interest',sign.interest);
app.get('/groups', sign.groups);


app.post('/login',sign.login);
app.post('/signup', sign.signup);
app.post('/logout',sign.logout);
app.post('/find_friends',sign.find_friends);
app.post('/friend_request',sign.friend_request);
app.post('/see_friends', sign.see_friends);
app.post('/friend_acccept',sign.friend_acccept);
app.post('/my_friends', sign.my_friends);
app.post('/create_group',sign.create_group);
app.post('/show_groups',sign.show_groups);


http.createServer(app).listen(app.get('port'), function(){
  console.log('Express server listening on port ' + app.get('port'));
});
