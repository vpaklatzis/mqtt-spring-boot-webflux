# MQTT Consumer Application

## Docker Setup

### Build a local docker image

`docker build -t mqtt-consumer:0.0.1 .`

## Run the docker image in a container

`docker run -p 8081:8081 -d mqtt-consumer:0.0.1`