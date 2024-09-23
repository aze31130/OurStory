# Ourstory, a minecraft plugin

Ourstory is a private minecraft server between long time friends. This plugin aims to provide a vanilla+ experience by adding challenges trough custom advancements, bosses and enchantments.

## Docker image

The server runs in a docker container using [itzg's image](https://github.com/itzg/docker-minecraft-server).

We use the latest release of [Paper](https://papermc.io/), an ultra optimized version of minecraft server that fixes multiple bugs.

Bellow, the `docker-compose.yml` file used to run the server:

```
services:
  minecraft:
    image: docker.io/itzg/minecraft-server
    container_name: minecraft
    tty: true
    stdin_open: true
    ports:
      - 25565:25565
      - 8100:8100
    environment:
      UID: "0"
      GID: "0"
      EULA: "TRUE"
      TYPE: "PAPER"
      VERSION: "1.21"
      MEMORY: "10G"
      INIT_MEMORY: "2G"
      GUI: "FALSE"
      ENABLE_RCON: "FALSE"
      ENABLE_COMMAND_BLOCK: "FALSE"
      FORCE_GAMEMODE: "TRUE"
      MODE: "survival"
      VIEW_DISTANCE: "16"
    volumes:
      - ./Server:/data:Z
    restart: always

networks:
  grafana_collector:
    external: true
```

## Gameplay features

TODO
- Boss
- Items Custom
- Recipes / Knowledge Books


announceAdvancements true
doLimitedCrafting true
doInsomnia false
playersSleepingPercentage 10


## How boss system works

We simply use loot tables

/summon zombie ~ ~ ~ {DeathLootTable:"ourstory:test",Health:20000,Attributes:[{Name:"generic.max_health",Base:20000f}]}

# spigot.yml
  attribute:
    maxHealth:
      max: 2000000.0