package ourstory;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import ourstory.commands.*;
import ourstory.events.*;
import ourstory.recipes.CraftingTable;
import ourstory.recipes.Furnace;
import ourstory.recipes.StoneCutter;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

public class Main extends JavaPlugin {

	public static final String namespace = "ourstory";
	public static final String prefix = "OurStory";
	public Permission permissions = null;
	public Economy economy = null;

	@Override
	public void onEnable() {
		Bukkit.getConsoleSender().sendMessage("Loading Ourstory...");

		// Instanciate Singleton (used for storing config data, tpa requests)
		// TODO
		this.permissions = getServer().getServicesManager().getRegistration(Permission.class).getProvider();
		this.economy = getServer().getServicesManager().getRegistration(Economy.class).getProvider();

		// Registers all events
		Bukkit.getPluginManager().registerEvents(new Player(), this);
		Bukkit.getPluginManager().registerEvents(new Entity(), this);

		// Registers all commands
		var manager = this.getLifecycleManager();
		manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
			final Commands commands = event.registrar();

			commands.register("boss", "some help description string", new Boss());
			commands.register("split", "some help description string", new Split());
			commands.register("rankup", "some help description string", new RankUp(this));

			// commands.register("shop", "some help description string", new Boss());

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
