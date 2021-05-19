#!/bin/bash

ENV_FILE="environment/$1.env"

set -e

export $(cat "$ENV_FILE" | xargs)

cd ..

mvn spring-boot:build-image -DskipTests