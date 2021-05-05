#!/bin/bash

USERNAME=$1
HOST=$2

scp environment/prod.env "$USERNAME"@"$HOST":/home/$USERNAME/prod.env

ssh "$USERNAME"@"$HOST" "
docker pull donkeyhott/ectm-api:latest
docker stop ectm-api
docker rm ectm-api
docker run -d -p 8080:8080 --name ectm-api --env-file ./prod.env donkeyhott/ectm-api
"

echo 'done'