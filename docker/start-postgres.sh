#!/usr/bin/env bash

CONTAINER_NAME="play-hateoas-postgres"
APP_NAME="play-hateoas"
DB_USER=${APP_NAME}
DB_PASSWORD=${APP_NAME}

CONTAINER_ID="$(docker ps -a -q -f name=${CONTAINER_NAME})"

if [ -n "$CONTAINER_ID" ]; then
    echo "Starting container [name: ${CONTAINER_NAME}, id: $CONTAINER_ID]"
    docker start ${CONTAINER_ID}
    else
    echo "Creating a new container [${CONTAINER_NAME}]"
    docker run --name ${CONTAINER_NAME} \
            -e POSTGRES_USER=${DB_USER} \
            -e POSTGRES_PASSWORD=${DB_PASSWORD} \
            -p 5432:5432 \
            -d \
            postgres:alpine
fi
