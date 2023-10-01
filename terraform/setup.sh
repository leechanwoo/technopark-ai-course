#!/bin/bash

sudo yum update -y
sudo yum install yum-utils -y 
sudo yum-config-manager --add-repo https://download.docker.com/linux/centos/docker-ce.repo
sudo yum update -y
sudo yum install git -y
sudo yum install docker -y 
sudo systemctl start docker
sudo mkdir -p /usr/local/lib/docker/cli-plugins/
sudo curl -SL https://github.com/docker/compose/releases/download/v2.20.3/docker-compose-linux-x86_64 -o /usr/local/lib/docker/cli-plugins/docker-compose
sudo chmod +x /usr/local/lib/docker/cli-plugins/docker-compose
sudo git clone https://github.com/leechanwoo/technopark-ai-course.git
cd technopark-ai-course
sudo docker compose up -d --build
~