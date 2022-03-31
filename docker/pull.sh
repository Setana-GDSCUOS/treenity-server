#!/bin/bash
cd ./docker
sudo docker-compose -f docker-compose.yml -f docker-compose.prod.yml down
sudo docker rmi "$1"/treenity-dev:latest
sudo docker-compose -f docker-compose.yml -f docker-compose.prod.yml up -d
