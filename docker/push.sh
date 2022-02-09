#!/bin/bash
TAG="latest"
docker rmi rkckr1/treenity-dev:$TAG
docker build -t rkckr1/treenity-dev:$TAG -f docker/Dockerfile .
docker push rkckr1/treenity-dev:$TAG
