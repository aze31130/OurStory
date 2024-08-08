FROM archlinux:latest

LABEL maintainer="Aze"
LABEL description="Docker image used to run OurStory server"

EXPOSE 25565
WORKDIR /server

# Install java runtime
RUN pacman -Syu --noconfirm && \
    pacman -S jre-openjdk --noconfirm

ENTRYPOINT java -jar paper-1.20.6-147.jar