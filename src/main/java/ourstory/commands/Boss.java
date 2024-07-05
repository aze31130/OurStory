package ourstory.commands;

import org.bukkit.Bukkit;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import ourstory.utils.Permissions;

public class Boss implements BasicCommand {
	@Override
	public void execute(CommandSourceStack sender, String[] args) {
		if (!Permissions.checkPermissions(sender.getSender(), "ourstory.boss"))
			return;

		// Bukkit.dispatchCommand(sender.getSender().getServer().getConsoleSender(), "function
		// ourstory:example_boss");
		Bukkit.dispatchCommand(sender.getSender().getServer().getConsoleSender(), "world world_nether");
	}
}
