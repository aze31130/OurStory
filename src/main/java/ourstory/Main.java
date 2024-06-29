package ourstory;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import ourstory.events.*;

public class Main extends JavaPlugin {

	public static final String prefix = "OurStory";
	public static final String version = "1.0";


	@Override
	public void onEnable() {
		Bukkit.getConsoleSender().sendMessage("Loading Ourstory...");

		// Registers all events
		Bukkit.getPluginManager().registerEvents(new Player(), this);
		Bukkit.getPluginManager().registerEvents(new Entity(), this);
		Bukkit.getPluginManager().registerEvents(new CommandEvent(), this);



		// this.getConfig().addDefault("messages.nopermission", "You do not have the permission to perform
		// this command");
		// this.getConfig().options().copyDefaults(true);
		// this.saveConfig();
		// CustomCraft();
	}

	@Override
	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage("Disabling Ourstory...");

		// Save inventory, player state, anything the plugin is manipulating
	}
}
