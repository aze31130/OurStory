package ourstory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import ourstory.commands.Boss;
import ourstory.commands.Chall;
import ourstory.commands.Count;
import ourstory.commands.RankUp;
import ourstory.commands.Reset;
import ourstory.commands.Skin;
import ourstory.commands.Split;
import ourstory.commands.Test;
import ourstory.events.onBossDeath;
import ourstory.events.onBossHit;
import ourstory.events.onEntityDeath;
import ourstory.events.onEntityHit;
import ourstory.events.onFinalDamage;
import ourstory.events.onItemConsume;
import ourstory.events.onMineAmethyst;
import ourstory.events.onMineDeepslate;
import ourstory.events.onPlayerDeath;
import ourstory.events.onPlayerInteract;
import ourstory.events.onPlayerJoin;
import ourstory.events.onPlayerPlace;
import ourstory.events.onPlayerSit;
import ourstory.events.onPlayerTips;
import ourstory.events.onSpawnerDrop;
import ourstory.events.onTeleport;
import ourstory.events.onTridentHit;
import ourstory.events.onXpPickup;
import ourstory.events.onZombieDeath;
import ourstory.recipes.CraftingTable;
import ourstory.recipes.Furnace;
import ourstory.recipes.StoneCutter;

public class Main extends JavaPlugin {

	public static final String namespace = "ourstory";
	public static List<String> deathMessagesFr, deathMessagesEn, tipMessages;
	public static List<CustomSkin> skins = new ArrayList<>();

	public record CustomSkin(int id, String name, int price) {
	}

	@Override
	public void onEnable() {
		Bukkit.getConsoleSender().sendMessage("Loading Ourstory...");

		// Load custom messages
		deathMessagesFr = getConfig().getStringList("deathMessages.fr");
		deathMessagesEn = getConfig().getStringList("deathMessages.en");
		tipMessages = getConfig().getStringList("tipMessages");

		loadSkins();

		// Register task for running the periodic tip broadcast
		new BukkitRunnable() {
			@Override
			public void run() {
				onPlayerTips.playerTips();
			}
		}.runTaskTimer(this, 0L, 27000L);

		/*
		 * Registers all events
		 */
		Bukkit.getPluginManager().registerEvents(new onBossDeath(), this);
		Bukkit.getPluginManager().registerEvents(new onBossHit(), this);
		Bukkit.getPluginManager().registerEvents(new onEntityDeath(), this);
		Bukkit.getPluginManager().registerEvents(new onEntityHit(), this);
		Bukkit.getPluginManager().registerEvents(new onFinalDamage(), this);
		Bukkit.getPluginManager().registerEvents(new onItemConsume(), this);
		Bukkit.getPluginManager().registerEvents(new onMineAmethyst(), this);
		Bukkit.getPluginManager().registerEvents(new onMineDeepslate(), this);
		Bukkit.getPluginManager().registerEvents(new onPlayerDeath(), this);
		Bukkit.getPluginManager().registerEvents(new onPlayerInteract(), this);
		Bukkit.getPluginManager().registerEvents(new onPlayerJoin(), this);
		Bukkit.getPluginManager().registerEvents(new onPlayerPlace(), this);
		Bukkit.getPluginManager().registerEvents(new onPlayerSit(), this);
		Bukkit.getPluginManager().registerEvents(new onSpawnerDrop(), this);
		Bukkit.getPluginManager().registerEvents(new onTeleport(), this);
		Bukkit.getPluginManager().registerEvents(new onTridentHit(), this);
		Bukkit.getPluginManager().registerEvents(new onXpPickup(), this);
		Bukkit.getPluginManager().registerEvents(new onZombieDeath(), this);

		// Registers all commands
		var manager = this.getLifecycleManager();
		manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
			final Commands commands = event.registrar();

			commands.register("boss", "WIP", new Boss());
			commands.register("reset", "Resets the repair cost of your items", new Reset());
			commands.register("test", "Test command", new Test());
			commands.register("skin", "Change the skin of your current weapon", new Skin());
			commands.register("split", "Splits the enchants on your books", new Split());
			commands.register("rankup", "Increases your rank", new RankUp());
			commands.register("count", "Count items in your inventory", new Count());
			commands.register("chall", "Display the chosen advancement", new Chall());
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

	private void loadSkins() {
		for (Map<?, ?> map : getConfig().getMapList("skins")) {
			int id = (int) map.get("id");
			String name = (String) map.get("name");
			int price = (int) map.get("price");

			skins.add(new CustomSkin(id, name, price));
		}
	}

	@Override
	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage("Disabling Ourstory...");

		// Stop Exporter server

		// Stops everything the plugin instanciated
	}
}
