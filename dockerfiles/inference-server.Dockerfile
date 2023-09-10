
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
    && sdk install java 20.0.2-amzn 

WORKDIR /root/.protoc
WORKDIR /root

# RUN curl -L -o /root/.protoc/plugins/protoc-gen-grpc-java https://repo.maven.apache.org/maven2/io/grpc/protoc-gen-grpc-java/1.41.0/protoc-gen-grpc-java-1.41.0-linux-x86_64
# RUN chmod +x /root/.protoc/plugins/protoc-gen-grpc-java