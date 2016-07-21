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

#Pi Codes

Raspberry Pi 433mhz

Different GPIO numbering in WiringPi
https://projects.drogon.net/raspberry-pi/wiringpi/pins/

Frequencies
A on = 1361
A off = 1364

B on = 4433
B off = 4436
