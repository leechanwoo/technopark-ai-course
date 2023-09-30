
FROM ubuntu:rolling

RUN apt update && apt upgrade
RUN apt install -y \
    wget \
    curl \
    zip 


RUN curl -s "https://get.sdkman.io" | bash

SHELL ["/bin/bash", "-c"] 
RUN source "$HOME/.sdkman/bin/sdkman-init.sh" \
    && sdk install gradle 8.3 \
    && sdk install java 20.0.2-amzn \
    && sdk install springboot 3.1.3
  

WORKDIR /home/ubuntu

ENV JAVA_HOME /root/.sdkman/candidates/java/current
CMD ["/root/.sdkman/candidates/java/current/bin/java", "-jar", "build/libs/test-0.0.1-SNAPSHOT.jar"]
