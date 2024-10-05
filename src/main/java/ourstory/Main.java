package ourstory;

import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import ourstory.commands.*;
import ourstory.events.*;
import ourstory.recipes.*;

public class Main extends JavaPlugin {

	public static final String namespace = "ourstory";
	public static List<String> deathMessages;
	public static List<String> tipMessages;

	@Override
	public void onEnable() {
		Bukkit.getConsoleSender().sendMessage("Loading Ourstory...");

		// Load custom messages
		deathMessages = getConfig().getStringList("deathMessages");
		tipMessages = getConfig().getStringList("tipMessages");

		// Register task for running the periodic tip broadcast
		new BukkitRunnable() {
			@Override
			public void run() {
				onPlayerTips.playerTips();
			}
		}.runTaskTimer(this, 0L, 30000L);

		/*
		 * Registers all events
		 */
		Bukkit.getPluginManager().registerEvents(new onEntityDeath(), this);
		Bukkit.getPluginManager().registerEvents(new onEntityHit(), this);
		Bukkit.getPluginManager().registerEvents(new onFireworkUse(), this);
		Bukkit.getPluginManager().registerEvents(new onItemConsume(), this);
		Bukkit.getPluginManager().registerEvents(new onMineAmethyst(), this);
		Bukkit.getPluginManager().registerEvents(new onPlayerDeath(), this);
		Bukkit.getPluginManager().registerEvents(new onPlayerInteract(), this);
		Bukkit.getPluginManager().registerEvents(new onPlayerJoin(), this);
		Bukkit.getPluginManager().registerEvents(new onPlayerPlace(), this);
		Bukkit.getPluginManager().registerEvents(new onPlayerSit(), this);
		Bukkit.getPluginManager().registerEvents(new onSpawnerDrop(), this);
		Bukkit.getPluginManager().registerEvents(new onTeleport(), this);
		Bukkit.getPluginManager().registerEvents(new onXpPickup(), this);
		Bukkit.getPluginManager().registerEvents(new onZombieDeath(), this);

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
	}

	@Override
	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage("Disabling Ourstory...");

		// Save inventory, player state, anything the plugin is manipulating
	}
}
