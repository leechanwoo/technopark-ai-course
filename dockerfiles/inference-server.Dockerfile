
FROM ubuntu:rolling

RUN apt update && apt upgrade -y 
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

ENV JAVA_HOME /root/.sdkman/candidates/java/current

ENV PATH "$PATH:/root/.sdkman/candidates/gradle/current/bin"

ENV RESOURCES_PATH /home/ubuntu/resources

RUN mkdir -p ${RESOURCES_PATH}

COPY inference-server/app/src/main/resources/ ${RESOURCES_PATH}


CMD ["gradle", "run"]