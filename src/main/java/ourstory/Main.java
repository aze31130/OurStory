package ourstory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import ourstory.commands.*;
import ourstory.events.*;
import ourstory.recipes.*;

public class Main extends JavaPlugin {

	public static final String namespace = "ourstory";
	public static FileConfiguration config;
	public static List<String> deathMessagesFr, deathMessagesEn, tipMessages;
	public static List<CustomSkin> skins = new ArrayList<>();

	public record CustomSkin(int id, String name, int price) {
	}

	@Override
	public void onEnable() {
		Bukkit.getConsoleSender().sendMessage("Loading Ourstory...");

		Main.config = getConfig();

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
		}.runTaskTimer(this, 0L, 72000L);

		/*
		 * Registers all events
		 */
		Listener[] eventsToRegister = {
				new onArrowRain(), new onBossDeath(), new onBossHit(), new onDisplayItem(), new onDummyHit(), new onEntityDeath(),
				new onEntityHit(), new onFinalDamage(), new onHeadDrop(), new onItemConsume(), new onMineAmethyst(),
				new onMineDeepslate(), new onPhoenixDeath(), new onPlayerDeath(), new onPlayerInteract(), new onPlayerJoin(),
				new onPlayerPlace(), new onPlayerSit(), new onReachEquip(), new onSpawnerDrop(), new onTeleport(),
				new onTridentHit(), new onXpPickup(), new onZombieDeath(), new onVulnerabilitySeeker()
		};

		for (Listener event : eventsToRegister)
			Bukkit.getPluginManager().registerEvents(event, this);

		/*
		 * Registers all commands
		 */
		Map<String, BasicCommand> commandsToRegister = Map.of(
				"boss", new Boss(),
				"dummy", new Dummy(),
				"reset", new Reset(),
				"test", new Test(),
				"skin", new Skin(),
				"split", new Split(),
				"rankup", new RankUp(),
				"count", new Count(),
				"chall", new Chall(),
				"cast", new Cast());

		var manager = this.getLifecycleManager();
		manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
			final Commands commands = event.registrar();

			for (Entry<String, BasicCommand> command : commandsToRegister.entrySet())
				commands.register(command.getKey(), command.getValue());
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
