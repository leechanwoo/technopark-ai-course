#!/bin/bash

cd /home/ec2-user
# sudo yum update -y
# sudo yum install yum-utils -y 
# sudo yum-config-manager --add-repo https://download.docker.com/linux/centos/docker-ce.repo
# sudo yum update -y
# sudo yum install git -y
# sudo yum install docker -y 
# sudo yum install docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin

sudo mkdir -p /usr/local/lib/docker/cli-plugins/
sudo curl -SL https://github.com/docker/compose/releases/download/v2.20.3/docker-compose-linux-x86_64 -o /usr/local/lib/docker/cli-plugins/docker-compose
sudo chmod +x /usr/local/lib/docker/cli-plugins/docker-compose

#sudo curl -s -L https://nvidia.github.io/libnvidia-container/stable/rpm/nvidia-container-toolkit.repo | \
#  sudo tee /etc/yum.repos.d/nvidia-container-toolkit.repo
#sudo yum install -y nvidia-container-toolkit
# sudo nvidia-ctk runtime configure --runtime=docker

# sudo systemctl start docker

sudo git clone -b gpu https://github.com/leechanwoo/technopark-ai-course.git
sudo docker compose -f $(pwd)/technopark-ai-course/docker-compose-deploy.yaml up -d 
