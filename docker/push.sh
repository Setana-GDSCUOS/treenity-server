#!/bin/bash
TAG="latest"
docker rmi "$1"/treenity-dev:$TAG
docker build -t "$1"/treenity-dev:$TAG -f docker/Dockerfile .
docker push "$1"/treenity-dev:$TAG
