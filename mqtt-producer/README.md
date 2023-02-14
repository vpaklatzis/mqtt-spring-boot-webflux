# MQTT Producer Application

## Docker Setup

### Build a local docker image

`docker build -t mqtt-producer:0.0.1 .`

## Run the docker image in a container

`docker run -p 8080:8080 -d mqtt-producer:0.0.1`