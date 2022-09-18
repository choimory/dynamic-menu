#!/bin/bash
APP_NAME="dynamic-menu"
ACTIVE_PROFILE="default"
VERSION="latest"
INNER_PORT="9090"
OTUER_PORT="9090"
DOCKER_NETWORK="bridge"

docker stop ${APP_NAME}
docker rm ${APP_NAME}
docker rmi ${APP_NAME}:${VERSION}

gradle build --exclude-task test

docker build \
-f ./Dockerfile \
-t ${APP_NAME}:${VERSION} .

docker run \
-d \
-p ${OTUER_PORT}:${INNER_PORT} \
-e PROFILE=${ACTIVE_PROFILE} \
--network ${DOCKER_NETWORK} \
--name ${APP_NAME} ${APP_NAME}:${VERSION}