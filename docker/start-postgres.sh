#!/usr/bin/env bash

CONTAINER_NAME="play-hateoas-postgres"
APP_NAME="play-hateoas"
DB_USER=${APP_NAME}
DB_PASSWORD=${APP_NAME}

CONTAINER_ID="$(docker ps -a -q -f name=${CONTAINER_NAME})"

if [ -n "$CONTAINER_ID" ]; then
    docker start ${CONTAINER_ID}
    else
    docker run --name ${CONTAINER_NAME} \
            -e POSTGRES_USER=${DB_USER} \
            -e POSTGRES_PASSWORD=${DB_PASSWORD} \
            -d \
            -p 5432:5432 \
            postgres:latest
fi
