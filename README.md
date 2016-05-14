# Installation instructions
Before you can run npm install you need to first install wiringPi.  

```
pi@raspberrypi ~ $ git clone git://git.drogon.net/wiringPi
...
pi@raspberrypi ~ $ cd wiringPi/wiringPi
pi@raspberrypi ~/wiringPi/wiringPi $ sudo su
...
root@raspberrypi:/home/pi/wiringPi/wiringPi# ./build
```

# Starting
For testing purposes I have also added a websocket server. You can start this with
```
npm ws_server.js
```

A Server will start on port `8080`

Now you can start a client with

```
npm ws_client.js
```
