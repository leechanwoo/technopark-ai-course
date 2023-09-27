
FROM ubuntu:rolling

RUN apt update && apt upgrade
RUN apt install -y \
    wget \
    curl \
    zip \
    git \
    protobuf-compiler 


RUN curl -s "https://get.sdkman.io" | bash

SHELL ["/bin/bash", "-c"] 
RUN source "$HOME/.sdkman/bin/sdkman-init.sh" \
    && sdk install gradle 8.3 \
    && sdk install java 20.0.2-amzn \
  

WORKDIR /home/ubuntu