const production = false;
const express = require('express');
const app = express();
const fs = require( 'fs' );
const ini = require('ini');
const http = production ? require('https') : require('http');
const { Server } = require("socket.io");
const server = production
	? http.createServer({
	key: fs.readFileSync('/etc/letsencrypt/live/agathaserver.ddns.net/privkey.pem'),
	cert: fs.readFileSync('/etc/letsencrypt/live/agathaserver.ddns.net/fullchain.pem')},
	app)
	: http.createServer(app)

const io = new Server(server, {
  cors: {
    origin: "*",
    methods: ["GET", "POST"]
  }
});
const socketioJwt   = require('socketio-jwt');

const port = 82;

const config = ini.parse(fs.readFileSync(production ? '/var/html/www/config/config.ini' : '../../config.ini', 'utf-8'));

//var playersInfo = {};

/*io.use(socketioJwt.authorize({
            secret: config.token.key,
            handshake: true
        }));*/

io.on('connection', (socket) => {
	console.log("User connected: " + socket.id);

	socket.on("connect_error", (err) => {
		console.log(`Connection error: ${err.message}`);
	  });

	socket.on("disconnect", ()=>{
		console.log("User disconnected: " + socket.id);
	});
  
	socket.on('DuelLobbyAck', (callback) => {
		console.log('DuelLobbyAck');
		var lobby = new Object();
		lobby.enDueloCon = 0;
		lobby.retosDuelo = [];
		lobby.amigosRanked = [];
		lobby.amigosAmistoso = [];
		callback(lobby);
	});
	
});

server.listen(port, () => {
	console.log("Listening on port " + port);
});