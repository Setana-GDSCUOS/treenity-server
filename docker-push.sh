#!/bin/bash

TAG="latest"

docker build -t rkckr1/treenity-dev:$TAG .

docker push rkckr1/treenity-dev:$TAG