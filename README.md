# Ourstory, a minecraft plugin

Ourstory is a private minecraft server between long time friends. This plugin aims to provide a vanilla+ experience by adding challenges through custom advancements, bosses and enchantments.

## All gameplay features

All plugin content is documented on the following pages:

* [Custom Enchants](./docs/enchants.md)
* [Custom Potions](./docs/potions.md)
* [Bosses](./docs/bosses.md)
* [Commands](./docs/commands.md)
* [Game mechanics](./docs/mechanics.md)

## Dependencies

[Vault](https://www.spigotmc.org/resources/vault.34315/)

## Disclaimer
This is not an officially supported or endorsed project by any organization and it is solely a personal effort.

## Contributing
### Debugging with VSCode
The configuration located at `.vscode/launch.json` will attach to the running instance of the server.
You can either start it manually with
```sh
java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005 -jar paper.jar nogui
```
or run the `build.sh` script.
