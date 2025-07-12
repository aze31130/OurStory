package ourstory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.json.JSONObject;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import ourstory.bosses.Instance;
import ourstory.commands.*;
import ourstory.events.*;
import ourstory.recipes.*;
import ourstory.utils.FileUtils;
import ourstory.guilds.Guild;

public class Main extends JavaPlugin {
	public static final String namespace = "ourstory";
	public static File configDir = new File(System.getProperty("user.dir") + "/plugins/Ourstory");

	// public static List<Guild> guilds = FileUtils.loadGuilds("./guilds.json");
	public static JSONObject messages = FileUtils.loadJsonObject("messages.json");
	public static List<Guild> guilds = new ArrayList<>();
	public static Instance runningInstance;

	@Override
	public void onEnable() {
		Bukkit.getConsoleSender().sendMessage("Loading Ourstory...");

		// Register task for running the periodic tip broadcast
		new BukkitRunnable() {
			@Override
			public void run() {
				onPlayerTips.playerTips();
			}
		}.runTaskTimer(this, 0L, 400000L);

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
	}

	@Override
	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage("Disabling Ourstory...");

		// Stop Exporter server, saves guilds
	}
}
