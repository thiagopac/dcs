FROM maven:3.6-openjdk-17-slim

RUN apt update -y
RUN apt install -y tzdata
ENV TZ=America/Sao_Paulo

RUN echo "alias ll='ls -alF'" >> /etc/bash.bashrc

# ARGS
ARG USERNAME=prodaub
ARG USER_ID=1000

RUN adduser -u $USER_ID  $USERNAME

WORKDIR /opt

ENV M2_REPO /m
RUN mkdir $M2_REPO

RUN mkdir /c
RUN chown -R $USERNAME:$USERNAME /c /opt
RUN ln -s $M2_REPO ~/.m2
RUN chown -R $USERNAME:$USERNAME $M2_REPO

USER $USERNAME

WORKDIR /c