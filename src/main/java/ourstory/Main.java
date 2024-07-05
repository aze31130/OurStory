package ourstory;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.math.Position;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import ourstory.commands.*;
import ourstory.events.*;

public class Main extends JavaPlugin {

	public static final String prefix = "OurStory";
	public static final String version = "2.0";

	@Override
	public void onEnable() {
		Bukkit.getConsoleSender().sendMessage("Loading Ourstory...");

		// Instanciate Singleton (used for storing config data, tpa requests)
		// TODO

		// Registers all events
		Bukkit.getPluginManager().registerEvents(new Player(), this);
		Bukkit.getPluginManager().registerEvents(new Entity(), this);

		// Registers all commands
		var manager = this.getLifecycleManager();
		manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
			final Commands commands = event.registrar();

			commands.register("boss", "some help description string", new Boss());

			// commands.register("shop", "some help description string", new Boss());

		});

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
