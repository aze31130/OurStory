package ourstory;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import ourstory.commands.*;
import ourstory.events.*;
import ourstory.recipes.*;

public class Main extends JavaPlugin {

	public static final String namespace = "ourstory";

	@Override
	public void onEnable() {
		Bukkit.getConsoleSender().sendMessage("Loading Ourstory...");

		// Registers all events
		Bukkit.getPluginManager().registerEvents(new onEntityDeath(), this);
		Bukkit.getPluginManager().registerEvents(new onEntityHit(), this);
		// Bukkit.getPluginManager().registerEvents(new onFireworkUse(), this);
		Bukkit.getPluginManager().registerEvents(new onItemConsume(), this);
		Bukkit.getPluginManager().registerEvents(new onPlayerDeath(), this);
		Bukkit.getPluginManager().registerEvents(new onPlayerInteract(), this);
		Bukkit.getPluginManager().registerEvents(new onPlayerJoin(), this);
		Bukkit.getPluginManager().registerEvents(new onPlayerSit(), this);
		Bukkit.getPluginManager().registerEvents(new onTeleport(), this);
		// Bukkit.getPluginManager().registerEvents(new onTridentUse(), this);
		Bukkit.getPluginManager().registerEvents(new onXpPickup(), this);

		// Registers all commands
		var manager = this.getLifecycleManager();
		manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
			final Commands commands = event.registrar();

			commands.register("boss", "TODO", new Boss());
			commands.register("split", "Splits the enchants on your books", new Split());
			commands.register("rankup", "Increases your rank", new RankUp());
			commands.register("count", "Count items in your inventory", new Count());
		});

		// Registers custom recipe
		CraftingTable.createCustomRecipes();
		StoneCutter.createCustomRecipes();
		Furnace.createCustomRecipes();

		// this.getConfig().addDefault("messages.nopermission", "You do not have the permission to perform
		// this command");
		// this.getConfig().options().copyDefaults(true);
		// this.saveConfig();
		// String name = this.getConfig().getString("player-name");

		// Bukkit.getConsoleSender().sendMessage(name);


		// Map<String, Object> def =
		// this.getConfig().getConfigurationSection("deathMessages").getValues(false);

		// for (Entry<String, Object> v : def.entrySet()) {
		// Bukkit.getConsoleSender().sendMessage(v.getKey() + " : " + v.getValue());
		// }
	}

	@Override
	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage("Disabling Ourstory...");

		// Save inventory, player state, anything the plugin is manipulating
	}
}
