# Ourstory, a minecraft plugin

Ourstory is a private minecraft server between long time friends. This plugin aims to provide a vanilla+ experience by adding challenges trough custom advancements, bosses and enchantments.

## Docker image

The server runs in a docker container using [itzg's image](https://github.com/itzg/docker-minecraft-server).

We use the latest release of [Paper](https://papermc.io/), an ultra optimized version of minecraft server that fixes multiple bugs.

## Gameplay features

Custom Enchants
  - Phoenix (1 => 10)
  - Final Damage (1 => 10)
  - Leech (1 => 10)
  - XP Hunter (1 => 1)

Boss
  - TODO

Custom Crafts
  - TODO


## How boss system works

We simply use loot tables

/summon zombie ~ ~ ~ {DeathLootTable:"ourstory:test",Health:20000,Attributes:[{Name:"generic.max_health",Base:20000f}]}

# spigot.yml
  attribute:
    maxHealth:
      max: 2000000.0