var WebSocketClient = require('websocket').client;
var log = require('winston');
var client = new WebSocketClient();

log.add(log.transports.File, { filename: 'logs/ws_client.log' });

client.on('connectFailed', function(error) {
    console.log('Connect Error: ' + error.toString());
});

client.on('connect', function(connection) {
    log.info('WebSocket Client Connected');
    connection.on('error', function(error) {
        console.log("Connection Error: " + error.toString());
    });

    connection.on('close', function() {
        console.log('echo-protocol Connection Closed');
    });

    connection.on('message', function(message) {
        if (message.type === 'utf8') {
            startFlashLight(message.utf8Data);
            console.log("Received: '" + message.utf8Data + "'");
        }
    });

    function startFlashLight(message){
      log.info('Received message: %s', message);
      var event = JSON.parse(message);

      if(event.eventType == 'xxx') {
        //flash light for 10sec
      }

    }

    function sendNumber() {
        if (connection.connected) {
            var number = Math.round(Math.random() * 0xFFFFFF);
            connection.sendUTF(number.toString());
            setTimeout(sendNumber, 1000);
        }
    }
    sendNumber();
});

client.connect('ws://localhost:8080/', 'echo-protocol');
