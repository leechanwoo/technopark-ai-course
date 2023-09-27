FROM ubuntu:rolling

RUN apt update
RUN apt install -y \
    protobuf-compiler \
    curl \ 
    ca-certificates \
    gnupg 

RUN curl -fsSL https://deb.nodesource.com/gpgkey/nodesource-repo.gpg.key | gpg --dearmor -o /etc/apt/keyrings/nodesource.gpg
RUN echo "deb [signed-by=/etc/apt/keyrings/nodesource.gpg] https://deb.nodesource.com/node_18.x nodistro main" | tee /etc/apt/sources.list.d/nodesource.list
RUN apt update && apt install nodejs -y

WORKDIR /home